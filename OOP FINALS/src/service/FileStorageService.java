package service;

import model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class FileStorageService {

    private static final String DATA_DIR = "data";
    private static final String BOOK_FILE = DATA_DIR + "/books.csv";
    private static final String LOAN_FILE = DATA_DIR + "/loans.csv";

    public FileStorageService() {
        new File(DATA_DIR).mkdirs();
        createIfNotExists(BOOK_FILE, "title,author,imagePath,description,rating,available");
        createIfNotExists(LOAN_FILE, "loanId,memberId,title,loanDate,returnDate,returned");
    }

    private void createIfNotExists(String path, String header) {
        File f = new File(path);
        if (!f.exists()) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
                pw.println(header);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // ================= BOOK =================
    public List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();

        try (Scanner sc = new Scanner(new File(BOOK_FILE))) {
            sc.nextLine(); // skip header
            while (sc.hasNextLine()) {
                String[] d = sc.nextLine().split(",");

                books.add(new Book(
                        d[0],                       // title
                        d[1],                       // author
                        d[2],                       // imagePath
                        d[3],                       // description
                        Integer.parseInt(d[4]),     // rating
                        Boolean.parseBoolean(d[5])  // available
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    public void saveBooks(List<Book> books) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(BOOK_FILE))) {
            pw.println("title,author,imagePath,description,rating,available");
            for (Book b : books) {
                pw.println(
                        b.getTitle() + "," +
                                b.getAuthor() + "," +
                                b.getImagePath() + "," +
                                b.getDescription() + "," +
                                b.getRating() + "," +
                                b.isAvailable()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= LOAN =================
    public List<Loan> loadLoans() {
        List<Loan> loans = new ArrayList<>();

        try (Scanner sc = new Scanner(new File(LOAN_FILE))) {
            sc.nextLine(); // skip header
            while (sc.hasNextLine()) {
                String[] d = sc.nextLine().split(",");

                Loan l = new Loan(d[0], d[1], d[2]);
                l.setLoanDate(LocalDate.parse(d[3]));

                if (!d[4].isEmpty()) {
                    l.setReturnDate(LocalDate.parse(d[4]));
                }

                if (Boolean.parseBoolean(d[5])) {
                    l.markReturned();
                }

                loans.add(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return loans;
    }

    public void saveLoans(List<Loan> loans) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(LOAN_FILE))) {
            pw.println("loanId,memberId,title,loanDate,returnDate,returned");
            for (Loan l : loans) {
                pw.println(
                        l.getLoanId() + "," +
                                l.getMemberId() + "," +
                                l.getTitle() + "," +
                                l.getLoanDate() + "," +
                                (l.getReturnDate() == null ? "" : l.getReturnDate()) + "," +
                                l.isReturned()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
