package dbbg2.view.user;

import dbbg2.controllers.user.EmployeeController;
import dbbg2.controllers.user.UserController;
import dbbg2.data.users.Employee;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeDetailController implements ChildController, Initializable {

    public TextField txtSalary;
    public CheckBox chkManagerAccess;
    private EmployeeController employeeController;

    @Override
    public void initializeUserController() {
        employeeController = new EmployeeController();
    }

    @Override
    public UserController getDataController() {
        return employeeController;
    }

    @Override
    public void updateUserData() {
        employeeController.changeSalary(Double.valueOf(txtSalary.getText()));
        employeeController.setManagerAccess(chkManagerAccess.isSelected());
    }

    @Override
    public void refreshInterface() {
        Employee e = (Employee) employeeController.getUser();
        txtSalary.setText(String.valueOf(e.getSalary()));
        chkManagerAccess.setSelected(e.isManagerAccess());

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeUserController();
        bindListeners();
    }

    private void bindListeners() {

    }

}
