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
    private final Set<LoanCopy> loanedCopies = new HashSet<>();

    /**
     * Adds an already created loancopy to this loan
     *
     * @param copy The loan copy to add
     */
    public void addCopy(LoanCopy copy) {
        // This needs to be on the Loan Controller
        //client.increaseLoanedItems(1);

        loanedCopies.add(copy);
        copy.setParentLoan(this);
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
        List<LoanCopy> copyList = Arrays.asList(this.loanedCopies.toArray(new LoanCopy[loanedCopies.size()]));
        return Collections.unmodifiableList(copyList);
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
}
