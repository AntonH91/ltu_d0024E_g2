package dbbg2.data.loans

import dbbg2.data.inventory.InventoryCopy;
import dbbg2.data.users.Visitor;

public class Loan {


    private Visitor client;
    public void addCopy(InventoryCopy copy) {
        client.increaseLoanedItems(1);
    }

 //Vart ska denna inventory copy någonstans?
 




}
