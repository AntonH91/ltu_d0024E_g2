package dbbg2.controllers.user;

import dbbg2.data.users.Employee;
import dbbg2.data.users.UserManager;

public class EmployeeController extends UserController {
    private Employee subjectToChange;


    public void setUser(Employee employee) {
        super.setUser(employee);

        this.subjectToChange = employee;
    }

    @Override
    public void selectExistingUser(String userId) {
        this.setUser(UserManager.getEmployee(userId));
    }

    @Override
    public void createUser() {
        this.setUser(new Employee());
    }


    /**
     * Changes the salary of the employee
     *
     * @param newSalary
     */
    public void changeSalary(Double newSalary) {
        this.subjectToChange.setSalary(newSalary);
    }

    /**
     * Sets the manager access of the user under modification
     *
     * @param newManagerAccess
     */
    public void setManagerAccess(boolean newManagerAccess) {
        this.subjectToChange.setManagerAccess(newManagerAccess);
    }

}
