package dbbg2.view.controllers.loans;

import dbbg2.controllers.Loans.Exceptions.ItemNotLendableException;
import dbbg2.controllers.Loans.Exceptions.TooManyItemsOnLoanException;
import dbbg2.controllers.Loans.LoanController;
import dbbg2.data.genericexceptions.LibraryEntityNotFoundException;
import dbbg2.data.loans.LoanCopy;
import dbbg2.data.users.User;
import dbbg2.data.users.Visitor;
import dbbg2.utils.AuthenticationManager;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class LoanAddView implements Initializable {
    public Button btnFinalize;
    public TextField txtBarcode;
    public Button btnAddBarcode;
    public TableView<LoanCopy> tblLoanItems;
    public TableColumn<LoanCopy, String> tcItemTitle;
    public TableColumn<LoanCopy, String> tcReturnDate;


    LoanController controller;

    boolean userCanLoanBooks = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = new LoanController();

        // Subscribe to AuthenticationManager events
        AuthenticationManager.getAuthManager().addListener(event -> handleAuthenticationChanges());
        handleAuthenticationChanges();

        tcReturnDate.setCellValueFactory(param -> new ObservableStringValue() {
            @Override
            public String get() {
                String result = null;
                try {
                    result = param.getValue().getReturnDate().toString();
                } catch (NullPointerException ignored) {
                }
                return result;
            }

            @Override
            public void addListener(ChangeListener<? super String> listener) {

            }

            @Override
            public void removeListener(ChangeListener<? super String> listener) {

            }

            @Override
            public String getValue() {
                return get();
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        });

        tcItemTitle.setCellValueFactory(param -> new ObservableStringValue() {
            @Override
            public String get() {
                String result = null;
                try {
                    result = param.getValue().getCopy().getItem().getTitle();
                } catch (NullPointerException ignored) {

                }
                return result;
            }

            @Override
            public void addListener(ChangeListener<? super String> listener) {

            }

            @Override
            public void removeListener(ChangeListener<? super String> listener) {

            }

            @Override
            public String getValue() {
                return get();
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        });
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
        } catch (LibraryEntityNotFoundException e) {
            showErrorMessage("This is not a valid barcode.");
        }

        updateControlStates();
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
        btnFinalize.setDisable(!userCanLoanBooks || controller.getLoanCopyCount() == 0);
        btnAddBarcode.setDisable(!userCanLoanBooks);

        tblLoanItems.setItems(FXCollections.observableList(controller.getLoanCopies()));

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