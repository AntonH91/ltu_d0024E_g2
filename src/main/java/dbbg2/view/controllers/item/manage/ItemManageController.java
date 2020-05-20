package dbbg2.view.controllers.item.manage;

import dbbg2.data.inventory.*;
import dbbg2.data.inventory.itemCategory.ItemCategory;
import dbbg2.utils.persistence.JpaPersistence;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static dbbg2.data.inventory.itemCategory.ItemCategoryType.FILM;
import static dbbg2.data.inventory.itemCategory.ItemCategoryType.OTHER_BOOKS;


public class ItemManageController implements Initializable {


    // Add books
    public TextField txtAbbBookTitle;
    public TextField txtBookIsbn;
    public TextField txtBookAuthor;

    public ChoiceBox ddBookCategory;


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

    //Film info
    public TextField txtFilmIdRemove;
    public TextField txtIdFoundRemoveFilm;
    public Button btnRemoveFilm;
    public Button btnSearchFilm;
    public TableColumn tcOriginCountry;
    public TableColumn tcAgeLimit;
    public TableColumn tcDirector;
    public TableColumn tcFilmTitle;
    public TextField txtFilmTitleRemove;
    public TableView tblFilmsFound;
    public TextField txtAbbFilmTitle;
    public TextField txtOriginCountry;
    public TextField txtFilmDirector;

    public ComboBox cbAgeLimits;


    public void handleAddBook(javafx.event.ActionEvent actionEvent) {

        List<Book> books = new ArrayList<>();


        //TODO add two text fields for first and last name
        books.add(new Book(txtAbbBookTitle.getText(), (ItemCategory) ddBookCategory.getSelectionModel().getSelectedItem(), true, txtBookIsbn.getText(), new Author (txtBookAuthor.getText(), "")));

        if(txtAbbBookTitle.getText().isEmpty() || ddBookCategory.getSelectionModel().isEmpty()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Cannot create new Book without title or category");
            alert.showAndWait();
            return;
        }

        else {

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

            film.add(new Film(txtAbbFilmTitle.getText(), FILM, true, txtFilmDirector.getText(), cbAgeLimits.getSelectionModel().getSelectedIndex(), txtOriginCountry.getText()));

            if(txtAbbFilmTitle.getText().isEmpty() || cbAgeLimits.getSelectionModel().isEmpty()){

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Cannot create new Film without title or age limit");
                alert.showAndWait();
                return;
            }
            else {

                int index = 0;
                EntityManager em = JpaPersistence.getEntityManager();

                em.getTransaction().begin();
                for (Film f : film) {
                    //b.addCopy(String.valueOf(index), "A Shelf");
                    index++;
                    em.merge(f);
                }

                em.getTransaction().commit();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("The film has been added");
                alert.showAndWait();

                txtAbbFilmTitle.clear();
                cbAgeLimits.valueProperty().set(null);
                txtOriginCountry.clear();
                txtFilmDirector.clear();

                return;

            }

        }



    @Override
    public void initialize(URL location, ResourceBundle resources){

        ddBookCategory.getItems().addAll(ItemCategory.getDefaultItemCategory(OTHER_BOOKS));

        //Set choices for age limits when adding film
        cbAgeLimits.getItems().addAll(17, 18, 20);


        tcBookTitle.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("title"));
        tcInventoryId.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("inventoryId"));

        tcFilmTitle.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("title"));
        tcOriginCountry.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("title"));


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
        } catch (RuntimeException e) {
            if(entityTransaction.isActive())
                entityTransaction.rollback();
            throw e;
        }


        /*int selectedBook = Integer.parseInt(txtPkId.getText());

        em.getTransaction().begin();
        em.remove(selectedBook);
        em.getTransaction().commit();*/
    }

    @FXML
    private void handleTableViewMouseClickedAction(){
        if(tblListBooks.getSelectionModel().getSelectedItem() !=null){
            Book selectedBook = (Book) tblListBooks.getSelectionModel().getSelectedItem();
            txtRemoveTitle.setText(selectedBook.getTitle());
            txtRemoveInventoryId.setText(selectedBook.getInventoryId());
            txtPkId.setText(String.valueOf(selectedBook.getInvId()));

        }
    }

    public void handleRemoveFilm(ActionEvent actionEvent) {
    }

    public void handleSearchFilm(ActionEvent actionEvent) {
        tblFilmsFound.setItems(FXCollections.observableArrayList(InventoryManager.getFilms(txtFilmTitleRemove.getText())));

    }

    public void handleClickedFilmRemove(MouseEvent mouseEvent) {
        if(tblFilmsFound.getSelectionModel().getSelectedItem() !=null){
            Book selectedBook = (Book) tblFilmsFound.getSelectionModel().getSelectedItem();
            txtFilmTitleRemove.setText(selectedBook.getTitle());
            txtIdFoundRemoveFilm.setText(String.valueOf(selectedBook.getInvId()));
        }


    }
}
