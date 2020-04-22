package dbbg2.data.loans;

import dbbg2.data.inventory.InventoryCopy;
import dbbg2.data.users.Visitor;

import java.util.ArrayList;

public class Loan {


    private Visitor client;
    private ArrayList<LoanCopies> loanedCopies = new ArrayList<>();

    public void addCopy(LoanCopies copy) {
        // This needs to be on the Loan Controller
        //client.increaseLoanedItems(1);
        loanedCopies.add(copy);

    }

 //Vart ska denna inventory copy någonstans?
 




}
