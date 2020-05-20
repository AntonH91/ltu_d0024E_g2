package dbbg2.view.controllers.item.Edit;

import dbbg2.data.inventory.*;
import dbbg2.data.inventory.itemCategory.ItemCategory;
import javafx.collections.FXCollections;
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


    protected BookController bookController;

    @Override
public void initialize(URL location, ResourceBundle resources) {
        clBookTitle.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("title"));
        clBookId.setCellValueFactory(new PropertyValueFactory<InventoryItem, Integer>("invId"));

        cbNewItemCategory.getItems().addAll(ItemCategory.getDefaultItemCategory(OTHER_BOOKS), ItemCategory.getDefaultItemCategory(FILM));


        }

    public void handleMakeChanges (ActionEvent actionEvent) {
        saveBook();
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

    public void saveBook(){
        //TODO fix author split
        //List<String> authors = new ArrayList<>(Arrays.asList(txtAreaAuthor.getText().split("\n")));

        bookController.ammendInformationBook(txtNewBookTitle.getText(),
                (ItemCategory) cbNewItemCategory.getSelectionModel().getSelectedItem(),
                txtNewIsbn.getText());

        bookController.saveChanges();
    }
}
