package dbbg2.controllers.Loans;

import dbbg2.controllers.Loans.Exceptions.ItemNotLendableException;
import dbbg2.controllers.Loans.Exceptions.TooManyItemsOnLoanException;
import dbbg2.data.inventory.InventoryCopy;
import dbbg2.data.inventory.InventoryManager;
import dbbg2.data.loans.Loan;
import dbbg2.data.loans.LoanCopies;
import dbbg2.data.users.Visitor;
import dbbg2.utils.persistence.JpaPersistence;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class LoanController {
    private Visitor client;
    private Loan loan;


    /**
     * Adds a visitor to the active loan
     *
     * @param visitor The visitor to be added to the loan
     * @throws IllegalStateException Thrown if an attempt is made to add a visitor before a loan is started, or if a visitor is attempted to be set when one already exists.
     */
    public void setUser(Visitor visitor) throws IllegalStateException {
        if (this.loan == null) {
            throw new IllegalStateException("Loan is not yet initialized");
        }
        if (this.loan.getClient() != null) {
            throw new IllegalStateException("A visitor is already added to the loan on this controller.");
        }

        client = visitor;
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
     * Aborts the currently on-going loan and removes the visitor from the controller
     *
     * @throws IllegalStateException
     */
    public void abortLoan() throws IllegalStateException {
        if (this.loan == null) {
            throw new IllegalStateException("A loan has not been started in this controller.");
        }
        this.loan = null;
        this.client = null;
    }

    public void startLoan(Visitor v) throws IllegalStateException {
        startLoan();
        setUser(v);
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

    //TODO
    //TODO 1. Find Loan
    //TODO 2. Find Loanedcopy
    //TODO 3. VisitorLoanedItems reduce by 1
    //TODO 4. Loanedcopy return as true
    //TODO 5. InvCopy is on loan = false
    public void returnItem(String barcode) {
        EntityManager em = JpaPersistence.getEntityManager();

    }

    public List<LoanCopies> getLoans() {
        return loan.getCopies();
    }

    /**
     * Checks if book is available
     *
     * @param barcode The barcode of the potentially available book
     * @return Returns lenable inventorycopy
     * @throws ItemNotLendableException throws exception if item is not lendable
     * @throws NoResultException        throws if item cannot be found
     */
    private InventoryCopy getBookWithRightBarCode(String barcode) throws ItemNotLendableException, NoResultException {
        InventoryCopy copy = InventoryManager.getInventoryCopy(barcode);
        if (copy.getLendable() && !copy.getOnLoan()) {
            return copy;
        } else {
            throw new ItemNotLendableException("Item is not lendable");
        }


    }


}