package dbbg2.data.users.category;

import dbbg2.data.users.User;

public class Employee extends User {
    private double salary = 0.0;
    private boolean managerAccess = false;

    public Employee() {
        super();
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
