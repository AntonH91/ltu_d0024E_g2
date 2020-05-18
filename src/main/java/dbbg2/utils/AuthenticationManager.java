package dbbg2.utils;

import dbbg2.data.genericexceptions.LibraryEntityNotFoundException;
import dbbg2.data.users.User;
import dbbg2.data.users.UserManager;
import dbbg2.utils.exceptions.LoginFailureException;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

public class AuthenticationManager implements Observable {
    private static User currentlyLoggedInUser = null;


    /**
     * Gets the currently logged in user in the application
     *
     * @return The user who is currently logged in.
     */
    public static User getCurrentlyLoggedInUser() {
        return currentlyLoggedInUser;
    }

    /**
     * Logs in a user in the application
     *
     * @param userName The username of the user to be logged in
     * @param password The password of the user to be logged in
     * @throws LoginFailureException Thrown if the login fails due to invalid username/password combo
     * @throws IllegalStateException Thrown if a login is attempted when there is already a logged in user.
     */
    public static void logIn(String userName, String password) throws LoginFailureException, IllegalStateException {
        if (currentlyLoggedInUser != null) {
            throw new IllegalStateException("A user is already logged in!");
        }

        try {
            currentlyLoggedInUser = UserManager.getAuthenticatedUser(userName, password);
        } catch (LibraryEntityNotFoundException e) {
            throw new LoginFailureException("UserID/Password mismatch.");
        }

    }

    public static void logOut() {
        currentlyLoggedInUser = null;
    }


    @Override
    public void addListener(InvalidationListener listener) {
        // TODO Implement this
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        // TODO Implement this
    }
}
