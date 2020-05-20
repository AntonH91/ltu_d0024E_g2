package dbbg2.view.controllers.item.Edit;

import dbbg2.data.inventory.*;
import dbbg2.data.inventory.itemCategory.ItemCategory;
import dbbg2.data.users.visitorcategory.VisitorCategory;
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
    
    
    //Edit films
    public TableView tvFilmsFound;
    public TableColumn tcFilmTitle;
    public TableColumn tcFilmId;
    public TextField txtFilmSearch;
    public TextField txtNewFilmTitle;
    public ComboBox<Integer> cbNewAgeLimit;
    public Button btnFindFilm;
    public TextField txtNewDirector;
    public TextField txtNewOriginCountry;
    public TextField txtFilmId;


    protected BookController bookController;

    @Override
public void initialize(URL location, ResourceBundle resources) {
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

        //related to books
        clBookTitle.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("title"));
        clBookId.setCellValueFactory(new PropertyValueFactory<InventoryItem, Integer>("invId"));



        clAuthorLastName.setCellValueFactory(new PropertyValueFactory<Book, String>("lastName"));
        clAuthorLastName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures param) {
                return null;
            }
        });

        cbNewItemCategory.setButtonCell(cbNewItemCategory.getCellFactory().call(null));

        ArrayList<ItemCategory> catArray = new ArrayList<>();
        catArray.add(ItemCategory.getDefaultItemCategory(OTHER_BOOKS));
        catArray.add(ItemCategory.getDefaultItemCategory(JOURNAL));
        catArray.add(ItemCategory.getDefaultItemCategory(REFERENCE_LITERATURE));



        cbNewItemCategory.setItems(FXCollections.observableList(catArray));
        //cbNewItemCategory.getItems().addAll(ItemCategory.getDefaultItemCategory(OTHER_BOOKS), ItemCategory.getDefaultItemCategory(FILM), ItemCategory.getDefaultItemCategory(REFERENCE_LITERATURE));

        //Related to films
        tcFilmTitle.setCellValueFactory(new PropertyValueFactory<Film, String>("title"));

        cbNewAgeLimit.getItems().addAll(17, 18, 20);


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
            bc.amendInformationBook(txtNewBookTitle.getText(), txtNewIsbn.getText(), newAuthorLastName.getText(), cbNewItemCategory.getValue());

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



            selectCategory(selectedBook.getCategory());

            txtNewBookTitle.setText(selectedBook.getTitle());
            txtNewIsbn.setText(String.valueOf(selectedBook.getInvId()));
            //Selectedbookg getCategory as parameter


            //cbNewItemCategory.setValue(selectedBook.getCategory());
            //txtNewAuthorFirstName.setText(selectedBook.getAuthors());
            newAuthorLastName.setText(selectedBook.getAuthors());



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

    private void selectCategory(ItemCategory targetCategory) {
        if (targetCategory != null) {
            for (ItemCategory ic : cbNewItemCategory.getItems()) {
                if (targetCategory.equals(ic)) {
                    cbNewItemCategory.setValue(ic);
                    break;
                }
            }
        }

    }


    public void handleFindFilm(ActionEvent actionEvent) {
        tvFilmsFound.setItems(FXCollections.observableArrayList(InventoryManager.getFilms(txtFilmSearch.getText())));

    }

    public void handleUpdateFilm(ActionEvent actionEvent) {
        EntityManager em = JpaPersistence.getEntityManager();

        EntityTransaction entityTransaction = null;

        try {
            entityTransaction = em.getTransaction();
            entityTransaction.begin();

            Film film = em.find(Film.class, Integer.parseInt(txtFilmId.getText()));
            FilmController fc = new FilmController();
            fc.setFilm(film);
            fc.ammendInformationFilm(txtNewFilmTitle.getText(), cbNewAgeLimit.getValue(), txtNewDirector.getText(), txtNewOriginCountry.getText());

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


    public void handleClickedFilm(MouseEvent mouseEvent) {

        if (tvFilmsFound.getSelectionModel().getSelectedItem() != null) {
            Film selectedFilm = (Film) tvFilmsFound.getSelectionModel().getSelectedItem();


            txtNewFilmTitle.setText(selectedFilm.getTitle());
            txtFilmId.setText(String.valueOf(selectedFilm.getInvId()));
            txtNewDirector.setText(selectedFilm.getDirector());
            txtNewOriginCountry.setText(selectedFilm.getOriginCountry());


            //Selectedbookg getCategory as parameter


            //cbNewItemCategory.setValue(selectedFilm.getCategory());
            //txtNewAuthorFirstName.setText(selectedFilm.getAuthors());
            //newAuthorLastName.setText(selectedFilm.getAuthors());

        }

    }
}
