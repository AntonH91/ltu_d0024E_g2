package dbbg2.view.dialogs;

import dbbg2.data.users.Visitor;
import dbbg2.data.users.visitorcategory.VisitorCategory;
import dbbg2.data.users.visitorcategory.VisitorCategoryManager;
import dbbg2.view.controllers.user.details.UserDetailController;
import dbbg2.view.controllers.user.details.VisitorDetailController;
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

            setForcedCategory((VisitorDetailController) uc.getChildController());

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

    private void setForcedCategory(VisitorDetailController vdc) {
        VisitorCategory defaultCategory = VisitorCategoryManager.getDefaultCategory();

        vdc.cbxVisitorCategory.setDisable(true);
        vdc.cbxVisitorCategory.setValue(defaultCategory);
    }

}
