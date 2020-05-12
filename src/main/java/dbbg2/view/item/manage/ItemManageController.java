package dbbg2.view.item.manage;

import dbbg2.data.inventory.Book;
import dbbg2.data.inventory.InventoryItem;
import dbbg2.data.inventory.InventoryManager;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;


public class ItemManageController implements Initializable {

    public TextField txtAbbBookTitle;
    public TextField txtBookIsbn;
    public TextField txtBookAuthor;
    public ChoiceBox ddBookCategory;




    //Fix add book
    public void handleAddBook(ActionEvent actionEvent){
    }


    @Override
    public void initialize(URL location, ResourceBundle resources){

    }

}
