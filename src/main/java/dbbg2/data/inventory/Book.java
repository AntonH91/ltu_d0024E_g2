package dbbg2.data.inventory;

import dbbg2.data.inventory.itemCategory.ItemCategory;
import dbbg2.data.inventory.itemCategory.ItemCategoryType;
import dbbg2.persistence.Database;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;

@Entity
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

            /*final String IS_AVAILABLE = "UPDATE InventoryItem SET is_available = ? WHERE inventory_id = ?";
            PreparedStatement pstAvailable = Database.getDefaultInstance().getPreparedStatement(IS_AVAILABLE);
            pstAvailable.setString(2, inventoryId);

            // If there are results where on_loan = false (meaning that there are books available) set the availability of the item to "available"
            if(resultSet.next()){
                //Get the value of the inventory ID from the table that was previously input in the system so that you do not have to enter it again
                final String BOOK_AVAILABLE = "UPDATE InventoryItem SET is_available = true WHERE inventory_id = ?";
                pst.setString(1, inventoryId);
                PreparedStatement pst2 = Database.getDefaultInstance().getPreparedStatement(BOOK_AVAILABLE);
                pst.executeQuery(BOOK_AVAILABLE);
            }
            // If there are no results where on_loan = false (meaning that there are no books available) set the availability of the item to "unavailable"
            else {
                //Get the value of the inventory ID from the table that was previously input in the system so that you do not have to enter it again
                String bookInventoryId = resultSet.getString("inventory_id");
                final String BOOK_UNAVAILABLE = "UPDATE InventoryItem SET is_available = false WHERE inventory_id = " + bookInventoryId;
                PreparedStatement pst3 = Database.getDefaultInstance().getPreparedStatement(BOOK_UNAVAILABLE);
                pst.executeQuery(BOOK_UNAVAILABLE);
            }*/
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }


}