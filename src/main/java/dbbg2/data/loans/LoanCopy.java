package dbbg2.data.loans;

import dbbg2.data.inventory.InventoryCopy;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class LoanCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long loan_copy_id;

    @ManyToOne(optional = false)
    private InventoryCopy copy;

    @Temporal(TemporalType.DATE)
    private Date returnDate;

    @ManyToOne(targetEntity = dbbg2.data.loans.Loan.class)
    private Loan parentLoan;
    private boolean returned = false;
    private boolean fined = false;

    public Loan getParentLoan() {
        return parentLoan;
    }

    public void setParentLoan(Loan parentLoan) {
        this.parentLoan = parentLoan;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanCopy loanCopy = (LoanCopy) o;
        return loan_copy_id == loanCopy.loan_copy_id &&
                returned == loanCopy.returned &&
                fined == loanCopy.fined &&
                Objects.equals(copy, loanCopy.copy) &&
                Objects.equals(parentLoan, loanCopy.parentLoan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loan_copy_id, copy, parentLoan, returned, fined);
    }
}



