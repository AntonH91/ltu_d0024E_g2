package dbbg2.view.item.Edit;

import dbbg2.data.inventory.Book;
import dbbg2.data.inventory.Film;
import dbbg2.data.inventory.InventoryItem;
import dbbg2.data.inventory.InventoryManager;
import dbbg2.data.inventory.itemCategory.ItemCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.*;

import static dbbg2.data.inventory.itemCategory.ItemCategoryType.FILM;
import static dbbg2.data.inventory.itemCategory.ItemCategoryType.OTHER_BOOKS;

public class EditItemController implements Initializable {

    public TextField txtSearchTitle;
    public TextField txtNewBookTitle;
    public ChoiceBox cbNewItemCategory;
    public ChoiceBox cbNewIsAvailable;
    public TextField txtNewIsbn;
    public TextField txtNewAuthor;
    public Button btnFindBook;
    public Button btnUpdateBook;
    public TableView tblBooksFound;
    public TableColumn clBookTitle;
    public TableColumn clBookId;
    public TextField txtBookId;
    public TextArea txtAreaAuthor;


    @Override
public void initialize(URL location, ResourceBundle resources) {
        clBookTitle.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("title"));
        clBookId.setCellValueFactory(new PropertyValueFactory<InventoryItem, Integer>("invId"));

        cbNewItemCategory.getItems().addAll(ItemCategory.getDefaultItemCategory(OTHER_BOOKS), ItemCategory.getDefaultItemCategory(FILM));


        }

    public void handleMakeChanges (ActionEvent actionEvent) {

    }

    public void handleFindBook(ActionEvent actionEvent) {
        tblBooksFound.setItems(FXCollections.observableArrayList(InventoryManager.getBooks(txtSearchTitle.getText(), txtBookId.getText())));
    }

    @FXML
    public void handleMouseClicked(MouseEvent mouseEvent) {

        if (tblBooksFound.getSelectionModel().getSelectedItem() != null) {
            Book selectedBook = (Book) tblBooksFound.getSelectionModel().getSelectedItem();
            txtNewBookTitle.setText(selectedBook.getTitle());
            txtNewIsbn.setText(String.valueOf(selectedBook.getInvId()));
            cbNewItemCategory.setValue(selectedBook.getCategory());


        }

    }
}
