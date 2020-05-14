package dbbg2.view.user;

import dbbg2.controllers.user.UserController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class UserDetailController {
    public TextField txtUserId;
    public TextField txtPersonNr;
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtEmail;
    public TextField txtPhoneNr;
    public TextField txtStreetAddress;
    public TextField txtPostCode;
    public TextField txtPostArea;

    public Button btnCancelButton;
    public Button btnSaveButton;
    public Button btnReturn;

    public AnchorPane childPane;

    protected UserController userController;

    public UserDetailController() {

    }

    public void loadChildPane() {

    }


    public void handleCancelButtonClick(ActionEvent actionEvent) {
    }

    public void handleSaveButtonClick(ActionEvent actionEvent) {
    }

    public void handleReturnButtonClick(ActionEvent actionEvent) {
    }
}
