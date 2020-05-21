package dbbg2.data.inventory.itemCategory;


import dbbg2.data.inventory.InventoryItem;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class ItemCategory {

    @Id
    private String itemCategoryTitle = "";
    private int lendingDays = 0;
    private boolean isLendable = false;


    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "category")
    private final Set<InventoryItem> invItem = new HashSet<>();

    public ItemCategory() {

    }

    public ItemCategory(String itemCategoryTitle, int lendingDays, boolean isLendable) {
        this.itemCategoryTitle = itemCategoryTitle;
        this.lendingDays = lendingDays;
        this.isLendable = isLendable;
    }

    public static ItemCategory getDefaultItemCategory(ItemCategoryType category) {
        ItemCategory ic;
        switch (category) {
            case FILM:
                ic = new ItemCategory("Film", 7, true);
                break;
            case JOURNAL:
                ic = new ItemCategory("Journal", 0, false);
                break;
            case OTHER_BOOKS:
                ic = new ItemCategory("Other Books", 30, true);
                break;
            case REFERENCE_LITERATURE:
                ic = new ItemCategory("Reference Literature", 0, false);
                break;
            case STUDENT_LITERATURE:
                ic = new ItemCategory("Student literature", 14, true);
                break;

            default:
                throw new IllegalArgumentException("This is not a valid category type");
        }

        return ic;
    }

    public String getItemCategoryTitle() {
        return itemCategoryTitle;
    }

    public int getLendingDays() {
        return lendingDays;
    }

    public boolean isLendable() {
        return isLendable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemCategory that = (ItemCategory) o;
        return lendingDays == that.lendingDays &&
                isLendable == that.isLendable &&
                itemCategoryTitle.equals(that.itemCategoryTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemCategoryTitle, lendingDays, isLendable);
    }
}
