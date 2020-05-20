package dbbg2.view.controllers.user.details;

import dbbg2.controllers.user.UserController;
import dbbg2.data.users.User;
import dbbg2.view.controllers.utils.nested.ChildController;

public abstract class UserChildController extends ChildController {

    /**
     * Instantiates the UserController
     */
    public abstract void initializeUserController(User u);

    /**
     * Gets the user controller in use by the ChildController
     *
     * @return
     */
    public abstract UserController getDataController();

    /**
     * Writes changes to the user controller and saves it
     */
    public abstract void updateUserData();

    /**
     * Refreshes the interface with information from the user controller
     */
    public abstract void refreshInterface();

    /**
     * Checks if all the controls on the subform have a valid value.
     *
     * @return True if everything is valid, false otherwise
     */
    public abstract boolean isInputValid();

    /**
     * <p>Gets an input validation message that can be used to improve user-friendliness of the application.</p>
     * NOTE: <b>The absence of a message does not indicate the input is valid.</b>
     * Reference isInputValid instead.
     *
     * @return The input validation message, or null if there is nothing to report
     */
    public abstract String getValidationMessage();

}
