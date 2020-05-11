package dbbg2.utils;

import dbbg2.data.users.User;
import dbbg2.data.users.UserManager;

import javax.persistence.NoResultException;

public class AuthenticationManager {
    private static User currentlyLoggedInUser = null;


    /**
     * Gets the currently logged in user in the application
     * @return
     */
    public static User getCurrentlyLoggedInUser() {
        return currentlyLoggedInUser;
    }

    /**
     * Logs in a user in the application
     * @param userName The username of the user to be logged in
     * @param password The password of the user to be logged in
     * @throws NoResultException Thrown if the login fails due to invalid username/password combo
     * @throws IllegalStateException Thrown if a login is attempted when there is already a logged in user.
     */
    public static void logIn(String userName, String password) throws NoResultException, IllegalStateException {
        if (currentlyLoggedInUser != null) {
            throw new IllegalStateException("A user is already logged in!");
        }

        currentlyLoggedInUser = UserManager.getAuthenticatedUser(userName, password);

    }

    public static void logOut() {
        currentlyLoggedInUser = null;
    }


}
