package dbbg2.data.users;

import javax.persistence.Basic;
import javax.persistence.Entity;

/**
 * @author Anton Högelin (anthge-7)
 * This class handles the responsibilities of the employee management, such as governing elevated access and similar features.
 */
@Entity(name = "Employees")
public class Employee extends User {
    @Basic(optional = false)
    private double salary = 0.0;
    @Basic(optional = false)
    private boolean managerAccess = false;

    public Employee() {
        super();
    }

    public Employee(String userNameOverride) {
        super(userNameOverride);
    }


    @Override
    public String getUserType() {
        return "Employee";
    }

    @Override
    public String toString() {
        return "Employee{" +
                "salary=" + salary +
                ", managerAccess=" + managerAccess +
                "} " + super.toString();
    }

    public double getSalary() {
        return salary;
    }

    /**
     * Sets the new salary for the employee
     *
     * @param salary The new salary
     * @throws IllegalArgumentException Thrown if the new salary is < 0
     */
    public void setSalary(double salary) throws IllegalArgumentException {
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be < 0");
        }
        this.salary = salary;
    }

    public boolean isManagerAccess() {
        return managerAccess;
    }

    public void setManagerAccess(boolean managerAccess) {
        this.managerAccess = managerAccess;
    }


}
