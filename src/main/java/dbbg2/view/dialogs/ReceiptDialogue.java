package dbbg2.view.dialogs;

import dbbg2.view.dialogs.controllers.ReceiptController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ReceiptDialogue extends Dialog<Boolean> {

    public ReceiptDialogue(String dialog) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/LoanReceipt.fxml"));

            Pane loadDialogPane = loader.load();
            //Get associated controller
            ReceiptController lc = loader.getController();
            this.getDialogPane().setContent(loadDialogPane);

            lc.txtReceipt.setText(dialog);


        } catch (IOException e) {
            e.printStackTrace();

        }

    }

}
