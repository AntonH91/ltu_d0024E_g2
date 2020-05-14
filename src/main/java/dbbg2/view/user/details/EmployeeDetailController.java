package dbbg2.view.user.details;

import dbbg2.controllers.user.EmployeeController;
import dbbg2.controllers.user.UserController;
import dbbg2.data.users.Employee;
import dbbg2.data.users.User;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeDetailController implements ChildController, Initializable {

    public TextField txtSalary;
    public CheckBox chkManagerAccess;
    private EmployeeController employeeController;

    @Override
    public void initializeUserController(User u) {
        if (u instanceof Employee) {
            employeeController = new EmployeeController();
            employeeController.setUser((Employee) u);
        } else {
            throw new ClassCastException("Cannot initialize EmployeeDetailController with non-Employee User");
        }

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
    public boolean isInputValid() {
        return validateSalary();
    }

    public boolean validateSalary() {
        boolean isValid = false;
        try {
            Double.valueOf(txtSalary.getText());
            isValid = true;
        } catch (Exception e) {

        }
        return isValid;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindListeners();
    }

    private void bindListeners() {
        // Bind a listener to validate the salary amount
        txtSalary.focusedProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue) {
                        if (!validateSalary()) {
                            Alert a = new Alert(Alert.AlertType.WARNING, "Please input a valid salary number.", ButtonType.OK);
                            a.showAndWait();
                            txtSalary.requestFocus();
                        }
                    }

                }
        );
    }

}
