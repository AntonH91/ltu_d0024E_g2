package dbbg2.controllers.Loans;

import dbbg2.controllers.Loans.Exceptions.EmptyLoanException;
import dbbg2.controllers.Loans.Exceptions.ItemNotLendableException;
import dbbg2.controllers.Loans.Exceptions.TooManyItemsOnLoanException;
import dbbg2.data.genericexceptions.LibraryEntityNotFoundException;
import dbbg2.data.inventory.InventoryCopy;
import dbbg2.data.inventory.InventoryManager;
import dbbg2.data.loans.Loan;
import dbbg2.data.loans.LoanCopy;
import dbbg2.data.users.Visitor;
import dbbg2.utils.persistence.JpaPersistence;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class LoanController {
    private Visitor client;
    private Loan loan;


    private boolean loanFinalized = false;

    // Accessors


    public boolean isLoanFinalized() {
        return loanFinalized;
    }

    /**
     * Gets the current list of LoanCopy registered on the loan
     *
     * @return Null if there is no Loan on the object, otherwise an unmodifiable list of LoanCopy
     */
    public List<LoanCopy> getLoanCopies() {
        List<LoanCopy> result = new ArrayList<>();

        if (loan != null) {
            result = loan.getCopies();
        }
        return Collections.unmodifiableList(result);
    }

    /**
     * Gets a count of the number of loaned items
     *
     * @return The amount of items currently registered on the loan.
     */
    public int getLoanCopyCount() {
        int result = 0;
        if (loan != null) {
            result = loan.getCopies().size();
        }
        return result;
    }


    /**
     * Checks if there is currently a loan started
     *
     * @return True if there is a started loan, false otherwise.
     */
    public boolean hasLoan() {
        return this.loan != null;
    }


    /**
     * Returns the client on the loan controller
     *
     * @return The visitor object currently registered on the controller
     */
    public Visitor getVisitor() {
        return client;
    }

    public String generateReceipt() {

        if (!this.loanFinalized) {
            throw new IllegalStateException("Cannot generate receipt before loan is finalized.");
        }


        String header = String.format("User: %-60s, Date: %-30s\n\n", client.getFirstName() + ' ' + client.getLastName(), new Date().toString());

        StringBuilder receiptBuilder = new StringBuilder();

        receiptBuilder.append(header);
        for (LoanCopy lc : loan.getCopies()) {
            receiptBuilder.append(String.format("Title: %-60s Return: %-30s\n", lc.getCopy().getItem().getTitle(), lc.getReturnDate()));
        }

        return receiptBuilder.toString();
    }


    // Mutators

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
     * @throws IllegalStateException Thrown if there is not currently an active loan in the controller
     */
    public void abortLoan() throws IllegalStateException {
        if (this.loan == null) {
            throw new IllegalStateException("A loan has not been started in this controller.");
        }
        this.loan = null;
        this.client = null;
        loanFinalized = false;
    }

    public void startLoan(Visitor v) throws IllegalStateException {
        startLoan();
        setUser(v);
    }


    /**
     * Adds an item to the loan with the given barcode.
     *
     * @param barcode The barcode of the item to lend
     * @throws IllegalStateException          Thrown if there is no active loan or user on the controller
     * @throws ItemNotLendableException       Thrown if the item cannot be loaned because of the settings.
     * @throws TooManyItemsOnLoanException    Thrown if the user is attempting to loan too many items.
     * @throws LibraryEntityNotFoundException Thrown if the barcode does not exist in the library.
     */
    public void addItemToLoan(String barcode) throws ItemNotLendableException, TooManyItemsOnLoanException, LibraryEntityNotFoundException {
        verifyControllerState();
        if (client.getLoanedItems() + loan.getCopies().size() >= client.getCategory().getMaxLoanedAmount()) {
            throw new TooManyItemsOnLoanException("There are too many items on loan. You cannot borrow more at this time");
        }
        InventoryCopy invItem = getLendableItemCopy(barcode);
        loan.addCopy(invItem);
    }

    /**
     * Finalizes the loan in the database
     *
     * @throws IllegalStateException Thrown if there is no loan or user on the controller
     * @throws EmptyLoanException    Thrown if the loan is empty when an attempt to finalize it is made
     */
    public void finalizeLoan() throws IllegalStateException, EmptyLoanException {
        verifyControllerState();

        if (loan.getCopies().size() == 0) {
            throw new EmptyLoanException("Cannot finalize a zero-item loan!");
        }

        EntityManager em = JpaPersistence.getEntityManager();
        em.getTransaction().begin();

        //TODO mark inventory copies as "on loan"
        //TODO increment count of users loan item
        //TODO persist loan
        //TODO mark item "isAvailable"

        try {
            // Mark all loaned items as "On Loan"
            for (LoanCopy ic : loan.getCopies()) {
                ic.getCopy().setOnLoan(true);
                em.merge(ic.getCopy());
            }

            // Update the count of loaned items on the client
            client.setLoanedItems(client.getLoanedItems() + loan.getCopies().size());
            client = em.merge(client);

            // Save the loan into the database
            em.persist(loan);

            // Commit everything
            em.getTransaction().commit();

            // Lock this controller for further loans
            loanFinalized = true;
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
        // TODO Implement item returning.
        EntityManager em = JpaPersistence.getEntityManager();

    }

    /**
     * Checks if a lendable item copy is available
     *
     * @param barcode The barcode of the potentially available book
     * @return Returns lenable inventorycopy
     * @throws ItemNotLendableException       throws exception if item is not lendable
     * @throws LibraryEntityNotFoundException throws if item cannot be found
     */
    private InventoryCopy getLendableItemCopy(String barcode) throws ItemNotLendableException, LibraryEntityNotFoundException {
        InventoryCopy copy = InventoryManager.getInventoryCopy(barcode);
        if (copy.getLendable() && !copy.getOnLoan() && copy.getItem().getCategory().isLendable()) {
            return copy;
        } else {
            throw new ItemNotLendableException("Item is not lendable");
        }


    }


    private void verifyControllerState() throws IllegalStateException {
        if (this.loan == null) {
            throw new IllegalStateException("LoanController does not currently have a user.");
        }
        if (this.client == null) {
            throw new IllegalStateException("LoanController does not currently have a client.");
        }

        if (loanFinalized) {
            throw new IllegalStateException("LoanController has already finalized this loan.");
        }

    }

}