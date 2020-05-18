package dbbg2.view.item.Edit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EditItemMain extends Application {

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/ItemEdit.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setTitle("Library DBB");
        primaryStage.setScene(scene);

        primaryStage.show();
    }

}

