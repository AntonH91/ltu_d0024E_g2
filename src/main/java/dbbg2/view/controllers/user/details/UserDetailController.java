package dbbg2.view.controllers.user.details;

import dbbg2.controllers.user.UserController;
import dbbg2.data.users.Employee;
import dbbg2.data.users.User;
import dbbg2.data.users.UserManager;
import dbbg2.data.users.Visitor;
import dbbg2.view.controllers.user.exceptions.UnknownUserTypeException;
import dbbg2.view.utils.GenericStyler;
import dbbg2.view.utils.nested.ChildController;
import dbbg2.view.utils.nested.ParentController;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDetailController extends ChildController implements Initializable, ParentController {
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
    public Label lblInputError;
    public ButtonBar bbrTopBar;
    public Button btnDeleteUser;

    protected UserController userController;
    private UserChildController childController;

    private TextField[] fieldsToValidate;

    /**
     * Loads a child pane to the user view GUI.
     *
     * @param resourceUrl The resource URL to load
     */
    public void loadChildPane(String resourceUrl) {
        try {

            childController = (UserChildController) GenericStyler.loadSinglePane(childPane, resourceUrl);

            childController.setParentController(this);
        } catch (IOException e) {
            Logger.getLogger("").log(Level.SEVERE, "IOException triggered when loading Child Pane", e);
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
        resizeSelf();

        handleValidationChanges();
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

    @Override
    public void notifyRequestReturn(ChildController theChild) {
        // Do nothing - we will not return from the childform
    }

    @Override
    public void notifyResizeRequest(ChildController theChild) {
        resizeSelf();
    }

    @Override
    public void notifyUpdate(ChildController theChild) {
        // The child-form has changed - re-run validation
        handleValidationChanges();
    }

    /**
     * If there is a parent controller then the return button should be made visible.
     *
     * @param parentController The parent controller
     */
    @Override
    public void setParentController(ParentController parentController) {
        super.setParentController(parentController);

        boolean makeVisible = parentController != null;
        btnReturn.setVisible(makeVisible);
        bbrTopBar.setVisible(makeVisible);
    }

    /**
     * Called whenever a text field changes, so that input validation can be re-run.
     */
    public void handleValidationChanges() {
        boolean validInput = hasValidInput();

        // Lock the save button if the input is not valid
        btnSaveButton.setDisable(!validInput);


        if (validInput) {
            setErrorLabel(null);
        } else {
            StringBuilder sb = new StringBuilder();
            PasswordStatus pst = validatePassword();

            sb.append("Not all mandatory fields are filled in. ");

            switch (pst) {
                case PASSWORD_MISMATCH:
                    sb.append(" Passwords must match each other.");
                    break;
                case PASSWORD_REQUIRED:
                    sb.append(" Password must be set for a new user.");
                    break;
            }

            sb.trimToSize();


            setErrorLabel(sb.toString());
        }


    }


    /**
     * Sets the text of the error label in the bottom row of the display
     *
     * @param message The message to show, or null to hide the label.
     */
    private void setErrorLabel(String message) {
        lblInputError.setText(message);
        lblInputError.setVisible(message != null);
        lblInputError.autosize();
    }

    /**
     * Checks the input in all the fields for validity, including in the childform
     *
     * @return True if all input fields are correctly filled in, false otherwise
     */
    private boolean hasValidInput() {


        boolean isValid = true;

        for (TextField tf : fieldsToValidate) {
            boolean fieldValid = !tf.getText().isEmpty();
            isValid = isValid && fieldValid;
            GenericStyler.markValidity(tf, fieldValid);

        }

        isValid = isValid && validatePassword() == PasswordStatus.OK;


        if (childController != null) {
            isValid = childController.isInputValid() && isValid;
        }
        return isValid;
    }

    /**
     * Validates the password input of the form.
     *
     * @return True if the password form is valid, False otherwise
     */
    private PasswordStatus validatePassword() {

        PasswordStatus pst = PasswordStatus.OK;

        // New user - we should define a password as well.

        if (userController.getUser() != null && userController.getUser().getUserId().isEmpty()) {
            if (pwdNewPassword.getText().isEmpty() || pwdConfirmPassword.getText().isEmpty()) {
                pst = PasswordStatus.PASSWORD_REQUIRED;
            }
        }

        // If a password is defined, they must both match each other
        if (!pwdNewPassword.getText().isEmpty()) {
            if (!pwdNewPassword.getText().equals(pwdConfirmPassword.getText())) {
                pst = PasswordStatus.PASSWORD_MISMATCH;
            }
        }

        GenericStyler.markValidity(pwdConfirmPassword, pst == PasswordStatus.OK);
        GenericStyler.markValidity(pwdNewPassword, pst == PasswordStatus.OK);

        return pst;
    }

    /**
     * Cancels the edit and returns the values to what is stored on the User object
     *
     * @param actionEvent the triggering event
     */
    public void handleCancelButtonClick(ActionEvent actionEvent) {
        refreshFields();
    }

    public void handleSaveButtonClick(ActionEvent actionEvent) {
        childController.updateUserData();
        saveUser();
    }

    public void handleReturnButtonClick(ActionEvent actionEvent) {
        this.triggerReturnRequest();
    }

    /**
     * Called when the delete button is pressed.
     */
    public void handleDeleteButtonClick(ActionEvent actionEvent) {
        // TODO Make this button only show for Employees
        // TODO Make managers the only Employees that can delete other Employees
        Optional<ButtonType> o = new Alert(Alert.AlertType.CONFIRMATION, "Delete the current user?", ButtonType.YES, ButtonType.NO).showAndWait();

        if (o.isPresent() && o.get() == ButtonType.YES) {

            try {
                userController.deleteUser();
                this.triggerReturnRequest();
                new Alert(Alert.AlertType.INFORMATION, "Deletion successful!");
            } catch (IllegalArgumentException e) {
                new Alert(Alert.AlertType.INFORMATION, "It's not possible to delete this user. They may be involved in loans or other items.");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fieldsToValidate = new TextField[]{txtPersonNr, txtFirstName, txtLastName, txtEmail};

        bindListeners();

    }

    /**
     * Binds listeners to fields that are subject to dynamic validation
     */
    private void bindListeners() {

        // Define the new change listener for the text fields
        ChangeListener<String> txtChangedListener = (observable, oldValue, newValue) -> handleValidationChanges();

        // Add the change listener to the default monitored fields
        for (TextField tf : fieldsToValidate) {
            tf.textProperty().addListener(txtChangedListener);
        }

        // Add the password change listener
        pwdConfirmPassword.textProperty().addListener(txtChangedListener);
        pwdNewPassword.textProperty().addListener(txtChangedListener);

    }


    public void resizeSelf() {
        childPane.autosize();
        childPane.getScene().getWindow().sizeToScene();
        triggerResizeRequest();
    }

    /**
     * Enum that defines how password validation should behave
     */
    public enum PasswordStatus {
        OK,
        PASSWORD_REQUIRED,
        PASSWORD_MISMATCH
    }


}
