package dbbg2.view.dialogs;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;

import java.io.IOException;

public class LoginDialog extends Dialog<Boolean> {

    public LoginDialog() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/UserLogInView.fxml"));

            getDialogPane().setContent(loader.load());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
