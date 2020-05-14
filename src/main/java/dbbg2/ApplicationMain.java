package dbbg2;


import dbbg2.view.user.details.UserDetailController;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/User/UserDetail.fxml"));


        Parent root = loader.load();

        UserDetailController udc = loader.getController();
        udc.loadUser("aein4027");
        //udc.loadUser(new Visitor());

        Scene scene = new Scene(root);

        primaryStage.setTitle("Library DBB");
        primaryStage.setScene(scene);


        primaryStage.show();
    }
}
