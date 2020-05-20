package dbbg2.data.inventory;

import dbbg2.data.inventory.itemCategory.ItemCategory;
import dbbg2.data.inventory.itemCategory.ItemCategoryType;
import dbbg2.data.inventory.Keyword;
import dbbg2.data.users.User;
import org.eclipse.persistence.annotations.Index;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "Inventory")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class InventoryItem {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int invId;

    private static int nextInventoryId = 10001;


    //@Index(unique = true)
    private String inventoryId = "";

    @Basic(optional = false)
    private String title = "";

    // TODO Make this attribute a ManyToMany
    @SuppressWarnings("JpaAttributeTypeInspection")
    @ManyToMany(mappedBy = "items", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    //@Basic(optional = false)
    private List<Keyword> keywords = new ArrayList<>();

    // Tried to fix the many to many table being created
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "item")
    private Set<InventoryCopy> copies = new HashSet<>();

    //Tried fixing keyword relation
    //@OneToOne(optional = false, cascade = CascadeType.ALL)
    //private ItemCategory category;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "Item_Category")
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

    public String getInventoryId() {
        return inventoryId;
    }

    public int getInvId() {
        return invId;
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

    //Tried changing from list to see if it works
    public Set<InventoryCopy> getCopies() {
        return copies;
    }

    public boolean isAvailable() {
        return isAvailable;
    }


    public void setCategory(ItemCategory category) {
        this.category = category;
    }

    //Tried changing from list to see if it works
    public List<Keyword> getKeyword() {
        return keywords;
    }





    public void addCopy(String barcode, String location) {
        InventoryCopy ic = new InventoryCopy(barcode, location, this.getCategory().isLendable(), this);
        this.copies.add(ic);


    }

    public void removeCopy(String barcode) {
        this.copies.removeIf(inventoryCopy ->
            inventoryCopy.getBarcode().equals(barcode));
    }


    @PrePersist
    private String generateInventoryId() {

        String newInventoryId = "I" + Integer.toString(nextInventoryId);
        nextInventoryId++;
        return newInventoryId;
    }




}
