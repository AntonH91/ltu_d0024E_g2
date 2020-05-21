package dbbg2.view.dialogs;

import dbbg2.controllers.Loans.LoanController;
import dbbg2.data.loans.Loan;
import dbbg2.view.controllers.LoanReceiptController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import java.io.IOException;

public class ReceiptDialogue extends Dialog<Boolean> {

    public ReceiptDialogue(String dialog) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/LoanReceipt"));

            DialogPane loadDialogPane = loader.load();

            //Get associated controller
            LoanReceiptController lc = loader.getController();

            lc.txtLoanReceipt.setText(dialog);


        } catch (IOException e) {
            e.printStackTrace();

        }

    }

}
