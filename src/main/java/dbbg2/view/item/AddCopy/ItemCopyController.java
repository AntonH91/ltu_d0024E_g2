package dbbg2.view.item.AddCopy;

import dbbg2.data.inventory.*;
import dbbg2.data.inventory.itemCategory.ItemCategory;
import dbbg2.utils.persistence.JpaPersistence;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.eclipse.persistence.internal.jpa.metadata.columns.PrimaryKeyMetadata;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static dbbg2.data.inventory.itemCategory.ItemCategoryType.FILM;
import static dbbg2.data.inventory.itemCategory.ItemCategoryType.OTHER_BOOKS;

public class ItemCopyController implements Initializable {
    public TextField txtCopyLocation;
    public TextField txtBarcode;
    public TextField txtItemNameSearch;
    public TextField txtItemIdSearch;
    public TextField txtItemNameFound;
    public TextField txtItemIdFound;
    public TableView tblItemView;
    public TableColumn clItemName;
    public TableColumn clItemId;
    public Button btnAddCopy;
    public Button btnFindItem;

    public TextField txtRmItemTitle;
    public TextField txtRmItemId;
    public TextField txtCfCid;
    public TextField txtCfItemId;
    public TextField txtCfItemTitle;

    public TableColumn tcItemName;
    public TableColumn txItemId;
    public TableColumn tcBarcode;
    public TableView tbItemFound;

    public Button btnFindCopy;



    public void handleAddCopy(ActionEvent actionEvent) {
        List<InventoryCopy> copy = new ArrayList<>();
        InventoryItem ii = InventoryManager.getItemCopy(txtItemNameSearch.getText(), Integer.parseInt(txtItemIdFound.getText()));

        copy.add(new InventoryCopy(txtBarcode.getText(), txtCopyLocation.getText(), true, ii));

        /*(String barcode, String location, boolean lendable, InventoryItem item)
        film.add(new Film(txtAbbFilmTitle.getText(), FILM, true, txtFilmDirector.getText(), cbAgeLimit.getSelectionModel().getSelectedIndex(), txtOriginCountry.getText()));*/

        int index = 0;
        EntityManager em = JpaPersistence.getEntityManager();

        em.getTransaction().begin();
        for (InventoryCopy ic : copy) {
            index++;
            em.merge(ic);
        }

        em.getTransaction().commit();

    }

    public void handleFindItem(ActionEvent actionEvent) {
        tblItemView.setItems(FXCollections.observableArrayList(InventoryManager.getBooks(txtItemNameSearch.getText(), txtItemIdSearch.getText())));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources){



        clItemName.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("title"));

        tcItemName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<InventoryCopy, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InventoryCopy, String> param) {

                return new SimpleObjectProperty<>(param.getValue().getItem().getTitle());
            }
        });


        //tcItemName.setCellValueFactory(new PropertyValueFactory<InventoryCopy, String>("item"));
        //txItemId.setCellValueFactory(new PropertyValueFactory<InventoryCopy, Integer>("invId"));
        tcBarcode.setCellValueFactory(new PropertyValueFactory<Book, String>("barcode"));




    }

    public void handleTableViewMouseClickedAction(MouseEvent mouseEvent) {
        if(tblItemView.getSelectionModel().getSelectedItem() !=null){
            Book selectedBook = (Book) tblItemView.getSelectionModel().getSelectedItem();
            txtItemNameFound.setText(selectedBook.getTitle());
            txtItemIdFound.setText(String.valueOf(selectedBook.getInvId()));
        }

    }


    public void handleFindCopy(ActionEvent actionEvent) {

        tbItemFound.setItems(FXCollections.observableArrayList(InventoryManager.test2GetItemTitleCopies(txtRmItemId.getText(), txtRmItemTitle.getText())));


    }

    public void handleRemoveCopy(ActionEvent actionEvent) {

        EntityManager em = JpaPersistence.getEntityManager();

        EntityTransaction entityTransaction = null;

        try {
            entityTransaction = em.getTransaction();
            entityTransaction.begin();

            InventoryCopy invCopy = em.find(InventoryCopy.class, Long.parseLong(txtCfCid.getText()));
            em.remove(invCopy);

            entityTransaction.commit();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("The Copy has been removed");
            alert.showAndWait();


            return;


        } catch (RuntimeException e) {
            if(entityTransaction.isActive())
                entityTransaction.rollback();
            throw e;
        }

    }

    public void handleRemoveMouseClick(MouseEvent mouseEvent) {
        if(tbItemFound.getSelectionModel().getSelectedItem() !=null){
            InventoryCopy selectedCopy = (InventoryCopy) tbItemFound.getSelectionModel().getSelectedItem();
            //txtItemNameFound.setText(selectedCopy.getTitle());
            txtCfCid.setText(String.valueOf(selectedCopy.getCid()));
        }
    }
}
