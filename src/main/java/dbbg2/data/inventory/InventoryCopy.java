package dbbg2.data.inventory;

public class InventoryCopy {

    private String barcode;
    private String location;
    private boolean onLoan = false;
    private boolean lendable;
    private InventoryItem item;


    private InventoryCopy(String barcode, String location, boolean lendable, InventoryItem item) {

        this.barcode = barcode;
        this.location = location;
        this.lendable = lendable;
        this.item = item;
    }
// Setters

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setOnLoan(boolean onLoan) {
        this.onLoan = onLoan;
    }

    public void setLendable(boolean lendable) {
        this.lendable = lendable;
    }

// Getters

    public String getBarcode() {
        return barcode;
    }

    public String getLocation() {
        return location;
    }

    public boolean getOnLoan() {
        return onLoan;
    }

    public boolean getLendable() {
        return lendable;
    }

    public InventoryItem getItem() {
        return item;
    }

}
