package dbbg2.utils;

import dbbg2.data.genericexceptions.LibraryEntityNotFoundException;
import dbbg2.data.users.Employee;
import dbbg2.data.users.User;
import dbbg2.data.users.UserManager;
import dbbg2.data.users.Visitor;
import dbbg2.utils.exceptions.LoginFailureException;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.util.HashSet;
import java.util.Set;

public class AuthenticationManager implements Observable {
    private static AuthenticationManager self;
    private final Set<InvalidationListener> invalidationListeners;
    private User currentlyLoggedInUser = null;


    /**
     * Instantiates a new authentication manager
     */
    private AuthenticationManager() {
        invalidationListeners = new HashSet<>();
    }

    public static AuthenticationManager getAuthManager() {
        if (self == null) {
            self = new AuthenticationManager();
        }

        return self;
    }

    /**
     * Gets the currently logged in user in the application
     *
     * @return The user who is currently logged in.
     */
    public User getCurrentlyLoggedInUser() {
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
    public void logIn(String userName, String password) throws LoginFailureException, IllegalStateException {
        if (currentlyLoggedInUser != null) {
            throw new IllegalStateException("A user is already logged in!");
        }

        try {
            currentlyLoggedInUser = UserManager.getAuthenticatedUser(userName, password);
            notifyUpdates();
        } catch (LibraryEntityNotFoundException e) {
            throw new LoginFailureException("UserID/Password mismatch.");
        }

    }

    /**
     * Logs out the currently logged in user and notifies listeners.
     */
    public void logOut() {
        currentlyLoggedInUser = null;
        notifyUpdates();
    }


    /**
     * Checks if the logged in user can currently loan books
     *
     * @return True if the user has authorization to loan books, false otherwise
     */
    public boolean userCanLoanBooks() {
        return currentlyLoggedInUser instanceof Visitor;
    }

    /**
     * Checks if the logged in user has employee access
     *
     * @return True if the user has employee access, otherwise false.
     */
    public boolean userHasEmployeeAccess() {
        return currentlyLoggedInUser instanceof Employee;
    }

    /**
     * Checks if the user has manager-level access
     *
     * @return True if the user has manager-access, otherwise false
     */
    public boolean userHasManagerAccess() {
        return userHasEmployeeAccess() && ((Employee) currentlyLoggedInUser).isManagerAccess();
    }


    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListeners.add(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListeners.remove(listener);
    }

    private void notifyUpdates() {
        for (InvalidationListener il : invalidationListeners) {
            il.invalidated(this);
        }
    }

}
