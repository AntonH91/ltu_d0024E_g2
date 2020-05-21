package dbbg2.view.dialogs;

import dbbg2.view.dialogs.controllers.ReceiptController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

import java.io.IOException;

public class ReceiptDialog extends Dialog<Boolean> {

    public ReceiptDialog(String dialog) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/LoanReceipt.fxml"));

            Pane loadDialogPane = loader.load();
            //Get associated controller
            ReceiptController lc = loader.getController();
            this.getDialogPane().setContent(loadDialogPane);

            lc.txtReceipt.setText(dialog);
            Window w = lc.txtReceipt.getScene().getWindow();
            w.setOnCloseRequest(event -> w.hide());


        } catch (IOException e) {
            e.printStackTrace();

        }

    }

}
