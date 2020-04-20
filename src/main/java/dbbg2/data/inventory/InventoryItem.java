package dbbg2.data.inventory;

import dbbg2.data.inventory.InventoryCopy;
import dbbg2.data.inventory.ItemCategory;

import java.util.ArrayList;

public abstract class InventoryItem {

    protected int inventoryId;
    protected String title;
    protected ArrayList<String> keyword = new ArrayList<String>();
    protected ArrayList<InventoryCopy> copies = new ArrayList<>();
    protected ItemCategory category;
    protected boolean isAvailable;

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    // Setters
    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getTitle() {
        return title;
    }


// Getters

    public void setTitle(String title) {
        this.title = title;
    }

    public ItemCategory getCategory() {
        return category;
    }

    public void setCategory(ItemCategory category) {
        this.category = category;
    }

    public ArrayList<String> getKeyword() {
        return keyword;
    }

}
