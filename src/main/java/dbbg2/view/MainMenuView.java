package dbbg2.view;

import dbbg2.view.utils.GenericStyler;
import dbbg2.view.utils.nested.ChildController;
import dbbg2.view.utils.nested.ParentController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainMenuView implements Initializable, ParentController {

    public Label lblLoggedInAs;
    public Button btnLoginLogout;
    public Button btnRegisterNewAccount;

    public AnchorPane achUsersPane;
    private ChildController ccUserController;

    public AnchorPane achInventoryPane;
    public AnchorPane achLoanPane;

    public AnchorPane achMainMenuRoot;

    public void handleLoginButtonClick(ActionEvent actionEvent) {
        // TODO Bring up password prompt to log in a new user
        autoSizeWindow();
    }

    public void handleRegisterButtonClick(ActionEvent actionEvent) {
        // TODO Bring up window to allow user to register themselves as a Visitor
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Add loader code for various sub-menus under the main menu

        try {
            loadSubMenus();
        } catch (Exception e) {
            Logger.getLogger("").log(Level.SEVERE, "Failed to load the submenus!", e);
        }
    }

    public void updateAuthenticatedAccess() {
        // TODO Add code that only permits access to the menu items the logged-in user should be able to access.
    }


    /**
     * <p>Loads the various tabs in the main menu</p>
     *
     * @throws IOException Thrown if the loading fails for any reason.
     */
    private void loadSubMenus() throws IOException {
        ccUserController = GenericStyler.loadSinglePane(achUsersPane, "/Views/User/UserOverview.fxml");
        ccUserController.setParentController(this);

        GenericStyler.loadSinglePaneWithoutController(achInventoryPane, "/Views/ItemHandling.fxml");
        GenericStyler.loadSinglePaneWithoutController(achLoanPane, "/Views/Overviewloansview.fxml");


    }

    private void autoSizeWindow() {
        achMainMenuRoot.getScene().getWindow().sizeToScene();
    }


    @Override
    public void notifyRequestReturn() {
        // We don't handle return requests from child forms
    }

    @Override
    public void notifyResizeRequest() {
        autoSizeWindow();
    }

    @Override
    public void notifyUpdate() {
        // We don't care about updates from the child
    }
}
