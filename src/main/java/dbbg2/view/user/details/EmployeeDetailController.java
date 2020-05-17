package dbbg2.view.user.details;

import dbbg2.controllers.user.EmployeeController;
import dbbg2.controllers.user.UserController;
import dbbg2.data.users.Employee;
import dbbg2.data.users.User;
import dbbg2.view.utils.GenericStyler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeDetailController extends UserChildController implements Initializable {

    public TextField txtSalary;
    public CheckBox chkManagerAccess;
    private EmployeeController employeeController;

    private String validationMessage = null;

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
        // TODO Make it so that only Managers can see employee salaries
        // TODO Make it so that only Managers can change the Manager Access checkbox
        // TODO Make it so that it's not possible for a Manager to revoke their own Manager access

        Employee e = (Employee) employeeController.getUser();
        txtSalary.setText(String.valueOf(e.getSalary()));
        chkManagerAccess.setSelected(e.isManagerAccess());

    }

    @Override
    public boolean isInputValid() {
        boolean isValid = validateSalary();
        GenericStyler.markValidity(txtSalary, isValid);
        return validateSalary();
    }

    public boolean validateSalary() {
        boolean isValid = false;
        try {
            Double.valueOf(txtSalary.getText());
            isValid = true;
            validationMessage = null;
        } catch (Exception e) {
            validationMessage = "Salary must be a valid number.";
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
                triggerParentUpdate();
                if (!validateSalary()) {
                    Alert a = new Alert(Alert.AlertType.WARNING, "Please input a valid salary number.", ButtonType.OK);
                    a.showAndWait();
                    txtSalary.requestFocus();
                }
            }

        });

        chkManagerAccess.selectedProperty().addListener((observable, oldValue, newValue) -> triggerParentUpdate());

    }

    @Override
    public String getValidationMessage() {
        return validationMessage;
    }
}
