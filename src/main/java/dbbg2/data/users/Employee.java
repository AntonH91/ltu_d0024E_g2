package dbbg2.data.users;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

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
