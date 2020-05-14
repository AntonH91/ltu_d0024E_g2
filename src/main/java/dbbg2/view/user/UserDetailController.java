package dbbg2.view.user;

import dbbg2.controllers.user.UserController;
import dbbg2.data.users.Employee;
import dbbg2.data.users.User;
import dbbg2.data.users.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserDetailController implements Initializable {
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

    private ChildController childController;

    protected UserController userController;


    public void loadChildPane(String resourceUrl) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resourceUrl));
            childPane.getChildren().setAll((AnchorPane) loader.load());

            childController = loader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveUser() {
        userController.amendSettings(txtFirstName.getText(),
                txtLastName.getText(),
                txtStreetAddress.getText(),
                txtPostCode.getText(),
                txtPostArea.getText(),
                txtEmail.getText(),
                txtPhoneNr.getText());

        userController.amendPersonNumber(txtPersonNr.getText());

        userController.saveChanges();

    }

    /**
     * Loads a user with the given User ID.
     *
     * @param userId The userID to load
     */
    public void loadUser(String userId) {

        User u = UserManager.getUser(userId);
        this.loadUser(u);

    }

    public void loadUser(User u) {
        if (u instanceof Employee) {
            loadChildPane("/Views/User/EmployeeDetail.fxml");
            childController.initializeUserController(u);
            userController = childController.getDataController();

        }

        refreshFields();
    }


    public void refreshFields() {
        User u = userController.getUser();
        if (u != null) {
            txtUserId.setText(u.getUserId());
            txtPersonNr.setText(u.getPersonNr());

            txtFirstName.setText((u.getFirstName()));
            txtLastName.setText(u.getLastName());
            txtEmail.setText(u.getEmail());
            txtPhoneNr.setText(u.getPhoneNr());
            txtStreetAddress.setText(u.getStreetAddress());
            txtPostCode.setText(u.getPostCode());
            txtPostArea.setText(u.getPostCode());

            // Refresh the child form
            childController.refreshInterface();
        }

    }

    public void handleCancelButtonClick(ActionEvent actionEvent) {
        refreshFields();
    }

    public void handleSaveButtonClick(ActionEvent actionEvent) {
        childController.updateUserData();
        saveUser();
    }

    public void handleReturnButtonClick(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


}
