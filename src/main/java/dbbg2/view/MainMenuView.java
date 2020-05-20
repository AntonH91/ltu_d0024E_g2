package dbbg2.view;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuView implements Initializable {

    public Label lblLoggedInAs;
    public Button btnLoginLogout;
    public Button btnRegisterNewAccount;

    public AnchorPane achUsersPane;
    public AnchorPane achInventoryPane;
    public AnchorPane achLoanPane;

    public void handleLoginButtonClick(ActionEvent actionEvent) {
        // TODO Bring up password prompt to log in a new user
    }

    public void handleRegisterButtonClick(ActionEvent actionEvent) {
        // TODO Bring up window to allow user to register themselves as a Visitor
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Add loader code for various sub-menus under the main menu
    }

    public void updateAuthenticatedAccess() {
        // TODO Add code that only permits access to the menu items the logged-in user should be able to access.
    }


    private void loadSubMenus() {

    }


}
