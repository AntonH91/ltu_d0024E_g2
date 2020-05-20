package dbbg2.view.controllers.user;

import dbbg2.data.users.Employee;
import dbbg2.data.users.User;
import dbbg2.data.users.UserManager;
import dbbg2.data.users.Visitor;
import dbbg2.data.users.visitorcategory.VisitorCategory;
import dbbg2.view.controllers.user.details.UserDetailController;
import dbbg2.view.controllers.user.exceptions.UnknownUserTypeException;
import dbbg2.view.utils.nested.ChildController;
import dbbg2.view.utils.nested.ParentController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserOverviewController extends ChildController implements Initializable, ParentController {
    public TextField txtUserId;
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtEmail;
    public Button btnSearchUser;
    public TableView<User> tblUserList;
    public TableColumn<User, String> tcUserId;
    public TableColumn<User, String> tcFirstName;
    public TableColumn<User, String> tcLastName;
    public TableColumn<User, String> tcEmail;

    public Button btnClearSearch;
    public Button btnEditUser;
    public Button btnNewUser;
    public AnchorPane acUserDetail;
    public VBox vbSearchControls;
    public ChoiceBox<VisitorCategory> cboCategoryFilter;

    public void handleSearchButtonClick(ActionEvent actionEvent) {
        // TODO Make it so that only Employees can search users
        tblUserList.setItems(FXCollections.observableArrayList(UserManager.getUsers(txtUserId.getText(), txtFirstName.getText(), txtLastName.getText(), txtEmail.getText())));


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tcUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        tcFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tcLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));


        bindListeners();

    }

    /**
     * Clears the search fields
     *
     * @param actionEvent The event that was fired
     */
    public void handleClearButtonClick(ActionEvent actionEvent) {
        txtUserId.clear();
        txtFirstName.clear();
        txtLastName.clear();
        txtEmail.clear();
    }

    /**
     * Triggers an edit of the currently selected user
     *
     * @param actionEvent The event that fired
     */
    public void handleEditUserButtonClick(ActionEvent actionEvent) {
        User currentUser = tblUserList.getSelectionModel().getSelectedItem();

        if (currentUser != null) {

            try {
                showUserDetail(currentUser);

            } catch (IOException | UnknownUserTypeException e) {
                e.printStackTrace();
                Logger.getLogger("").log(Level.SEVERE, "Exception triggered when loading form in UserOverviewController", e);
            }
        } else {
            Logger.getLogger("").log(Level.WARNING, "Attempt to Edit user without selecting entity.");
        }


    }

    /**
     * Displays the detail view for a given User
     *
     * @param theUser The user to display the detail view for
     * @throws IOException              Thrown if loading the JavaFX resource fails
     * @throws UnknownUserTypeException Thrown if the User class is not implemented in the Edit view.
     */
    private void showUserDetail(User theUser) throws IOException, UnknownUserTypeException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/User/UserDetail.fxml"));
        acUserDetail.getChildren().setAll((AnchorPane) loader.load());
        UserDetailController udc = loader.getController();

        udc.loadUser(theUser);
        udc.setParentController(this);

        vbSearchControls.setVisible(false);
        acUserDetail.setVisible(true);

        vbSearchControls.getParent().autosize();
        acUserDetail.autosize();
        //acUserDetail.getParent().autosize();
    }

    /**
     * Handle a click on the New User button
     *
     * @param actionEvent The event triggering the action
     */
    public void handleNewUserButtonClick(ActionEvent actionEvent) {

        // TODO Make it so that only Employees can create new users
        User newUser = getUserFromDialog();
        if (newUser != null) {

            try {
                showUserDetail(newUser);
            } catch (IOException | UnknownUserTypeException e) {
                Logger.getLogger("").log(Level.SEVERE, "Exception during User loading during User Creation.", e);
            }
        } else {
            Logger.getLogger("").log(Level.SEVERE, "Could not select a new user type during User Creation");
        }

    }

    /**
     * Prompts with a dialog to create a new user.
     *
     * @return The newly created User object, or null if none created.
     */
    private User getUserFromDialog() {
        // TODO Make it so that only Manager-level employees can create new Employees
        String[] choices = {"Visitor", "Employee"};

        ChoiceDialog<String> cd = new ChoiceDialog<>(choices[0], Arrays.asList(choices));

        Optional<String> o = cd.showAndWait();
        User newUser = null;
        if (o.isPresent()) {
            switch (o.get()) {
                case "Visitor":
                    newUser = new Visitor();
                    break;
                case "Employee":
                    newUser = new Employee();
                    break;
            }


        }
        return newUser;
    }


    /**
     * Binds the change listeners in this controller
     */
    private void bindListeners() {
        tblUserList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> btnEditUser.setDisable(newValue == null)
        );


    }

    @Override
    public void notifyRequestReturn(ChildController theChild) {
        vbSearchControls.setVisible(true);

        acUserDetail.setVisible(false);
        acUserDetail.getChildren().clear();
        resizeTheView();
    }

    /**
     * Resizes the window and notifies the parent to do the same
     */
    private void resizeTheView() {
        acUserDetail.autosize();
        vbSearchControls.autosize();
        vbSearchControls.getScene().getWindow().sizeToScene();
        triggerParentUpdate();
    }

    @Override
    public void notifyResizeRequest(ChildController theChild) {
        resizeTheView();
    }

    @Override
    public void notifyUpdate(ChildController theChild) {
        // No need to handle updates from the child form
    }


}
