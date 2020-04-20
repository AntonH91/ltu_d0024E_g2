package dbbg2.data.inventory.itemCategory;


public abstract class ItemCategory {

    private String itemCategoryTitle = "";
    private int lendingDays = 0;
    private boolean isLendable = false;

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



    //int getDuration(){


}
