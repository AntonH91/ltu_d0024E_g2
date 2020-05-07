package dbbg2.data.loans;

import dbbg2.data.users.Visitor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long loan_id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "lender")
    private Visitor client;


    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "loan_id")
    private final List<LoanCopies> loanedCopies = new ArrayList<>();

    public void addCopy(LoanCopies copy) {
        // This needs to be on the Loan Controller
        //client.increaseLoanedItems(1);
        loanedCopies.add(copy);

    }

    public Visitor getClient() {
        return client;
    }

    public void setClient(Visitor client) {
        this.client = client;
    }

    public long getLoan_id() {
        return loan_id;
    }
//Vart ska denna inventory copy någonstans?


}
