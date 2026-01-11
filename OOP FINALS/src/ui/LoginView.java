package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.User;
import service.AuthenticationService;

public class LoginView {

    private final AuthenticationService authService;
    private final MainLayout layout;

    public LoginView(AuthenticationService authService, MainLayout layout) {
        this.authService = authService;
        this.layout = layout;
    }

    public Pane getView() {

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #FFD9B3;");
        root.setPrefSize(1920, 1080);

        // HEADER
        Label logo = new Label("LendBook");
        logo.setFont(Font.font(32));
        logo.setTextFill(Color.web("#E11D48"));

        HBox header = new HBox(logo);
        header.setPadding(new Insets(20, 40, 20, 40));
        header.setStyle("-fx-background-color: #FFEAD1;");
        root.setTop(header);

        // FORM
        VBox form = new VBox(20);
        form.setPadding(new Insets(120, 100, 0, 150));
        form.setAlignment(Pos.TOP_LEFT);

        TextField userField = new TextField();
        userField.setPromptText("Enter your username");
        userField.setPrefSize(400, 45);

        PasswordField passField = new PasswordField();
        passField.setPromptText("Enter your password");
        passField.setPrefSize(400, 45);

        Label msg = new Label();
        msg.setTextFill(Color.RED);

        Button login = new Button("Log in");
        login.setPrefSize(400, 50);
        login.setStyle("-fx-background-color:black;-fx-text-fill:white;-fx-background-radius:25;");

        login.setOnAction(e -> {
            User user = authService.login(userField.getText(), passField.getText());
            if (user != null) {
                layout.showHome(user);
            } else {
                msg.setText("Username atau password salah");
            }
        });

        form.getChildren().addAll(
                new Label("Log in"),
                userField,
                passField,
                login,
                msg
        );

        root.setLeft(form);

        // IMAGE
        ImageView img = new ImageView(new Image("file:images/books.png"));
        img.setFitWidth(500);
        img.setPreserveRatio(true);

        StackPane imgPane = new StackPane(img);
        imgPane.setPadding(new Insets(100, 200, 0, 0));
        root.setCenter(imgPane);

        return root;
    }
}
