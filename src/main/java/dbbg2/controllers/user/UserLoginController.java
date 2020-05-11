package dbbg2.controllers.user;

import dbbg2.utils.AuthenticationManager;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.persistence.NoResultException;

public class UserLoginController {
    public TextField txtUserName;
    public PasswordField pwdPassword;

    public void handleLoginClick(ActionEvent actionEvent) {
        try {
            AuthenticationManager.logIn(txtUserName.getText(), pwdPassword.getText());
        } catch (NoResultException e) {
            System.out.println("Invalid username/password combination.");
        } catch (IllegalStateException e) {
            System.out.println("Someone is already logged in!");
        }
    }

    public void handleCancelClick(ActionEvent actionEvent) {
    }
}
