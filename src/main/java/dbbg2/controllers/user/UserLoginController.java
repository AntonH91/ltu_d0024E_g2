package dbbg2.controllers.user;

import dbbg2.utils.AuthenticationManager;
import dbbg2.utils.exceptions.LoginFailureException;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class UserLoginController {
    public TextField txtUserName;
    public PasswordField pwdPassword;

    public void handleLoginClick(ActionEvent actionEvent) {
        try {
            AuthenticationManager.logIn(txtUserName.getText(), pwdPassword.getText());
        } catch (LoginFailureException e) {

        } catch (IllegalStateException e) {
            System.out.println("Someone is already logged in!");
        }
    }

    public void handleCancelClick(ActionEvent actionEvent) {
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.showAndWait();
        pwdPassword.requestFocus();
    }
}
