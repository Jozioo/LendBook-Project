    package model;

    public class Book {

        private String title;
        private String author;
        private String imagePath;
        private String description;
        private int rating;
        private boolean available;

        public Book(String title, String author, String imagePath,
                    String description, int rating, boolean available) {
            this.title = title;
            this.author = author;
            this.imagePath = imagePath;
            this.description = description;
            this.rating = rating;
            this.available = available;
        }

        // ===== GETTER =====
        public String getTitle() { return title; }
        public String getAuthor() { return author; }
        public String getImagePath() { return imagePath; }
        public String getDescription() { return description; }
        public int getRating() { return rating; }
        public boolean isAvailable() { return available; }

        // ===== SETTER =====
        public void setAvailable(boolean available) {
            this.available = available;
        }

        // 🔥 INI YANG HILANG
        public void setRating(int rating) {
            if (rating < 1) rating = 1;
            if (rating > 5) rating = 5;
            this.rating = rating;
        }
    }
