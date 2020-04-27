package dbbg2.data.users;

import dbbg2.persistence.Database;

import java.sql.SQLException;

/**
 * @author Anton Högelin (anthge-7)
 * This class handles the responsibilities of the employee management, such as governing elevated access and similar features.
 */
public class Employee extends User {
    private double salary = 0.0;
    private boolean managerAccess = false;

    public Employee() {
        super();
    }

    @Override
    public String getUserType() {
        return "Employee";
    }

    @Override
    protected void saveSpecificDetails(String userId) throws SQLException {
        Database.getDefaultInstance().getPreparedStatement("UPDATE users " +
                "                                                   SET user_type='Employee', " +
                "                                                       salary=?," +
                "                                                               manager_access=? " +
                "                                                       WHERE user_id=?;");


    }


    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public boolean isManagerAccess() {
        return managerAccess;
    }

    public void setManagerAccess(boolean managerAccess) {
        this.managerAccess = managerAccess;
    }


}
