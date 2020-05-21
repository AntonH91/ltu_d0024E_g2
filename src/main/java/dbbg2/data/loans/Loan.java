package dbbg2.data.loans;

import dbbg2.data.inventory.InventoryCopy;
import dbbg2.data.users.Visitor;

import javax.persistence.*;
import java.util.*;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long loan_id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "lender")
    private Visitor client;


    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "parentLoan")
    @JoinColumn(name = "loan_id")
    private final Map<String, LoanCopy> loanedCopies = new HashMap<>();

    /**
     * Adds an already created loancopy to this loan
     *
     * @param copy The loan copy to add
     */
    public void addCopy(LoanCopy copy) {
        // This needs to be on the Loan Controller
        //client.increaseLoanedItems(1);

        copy.setParentLoan(this);
        loanedCopies.put(copy.getCopy().getBarcode(), copy);
    }

    public void addCopy(InventoryCopy invCopy) {
        LoanCopy lc = new LoanCopy();
        lc.setCopy(invCopy);


        // Calculate return date for item
        int lendingDays = invCopy.getItem().getCategory().getLendingDays();

        Date returnDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(returnDate);
        c.add(Calendar.DATE, lendingDays);

        returnDate = c.getTime();


        lc.setReturnDate(returnDate);

        this.addCopy(lc);
    }

    public List<LoanCopy> getCopies() {
        List<LoanCopy> copyList = new ArrayList<>(loanedCopies.size());
        this.loanedCopies.forEach((s, loanCopy) -> copyList.add(loanCopy));

        return Collections.unmodifiableList(copyList);
    }

    /**
     * Gets a LoanCopy from the loan with the given barcode
     *
     * @param barcode The barcode to search for
     * @return The LoanCopy or null if not found
     */
    public LoanCopy getCopyFromBarcode(String barcode) {
        return loanedCopies.get(barcode);
    }

    public Visitor getClient() {
        return client;
    }

    public void setClient(Visitor client) {
        this.client = client;
    }

    public long getLoanId() {
        return loan_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return loan_id == loan.loan_id &&
                Objects.equals(client, loan.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loan_id, client);
    }
}
