package dbbg2.data.loans;

import dbbg2.data.inventory.InventoryCopy;

import javax.persistence.*;
import java.util.Date;

@Entity
public class LoanCopies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long loan_copy_id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private InventoryCopy copy;

    @Temporal(TemporalType.DATE)
    private Date returnDate;

    private boolean returned = false;
    private boolean fined = false;

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



