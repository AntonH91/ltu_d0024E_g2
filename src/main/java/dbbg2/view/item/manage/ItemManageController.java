package dbbg2.view.item.manage;

import dbbg2.controllers.LibraryDbb;
import dbbg2.data.inventory.*;
import dbbg2.data.inventory.itemCategory.ItemCategory;
import dbbg2.data.inventory.itemCategory.ItemCategoryType;
import dbbg2.data.users.visitorcategory.VisitorCategory;
import dbbg2.utils.persistence.JpaPersistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.awt.*;
import java.math.BigInteger;
import java.net.URL;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static dbbg2.data.inventory.itemCategory.ItemCategoryType.*;


public class ItemManageController implements Initializable {


    // Add books
    public TextField txtAbbBookTitle;
    public TextField txtBookIsbn;
    public TextField txtBookAuthor;

    public ComboBox<ItemCategory> ddBookCategory;

    //Add film
    public TextField txtAbbFilmTitle;
    public TextField txtOriginCountry;
    public TextField txtFilmDirector;

    //public ComboBox cbAgeLimit;

    //Remove book
    public TextField txtRemoveTitle;
    public TextField txtRemoveInventoryId;
    public TextField txtRemoveIsbn;

    public TableColumn tcBookTitle;
    public TableColumn tcInventoryId;
    public TableColumn tcIsbn;

    public TableView tblListBooks;
    public Button btnSearch;
    public Button btnRemove;
    public TextField txtPkId;
    public TableColumn tcInvId;

    //Remove Films
    public TextField txtFilmTitleRemove;
    public TextField txtFilmIdRemove;
    public TextField txtIdFoundRemoveFilm;
    public TableView tblFilmsFound;
    public TableColumn tcFilmTitle;
    public TableColumn tcDirector;
    public Button btnSearchFilm;
    public Button btnRemoveFilm;
    public TableColumn tcOriginCountry;
    public TableColumn tcAgeLimit;
    public ChoiceBox ccbAgeLimit;


    public void handleAddBook(javafx.event.ActionEvent actionEvent) {

        List<Book> books = new ArrayList<>();


        books.add(new Book(txtAbbBookTitle.getText(), (ItemCategory) ddBookCategory.getSelectionModel().getSelectedItem(), true, txtBookIsbn.getText(), txtBookAuthor.getText()));

        if (txtAbbBookTitle.getText().isEmpty() || ddBookCategory.getSelectionModel().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Cannot create new Book without title or category");
            alert.showAndWait();
            return;
        } else {

            int index = 0;
            EntityManager em = JpaPersistence.getEntityManager();

            em.getTransaction().begin();
            for (Book b : books) {
                //b.addCopy(String.valueOf(index), "A Shelf");
                index++;
                em.merge(b);
            }

            em.getTransaction().commit();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("The book has been added");
            alert.showAndWait();

            txtAbbBookTitle.clear();
            ddBookCategory.valueProperty().set(null);
            txtBookIsbn.clear();
            txtBookAuthor.clear();


            return;

        }
    }


    public void handleAddFilm(ActionEvent actionEvent) {
        List<Film> film = new ArrayList<>();

        film.add(new Film(txtAbbFilmTitle.getText(), FILM, true, txtFilmDirector.getText(), Integer.parseInt(ccbAgeLimit.getValue().toString()), txtOriginCountry.getText()));

        if (txtAbbFilmTitle.getText().isEmpty() || ccbAgeLimit.getSelectionModel().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Cannot create new Film without title or age limit");
            alert.showAndWait();
            return;
        } else {

            int index = 0;
            EntityManager em = JpaPersistence.getEntityManager();

            em.getTransaction().begin();
            for (Film f : film) {
                index++;
                em.merge(f);
            }

            em.getTransaction().commit();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("The film has been added");
            alert.showAndWait();

            txtAbbFilmTitle.clear();
            txtOriginCountry.clear();
            txtFilmDirector.clear();

            return;

        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ddBookCategory.getItems().addAll(ItemCategory.getDefaultItemCategory(OTHER_BOOKS), ItemCategory.getDefaultItemCategory(JOURNAL), ItemCategory.getDefaultItemCategory(REFERENCE_LITERATURE));

        ddBookCategory.setCellFactory(new Callback<ListView<ItemCategory>, ListCell<ItemCategory>>(){
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






        //Set choices for age limits when adding film

        ccbAgeLimit.getItems().addAll(17, 18, 20);


        tcBookTitle.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("title"));
        tcInventoryId.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("inventoryId"));

        //Remove Film

        tcFilmTitle.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("title"));
        tcDirector.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("director"));
        tcOriginCountry.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("originCountry"));
        tcAgeLimit.setCellValueFactory(new PropertyValueFactory<InventoryCopy, BigInteger>("ageLimit"));


    }



    public void handleFindBook(ActionEvent actionEvent) {
        tblListBooks.setItems(FXCollections.observableArrayList(InventoryManager.getBooks(txtRemoveTitle.getText(), txtRemoveInventoryId.getText())));

    }

    public void handleRemovebook(ActionEvent actionEvent) {
        EntityManager em = JpaPersistence.getEntityManager();

        EntityTransaction entityTransaction = null;

        try {
            entityTransaction = em.getTransaction();
            entityTransaction.begin();

            InventoryItem invItem = em.find(InventoryItem.class, Integer.parseInt(txtPkId.getText()));
            em.remove(invItem);

            entityTransaction.commit();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("The book has been removed");
            alert.showAndWait();

            txtRemoveTitle.clear();
            txtRemoveInventoryId.clear();
            txtRemoveIsbn.clear();
            txtPkId.clear();

            tblListBooks.setItems(FXCollections.observableArrayList(InventoryManager.getBooks(txtRemoveTitle.getText(), txtRemoveInventoryId.getText())));

            return;


        } catch (RuntimeException e) {
            if (entityTransaction.isActive())
                entityTransaction.rollback();
            throw e;
        }


        /*int selectedBook = Integer.parseInt(txtPkId.getText());

        em.getTransaction().begin();
        em.remove(selectedBook);
        em.getTransaction().commit();*/
    }

    @FXML
    private void handleTableViewMouseClickedAction() {
        if (tblListBooks.getSelectionModel().getSelectedItem() != null) {
            Book selectedBook = (Book) tblListBooks.getSelectionModel().getSelectedItem();
            txtRemoveTitle.setText(selectedBook.getTitle());
            txtRemoveInventoryId.setText(selectedBook.getInventoryId());
            txtPkId.setText(String.valueOf(selectedBook.getInvId()));

        }
    }

    public void handleSearchFilm(ActionEvent actionEvent) {
        tblFilmsFound.setItems(FXCollections.observableArrayList(InventoryManager.getFilms(txtFilmTitleRemove.getText())));
    }


    public void handleRemoveFilm(ActionEvent actionEvent) {

        EntityManager em = JpaPersistence.getEntityManager();

        EntityTransaction entityTransaction = null;

        try {
            entityTransaction = em.getTransaction();
            entityTransaction.begin();

            InventoryItem invItem = em.find(InventoryItem.class, Integer.parseInt(txtIdFoundRemoveFilm.getText()));
            em.remove(invItem);

            entityTransaction.commit();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("The film has been removed");
            alert.showAndWait();

            txtFilmTitleRemove.clear();
            txtIdFoundRemoveFilm.clear();


            tblFilmsFound.setItems(FXCollections.observableArrayList(InventoryManager.getFilms(txtFilmTitleRemove.getText())));

            return;


        } catch (RuntimeException e) {
            if (entityTransaction.isActive())
                entityTransaction.rollback();
            throw e;
        }


        /*int selectedBook = Integer.parseInt(txtPkId.getText());

        em.getTransaction().begin();
        em.remove(selectedBook);
        em.getTransaction().commit();*/
    }


    public void handleClickedFilmRemove(MouseEvent mouseEvent) {
        if (tblFilmsFound.getSelectionModel().getSelectedItem() != null) {
            Film selectedFilm = (Film) tblFilmsFound.getSelectionModel().getSelectedItem();
            txtFilmTitleRemove.setText(selectedFilm.getTitle());
            txtIdFoundRemoveFilm.setText(String.valueOf(selectedFilm.getInvId()));


        }
    }
}
