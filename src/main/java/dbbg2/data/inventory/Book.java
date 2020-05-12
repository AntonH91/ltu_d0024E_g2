package dbbg2.data.inventory;

import dbbg2.data.inventory.itemCategory.ItemCategory;
import dbbg2.data.inventory.itemCategory.ItemCategoryType;
import dbbg2.utils.persistence.Database;
import dbbg2.utils.persistence.JpaPersistence;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.sql.*;
import java.util.ArrayList;

@Entity(name = "Book")
public class Book extends InventoryItem {


    ArrayList<String> author = new ArrayList<String>();
    @Basic(optional = false)
    String isbn;

    public Book() {
        super();
    }

    public Book(String title, ItemCategoryType category, boolean isAvailable, String isbn, String author){
        this(title, ItemCategory.getDefaultItemCategory(category), isAvailable, isbn, author);
    }

    public Book(String title, ItemCategory category, boolean isAvailable, String isbn, String author) {
        super(title, category, isAvailable);
        this.isbn = isbn;
        this.author.add(author);
    }

// Setters

    public void setIsbn(){
        this.isbn = isbn;
}
// Getters

    public String getIsbn(){
        return isbn;
    }

    public ArrayList<String> getAuthor() {
        return author;
    }


    //Change availibility of book
    public void sqlBookUpdate(String inventoryId){
        try{
            final String UPDATE_ISAVAILABLE = "UPDATE InventoryItem SET is_available = inventory_id IN (SELECT inventory_id FROM InventoryCopy WHERE on_loan = 0 AND lendable = 1) WHERE inventory_id = ?";
            PreparedStatement pst = Database.getDefaultInstance().getPreparedStatement(UPDATE_ISAVAILABLE);
            pst.setString(1, inventoryId);
            pst.executeQuery();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    //Fix method for adding book to database
    public void addBookToDb(String title, ItemCategoryType category, boolean isAvailable, String isbn, String author){

    }




}