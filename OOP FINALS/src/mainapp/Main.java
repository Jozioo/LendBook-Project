package mainapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.AuthenticationService;
import ui.MainLayout;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        AuthenticationService authService = new AuthenticationService();

        MainLayout layout = new MainLayout(stage, authService);

        Scene scene = new Scene(layout.getRoot(), 1920, 1080);
        stage.setScene(scene);
        stage.setTitle("LendBook");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
