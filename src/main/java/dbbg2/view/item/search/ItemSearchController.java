package dbbg2.view.item.search;

import dbbg2.data.inventory.Book;
import dbbg2.data.inventory.InventoryItem;
import dbbg2.data.inventory.InventoryManager;
import dbbg2.data.inventory.Keyword;
import dbbg2.data.inventory.itemCategory.ItemCategory;
import dbbg2.data.inventory.itemCategory.ItemCategoryType;
import dbbg2.data.users.UserManager;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;


public class ItemSearchController implements Initializable {


    public TextField txtItemId;
    public TextField txtItemTitle;
    public TextField txtCategory;
    public TextField txtKeyword;
    public TextField txtDirector;
    public TextField txtAuthor;


    public TableView tblBookList;
    //public TableView tblFilmList;

    public TableColumn<InventoryItem, String> tcTitle;
    public TableColumn tcAuthor;
    public TableColumn tcCategory;
    public TableColumn tcKeyword;
    public TableColumn tcInventoryId;
    //public TableColumn tcDirector;

    public Button btnSearchBook;


    public void handleSearchBookClick(ActionEvent actionEvent){
        tblBookList.setItems(FXCollections.observableArrayList(InventoryManager.getBooks(txtItemTitle.getText(), txtItemId.getText())));
    }

    //ItemCategory.getDefaultItemCategory(OTHER_BOOKS).getItemCategoryTitle()


    @Override
    public void initialize(URL location, ResourceBundle resources){


        tcTitle.setCellValueFactory(new PropertyValueFactory <InventoryItem, String>("title"));
        tcCategory.setCellValueFactory(new PropertyValueFactory<ItemCategory, String>("category"));
        tcKeyword.setCellValueFactory(new PropertyValueFactory<Keyword, String>("keyword"));
        tcInventoryId.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("inventoryId"));
        tcAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));

    }

    public void handleClearText(ActionEvent actionEvent) {
        txtItemId.clear();
        txtItemTitle.clear();
        txtAuthor.clear();
        txtCategory.clear();
        txtKeyword.clear();
        txtDirector.clear();
    }


}