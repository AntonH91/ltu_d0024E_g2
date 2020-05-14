package dbbg2;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class cannot start by itself. It must be called from a class that does not extend javafx.application.Application
 */
public class ApplicationMain extends Application {
    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/User/UserOverview.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setTitle("Library DBB");
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
