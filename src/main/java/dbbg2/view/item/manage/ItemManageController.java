package dbbg2.view.item.manage;

import dbbg2.data.inventory.Book;
import dbbg2.data.inventory.Film;
import dbbg2.data.inventory.InventoryItem;
import dbbg2.data.inventory.InventoryManager;
import dbbg2.data.inventory.itemCategory.ItemCategory;
import dbbg2.utils.persistence.JpaPersistence;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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

    //Add film
    public TextField txtAbbFilmTitle;
    public TextField txtOriginCountry;
    public TextField txtFilmDirector;

    public ComboBox cbAgeLimit;

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


    public void handleAddBook(javafx.event.ActionEvent actionEvent) {

        List<Book> books = new ArrayList<>();


        books.add(new Book(txtAbbBookTitle.getText(), (ItemCategory) ddBookCategory.getSelectionModel().getSelectedItem(), true, txtBookIsbn.getText(), txtBookAuthor.getText()));

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

            film.add(new Film(txtAbbFilmTitle.getText(), FILM, true, txtFilmDirector.getText(), cbAgeLimit.getSelectionModel().getSelectedIndex(), txtOriginCountry.getText()));

            if(txtAbbFilmTitle.getText().isEmpty() || cbAgeLimit.getSelectionModel().isEmpty()){

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
                cbAgeLimit.valueProperty().set(null);
                txtOriginCountry.clear();
                txtFilmDirector.clear();

                return;

            }

        }



    @Override
    public void initialize(URL location, ResourceBundle resources){

        ddBookCategory.getItems().addAll(ItemCategory.getDefaultItemCategory(OTHER_BOOKS));

        //Set choices for age limits when adding film
        cbAgeLimit.getItems().addAll(17, 18, 20);


        tcBookTitle.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("title"));
        tcInventoryId.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("inventoryId"));

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

}
