package dbbg2.view.controllers.loans;

import dbbg2.controllers.loans.Exceptions.EmptyLoanException;
import dbbg2.controllers.loans.Exceptions.ItemNotOnLoanException;
import dbbg2.controllers.loans.LoanReturnController;
import dbbg2.data.loans.LoanCopy;
import dbbg2.data.users.Visitor;
import dbbg2.view.dialogs.ReceiptDialog;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LoanReturnView implements Initializable {
    private final LoanReturnController controller = new LoanReturnController();
    public AnchorPane anchRootPane;
    public TextField txtBarcode;
    public Button btnAdd;
    public Button btnReturnAllItems;
    public TableView<LoanCopy> tblItemList;
    public TableColumn<LoanCopy, String> tcVisitorName;
    public TableColumn<LoanCopy, String> tcItemTitle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        tcItemTitle.setCellValueFactory(param -> new ObservableStringValue() {
            @Override
            public String get() {
                try {
                    return param.getValue().getCopy().getItem().getTitle();
                } catch (Exception ignored) {
                    return null;
                }
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

        tcVisitorName.setCellValueFactory(param -> new ObservableStringValue() {
            @Override
            public String get() {
                try {
                    Visitor v = param.getValue().getParentLoan().getClient();
                    return v.getFirstName() + ' ' + v.getLastName();
                } catch (Exception ignored) {
                    return null;
                }
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

        txtBarcode.textProperty().addListener(event -> updateControlState());
    }

    public void updateControlState() {
        btnAdd.setDisable(controller.isLoanReturnFinalized() || txtBarcode.getText().isEmpty());
        btnReturnAllItems.setDisable(controller.isLoanReturnFinalized() || controller.getPendingReturnCount() == 0);

    }

    public void updateData() {
        tblItemList.setItems(FXCollections.observableList(controller.getPendingLoanReturns()));
        updateControlState();
    }


    public void handleAddButtonClick(ActionEvent actionEvent) {
        try {
            controller.returnItemOnLoan(txtBarcode.getText());
            updateData();
            txtBarcode.setText("");
            txtBarcode.requestFocus();
        } catch (ItemNotOnLoanException e) {
            showErrorMessage("This item is not currently on loan!");
        }
    }

    public void handleReturnButtonClick(ActionEvent actionEvent) {
        try {
            controller.finalizeReturn();
            ReceiptDialog rd = new ReceiptDialog(controller.getReturnReceipt());
            rd.setTitle("Return receipt");
            updateControlState();
            rd.showAndWait();

        } catch (EmptyLoanException e) {
            showErrorMessage("Cannot complete return when there are no items to return!");
        }
    }

    private void showErrorMessage(String message) {
        Alert a = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        a.setTitle("Loan Return Error");
        a.showAndWait();
    }
}
