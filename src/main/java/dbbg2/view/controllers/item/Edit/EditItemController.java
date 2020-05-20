package dbbg2.view.controllers.item.Edit;

import dbbg2.data.inventory.*;
import dbbg2.data.inventory.itemCategory.ItemCategory;
import dbbg2.data.users.visitorcategory.VisitorCategory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.net.URL;
import java.util.*;

import static dbbg2.data.inventory.itemCategory.ItemCategoryType.*;

public class EditItemController implements Initializable {

    public TextField txtSearchTitle;
    public TextField txtNewBookTitle;
    public ComboBox<ItemCategory> cbNewItemCategory;
    public ChoiceBox cbNewIsAvailable;
    public TextField txtNewIsbn;
    public Button btnFindBook;
    public Button btnUpdateBook;
    public TableView tblBooksFound;
    public TableColumn clBookTitle;
    public TableColumn clBookId;
    public TextField txtBookId;
    public TextField newAuthorLastName;
    public TextField txtNewAuthorFirstName;
    public TableColumn clAuthorLastName;


    protected BookController bookController;

    @Override
public void initialize(URL location, ResourceBundle resources) {
        clBookTitle.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("title"));
        clBookId.setCellValueFactory(new PropertyValueFactory<InventoryItem, Integer>("invId"));




        cbNewItemCategory.getItems().addAll(ItemCategory.getDefaultItemCategory(OTHER_BOOKS), ItemCategory.getDefaultItemCategory(FILM), ItemCategory.getDefaultItemCategory(REFERENCE_LITERATURE));


        clAuthorLastName.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("lastName"));


        cbNewItemCategory.setCellFactory(new Callback<ListView<ItemCategory>, ListCell<ItemCategory>>() {
            @Override
            public ListCell<ItemCategory> call(ListView<ItemCategory> param) {
                return new ListCell<ItemCategory>() {
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

        cbNewItemCategory.setButtonCell(cbNewItemCategory.getCellFactory().call(null));

        }

    public void handleMakeChanges (ActionEvent actionEvent) {
        saveBook();
    }


    public void handleFindBook(ActionEvent actionEvent) {
        tblBooksFound.setItems(FXCollections.observableArrayList(InventoryManager.getBooks(txtSearchTitle.getText(), txtBookId.getText())));
    }

    @FXML
    public void handleMouseClicked(MouseEvent mouseEvent) {

        //List<String> authors = new ArrayList<>(Arrays.asList(txtNewAuthorFirstName.getText().split("\n")));

        if (tblBooksFound.getSelectionModel().getSelectedItem() != null) {
            Book selectedBook = (Book) tblBooksFound.getSelectionModel().getSelectedItem();





            txtNewBookTitle.setText(selectedBook.getTitle());
            txtNewIsbn.setText(String.valueOf(selectedBook.getInvId()));
            cbNewItemCategory.setValue(selectedBook.getCategory());
            //txtNewAuthorFirstName.setText(selectedBook.getAuthors());

        }

    }

    public void saveBook(){
        //TODO fix author split
        //List<String> authors = new ArrayList<>(Arrays.asList(txtAreaAuthor.getText().split("\n")));

        List<Book> book = new ArrayList<>();
        bookController.ammendInformationBook(txtNewBookTitle.getText(),
                (ItemCategory) cbNewItemCategory.getSelectionModel().getSelectedItem(),
                txtNewIsbn.getText());

        bookController.saveChangesBook();
    }

    public void saveAuthor(){
        bookController.ammendAuthorInformation(txtNewAuthorFirstName.getText(), txtNewAuthorFirstName.getText());
        bookController.saveChangesAuthor();
    }

}
