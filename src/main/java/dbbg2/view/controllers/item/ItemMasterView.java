package dbbg2.view.controllers.item;

import dbbg2.view.utils.GenericStyler;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadSubMenus();
        } catch (IOException e) {
            Logger.getLogger("").log(Level.SEVERE, "IOException when loading submenus for ItemMaster view", e);
        }
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

}
