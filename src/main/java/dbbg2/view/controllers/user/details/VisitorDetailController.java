package dbbg2.view.controllers.user.details;

import dbbg2.controllers.user.UserController;
import dbbg2.controllers.user.VisitorController;
import dbbg2.data.genericexceptions.LibraryEntityNotFoundException;
import dbbg2.data.loans.LoanCopy;
import dbbg2.data.loans.LoanManager;
import dbbg2.data.users.User;
import dbbg2.data.users.Visitor;
import dbbg2.data.users.visitorcategory.VisitorCategory;
import dbbg2.data.users.visitorcategory.VisitorCategoryManager;
import dbbg2.view.utils.GenericStyler;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import javax.persistence.NoResultException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VisitorDetailController extends UserChildController implements Initializable {
    public ComboBox<VisitorCategory> cbxVisitorCategory;

    // TODO filtering in the table
    public CheckBox chkShowReturned;
    public CheckBox chkShowLate;
    public CheckBox chkShowActive;

    public TableView<LoanCopy> tblLoanDisplay;
    public TableColumn<LoanCopy, Boolean> tcReturned;
    public TableColumn<LoanCopy, String> tcTitle;
    public TableColumn<LoanCopy, Date> tcReturnDate;

    private VisitorController visitorController;

    private String validationErrors;

    private FilteredList<LoanCopy> filteredLoans;

    @Override
    public String getValidationMessage() {
        return validationErrors;
    }

    @Override
    public void initializeUserController(User u) {
        if (u instanceof Visitor) {
            visitorController = new VisitorController();
            visitorController.setUser(u);
        } else {
            throw new ClassCastException("Cannot initialize VisitorDetailController with non-Visitor User");
        }
    }

    @Override
    public UserController getDataController() {
        return visitorController;
    }

    /**
     * Change the visitor category of the user.
     */
    @Override
    public void updateUserData() {
        visitorController.setVisitorCategory(cbxVisitorCategory.getValue());


    }

    /**
     * Refresh the user interface.
     */
    @Override
    public void refreshInterface() {
        Visitor v = (Visitor) visitorController.getUser();
        selectCategory(v.getCategory());

        try {
            filteredLoans = new FilteredList<>(FXCollections.observableList(LoanManager.getLoanCopiesFromUser(v.getUserId())));
            tblLoanDisplay.setItems(filteredLoans);
        } catch (LibraryEntityNotFoundException e) {
            filteredLoans = null;
            tblLoanDisplay.getItems().clear();
        }
        refreshLoanFiltering();
    }

    public void refreshLoanFiltering() {
        if (filteredLoans != null) {
            Date today = new Date();
            filteredLoans.setPredicate(loanCopy -> chkShowReturned.isSelected() && loanCopy.isReturned()
                    || chkShowActive.isSelected() && loanCopy.getReturnDate().after(today) && !loanCopy.isReturned()
                    || chkShowLate.isSelected() && loanCopy.getReturnDate().before(today)
            );
        }
    }


    /**
     * Helper method to set the value of the combobox to the target visitor category.
     *
     * @param targetCategory The category that should be selected.
     */
    private void selectCategory(VisitorCategory targetCategory) {
        if (targetCategory != null) {
            for (VisitorCategory vc : cbxVisitorCategory.getItems()) {
                if (targetCategory.equals(vc)) {
                    cbxVisitorCategory.setValue(vc);
                    break;
                }
            }
        }

    }

    @Override
    public boolean isInputValid() {
        boolean isValid = cbxVisitorCategory.getValue() != null;
        GenericStyler.markValidity(cbxVisitorCategory, isValid);
        return isValid;
    }

    /**
     * Initialize the VisitorDetail view and configure the visitor category combobox to show the correct user type.
     *
     * @param location  The location
     * @param resources The resource bundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbxVisitorCategory.setCellFactory(new Callback<ListView<VisitorCategory>, ListCell<VisitorCategory>>() {
            @Override
            public ListCell<VisitorCategory> call(ListView<VisitorCategory> param) {
                return new ListCell<VisitorCategory>() {
                    @Override
                    protected void updateItem(VisitorCategory item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            this.setGraphic(null);
                        } else {
                            this.setText(item.getCategoryTitle());
                        }
                    }
                };
            }
        });

        cbxVisitorCategory.setButtonCell(cbxVisitorCategory.getCellFactory().call(null));

        cbxVisitorCategory.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> this.triggerParentUpdate()));


        tcReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        tcTitle.setCellValueFactory(param -> new ObservableStringValue() {
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
        tcReturned.setCellValueFactory(new PropertyValueFactory<>("returned"));

        loadVisitorCategories();


        filteredLoans = null;


    }

    /**
     * Loads the selectable visitor categories from the database
     */
    private void loadVisitorCategories() {
        List<VisitorCategory> categoryList = VisitorCategoryManager.getVisitorCategories();
        cbxVisitorCategory.setItems(FXCollections.observableArrayList(categoryList));
        try {
            cbxVisitorCategory.setValue(VisitorCategoryManager.getDefaultCategory());
        } catch (NoResultException e) {
            Logger.getLogger("").log(Level.WARNING, "No default visitor category specified!");
        }
    }


}
