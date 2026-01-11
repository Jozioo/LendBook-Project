package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Catalog {

    private final List<Book> books = new ArrayList<>();

    // ================= ADD =================
    public void add(Book book) {
        if (book != null) {
            books.add(book);
        }
    }

    // ================= REMOVE BY TITLE =================
    public boolean removeByTitle(String title) {
        if (title == null) return false;
        return books.removeIf(b ->
                b.getTitle() != null &&
                        b.getTitle().equalsIgnoreCase(title)
        );
    }

    // ================= GET ALL =================
    public List<Book> getAll() {
        return Collections.unmodifiableList(books);
    }

    // ================= FIND BY TITLE =================
    public Book findByTitle(String title) {
        if (title == null) return null;

        for (Book b : books) {
            if (b.getTitle() != null &&
                    b.getTitle().equalsIgnoreCase(title)) {
                return b;
            }
        }
        return null;
    }

    // ================= SEARCH BY TITLE =================
    public List<Book> searchByTitle(String keyword) {
        List<Book> result = new ArrayList<>();
        if (keyword == null) return result;

        String k = keyword.toLowerCase();
        for (Book b : books) {
            if (b.getTitle() != null &&
                    b.getTitle().toLowerCase().contains(k)) {
                result.add(b);
            }
        }
        return result;
    }

    // ================= SEARCH BY AUTHOR =================
    public List<Book> searchByAuthor(String keyword) {
        List<Book> result = new ArrayList<>();
        if (keyword == null) return result;

        String k = keyword.toLowerCase();
        for (Book b : books) {
            if (b.getAuthor() != null &&
                    b.getAuthor().toLowerCase().contains(k)) {
                result.add(b);
            }
        }
        return result;
    }
}
