package dbbg2.view.controllers.item;

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

public class ItemMasterView implements Initializable {
    public AnchorPane anchRootPane;
    public Tab tbAddItem;
    public Tab tbEditItem;
    public Tab tbManageItem;
    public Tab tbSearchItem;

    public AnchorPane anchSearch;
    public AnchorPane anchManage;
    public AnchorPane anchEdit;
    public AnchorPane anchAdd;
    public TabPane tbpItemTabs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadSubMenus();
        } catch (IOException e) {
            Logger.getLogger("").log(Level.SEVERE, "IOException when loading submenus for ItemMaster view", e);
        }
        handleAccessRights();

        AuthenticationManager.getAuthManager().addListener(event -> handleAccessRights());

    }

    /**
     * Loads the submenus for the ItemMaster view
     *
     * @throws IOException Thrown when the XML cannot be loaded due to IO failure
     */
    public void loadSubMenus() throws IOException {
        GenericStyler.loadSinglePaneWithoutController(anchSearch, "/Views/Items/ItemSearch.fxml");
        GenericStyler.loadSinglePaneWithoutController(anchManage, "/Views/Items/ItemHandling.fxml");
        GenericStyler.loadSinglePaneWithoutController(anchEdit, "/Views/Items/ItemEdit.fxml");
        GenericStyler.loadSinglePaneWithoutController(anchAdd, "/Views/Items/ItemAddCopy.fxml");
    }


    public void handleAccessRights() {
        tbpItemTabs.getTabs().clear();

        tbpItemTabs.getTabs().add(tbSearchItem);

        if (AuthenticationManager.getAuthManager().userHasEmployeeAccess()) {
            Tab[] managerTabs = {tbAddItem, tbEditItem, tbManageItem};
            for (Tab t : managerTabs) {
                tbpItemTabs.getTabs().add(t);
            }
        }


    }

}
