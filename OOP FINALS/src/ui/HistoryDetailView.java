package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Book;
import model.User;
import service.LibraryService;


public class HistoryDetailView {

    private final User user;
    private final Book book;
    private final MainLayout layout;

    public HistoryDetailView(User user, Book book, MainLayout layout) {
        this.user = user;
        this.book = book;
        this.layout = layout;
    }

    public BorderPane getView() {

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color:#FFD9B3;");
        root.setPrefSize(1920, 1080);

        // ===== NAVBAR =====
        Label logo = nav("LendBook", true);
        Label back = nav("← Back", false);
        back.setOnMouseClicked(e -> layout.showHistory(user));

        HBox header = new HBox(40, logo, back);
        header.setPadding(new Insets(20, 40, 20, 40));
        header.setStyle("-fx-background-color:#FFEAD1;");
        root.setTop(header);

        // ===== CONTENT =====
        HBox content = new HBox(80);
        content.setPadding(new Insets(60));
        content.setAlignment(Pos.TOP_LEFT);

        // ===== LEFT (IMAGE + STARS) =====
        ImageView img = new ImageView(new Image("file:images/book1.png"));
        img.setFitWidth(260);
        img.setPreserveRatio(true);

        HBox stars = createRatingStars();

        VBox left = new VBox(20, img, stars);
        left.setAlignment(Pos.TOP_CENTER);

        // ===== RIGHT (INFO) =====
        Label title = new Label(book.getTitle());
        title.setFont(Font.font(28));
        title.setStyle("-fx-font-weight: bold;");

        Label author = new Label("by " + book.getAuthor());
        author.setFont(Font.font(16));
        author.setTextFill(Color.GRAY);

        Label descTitle = new Label("Description");
        descTitle.setFont(Font.font(18));
        descTitle.setStyle("-fx-font-weight: bold;");

        Label desc = new Label(book.getDescription());
        desc.setWrapText(true);
        desc.setMaxWidth(600);

        VBox right = new VBox(20, title, author, descTitle, desc);
        right.setAlignment(Pos.TOP_LEFT);

        content.getChildren().addAll(left, right);
        root.setCenter(content);

        return root;
    }

    // ⭐⭐⭐⭐⭐ CLICKABLE
    private HBox createRatingStars() {
        HBox box = new HBox(6);
        box.setAlignment(Pos.CENTER_LEFT);

        Label[] stars = new Label[5];

        for (int i = 0; i < 5; i++) {
            Label star = new Label("★");
            star.setFont(Font.font(28));
            int ratingValue = i + 1;

            star.setOnMouseClicked(e -> {
                book.setRating(ratingValue);
                LibraryService.getInstance().saveBooks();
                updateStars(stars);
            });

            stars[i] = star;
            box.getChildren().add(star);
        }

        updateStars(stars);
        return box;
    }

    private void updateStars(Label[] stars) {
        for (int i = 0; i < stars.length; i++) {
            stars[i].setTextFill(
                    i < book.getRating()
                            ? Color.web("#FACC15")
                            : Color.LIGHTGRAY
            );
        }
    }

    private Label nav(String text, boolean logo) {
        Label l = new Label(text);
        l.setFont(Font.font(logo ? 28 : 18));
        if (logo) l.setTextFill(Color.web("#E11D48"));
        return l;
    }
}
