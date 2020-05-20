package dbbg2.data.inventory;

import dbbg2.data.inventory.itemCategory.ItemCategory;
import dbbg2.data.inventory.itemCategory.ItemCategoryType;
import dbbg2.utils.persistence.Database;

import javax.persistence.*;
import java.sql.*;
import java.util.*;

@Entity(name = "Book")
public class Book extends InventoryItem {


    @ManyToMany(mappedBy = "books", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Author> authors = new HashSet<>();

    @Basic(optional = false)
    String isbn;

    public Book() {
        super();
    }

    public Book(String title, ItemCategoryType category, boolean isAvailable, String isbn, Author authors){
        this(title, ItemCategory.getDefaultItemCategory(category), isAvailable, isbn, authors);
    }

    public Book(String title, ItemCategory category, boolean isAvailable, String isbn, Author authors) {
        super(title, category, isAvailable);
        this.isbn = isbn;
        addAuthor(authors);
    }

// Setters

    public void addAuthor(Author author){
        authors.add(author);
        author.addBook(this);
    }

    public void removeAuthor(Author author){
        authors.remove(author);
        author.removeBook(this);
    }

    public void setIsbn(String isbn){
        this.isbn = isbn;
}

// Getters

    public String getIsbn(){
        return isbn;
    }


    public Set<Author> getAuthors() {
        Collections.unmodifiableSet(authors);
        return Collections.unmodifiableSet(authors);
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