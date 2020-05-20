package dbbg2.view.controllers.user;

import dbbg2.utils.AuthenticationManager;
import dbbg2.utils.exceptions.LoginFailureException;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UserLoginController {
    public TextField txtUserName;
    public PasswordField pwdPassword;
    public Button btnLogin;
    public Button btnCancel;

    public void handleLoginClick(ActionEvent actionEvent) {
        attemptLogin(txtUserName.getText(), pwdPassword.getText());
    }

    private void attemptLogin(String userName, String password) {
        try {

            AuthenticationManager.getAuthManager().logIn(userName, password);
            txtUserName.getScene().getWindow().hide();
        } catch (LoginFailureException e) {
            Logger.getLogger("").log(Level.INFO, "Failed login attempt for user: " + userName);
            showAlert("Invalid username / password combination.");
        } catch (IllegalStateException e) {
            Logger.getLogger("").log(Level.WARNING, "Login attempted when a user was already logged in.", e);
            showAlert("Cannot login when a user is already logged in.");
        }
    }

    public void handleCancelClick(ActionEvent actionEvent) {
        txtUserName.getScene().getWindow().hide();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.showAndWait();
        pwdPassword.requestFocus();
    }
}
