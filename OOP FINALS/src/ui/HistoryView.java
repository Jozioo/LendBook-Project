package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Book;
import model.Loan;
import model.User;
import service.LibraryService;

import java.util.List;

public class HistoryView {

    private final User user;
    private final MainLayout layout;

    public HistoryView(User user, MainLayout layout) {
        this.user = user;
        this.layout = layout;
    }

    public BorderPane getView() {

        BorderPane root = new BorderPane();
        root.setPrefSize(1920, 1080);
        root.setStyle("-fx-background-color:#FFD9B3;");

        // ================= NAVBAR =================
        Label logo = nav("LendBook", true);
        Label books = nav("Books", false);
        Label loans = nav("Loans", false);
        Label history = nav("History", false);

        logo.setOnMouseClicked(e -> layout.showHome(user));
        logo.setStyle("-fx-cursor: hand;");

        books.setOnMouseClicked(e -> layout.showBooks(user));
        loans.setOnMouseClicked(e -> layout.showLoans(user));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(40, logo, books, loans, history, spacer);
        header.setPadding(new Insets(20, 40, 20, 40));
        header.setStyle("-fx-background-color:#FFEAD1;");
        root.setTop(header);

        // ================= CONTENT =================
        VBox content = new VBox(25);
        content.setPadding(new Insets(40));
        content.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("History");
        title.setFont(Font.font(26));

        VBox container = new VBox(30);
        container.setPadding(new Insets(30));
        container.setStyle("""
            -fx-background-color:#FFEAD1;
            -fx-background-radius:30;
        """);

        GridPane grid = new GridPane();
        grid.setHgap(40);
        grid.setVgap(30);

        LibraryService service = LibraryService.getInstance();
        List<Loan> historyData = service.getHistoryByMember(user.getId());

        int col = 0, row = 0;
        for (Loan loan : historyData) {

            // 🔥 AMBIL BOOK ASLI (BUKAN DUMMY)
            Book book = service.findBookByTitle(loan.getTitle());
            if (book == null) continue;

            grid.add(historyCard(book), col, row);

            col++;
            if (col == 2) {
                col = 0;
                row++;
            }
        }

        container.getChildren().add(grid);

        ScrollPane scroll = new ScrollPane(container);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent;");

        content.getChildren().addAll(title, scroll);
        root.setCenter(content);

        return root;
    }

    // ================= HISTORY CARD =================
    private HBox historyCard(Book book) {

        ImageView img = new ImageView(new Image("file:" + book.getImagePath()));
        img.setFitWidth(140);
        img.setPreserveRatio(true);

        Label title = new Label(book.getTitle());
        title.setFont(Font.font(16));
        title.setStyle("-fx-font-weight: bold;");

        Label author = new Label("by " + book.getAuthor());
        author.setTextFill(Color.GRAY);

        // ⭐ RATING DARI BOOK ASLI
        Label rating = new Label("⭐ " + book.getRating());
        rating.setTextFill(Color.web("#FACC15"));
        rating.setFont(Font.font(14));

        VBox info = new VBox(6, title, author, rating);
        info.setAlignment(Pos.CENTER_LEFT);

        HBox card = new HBox(20, img, info);
        card.setPadding(new Insets(20));
        card.setStyle("""
            -fx-background-color:#FFEAD1;
            -fx-background-radius:20;
        """);

        card.setOnMouseClicked(e ->
                layout.showHistoryDetail(user, book)
        );

        return card;
    }

    // ================= NAV ITEM =================
    private Label nav(String text, boolean logo) {
        Label l = new Label(text);
        l.setFont(Font.font(logo ? 28 : 18));
        if (logo) l.setTextFill(Color.web("#E11D48"));
        return l;
    }
}
