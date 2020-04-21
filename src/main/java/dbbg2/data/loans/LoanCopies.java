package dbbg2.data.loans;

import dbbg2.data.inventory.InventoryCopy;

import java.util.Date;

public class LoanCopies {
    private InventoryCopy copy;
    private Date returnDate;
    private boolean returned;
    private boolean fined;

    public InventoryCopy getCopy() {
        return copy;
    }

    public void setCopy(InventoryCopy copy) {
        this.copy = copy;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public boolean isFined() {
        return fined;
    }

    public void setFined(boolean fined) {
        this.fined = fined;
    }
}



