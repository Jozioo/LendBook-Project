package service;

import model.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryService {

    private static LibraryService instance;

    private List<Book> books;
    private List<Loan> loans;
    private FileStorageService storage;

    private LibraryService() {
        storage = new FileStorageService();
        books = storage.loadBooks();
        loans = storage.loadLoans();
    }

    public static LibraryService getInstance() {
        if (instance == null) {
            instance = new LibraryService();
        }
        return instance;
    }

    public List<Book> getBooks() {
        return books;
    }

    public Book getBookByTitle(String title) {
        for (Book b : books) {
            if (b.getTitle().equalsIgnoreCase(title)) {
                return b;
            }
        }
        return null;
    }

    public List<Loan> getHistoryByMember(String memberId) {
        List<Loan> result = new ArrayList<>();
        for (Loan l : loans) {
            if (l.getMemberId().equals(memberId) && l.isReturned()) {
                result.add(l);
            }
        }
        return result;
    }

    public List<Loan> getLoansByMember(String memberId) {
        List<Loan> result = new ArrayList<>();
        for (Loan l : loans) {
            if (l.getMemberId().equals(memberId) && !l.isReturned()) {
                result.add(l);
            }
        }
        return result;
    }

    public Loan borrowBook(String memberId, Book book) {
        if (memberId == null || book == null) return null;
        if (!book.isAvailable()) return null;

        Loan loan = new Loan(
                "L" + (loans.size() + 1),
                memberId,
                book.getTitle()
        );

        loans.add(loan);
        book.setAvailable(false);

        storage.saveLoans(loans);
        storage.saveBooks(books);

        return loan;
    }

    public void returnBook(String loanId) {
        for (Loan l : loans) {
            if (l.getLoanId().equals(loanId) && !l.isReturned()) {
                l.returnBook();

                for (Book b : books) {
                    if (b.getTitle().equals(l.getTitle())) {
                        b.setAvailable(true);
                        break;
                    }
                }

                storage.saveLoans(loans);
                storage.saveBooks(books);
                return;
            }
        }
    }
    public Book findBookByTitle(String title) {
        for (Book b : books) {
            if (b.getTitle().equalsIgnoreCase(title)) {
                return b;
            }
        }
        return null;
    }


    // ⭐ SIMPAN RATING
    public void saveBooks() {
        storage.saveBooks(books);
    }
}
