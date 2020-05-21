package dbbg2.view.controllers.item.search;

import dbbg2.data.inventory.Book;
import dbbg2.data.inventory.InventoryItem;
import dbbg2.data.inventory.InventoryManager;
import dbbg2.data.inventory.Keyword;
import dbbg2.data.inventory.itemCategory.ItemCategory;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
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

    //Film search info
    public Button btnSearchBook;
    public Button btnClearFilmText;
    public Button btnSearchFilm;
    public TextField txFilmTitle;
    public TableColumn tcFilmDirector;
    public TableColumn tcFilmTitle;
    public TableColumn tcFilmId;
    public TableView tbFilmList;
    public TextField txtOriginCountry;
    public TableColumn tcCountryFilm;


    public void handleSearchBookClick(ActionEvent actionEvent) {
        tblBookList.setItems(FXCollections.observableArrayList(InventoryManager.getBooks(txtItemTitle.getText(), txtAuthor.getText())));
    }

    //ItemCategory.getDefaultItemCategory(OTHER_BOOKS).getItemCategoryTitle()

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        //Column values for books
        tcTitle.setCellValueFactory(new PropertyValueFactory <InventoryItem, String>("title"));
        tcCategory.setCellValueFactory(new PropertyValueFactory<ItemCategory, String>("category"));
        //tcKeyword.setCellValueFactory(new PropertyValueFactory<Keyword, String>("keyword"));
        tcInventoryId.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("invId"));
        tcAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("authors"));


        //Column values for films
        tcFilmDirector.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("director"));
        tcFilmTitle.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("title"));
        tcFilmId.setCellValueFactory(new PropertyValueFactory<InventoryItem, Integer>("invId"));
        tcCountryFilm.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("originCountry"));



        tcCategory.setCellValueFactory(new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures param) {
                return null;
            }
        })

        tcCategory.setCellValueFactory(new Callback<ListView<ItemCategory>, ListCell<ItemCategory>>(){
            @Override
            public ListCell<ItemCategory> call(ListView<ItemCategory> param) {
                return new ListCell<ItemCategory>(){
                    @Override
                    protected void updateItem(ItemCategory item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            this.setGraphic(null);
                        } else {
                            this.setText(item.getItemCategoryTitle());
                        }
                    }
                };
            }
        });
    }

    public void handleClearText(ActionEvent actionEvent) {
        txtItemTitle.clear();
        txtAuthor.clear();
    }

    public void handleSearchFilmClick(ActionEvent actionEvent) {
        //tbFilmList.setItems(FXCollections.observableArrayList(InventoryManager.getFilms(txFilmTitle.getText())));

        tbFilmList.setItems(FXCollections.observableArrayList(InventoryManager.getFilms(txFilmTitle.getText(), txtOriginCountry.getText(), txtDirector.getText())));

        //String title, String originCountry, Integer invId, Integer ageLimit

    }

    public void handleClearTextFilm(ActionEvent actionEvent) {
        txFilmTitle.clear();
        txtOriginCountry.clear();
        txtDirector.clear();
    }

}