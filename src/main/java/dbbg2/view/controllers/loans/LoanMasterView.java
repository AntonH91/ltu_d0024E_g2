package dbbg2.view.controllers.loans;

import dbbg2.utils.AuthenticationManager;
import dbbg2.view.utils.GenericStyler;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoanMasterView implements Initializable {
    public AnchorPane anchRootPane;
    public AnchorPane anchMakeLoan;
    public AnchorPane anchReturnItems;

    public Tab tbMakeLoan;
    public Tab tbReturnItems;

    public TabPane tbpTabs;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AuthenticationManager.getAuthManager().addListener(event -> updateAuthenticatedFields());


        try {
            GenericStyler.loadSinglePaneWithoutController(anchMakeLoan, "/Views/Loans/AddItemToLoanView.fxml");
            GenericStyler.loadSinglePaneWithoutController(anchReturnItems, "/Views/Loans/LoanReturnView.fxml");
        } catch (IOException e) {
            Logger.getLogger("").log(Level.SEVERE, "Could not load child panes for LoanMaster.", e);
        }

        updateAuthenticatedFields();
    }

    public void updateAuthenticatedFields() {
        tbpTabs.getTabs().clear();
        tbpTabs.getTabs().add(tbReturnItems);

        if (AuthenticationManager.getAuthManager().userCanLoanBooks()) {
            tbpTabs.getTabs().add(tbMakeLoan);
        }

    }

}
