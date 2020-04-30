package dbbg2.data.inventory;

import dbbg2.data.inventory.itemCategory.ItemCategory;
import dbbg2.persistence.Database;

import javax.persistence.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.ArrayList;

@Entity(name = "Inventory")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class InventoryItem {

    private static int nextInventoryId = 10001;
    @Id
    @GeneratedValue
    private int invId;

    private String inventoryId = "";

    @Basic(optional = false)
    private String title = "";
    @Basic(optional = false)
    private ArrayList<String> keyword = new ArrayList<String>();
    @OneToMany(cascade = CascadeType.ALL)
    private ArrayList<InventoryCopy> copies = new ArrayList<>();
    @Basic(optional = false)
    private ItemCategory category;
    @Basic(optional = false)
    private boolean isAvailable;


    public InventoryItem() {

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

    public void addNewItem() throws SQLException {
        PreparedStatement pst;
        pst = Database.getDefaultInstance().getPreparedStatement("INSERT INTO inventory title, category, is_available" + "values (?, ?, ?, ?, ?");
        pst.setString(1, this.title);
        pst.setObject(2, this.category);
        pst.setBoolean(3, this.isAvailable);
    }

}
