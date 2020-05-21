package dbbg2.view.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import dbbg2.view.dialogs.ReceiptDialogue;

import java.net.URL;
import java.util.ResourceBundle;

public class LoanReceiptController implements Initializable {


    public TextArea txtLoanReceipt;

    public void initialize(URL location, ResourceBundle resources){

        txtLoanReceipt.getText();

    }

}
