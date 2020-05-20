package dbbg2.view.dialogs;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;

import java.io.IOException;

public class LoginDialog extends Dialog<Boolean> {

    public LoginDialog() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/User/UserLogInView.fxml"));

            getDialogPane().setContent(loader.load());
            getDialogPane().getScene().getWindow().setOnCloseRequest(event -> getDialogPane().getScene().getWindow().hide());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
