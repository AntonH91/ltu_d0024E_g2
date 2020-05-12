package dbbg2.data.inventory.itemCategory;


import dbbg2.data.inventory.InventoryItem;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ItemCategory {

    @Id
    private String itemCategoryTitle = "";
    private int lendingDays = 0;
    private boolean isLendable = false;

    public ItemCategory(){

    }

    public ItemCategory(String itemCategoryTitle, int lendingDays, boolean isLendable) {
        this.itemCategoryTitle = itemCategoryTitle;
        this.lendingDays = lendingDays;
        this.isLendable = isLendable;
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

    public static ItemCategory getDefaultItemCategory(ItemCategoryType category){
        ItemCategory ic;
        switch (category) {
            case FILM:
                ic = new ItemCategory("film", 7, true);
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


}
