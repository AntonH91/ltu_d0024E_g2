package dbbg2.view.dialogs;

import dbbg2.data.users.User;
import dbbg2.view.controllers.user.details.UserDetailController;
import dbbg2.view.controllers.user.details.VisitorDetailController;
import dbbg2.view.controllers.user.exceptions.UnknownUserTypeException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;

import java.io.IOException;

public class UserDialog extends Dialog<Boolean> {

    public UserDialog(User theUser) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/User/UserDetail.fxml"));
            this.getDialogPane().setContent(loader.load());

            UserDetailController uc = loader.getController();
            uc.loadUser(theUser);
            setRestrictions(uc);

            bindDialogListeners(uc);

        } catch (IOException | UnknownUserTypeException e) {
            e.printStackTrace();
        }

    }

    protected void setRestrictions(UserDetailController udc) {
        if (udc.getChildController() instanceof VisitorDetailController) {
            VisitorDetailController vdc = (VisitorDetailController) udc.getChildController();
            vdc.cbxVisitorCategory.setDisable(true);
        }
    }


    protected void bindDialogListeners(UserDetailController udc) {
        udc.btnDeleteUser.setVisible(false);

        // Hook the button action events to close the dialog when done
        udc.btnCancelButton.setOnAction(event -> {
            udc.handleCancelButtonClick(event);
            getDialogPane().getScene().getWindow().hide();
        });

        udc.btnSaveButton.setOnAction(event -> {
            udc.handleSaveButtonClick(event);
            getDialogPane().getScene().getWindow().hide();
        });


        getDialogPane().getScene().getWindow().setOnCloseRequest(event -> getDialogPane().getScene().getWindow().hide());


    }
}
