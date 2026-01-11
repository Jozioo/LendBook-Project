package ui;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.User;
import service.AuthenticationService;
import model.Book;


public class MainLayout {

    private final Stage stage;
    private final AuthenticationService authService;
    private final BorderPane root;

    public MainLayout(Stage stage, AuthenticationService authService) {
        this.stage = stage;
        this.authService = authService;
        this.root = new BorderPane();

        showLogin();
    }

    public Stage getStage() {
        return stage;
    }

    public BorderPane getRoot() {
        return root;
    }

    public void showLogin() {
        LoginView loginView = new LoginView(authService, this);
        root.setCenter(loginView.getView());
    }

    public void showHome(User user) {
        HomeView homeView = new HomeView(user, this);
        root.setCenter(homeView.getView());
    }

    public void showBooks(User user) {
        BookView bookView = new BookView(user, this);
        root.setCenter(bookView.getView());
    }
    public void showLoans(User user) {
        LoanView loanView = new LoanView(user, this);
        root.setCenter(loanView.getView());
    }
    public void showHistory(User user) {
        HistoryView historyView = new HistoryView(user, this);
        root.setCenter(historyView.getView());
    }
    public void showBookDetail(User user, Book book) {
        BookDetailView view = new BookDetailView(user, book, this);
        root.setCenter(view.getView());
    }
    public void showHistoryDetail(User user, Book book) {
        HistoryDetailView view = new HistoryDetailView(user, book, this);
        root.setCenter(view.getView());
    }


}
