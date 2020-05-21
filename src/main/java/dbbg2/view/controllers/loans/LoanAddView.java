package dbbg2.view.controllers.loans;

import dbbg2.controllers.Loans.Exceptions.ItemNotLendableException;
import dbbg2.controllers.Loans.LoanController;
import dbbg2.data.loans.LoanCopies;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoanAddView implements Initializable {
    public Button btnFinalize;
    public TextField txtBarcode;
    public Button addBarcode;
    public TableView<LoanCopies> tblLoanItems;
    LoanController controller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = new LoanController();

        // 1. Authenticate currently logged in user
        // 2. Gives permission to use gui for user

        // Subscribe to AuthenticationManager events

    }

    // Java instanceof, Java casting,
    public void handleAddClick(ActionEvent actionEvent) throws ItemNotLendableException {

        /* 1. If loan not started, start one.
           2. Checks user for permission to loan
           2.1 Add user to loan
           3.  Checks if book is available for user
           4. If not, show error message
           5 . if available, add book to info gui

         */
    }


    public void handleFinalizeClick(ActionEvent actionEvent) {
        /* 1. Takes all books/barcodes on tableview in GUI and adds them to the currently logged in user
           2.
           3. Update database of new loans made.
           4. Print receipt
        */
    }

}