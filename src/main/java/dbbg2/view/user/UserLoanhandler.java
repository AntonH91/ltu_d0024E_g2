package dbbg2.view.user;

import dbbg2.controllers.Loans.LoanController;
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

public class UserLoanhandler implements Initializable {
    public TableView tblUserList;
    public TableColumn tcUserId;
    public TableColumn tcFirstName;
    public TableColumn tcLastName;
    public TableColumn tcEmail;

    public void handleSearchButtonClick(ActionEvent actionEvent) {
        // tblUserList.setItems(FXCollections.observableArrayList(UserManager.getUsers(txtUserId.getText(), txtFirstName.getText(), txtLastName.getText(), txtEmail.getText())));



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        LoanController lc = new LoanController(); // this shouldn't be createdit
        tcUserId.setCellValueFactory(new PropertyValueFactory<User, String>("userId"));
        tcFirstName.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        tcLastName.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
    }

}