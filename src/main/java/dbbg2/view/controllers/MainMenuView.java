package dbbg2.view.controllers;

import dbbg2.data.users.User;
import dbbg2.utils.AuthenticationManager;
import dbbg2.view.dialogs.LoginDialog;
import dbbg2.view.dialogs.RegisterNewUserDialog;
import dbbg2.view.utils.GenericStyler;
import dbbg2.view.utils.nested.ChildController;
import dbbg2.view.utils.nested.ParentController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainMenuView implements Initializable, ParentController {

    public Label lblLoggedInAs;
    public Button btnLoginLogout;
    public Button btnRegisterNewAccount;

    public AnchorPane achUsersPane;
    public Tab tbUsers;
    public Tab tbInventory;
    public Tab tbLoans;
    public TabPane tbpTabPane;
    private ChildController ccUserController;

    public AnchorPane achInventoryPane;
    public AnchorPane achLoanPane;

    public AnchorPane achMainMenuRoot;

    public void handleLoginButtonClick(ActionEvent actionEvent) {

        if (AuthenticationManager.getAuthManager().getCurrentlyLoggedInUser() == null) {
            LoginDialog d = new LoginDialog();
            d.showAndWait();
        } else {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to log out?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = a.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                AuthenticationManager.getAuthManager().logOut();
            }
        }

        autoSizeWindow();
    }

    public void handleRegisterButtonClick(ActionEvent actionEvent) {
        RegisterNewUserDialog d = new RegisterNewUserDialog();
        d.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            loadSubMenus();
        } catch (Exception e) {
            Logger.getLogger("").log(Level.SEVERE, "Failed to load the submenus!", e);
        }

        // Subscribe to AuthenticationManager updates
        AuthenticationManager.getAuthManager().addListener(observable -> this.updateAuthenticatedAccess());

        // Update controls to match current access level.
        updateAuthenticatedAccess();

    }

    public void updateAuthenticatedAccess() {

        //Update the login label
        User loggedIn = AuthenticationManager.getAuthManager().getCurrentlyLoggedInUser();
        if (loggedIn != null) {
            lblLoggedInAs.setText(String.format("Logged in as: %s %s (%s)<%s>", loggedIn.getFirstName(), loggedIn.getLastName(), loggedIn.getUserId(), loggedIn.getUserType()));
            lblLoggedInAs.autosize();
            lblLoggedInAs.setVisible(true);

            // Set up the controls
            btnLoginLogout.setText("Logout");
            btnRegisterNewAccount.setVisible(false);

            // Do stuff based on the user's access role


        } else {
            btnLoginLogout.setText("Log In");
            btnRegisterNewAccount.setVisible(true);
            lblLoggedInAs.setText("Not Logged In");
        }

        changeAccessBasedOnLogin();

    }

    public void changeAccessBasedOnLogin() {
        tbpTabPane.getTabs().clear();

        // Employee access
        if (AuthenticationManager.getAuthManager().userHasEmployeeAccess()) {
            tbpTabPane.getTabs().add(tbUsers);
        }

        // Visitor access
        if (AuthenticationManager.getAuthManager().userCanLoanBooks()) {
            tbpTabPane.getTabs().add(tbLoans);
        }

        // Anonymous access
        if (AuthenticationManager.getAuthManager().getCurrentlyLoggedInUser() == null) {
            // No need for specific anonymous access
        }

        // General access
        tbpTabPane.getTabs().add(tbInventory);


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
    public void notifyRequestReturn(ChildController theChild) {
        // We don't handle return requests from child forms
    }

    @Override
    public void notifyResizeRequest(ChildController theChild) {
        autoSizeWindow();
    }

    @Override
    public void notifyUpdate(ChildController theChild) {
        // We don't care about updates from the child
    }

}
