package dbbg2.data.inventory;

import dbbg2.data.inventory.itemCategory.ItemCategory;
import dbbg2.data.inventory.itemCategory.ItemCategoryType;
import dbbg2.utils.persistence.Database;

import javax.persistence.Basic;
import javax.persistence.Entity;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Entity(name = "Book")
public class Book extends InventoryItem {

    @Basic(optional = false)
    String isbn;
    private String authors = "";

    public Book() {
        super();
    }

    public Book(String title, ItemCategoryType category, boolean isAvailable, String isbn, String authors) {
        this(title, ItemCategory.getDefaultItemCategory(category), isAvailable, isbn, authors);
    }

    public Book(String title, ItemCategory category, boolean isAvailable, String isbn, String authors) {
        super(title, category, isAvailable);
        this.isbn = isbn;
        this.authors = authors;
    }

// Setters


    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getIsbn() {
        return isbn;
    }

// Getters

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    //Change availibility of book
    public void sqlBookUpdate(String inventoryId) {
        try {
            final String UPDATE_ISAVAILABLE = "UPDATE InventoryItem SET is_available = inventory_id IN (SELECT inventory_id FROM InventoryCopy WHERE on_loan = 0 AND lendable = 1) WHERE inventory_id = ?";
            PreparedStatement pst = Database.getDefaultInstance().getPreparedStatement(UPDATE_ISAVAILABLE);
            pst.setString(1, inventoryId);
            pst.executeQuery();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    //Fix method for adding book to database
    public void addBookToDb(String title, ItemCategoryType category, boolean isAvailable, String isbn, String author) {

    }


}