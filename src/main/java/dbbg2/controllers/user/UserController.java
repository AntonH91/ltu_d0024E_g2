package dbbg2.controllers.user;

import dbbg2.data.users.User;
import dbbg2.utils.persistence.JpaPersistence;

import javax.persistence.EntityManager;

/**
 * @author Anton Högelin (anthge-7)
 * <p>
 * This class handles all operations related to managing the user
 */
public abstract class  UserController {
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
     * @param firstName The new first name for the user
     * @param lastName The new last name for the user
     * @param streetAddress The street address of the user
     * @param postCode The new post code of the user
     * @param postArea The new post area
     * @param email The new email-address
     * @param phoneNr The new phone number
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
     * @param personNr The new person number to use
     */
    public void amendPersonNumber(String personNr) {
        subjectToChange.setPersonNr(personNr);
    }


    /**
     * Changes the password of the currently selected user
     * @param newPassword The new password to set
     */
    public void setPassword(String newPassword) {
        subjectToChange.setPassword(newPassword);
    }


    /**
     * Saves the changes to the user object
     */
    public void saveChanges() {
        EntityManager em = JpaPersistence.getEntityManager();

        em.getTransaction().begin();

        this.setUser(em.merge(subjectToChange));

        em.getTransaction().commit();

    }

}
