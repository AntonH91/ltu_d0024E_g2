package dbbg2.data.users;

import dbbg2.data.users.visitorcategory.GeneralPublic;

public class UserManager {

    public static User getAuthenticatedUser(String userId, String password) {
        // TODO Implement this
        return new Employee();
    }

    public static User getUser(String userId) {
        // TODO Implement this
        // TODO Add exceptions
        return new Employee();
    }

    public static Employee getEmployee(String userId) {
        // TODO Implement this
        // TODO Add exceptions
        return new Employee();
    }

    public static Visitor getVisitor(String userId) {
        // TODO Implement this
        // TODO Add exceptions
        return new Visitor(new GeneralPublic());
    }

}
