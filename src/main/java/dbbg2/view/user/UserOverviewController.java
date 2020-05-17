package dbbg2.view.user;

import dbbg2.data.users.User;
import dbbg2.data.users.UserManager;
import dbbg2.view.user.details.UserDetailController;
import dbbg2.view.user.exceptions.UnknownUserTypeException;
import dbbg2.view.utils.nested.ParentController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserOverviewController implements Initializable, ParentController {
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

    public void handleSearchButtonClick(ActionEvent actionEvent) {
        tblUserList.setItems(FXCollections.observableArrayList(UserManager.getUsers(txtUserId.getText(), txtFirstName.getText(), txtLastName.getText(), txtEmail.getText())));
        tblUserList.autosize();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tcUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        tcFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tcLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

    }

    public void handleClearButtonClick(ActionEvent actionEvent) {
        txtUserId.clear();
        txtFirstName.clear();
        txtLastName.clear();
        txtEmail.clear();
    }

    public void handleEditUserButtonClick(ActionEvent actionEvent) {
        // TODO Refactor this into a better structure
        User currentUser = tblUserList.getSelectionModel().getSelectedItem();
        System.out.println(currentUser);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/User/UserDetail.fxml"));
            acUserDetail.getChildren().setAll((AnchorPane) loader.load());
            UserDetailController udc = loader.getController();

            udc.loadUser(currentUser);
            udc.setParentController(this);

            vbSearchControls.setVisible(false);
            acUserDetail.setVisible(true);

            vbSearchControls.getParent().autosize();
            acUserDetail.autosize();
            //acUserDetail.getParent().autosize();

        } catch (IOException | UnknownUserTypeException e) {
            e.printStackTrace();
            Logger.getLogger("").log(Level.SEVERE, "Exception triggered when loading form in UserOverviewController", e);
        }


    }

    /**
     * Handle a click on the New User button
     *
     * @param actionEvent The event triggering the action
     */

    public void handleNewUserButtonClick(ActionEvent actionEvent) {
        // TODO Implement New User addition
    }

    @Override
    public void notifyRequestReturn() {
        vbSearchControls.setVisible(true);

        acUserDetail.setVisible(false);
        acUserDetail.getChildren().clear();
        acUserDetail.autosize();
        // TODO Make auto-sizing work correctly
        vbSearchControls.autosize();
        vbSearchControls.getParent().autosize();
    }

    @Override
    public void notifyUpdate() {
        // No need to handle updates from the child form
    }
}
