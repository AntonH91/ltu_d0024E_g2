package dbbg2.view.user.details;

import dbbg2.controllers.user.UserController;
import dbbg2.data.users.Employee;
import dbbg2.data.users.User;
import dbbg2.data.users.UserManager;
import dbbg2.data.users.Visitor;
import dbbg2.view.user.exceptions.UnknownUserTypeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

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

    public PasswordField pwdNewPassword;
    public PasswordField pwdConfirmPassword;

    protected UserController userController;
    private ChildController childController;

    /**
     * Loads a child pane to the user view GUI.
     *
     * @param resourceUrl The resource URL to load
     */
    public void loadChildPane(String resourceUrl) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resourceUrl));
            childPane.getChildren().setAll((AnchorPane) loader.load());

            childController = loader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the user to database
     */
    public void saveUser() {


        if (this.hasValidInput()) {
            userController.amendSettings(txtFirstName.getText(),
                    txtLastName.getText(),
                    txtStreetAddress.getText(),
                    txtPostCode.getText(),
                    txtPostArea.getText(),
                    txtEmail.getText(),
                    txtPhoneNr.getText());

            userController.amendPersonNumber(txtPersonNr.getText());


            // Change the password only if there is a new one entered
            if (!pwdNewPassword.getText().isEmpty()) {
                userController.setPassword(pwdNewPassword.getText());
            }

            userController.saveChanges();

            this.refreshFields();
            new Alert(Alert.AlertType.INFORMATION, "User saved successfully!", ButtonType.OK).showAndWait();
        } else {
            new Alert(Alert.AlertType.WARNING, "Please fill in all mandatory fields!", ButtonType.OK).showAndWait();
            // TODO Tell user which fields are mandatory
        }


    }

    /**
     * Loads a user with the given User ID.
     *
     * @param userId The userID to load
     * @throws UnknownUserTypeException Thrown when the loaded user is not a known type to the UI component
     */
    public void loadUser(String userId) throws UnknownUserTypeException {

        User u = UserManager.getUser(userId);
        this.loadUser(u);

    }

    /**
     * Loads the user  type
     *
     * @param user The user to be loaded
     * @throws UnknownUserTypeException Thrown when the user is not a handled type of user by the detail controller
     */
    public void loadUser(User user) throws UnknownUserTypeException {
        if (user instanceof Employee) {
            loadChildPane("/Views/User/EmployeeDetail.fxml");


        } else if (user instanceof Visitor) {
            loadChildPane("/Views/User/VisitorView.fxml");
        } else {
            throw new UnknownUserTypeException("Usertype " + user.getClass().getName() + " is not a known kind of user.");
        }

        childController.initializeUserController(user);
        userController = childController.getDataController();
        refreshFields();
        childPane.getScene().getWindow().sizeToScene();
    }


    /**
     * Refreshes the fields in the main- and subforms.
     */
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
            txtPostArea.setText(u.getPostArea());

            // Refresh the child form
            childController.refreshInterface();
        }

    }

    /**
     * Checks the input in all the fields for validity, including in the childform
     *
     * @return True if all input fields are correctly filled in, false otherwise
     */
    private boolean hasValidInput() {

        TextField [] fieldsToValidate = {txtFirstName, txtLastName, txtEmail, txtPersonNr};

        boolean isValid = true;

        for (TextField tf : fieldsToValidate) {
            boolean fieldValid = !tf.getText().isEmpty();
            isValid = isValid && fieldValid;
            markTextFieldValidity(tf, fieldValid);

        }

        isValid = isValid && validatePassword();


        // TODO Add password for a new user
        if (isValid && childController != null) {
            isValid = childController.isInputValid();
        }
        return isValid;
    }

    /**
     * Validates the password input of the form.
     * @return True if the password form is valid, False otherwise
     */
    private boolean validatePassword() {
        boolean isValid = true;
        // New user - we should define a password as well.
        if (userController.getUser().getUserId().isEmpty()) {
            isValid = isValid && !pwdNewPassword.getText().isEmpty() && !pwdConfirmPassword.getText().isEmpty();
        }

        // If a password is defined, they must both match each other
        if (!pwdNewPassword.getText().isEmpty()) {
            isValid = isValid && pwdNewPassword.getText().equals(pwdConfirmPassword.getText());
        }

        return isValid;
    }

    /**
     * Sets the layout of the text field to indicate to a user that it is a mandatory field
     * @param textField The textfield to be changed
     * @param valid True if the input is valid, false otherwise
     */
    private void markTextFieldValidity(TextField textField, boolean valid) {
        //TODO Change the layout of the text field to indicate it's mandatory
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
