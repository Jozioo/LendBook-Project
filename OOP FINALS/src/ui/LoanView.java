package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Loan;
import model.User;
import service.LibraryService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LoanView {

    private final User user;
    private final MainLayout layout;

    public LoanView(User user, MainLayout layout) {
        this.user = user;
        this.layout = layout;
    }

    public BorderPane getView() {

        BorderPane root = new BorderPane();
        root.setPrefSize(1920, 1080);
        root.setStyle("-fx-background-color:#FFD9B3;");

        // ===== NAVBAR =====
        Label logo = nav("LendBook", true);
        Label books = nav("Books", false);
        Label loans = nav("Loans", false);
        Label history = nav("History", false);

        logo.setOnMouseClicked(e -> layout.showHome(user));
        books.setOnMouseClicked(e -> layout.showBooks(user));
        history.setOnMouseClicked(e -> layout.showHistory(user));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(40, logo, books, loans, history, spacer);
        header.setPadding(new Insets(20, 40, 20, 40));
        header.setStyle("-fx-background-color:#FFEAD1;");
        root.setTop(header);

        // ===== CONTENT =====
        VBox content = new VBox(25);
        content.setPadding(new Insets(40));
        content.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Loans");
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
        List<Loan> loansData = service.getLoansByMember(user.getId());

        int col = 0, row = 0;
        for (Loan loan : loansData) {
            if (loan.isReturned()) continue;

            grid.add(loanCard(loan), col, row);

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

    // ===== LOAN CARD =====
    private HBox loanCard(Loan loan) {

        ImageView img = new ImageView(new Image("file:images/book1.png"));
        img.setFitWidth(140);
        img.setPreserveRatio(true);

        Label title = new Label(loan.getTitle());
        title.setFont(Font.font(16));
        title.setStyle("-fx-font-weight:bold;");

        Label author = new Label("Author"); // optional (bisa di-extend nanti)
        author.setTextFill(Color.GRAY);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMMM yyyy");

        Label loanDate = new Label("Borrowed: " + loan.getLoanDate().format(fmt));
        Label dueDate = new Label("Due: " + loan.getReturnDate().format(fmt));

        VBox info = new VBox(6, title, author, loanDate, dueDate);

        boolean overdue = loan.getReturnDate().isBefore(LocalDate.now());

        Label overdueLabel = new Label("*overdue");
        overdueLabel.setTextFill(Color.RED);
        overdueLabel.setFont(Font.font(12));

        Button btn = new Button("Return");
        btn.setPrefSize(100, 36);
        btn.setStyle("""
            -fx-background-color:#FAD4AF;
            -fx-background-radius:12;
            -fx-font-weight:bold;
        """);

        btn.setOnAction(e -> {
            LibraryService.getInstance().returnBook(loan.getLoanId());
            layout.showLoans(user);
        });

        VBox action = new VBox(6);
        action.setAlignment(Pos.BOTTOM_RIGHT);
        if (overdue) action.getChildren().add(overdueLabel);
        action.getChildren().add(btn);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        VBox right = new VBox(spacer, action);

        HBox card = new HBox(30,
                new HBox(20, img, info),
                right
        );

        card.setPadding(new Insets(20));
        card.setStyle("""
            -fx-background-color:#FFEAD1;
            -fx-background-radius:20;
        """);

        HBox.setHgrow(right, Priority.ALWAYS);

        return card;
    }

    private Label nav(String text, boolean logo) {
        Label l = new Label(text);
        l.setFont(Font.font(logo ? 28 : 18));
        if (logo) l.setTextFill(Color.web("#E11D48"));
        return l;
    }
}
