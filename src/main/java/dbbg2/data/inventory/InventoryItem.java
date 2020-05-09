package dbbg2.data.inventory;

import dbbg2.data.inventory.itemCategory.ItemCategory;
import dbbg2.data.inventory.itemCategory.ItemCategoryType;
import dbbg2.persistence.Database;

import javax.persistence.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Inventory")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class InventoryItem {

    private static int nextInventoryId = 10001;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int invId;

    private String inventoryId = "";

    @Basic(optional = false)
    private String title = "";

    // TODO Make this attribute a ManyToMany
    @SuppressWarnings("JpaAttributeTypeInspection")
    @Basic(optional = false)
    private List<String> keyword = new ArrayList<String>();

    // Tried to fix the many to many table being created
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "item")
    private List<InventoryCopy> copies = new ArrayList<>();

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private ItemCategory category;
    @Basic(optional = false)
    private boolean isAvailable;

    public InventoryItem() {

    }

    public InventoryItem(String title, ItemCategoryType category, boolean isAvailable) {
        this(title, ItemCategory.getDefaultItemCategory(category), isAvailable);
    }

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

    public List<InventoryCopy> getCopies() {
        return copies;
    }

    public boolean isAvailable() {
        return isAvailable;
    }


    public void setCategory(ItemCategory category) {
        this.category = category;
    }

    public List<String> getKeyword() {
        return keyword;
    }

    private String generateInventoryId() {
        String newInventoryId = "I" + Integer.toString(nextInventoryId);
        nextInventoryId++;
        return newInventoryId;
    }

    public void addCopy(String barcode, String location) {
        InventoryCopy ic = new InventoryCopy(barcode,  location, this.getCategory().isLendable(), this);
        this.copies.add(ic);


    }

    public void removeCopy(String barcode) {
        this.copies.removeIf(inventoryCopy ->
            inventoryCopy.getBarcode().equals(barcode));
    }



}
