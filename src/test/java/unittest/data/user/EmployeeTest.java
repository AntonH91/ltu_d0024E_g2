package unittest.data.user;

import dbbg2.data.users.Employee;
import dbbg2.data.users.User;

public class EmployeeTest extends UserTest {

    @Override
    public User getDefaultUser() {
        return new Employee();
    }
}
