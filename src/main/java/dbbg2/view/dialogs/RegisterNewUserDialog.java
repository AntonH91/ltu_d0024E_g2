package dbbg2.view.dialogs;

import dbbg2.data.users.Visitor;
import dbbg2.view.controllers.user.details.UserDetailController;
import dbbg2.view.controllers.user.exceptions.UnknownUserTypeException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;

import java.io.IOException;

public class RegisterNewUserDialog extends Dialog<Boolean> {

    public RegisterNewUserDialog() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/User/UserDetail.fxml"));

            this.getDialogPane().setContent(loader.load());
            UserDetailController uc = loader.getController();

            uc.loadUser(new Visitor());

            bindDialogListeners(uc);

            // TODO Prevent selection of different user type than default

        } catch (IOException | UnknownUserTypeException e) {
            e.printStackTrace();
        }

    }


    private void bindDialogListeners(UserDetailController udc) {
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
