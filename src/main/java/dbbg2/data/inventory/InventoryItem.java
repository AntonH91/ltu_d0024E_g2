package dbbg2.data.inventory;

import dbbg2.data.inventory.itemCategory.ItemCategory;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.ArrayList;

public abstract class InventoryItem {

    private String inventoryId;
    private String title;
    private ArrayList<String> keyword = new ArrayList<String>();
    private ArrayList<InventoryCopy> copies = new ArrayList<>();
    private ItemCategory category;
    private boolean isAvailable;

    private static int nextInventoryId = 10001;

    public InventoryItem(String title, ItemCategory category, boolean isAvailable) {
        this.inventoryId = generateInventoryId();
        this.title = title;
        this.category = category;
        this.isAvailable = isAvailable;
    }



    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public int getInventoryId() {
        return nextInventoryId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ItemCategory getCategory() {
        return category;
    }

    public ArrayList<InventoryCopy> getCopies() {
        return copies;
    }

    public boolean isAvailable() {
        return isAvailable;
    }



    public void setCategory(ItemCategory category) {
        this.category = category;
    }

    public ArrayList<String> getKeyword() {
        return keyword;
    }

    private String generateInventoryId() {
        String newInventoryId = "I" + Integer.toString(nextInventoryId);
        nextInventoryId++;
        return newInventoryId;
    }

}
