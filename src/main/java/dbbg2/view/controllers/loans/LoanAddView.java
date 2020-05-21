package dbbg2.view.controllers.loans;

import dbbg2.controllers.Loans.Exceptions.ItemNotLendableException;
import dbbg2.controllers.Loans.Exceptions.TooManyItemsOnLoanException;
import dbbg2.controllers.Loans.LoanController;
import dbbg2.data.loans.LoanCopies;
import dbbg2.data.users.User;
import dbbg2.data.users.Visitor;
import dbbg2.utils.AuthenticationManager;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class LoanAddView implements Initializable {
    public Button btnFinalize;
    public TextField txtBarcode;
    public Button btnAddBarcode;
    public TableView<LoanCopies> tblLoanItems;


    LoanController controller;

    boolean userCanLoanBooks = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = new LoanController();
        AuthenticationManager.getAuthManager().addListener(event -> handleAuthenticationChanges());
        // 1. Authenticate currently logged in user
        // 2. Gives permission to use gui for user

        // Subscribe to AuthenticationManager events

    }

    // Java instanceof, Java casting,
    public void handleAddClick(ActionEvent actionEvent) {
        // Verify that the user can loan books
        if (!userCanLoanBooks) {
            return;
        }

        if (!controller.hasLoan()) {
            initializeLoan();
        }

        try {
            controller.addItemToLoan(txtBarcode.getText());
        } catch (ItemNotLendableException e) {
            showErrorMessage("This item is not lendable and cannot be added to the loan.");
        } catch (TooManyItemsOnLoanException e) {
            showErrorMessage("You have exceeded the amount of items you are allowed to loan.");
        }



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

    /**
     * Updates the control states on the form based on the logged in user and properties of the loan
     */
    public void updateControlStates() {
        btnFinalize.setDisable(!userCanLoanBooks && controller.getLoanCopyCount() > 0);
        btnAddBarcode.setDisable(!userCanLoanBooks);
    }

    /**
     * Listens for changes in the AuthenticationManager and handles them accordingly
     */
    public void handleAuthenticationChanges() {
        userCanLoanBooks = AuthenticationManager.getAuthManager().userCanLoanBooks();
        if (controller.hasLoan()) {
            controller.abortLoan();
        }
        updateControlStates();

    }

    /**
     * Initializes a loan based on what's currently in the AuthenticationManager
     */
    private void initializeLoan() {
        User u = AuthenticationManager.getAuthManager().getCurrentlyLoggedInUser();
        if (u instanceof Visitor) {
            controller.startLoan((Visitor) u);
        }
    }

    private void showErrorMessage(String message) {
        Alert a = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        a.setTitle("Loan error");
        a.showAndWait();
    }

}