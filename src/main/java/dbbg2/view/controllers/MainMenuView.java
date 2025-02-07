package dbbg2.view.controllers;

import dbbg2.data.users.User;
import dbbg2.utils.AuthenticationManager;
import dbbg2.view.dialogs.LoginDialog;
import dbbg2.view.dialogs.RegisterNewUserDialog;
import dbbg2.view.dialogs.UserDialog;
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
    public AnchorPane achInventoryPane;
    public AnchorPane achLoanPane;
    public AnchorPane achMainMenuRoot;
    private ChildController ccUserController;

    public void handleLoginButtonClick(ActionEvent actionEvent) {
        // Altering behaviour of the login button depending on what the current login state is, so that the layout is kept consistent.
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
        if (AuthenticationManager.getAuthManager().getCurrentlyLoggedInUser() == null) {
            RegisterNewUserDialog d = new RegisterNewUserDialog();
            d.showAndWait();
        } else {
            // TODO Add a user profile dialog here
            new UserDialog(AuthenticationManager.getAuthManager().getCurrentlyLoggedInUser()).showAndWait();
        }
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

    /**
     * Triggered when the authenticationmanager changes state
     */
    public void updateAuthenticatedAccess() {

        //Update the login label
        User loggedIn = AuthenticationManager.getAuthManager().getCurrentlyLoggedInUser();
        if (loggedIn != null) {
            lblLoggedInAs.setText(String.format("Logged in as: %s %s (%s)<%s>", loggedIn.getFirstName(), loggedIn.getLastName(), loggedIn.getUserId(), loggedIn.getUserType()));
            lblLoggedInAs.autosize();
            lblLoggedInAs.setVisible(true);

            // Set up the controls
            btnLoginLogout.setText("Logout");
            btnRegisterNewAccount.setText("Profile");


            // Do stuff based on the user's access role


        } else {
            btnLoginLogout.setText("Log In");
            btnRegisterNewAccount.setText("Register");
            lblLoggedInAs.setText("Not Logged In");
        }

        changeAccessBasedOnLogin();

    }

    /**
     * Sets the tab panes to the right availability based on the logged in user.
     */
    public void changeAccessBasedOnLogin() {
        tbpTabPane.getTabs().clear();

        // Employee access
        if (AuthenticationManager.getAuthManager().userHasEmployeeAccess()) {
            tbpTabPane.getTabs().add(tbUsers);
        }

        // Visitor access
        if (AuthenticationManager.getAuthManager().userCanLoanBooks()) {

        }

        // Anonymous access
        /*
        if (AuthenticationManager.getAuthManager().getCurrentlyLoggedInUser() == null) {
            // No need for specific anonymous access
        }
        */

        // General access
        tbpTabPane.getTabs().add(tbInventory);
        tbpTabPane.getTabs().add(tbLoans);


    }


    /**
     * <p>Loads the various tabs in the main menu</p>
     *
     * @throws IOException Thrown if the loading fails for any reason.
     */
    private void loadSubMenus() throws IOException {
        ccUserController = GenericStyler.loadSinglePane(achUsersPane, "/Views/User/UserOverview.fxml");
        ccUserController.setParentController(this);

        GenericStyler.loadSinglePaneWithoutController(achInventoryPane, "/Views/Items/ItemMaster.fxml");
        GenericStyler.loadSinglePaneWithoutController(achLoanPane, "/Views/Loans/LoanMaster.fxml");


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
