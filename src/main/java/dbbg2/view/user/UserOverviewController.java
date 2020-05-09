package dbbg2.view.user;

import dbbg2.data.users.User;
import dbbg2.data.users.UserManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class UserOverviewController implements Initializable {
    public TextField txtUserId;
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtEmail;
    public Button btnSearchUser;
    public TableView tblUserList;
    public TableColumn tcUserId;
    public TableColumn tcFirstName;
    public TableColumn tcLastName;
    public TableColumn tcEmail;

    public void handleSearchButtonClick(ActionEvent actionEvent) {
        tblUserList.setItems(FXCollections.observableArrayList(UserManager.getUsers()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tcUserId.setCellValueFactory(new PropertyValueFactory<User, String>("userId"));
        tcFirstName.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
    }
}
