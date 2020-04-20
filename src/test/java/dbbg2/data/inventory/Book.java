package data.inventory;

import java.util.ArrayList;
import java.util.Collections;

public class Book extends InventoryItem {

    ArrayList<String> author = new ArrayList<String>();
    String isbn;

    private Book(int inventoryId, String title, ArrayList<String> bookKeyword, ItemCategory category, String isbn,  ArrayList<String> bookAuthor) {

        this.category = category;
        this.inventoryId = inventoryId;
        this.title = title;
        this.isbn = isbn;
        author = bookAuthor;
        keyword = bookKeyword;
    }


// Setters

    public void setIbsn(){
        this.isbn = isbn;
}
// Getters

    public String getIsbn(){
        return isbn;
    }

    public ArrayList<String> getAuthor() {
        return author;
    }

    @Override
    public ArrayList<String> getKeyword() {
        return keyword;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getInventoryId() {
        return inventoryId;
    }

    @Override
    public ItemCategory getCategory() {
        return category;
    }
}

