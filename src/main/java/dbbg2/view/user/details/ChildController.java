package dbbg2.view.user.details;

import dbbg2.controllers.user.UserController;
import dbbg2.data.users.User;

public interface ChildController {


    /**
     * Instantiates the UserController
     */
    void initializeUserController(User u);

    /**
     * Gets the user controller in use by the ChildController
     *
     * @return
     */
    UserController getDataController();

    /**
     * Writes changes to the user controller and saves it
     */
    void updateUserData();

    /**
     * Refreshes the interface with information from the user controller
     */
    void refreshInterface();

    /**
     * Checks if all the controls on the subform have a valid value.
     *
     * @return True if everything is valid, false otherwise
     */
    boolean isInputValid();

}
