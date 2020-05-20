package unittest.data.user;

import dbbg2.data.users.Employee;
import dbbg2.data.users.User;
import org.junit.Assert;
import org.junit.Test;

public class EmployeeTest extends UserTest {

    @Override
    public User getDefaultUser() {
        return new Employee();
    }

    private Employee getDefaultEmployee() {
        return (Employee) getDefaultUser();
    }

    @Test
    public void salaryIsChangeable() {
        final double SALARY = 1123456.57;

        Employee a = getDefaultEmployee();

        a.setSalary(SALARY);

        Assert.assertEquals(SALARY, a.getSalary(), 0.01);

    }

    @Test(expected = IllegalArgumentException.class)
    public void salaryCanNotBeNegative() {
        final double SALARY = -123;
        Employee a = getDefaultEmployee();

        a.setSalary(SALARY);


    }

    @Test
    public void managerAccessDefaultsToFalse() {
        Employee a = getDefaultEmployee();
        Assert.assertFalse("Employee manager access does not default to False", a.isManagerAccess());
    }

    @Test
    public void managerAccessIsChangeable() {
        Employee a = getDefaultEmployee();
        a.setManagerAccess(true);

        Assert.assertTrue("Employee manager access could not be changed.", a.isManagerAccess());
    }

    @Test
    public void userTypeIsEmployee() {
        final String EXPECTED_TYPE = "Employee";
        Employee a = getDefaultEmployee();

        Assert.assertEquals(EXPECTED_TYPE, a.getUserType());


    }


}
