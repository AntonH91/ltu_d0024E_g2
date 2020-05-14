package dbbg2.view.user;

import dbbg2.controllers.user.UserController;

public interface ChildController {


    /**
     * Instantiates the UserController
     */
    void initializeUserController();

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


}
