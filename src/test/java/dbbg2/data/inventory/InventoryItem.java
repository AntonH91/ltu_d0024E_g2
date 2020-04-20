package data.inventory;

import data.inventory.Book;
import data.inventory.Film;
import data.inventory.InventoryCopy;
import data.inventory.ItemCategory;

import java.util.ArrayList;

public abstract class InventoryItem {

    protected int inventoryId;
    protected String title ;
    protected ArrayList<String> keyword = new ArrayList<String>();
    protected ArrayList<InventoryCopy> copies = new ArrayList<>();
    protected ItemCategory category;
    protected boolean isAvailable;


    // Setters
    public void setInventoryId(int inventoryId){
        this.inventoryId = inventoryId;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setCategory(ItemCategory category){
        this.category = category;
    }

    public void setAvailable(boolean isAvailable){
        this.isAvailable = isAvailable;
    }


// Getters


    public int getInventoryId() {
        return inventoryId;
    }

    public String getTitle() {
        return title;
    }

    public ItemCategory getCategory() {
        return category;
    }

    public ArrayList<String> getKeyword() {
        return keyword;
    }

}
