package data.inventory;

import java.util.Date;


public class ItemCategory {

    private String categoryTitle;
    private int lendingDays;
    boolean isLendable;

    String ItemCategory1;
    String ItemCategory2;
    String ItemCategory3;
    String ItemCategory4;
    String ItemCategory5;


    private ItemCategory(String categoryTitle, int lendingDays, boolean isLendable) {

        this.categoryTitle = categoryTitle;
        this.lendingDays = lendingDays;
        this.isLendable = isLendable;
    }

    public ItemCategory newItemCategory1(){
        return new ItemCategory("Course Literature", 14, true);
    }

    public ItemCategory newItemCategory2(){
        return new ItemCategory("Other Books", 30, true);
    }

    public ItemCategory newItemCategory3(){
        return new ItemCategory("Film", 7, true);
    }

    public ItemCategory newItemCategory4(){
        return new ItemCategory("Reference literature", 0, false);
    }

    public ItemCategory newItemCategory5(){
        return new ItemCategory("Journal", 0, false);
    }



    boolean checkLendable(){
        boolean lendable;

        if (this.ItemCategory == ItemCategory1 || ItemCategory2 || ItemCategory3) {
            lendable = true;
            return lendable;
        }
        else {
            lendable = false;
            return lendable;
        }
        }


    int getDuration(){
    }

}
