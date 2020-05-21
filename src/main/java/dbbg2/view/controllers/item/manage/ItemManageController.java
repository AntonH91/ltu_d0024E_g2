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
import javafx.util.Callback;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static dbbg2.data.inventory.itemCategory.ItemCategoryType.*;
import static dbbg2.data.inventory.itemCategory.ItemCategoryType.REFERENCE_LITERATURE;


public class ItemManageController implements Initializable {


    // Add books
    public TextField txtAbbBookTitle;
    public TextField txtBookIsbn;
    public TextField txtBookAuthor;


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

    public ComboBox<ItemCategory> cbCategoryTest;

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

    public ComboBox<Integer> cbAgeLimits;



    public void handleAddBook(javafx.event.ActionEvent actionEvent) {

        List<Book> books = new ArrayList<>();


        //TODO add two text fields for first and last name
        books.add(new Book(txtAbbBookTitle.getText(), (ItemCategory) cbCategoryTest.getSelectionModel().getSelectedItem(), true, txtBookIsbn.getText(), txtBookAuthor.getText()));

        if(txtAbbBookTitle.getText().isEmpty() || cbCategoryTest.getSelectionModel().isEmpty()){

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
            cbCategoryTest.valueProperty().set(null);
            txtBookIsbn.clear();
            txtBookAuthor.clear();


            return;

        }
    }


        public void handleAddFilm(ActionEvent actionEvent) {
            List<Film> film = new ArrayList<>();

            film.add(new Film(txtAbbFilmTitle.getText(), ItemCategory.getDefaultItemCategory(FILM), true, txtFilmDirector.getText(), cbAgeLimits.getValue(), txtOriginCountry.getText()));

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
        cbCategoryTest.setCellFactory(new Callback<ListView<ItemCategory>, ListCell<ItemCategory>>() {
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

        cbCategoryTest.setButtonCell(cbCategoryTest.getCellFactory().call(null));

        ArrayList<ItemCategory> catArray = new ArrayList<>();
        catArray.add(ItemCategory.getDefaultItemCategory(OTHER_BOOKS));
        catArray.add(ItemCategory.getDefaultItemCategory(JOURNAL));
        catArray.add(ItemCategory.getDefaultItemCategory(REFERENCE_LITERATURE));

        cbCategoryTest.setItems(FXCollections.observableList(catArray));


        //Set choices for age limits when adding film
        cbAgeLimits.getItems().addAll(17, 18, 20);

        tcBookTitle.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("title"));
        tcInventoryId.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("authors"));
        tcInvId.setCellValueFactory(new PropertyValueFactory<InventoryItem, Integer>("invId"));

        tcFilmTitle.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("title"));
        tcOriginCountry.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("originCountry"));
        tcAgeLimit.setCellValueFactory(new PropertyValueFactory<InventoryItem, Integer>("ageLimit"));
        tcDirector.setCellValueFactory(new PropertyValueFactory<Film, String>("director"));




    }


    public void handleFindBook(ActionEvent actionEvent) {
        tblListBooks.setItems(FXCollections.observableArrayList(InventoryManager.getBooks(txtRemoveTitle.getText(), txtRemoveInventoryId.getText())));

    }

    public void handleSearchFilm(ActionEvent actionEvent) {
        tblFilmsFound.setItems(FXCollections.observableArrayList(InventoryManager.getFilms(txtFilmTitleRemove.getText())));

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
            txtPkId.clear();

            tblListBooks.setItems(FXCollections.observableArrayList(InventoryManager.getBooks(txtRemoveTitle.getText(), txtRemoveInventoryId.getText())));

        } catch (RuntimeException e) {
            if(entityTransaction.isActive())
                entityTransaction.rollback();
            throw e;
        }


    }

    @FXML
    private void handleTableViewMouseClickedAction(){
        if(tblListBooks.getSelectionModel().getSelectedItem() !=null){
            Book selectedBook = (Book) tblListBooks.getSelectionModel().getSelectedItem();
            txtRemoveTitle.setText(selectedBook.getTitle());
            txtRemoveInventoryId.setText(selectedBook.getAuthors());
            txtPkId.setText(String.valueOf(selectedBook.getInvId()));
            txtRemoveIsbn.setText(selectedBook.getIsbn());

        }
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

            txtIdFoundRemoveFilm.clear();
            txtFilmTitleRemove.clear();

            tblFilmsFound.setItems(FXCollections.observableArrayList(InventoryManager.getFilms(txtFilmTitleRemove.getText())));

            return;


        } catch (RuntimeException e) {
            if(entityTransaction.isActive())
                entityTransaction.rollback();
            throw e;
        }



    }



    public void handleClickedFilmRemove(MouseEvent mouseEvent) {
        if(tblFilmsFound.getSelectionModel().getSelectedItem() !=null){
            Film selectedFilm = (Film) tblFilmsFound.getSelectionModel().getSelectedItem();
            txtFilmTitleRemove.setText(selectedFilm.getTitle());
            txtIdFoundRemoveFilm.setText(String.valueOf(selectedFilm.getInvId()));

        }


    }

    private void selectCategory(ItemCategory targetCategory) {
        if (targetCategory != null) {
            for (ItemCategory ic : cbCategoryTest.getItems()) {
                if (targetCategory.equals(ic)) {
                    cbCategoryTest.setValue(ic);
                    break;
                }
            }
        }

    }

}
