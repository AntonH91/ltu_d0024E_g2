package dbbg2.controllers.Loans;

import dbbg2.controllers.Loans.Exceptions.ItemNotLendableException;
import dbbg2.controllers.Loans.Exceptions.TooManyItemsOnLoanException;
import dbbg2.data.genericexceptions.LibraryEntityNotFoundException;
import dbbg2.data.inventory.InventoryCopy;
import dbbg2.data.inventory.InventoryManager;
import dbbg2.data.loans.Loan;
import dbbg2.data.loans.LoanCopies;
import dbbg2.data.users.UserManager;
import dbbg2.data.users.Visitor;
import dbbg2.utils.persistence.JpaPersistence;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class LoanController {
    private Visitor client;
    private Loan loan;
    

    /*
        1. Get user
        2. Start new loan
        3. Add user to loan
        4. Get item
        5. Add item to loan
        6. Goto 4 if not done
        7. Finalize loan
     */

    public static void main(String[] args) {
        LoanController lc = new LoanController();

        lc.startLoan();


        try {
            lc.getUser("aein3799", "pass");
        } catch (LibraryEntityNotFoundException e) {
            e.printStackTrace();
        }


        try {
            lc.addItemToLoan("0");
            lc.addItemToLoan("1");
        } catch (ItemNotLendableException | TooManyItemsOnLoanException e) {
            e.printStackTrace();
        }

        lc.finalizeLoan();


    }

    public void getUser(String userName, String pw) throws LibraryEntityNotFoundException, ClassCastException {
        client = (Visitor) UserManager.getAuthenticatedUser(userName, pw);
        loan.setClient(client);


    }

    /**
     * Begin the loan process for the user
     *
     * @throws IllegalStateException Thrown if a loan is attempted when one is already in progress
     */
    public void startLoan() throws IllegalStateException {
        if (loan != null) {
            throw new IllegalStateException("Cannot start a loan while one is active");
        }

        loan = new Loan();

    }

    /**
     * Checks ifs book is available
     *
     * @param barcode
     * @return Returns lenable inventorycopy
     * @throws ItemNotLendableException throws exception if item is not lendable
     * @throws NoResultException        throws if item cannot be found
     */

    public InventoryCopy getBookWithRightBarCode(String barcode) throws ItemNotLendableException, NoResultException {
        InventoryCopy copy = InventoryManager.getInventoryCopy(barcode);
        if (copy.getLendable() && !copy.getOnLoan()) {
            return copy;
        } else {
            throw new ItemNotLendableException("Item is not lendable");
        }


    }

    public void addItemToLoan(String barcode) throws ItemNotLendableException, TooManyItemsOnLoanException {
        if (client.getLoanedItems() + loan.getCopies().size() >= client.getCategory().getMaxLoanedAmount()) {
            throw new TooManyItemsOnLoanException("There are too many items on loan. You cannot borrow more at this time");
        }
        InventoryCopy invItem = getBookWithRightBarCode(barcode);
        loan.addCopy(invItem);
    }

    public void finalizeLoan() {
        EntityManager em = JpaPersistence.getEntityManager();
        em.getTransaction().begin();

        //TODO mark inventory copies as "on loan"
        //TODO increment count of users loan item
        //TODO persist loan
        //TODO mark item "isAvailable"

        try {
            for (LoanCopies ic : loan.getCopies()) {
                ic.getCopy().setOnLoan(true);
                em.merge(ic.getCopy());
            }

            client.setLoanedItems(client.getLoanedItems() + loan.getCopies().size());

            em.merge(client);
            em.persist(loan);

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            throw e;
        }
    }

    public void returnItem(String barcode) {
        for (LoanCopies item : loan.getCopies()) {
            if (item.getCopy().getBarcode() == barcode) {
                EntityManager em = JpaPersistence.getEntityManager();
                em.getTransaction().begin();

                try {
                    item.getCopy().setOnLoan(false);
                    em.merge(item.getCopy());


                    client.setLoanedItems(client.getLoanedItems() + loan.getCopies().size());

                    em.merge(client);
                    em.persist(loan);

                    em.getTransaction().commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    em.getTransaction().rollback();
                    throw e;
                }
                return;
            }
        }

    }

    public LoanCopies getLoan(String barcode) {
        for(LoanCopies lc : loan.getCopies()) {
            if(lc.getCopy().getBarcode() == barcode) {
                return lc;
            }
        }
        return null;
    }
    
    public List<LoanCopies> getLoans() {
        return loan.getCopies();
    }
}
