package dbbg2.data.inventory;

import dbbg2.data.inventory.itemCategory.ItemCategory;

import java.util.ArrayList;

public class Book extends InventoryItem {

    ArrayList<String> author = new ArrayList<String>();
    String isbn;

    public Book(String title, ItemCategory category, boolean isAvailable, String isbn, String author) {
        super(title, category, isAvailable);
        this.isbn = isbn;
        this.author.add(author);
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


}

