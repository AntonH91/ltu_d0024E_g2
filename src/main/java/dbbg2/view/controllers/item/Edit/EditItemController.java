package dbbg2.view.controllers.item.Edit;

import dbbg2.data.inventory.*;
import dbbg2.data.inventory.itemCategory.ItemCategory;
import dbbg2.utils.persistence.JpaPersistence;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.net.URL;
import java.sql.PreparedStatement;
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


        clAuthorLastName.setCellValueFactory(new PropertyValueFactory<Book, String>("lastName"));
        clAuthorLastName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures param) {
                return null;
            }
        });


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

        //bookController.ammendInformationBook(txtNewBookTitle.getText(), txtNewIsbn.getText());

        //saveBook();

        //updateBook(txtNewBookTitle.getText(), txtNewIsbn.getText());

        EntityManager em = JpaPersistence.getEntityManager();

        EntityTransaction entityTransaction = null;

        try {
            entityTransaction = em.getTransaction();
            entityTransaction.begin();

            Book book = em.find(Book.class, Integer.parseInt(txtNewIsbn.getText()));
            BookController bc = new BookController();
            bc.setBook(book);
            bc.amendInformationBook(txtNewBookTitle.getText(), txtNewIsbn.getText());

            entityTransaction.commit();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("The book has been updated");
            alert.showAndWait();


            return;


        } catch (RuntimeException e) {
            if(entityTransaction.isActive())
                entityTransaction.rollback();
            throw e;
        }

    }




        //updateBook(txtNewBookTitle.getText(), txtNewIsbn.getText());




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

        //bookController.amendInformationBook(txtNewBookTitle.getText(), txtNewIsbn.getText());

        //updateBook();

        /*Set<Book> book = new HashSet<>();

        bookController.ammendInformationBook(txtNewBookTitle.getText(),
                //(ItemCategory) cbNewItemCategory.getSelectionModel().getSelectedItem(),
                txtNewIsbn.getText());

        bookController.saveChangesBook();*/
    }


    /*public void updateBook(String title, String isbn){

        //bookController.amendInformationBook(title, isbn);
        bookController.saveChanges();



    }*/

}
