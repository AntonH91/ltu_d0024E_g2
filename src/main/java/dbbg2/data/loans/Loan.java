package dbbg2.data.loans;

import dbbg2.data.users.Visitor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long uid;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "lender")
    private Visitor client;

    @OneToMany(cascade = CascadeType.ALL)
    private final List<LoanCopies> loanedCopies = new ArrayList<>();

    public void addCopy(LoanCopies copy) {
        // This needs to be on the Loan Controller
        //client.increaseLoanedItems(1);
        loanedCopies.add(copy);

    }

    //Vart ska denna inventory copy någonstans?


}
