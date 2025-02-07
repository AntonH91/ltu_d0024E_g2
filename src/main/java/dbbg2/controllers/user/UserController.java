package dbbg2.controllers.user;

import dbbg2.data.users.User;
import dbbg2.data.users.UserManager;
import dbbg2.utils.persistence.JpaPersistence;

import javax.persistence.EntityManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Anton Högelin (anthge-7)
 * <p>
 * This class handles all operations related to managing the user
 */
public abstract class UserController {
    private User subjectToChange;

    /**
     * Fetches an existing user to amend
     *
     * @param userId The userId to be changed
     */
    public abstract void selectExistingUser(String userId);

    public abstract void createUser();


    public User getUser() {
        return subjectToChange;
    }

    public void setUser(User user) {
        subjectToChange = user;
    }

    /**
     * Changes the user set for display by modifiying all the available parameters
     *
     * @param firstName     The new first name for the user
     * @param lastName      The new last name for the user
     * @param streetAddress The street address of the user
     * @param postCode      The new post code of the user
     * @param postArea      The new post area
     * @param email         The new email-address
     * @param phoneNr       The new phone number
     */
    public void amendSettings(String firstName, String lastName, String streetAddress,
                              String postCode, String postArea, String email, String phoneNr) {
        subjectToChange.setFirstName(firstName);
        subjectToChange.setLastName(lastName);
        subjectToChange.setStreetAddress(streetAddress);
        subjectToChange.setPostCode(postCode);
        subjectToChange.setPostArea(postArea);
        subjectToChange.setEmail(email);
        subjectToChange.setPhoneNr(phoneNr);
    }

    /**
     * Changes the person number of a user
     *
     * @param personNr The new person number to use
     */
    public void amendPersonNumber(String personNr) {
        subjectToChange.setPersonNr(personNr);
    }


    /**
     * Changes the password of the currently selected user
     *
     * @param newPassword The new password to set
     */
    public void setPassword(String newPassword) {
        subjectToChange.setPassword(newPassword);
    }


    /**
     * Saves the changes to the user object
     */
    public void saveChanges() {

        this.setUser(UserManager.persistUser(subjectToChange));

    }

    /**
     * Deletes the user from the entity manager and removes the user from the UserController
     *
     * @throws IllegalArgumentException Thrown when the user is not managed by JPA.
     */
    public void deleteUser() throws IllegalArgumentException {
        EntityManager em = JpaPersistence.getEntityManager();

        try {
            em.getTransaction().begin();
            em.remove(em.merge(subjectToChange));
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger("").log(Level.SEVERE, "Exception during deleting user.", e);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
        this.setUser(null);
    }

}
