package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Book;
import model.User;
import service.LibraryService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookDetailView {

    private final User user;
    private final Book book;
    private final MainLayout layout;

    public BookDetailView(User user, Book book, MainLayout layout) {
        this.user = user;
        this.book = book;
        this.layout = layout;
    }

    public BorderPane getView() {

        BorderPane root = new BorderPane();
        root.setPrefSize(1920, 1080);
        root.setStyle("-fx-background-color:#FFD9B3;");

        // ================= TOP BAR =================
        Button backBtn = new Button("← Back");
        backBtn.setStyle("""
            -fx-background-color: transparent;
            -fx-font-size:16;
            -fx-font-weight:bold;
        """);
        backBtn.setOnAction(e -> layout.showBooks(user));

        HBox top = new HBox(backBtn);
        top.setPadding(new Insets(20, 40, 20, 40));
        root.setTop(top);

        // ================= LEFT (IMAGE + BUTTON) =================
        ImageView img = new ImageView(
                new Image("file:" + book.getImagePath())
        );
        img.setFitWidth(260);
        img.setPreserveRatio(true);

        Button loanBtn = new Button(
                book.isAvailable() ? "Loan Book" : "Already Loaned"
        );
        loanBtn.setDisable(!book.isAvailable());
        loanBtn.setPrefSize(260, 55);
        loanBtn.setStyle("""
            -fx-background-color:#FFEAD1;
            -fx-background-radius:20;
            -fx-font-size:18;
            -fx-font-weight:bold;
        """);

        loanBtn.setOnAction(e -> showLoanPopup());

        VBox left = new VBox(20, img, loanBtn);
        left.setAlignment(Pos.TOP_CENTER);

        // ================= RIGHT (TEXT) =================
        Label title = new Label(book.getTitle());
        title.setFont(Font.font(32));
        title.setStyle("-fx-font-weight:bold;");

        Label author = new Label("by " + book.getAuthor());
        author.setFont(Font.font(16));
        author.setStyle("-fx-text-fill:gray;");

        Label descTitle = new Label("Description");
        descTitle.setFont(Font.font(18));
        descTitle.setStyle("-fx-font-weight:bold;");

        Label desc = new Label(book.getDescription());
        desc.setWrapText(true);
        desc.setMaxWidth(520);

        VBox right = new VBox(16, title, author, descTitle, desc);
        right.setAlignment(Pos.TOP_LEFT);

        // ================= CONTENT =================
        HBox content = new HBox(80, left, right);
        content.setAlignment(Pos.TOP_LEFT);
        content.setPadding(new Insets(80));

        root.setCenter(content);
        return root;
    }

    // ================= POPUP =================
    private void showLoanPopup() {

        Stage popup = new Stage();
        popup.initOwner(layout.getStage());
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initStyle(StageStyle.UTILITY);
        popup.setResizable(false);

        LocalDate dueDate = LocalDate.now().plusDays(7);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        ImageView img = new ImageView(
                new Image("file:" + book.getImagePath())
        );
        img.setFitWidth(90);
        img.setPreserveRatio(true);

        Label title = new Label(
                "Loan \"" + book.getTitle() + "\" until:"
        );
        title.setFont(Font.font(16));
        title.setStyle("-fx-font-weight:bold;");

        Label dateLabel = new Label(dueDate.format(fmt));
        dateLabel.setPadding(new Insets(8, 16, 8, 16));
        dateLabel.setStyle("""
            -fx-border-color:#6B7280;
            -fx-border-radius:8;
            -fx-background-radius:8;
            -fx-background-color:white;
        """);

        Button ok = new Button("OK");
        ok.setPrefSize(100, 36);
        ok.setStyle("""
            -fx-background-color:#86EFAC;
            -fx-background-radius:12;
            -fx-font-weight:bold;
        """);

        Button cancel = new Button("Cancel");
        cancel.setPrefSize(100, 36);
        cancel.setStyle("""
            -fx-background-color:transparent;
            -fx-border-color:#991B1B;
            -fx-border-radius:12;
            -fx-text-fill:#991B1B;
            -fx-font-weight:bold;
        """);

        ok.setOnAction(e -> {
            LibraryService.getInstance()
                    .borrowBook(user.getId(), book);
            popup.close();
            layout.showLoans(user);
        });

        cancel.setOnAction(e -> popup.close());

        HBox buttons = new HBox(16, ok, cancel);
        buttons.setAlignment(Pos.CENTER);

        VBox root = new VBox(18, img, title, dateLabel, buttons);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(24));
        root.setStyle("""
            -fx-background-color:#FFEAD1;
            -fx-background-radius:20;
        """);

        popup.setScene(new Scene(root, 320, 280));
        popup.showAndWait();
    }
}
