package dbbg2.data.inventory;

import dbbg2.data.inventory.itemCategory.ItemCategory;
import dbbg2.data.inventory.itemCategory.ItemCategoryType;

import javax.persistence.*;
import java.util.*;

@Entity(name = "Inventory")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class InventoryItem {


    @ManyToMany(mappedBy = "items", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    //@Basic(optional = false)
    private final List<Keyword> keywords = new ArrayList<>();
    // Tried to fix the many to many table being created
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "item")
    private final Set<InventoryCopy> copies = new HashSet<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int invId;
    @Basic(optional = false)
    private String title = "";

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
        this.title = title;
        this.category = category;
        this.isAvailable = isAvailable;
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

    public void setCategory(ItemCategory category) {
        this.category = category;
    }

    //Tried changing from list to see if it works
    public Set<InventoryCopy> getCopies() {
        return copies;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryItem that = (InventoryItem) o;
        return invId == that.invId &&
                isAvailable == that.isAvailable &&
                title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invId, title, isAvailable);
    }
}
