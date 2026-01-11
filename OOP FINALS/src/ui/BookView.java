package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Book;
import model.User;
import service.LibraryService;

public class BookView {

    private final User user;
    private final MainLayout layout;

    public BookView(User user, MainLayout layout) {
        this.user = user;
        this.layout = layout;
    }

    public BorderPane getView() {

        // ================= ROOT =================
        BorderPane root = new BorderPane();
        root.setPrefSize(1920, 1080);
        root.setStyle("-fx-background-color:#FFD9B3;");

        // ================= NAVBAR =================
        Label logo = nav("LendBook", true);
        Label books = nav("Books", false);
        Label loans = nav("Loans", false);
        Label history = nav("History", false);

        logo.setOnMouseClicked(e -> layout.showHome(user));
        loans.setOnMouseClicked(e -> layout.showLoans(user));
        history.setOnMouseClicked(e -> layout.showHistory(user));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(40, logo, books, loans, history, spacer);
        header.setPadding(new Insets(20, 40, 20, 40));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color:#FFEAD1;");
        root.setTop(header);

        // ================= CONTENT =================
        VBox content = new VBox(25);
        content.setPadding(new Insets(40));
        content.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("All Books");
        title.setFont(Font.font("Arial", 26));

        // ================= GRID =================
        GridPane grid = new GridPane();
        grid.setHgap(40);
        grid.setVgap(40);
        grid.setAlignment(Pos.TOP_CENTER);

        LibraryService service = LibraryService.getInstance();

        int col = 0;
        int row = 0;

        for (Book book : service.getBooks()) {

            VBox card = bookCard(book);
            grid.add(card, col, row);

            col++;
            if (col == 4) {
                col = 0;
                row++;
            }
        }

        // ================= CONTAINER =================
        VBox container = new VBox(grid);
        container.setPadding(new Insets(40));
        container.setMaxWidth(Double.MAX_VALUE);
        container.setStyle("""
            -fx-background-color: #FFEAD1;
            -fx-background-radius: 30;
        """);

        // ================= SCROLL =================
        ScrollPane scroll = new ScrollPane(container);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);
        scroll.setStyle("""
            -fx-background-color: transparent;
            -fx-border-color: transparent;
        """);

        VBox.setVgrow(scroll, Priority.ALWAYS);

        content.getChildren().addAll(title, scroll);
        root.setCenter(content);

        return root;
    }

    // ================= BOOK CARD =================
    private VBox bookCard(Book book) {

        ImageView img = new ImageView(new Image("file:" + book.getImagePath()));
        img.setFitWidth(260);
        img.setFitHeight(170);
        img.setPreserveRatio(true);

        Label title = new Label(book.getTitle());
        title.setFont(Font.font("Arial", 15));
        title.setStyle("-fx-font-weight: bold;");

        Label author = new Label(book.getAuthor());
        author.setFont(Font.font(12));
        author.setTextFill(Color.GRAY);

        Label rating = new Label("⭐ " + book.getRating());
        rating.setFont(Font.font(12));

        VBox box = new VBox(8, img, title, author, rating);
        box.setAlignment(Pos.TOP_LEFT);
        box.setPrefWidth(260);
        box.setCursor(Cursor.HAND);

        box.setOnMouseClicked(e ->
                layout.showBookDetail(user, book)
        );

        return box;
    }

    // ================= NAV ITEM =================
    private Label nav(String text, boolean logo) {
        Label l = new Label(text);
        l.setFont(Font.font("Arial", logo ? 28 : 18));
        if (logo) l.setTextFill(Color.web("#E11D48"));
        return l;
    }
}
