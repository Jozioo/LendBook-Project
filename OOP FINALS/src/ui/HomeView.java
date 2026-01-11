package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Book;
import model.User;
import service.LibraryService;

import java.util.Random;

public class HomeView {

    private final User user;
    private final MainLayout layout;

    public HomeView(User user, MainLayout layout) {
        this.user = user;
        this.layout = layout;
    }

    public BorderPane getView() {

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color:#FFD9B3;");
        root.setPrefSize(1920, 1080);

        // ================= NAVBAR =================
        Label logo = nav("LendBook", true);
        logo.setOnMouseClicked(e -> layout.showHome(user));
        logo.setStyle("-fx-cursor: hand;");

        Label books = nav("Books", false);
        Label loans = nav("Loans", false);
        Label history = nav("History", false);

        books.setOnMouseClicked(e -> layout.showBooks(user));
        loans.setOnMouseClicked(e -> layout.showLoans(user));
        history.setOnMouseClicked(e -> layout.showHistory(user));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(40, logo, books, loans, history, spacer);
        header.setPadding(new Insets(20, 40, 20, 40));
        header.setStyle("-fx-background-color:#FFEAD1;");
        root.setTop(header);

        // ================= CENTER =================
        VBox center = new VBox(35);
        center.setPadding(new Insets(60));
        center.setAlignment(Pos.TOP_CENTER);

        Label welcome = new Label("Welcome back, " + user.getUsername() + " 👋");
        welcome.setFont(Font.font(30));

        Label subtitle = new Label("Find your next favorite book today.");
        subtitle.setFont(Font.font(16));
        subtitle.setTextFill(Color.GRAY);

        VBox banner = new VBox(6, welcome, subtitle);
        banner.setAlignment(Pos.CENTER_LEFT);

        Label rec = new Label("Recommended Books");
        rec.setFont(Font.font(26));

        // ================= BOOK ROW (REAL DATA) =================
        LibraryService service = LibraryService.getInstance();

        HBox booksRow = new HBox(25);
        booksRow.setAlignment(Pos.CENTER);
        booksRow.setPadding(new Insets(25));
        booksRow.setStyle("""
            -fx-background-color:#FFEAD1;
            -fx-background-radius:25;
        """);

        int count = 0;
        for (Book book : service.getBooks()) {
            booksRow.getChildren().add(homeBookCard(book));
            count++;
            if (count == 5) break; // max 5 recommended
        }

        center.getChildren().addAll(
                banner,
                rec,
                booksRow
        );

        root.setCenter(center);

        // ================= RIGHT PANEL =================
        Label statsTitle = new Label("Your Stats");
        statsTitle.setFont(Font.font(18));
        int totalRead = service.getHistoryByMember(user.getId()).size();
        Label totalBooks = new Label("📚 Total Books: " + totalRead);
        Label borrowed = new Label("📖 Borrowed: " + service.getLoansByMember(user.getId()).size());
        Label dueSoon = new Label("⏰ Due Soon: 1");

        VBox statsBox = new VBox(10, statsTitle, totalBooks, borrowed, dueSoon);
        statsBox.setPadding(new Insets(20));
        statsBox.setStyle("""
            -fx-background-color:#FFEAD1;
            -fx-background-radius:20;
        """);

        Label quote = new Label(
                "\"A room without books is like a body without a soul.\""
        );
        quote.setWrapText(true);
        quote.setFont(Font.font(14));
        quote.setTextFill(Color.DARKGRAY);

        VBox sidePanel = new VBox(30, statsBox, quote);
        sidePanel.setPadding(new Insets(60, 40, 0, 0));
        sidePanel.setPrefWidth(300);

        root.setRight(sidePanel);

        return root;
    }

    // ================= BOOK CARD (CLICKABLE) =================
    private VBox homeBookCard(Book book) {

        ImageView iv = new ImageView(new Image("file:" + book.getImagePath()));
        iv.setFitWidth(150);
        iv.setFitHeight(220);
        iv.setPreserveRatio(true);

        // 🔥 BADGE RANDOM
        Label badge = new Label(randomBadge());
        badge.setStyle("""
            -fx-background-color:#F43F5E;
            -fx-text-fill:white;
            -fx-font-size:10;
            -fx-padding:3 8;
            -fx-background-radius:10;
        """);

        Label ratingLbl = new Label("⭐ " + book.getRating());
        ratingLbl.setFont(Font.font(14));

        Label title = new Label(book.getTitle());
        title.setWrapText(true);
        title.setMaxWidth(150);
        title.setMinHeight(40);
        title.setFont(Font.font(12));
        title.setStyle("-fx-font-weight:bold;");

        VBox box = new VBox(6, badge, iv, ratingLbl, title);
        box.setAlignment(Pos.CENTER);
        box.setCursor(Cursor.HAND);

        box.setOnMouseClicked(e ->
                layout.showBookDetail(user, book)
        );

        return box;
    }

    // ================= RANDOM BADGE =================
    private String randomBadge() {
        String[] badges = {
                "POPULAR",
                "BEST SELLER",
                "NEW",
                "TRENDING"
        };
        return badges[new Random().nextInt(badges.length)];
    }

    // ================= NAV ITEM =================
    private Label nav(String text, boolean logo) {
        Label l = new Label(text);
        l.setFont(Font.font(logo ? 28 : 18));
        if (logo) l.setTextFill(Color.web("#E11D48"));
        return l;
    }
}
