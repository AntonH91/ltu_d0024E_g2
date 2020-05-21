package dbbg2.controllers.loans;

import dbbg2.controllers.loans.Exceptions.EmptyLoanException;
import dbbg2.controllers.loans.Exceptions.ItemNotOnLoanException;
import dbbg2.data.genericexceptions.LibraryEntityNotFoundException;
import dbbg2.data.inventory.InventoryCopy;
import dbbg2.data.inventory.InventoryItem;
import dbbg2.data.loans.LoanCopy;
import dbbg2.data.loans.LoanManager;
import dbbg2.data.users.Visitor;
import dbbg2.utils.persistence.JpaPersistence;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoanReturnController {
    private final Set<LoanCopy> loanCopies = new HashSet<>();
    private boolean loanReturnFinalized = false;


    public boolean isLoanReturnFinalized() {
        return loanReturnFinalized;
    }

    public int getPendingReturnCount() {
        return loanCopies.size();
    }

    /**
     * Print the receipt for the return
     *
     * @return The string representing the receipt of all returned items
     */
    public String getReturnReceipt() {
        if (!loanReturnFinalized) {
            throw new IllegalStateException("Cannot print receipt before return finalized.");
        }

        StringBuilder sb = new StringBuilder();

        sb.append(String.format("RETURN RECEIPT - %s\n", new Date().toString()));

        for (LoanCopy loanCopy : loanCopies) {
            Visitor v = loanCopy.getParentLoan().getClient();
            InventoryItem i = loanCopy.getCopy().getItem();

            sb.append(String.format("%-60s %-60s %-60s",
                    v.getFirstName() + ' ' + v.getLastName(),
                    i.getTitle(),
                    loanCopy.getCopy().getBarcode()));

        }

        return sb.toString();

    }

    /**
     * Adds a single item to the collection of LoanCopy objects due for return
     *
     * @param barcode The barcode of the item to return
     */
    public void returnItemOnLoan(String barcode) throws ItemNotOnLoanException {
        if (loanReturnFinalized) {
            throw new IllegalStateException("Cannot return more items as this control is finalized!");
        }

        try {
            LoanCopy lc = getLoanForBarcode(barcode);
            loanCopies.add(lc);
        } catch (LibraryEntityNotFoundException e) {
            throw new ItemNotOnLoanException("This item is not currently on loan.");
        }
    }

    /**
     * Completes the return of items
     */
    public void finalizeReturn() throws EmptyLoanException {
        if (loanReturnFinalized) {
            throw new IllegalStateException("Controller has already finalized the return.");
        }
        if (loanCopies.size() == 0) {
            throw new EmptyLoanException("Cannot make a return without any registered items");
        }


        EntityManager em = JpaPersistence.getEntityManager();
        em.getTransaction().begin();


        try {
            // Iterate loanCopies
            for (LoanCopy loanCopy : loanCopies) {
                Visitor v = loanCopy.getParentLoan().getClient();
                InventoryCopy ic = loanCopy.getCopy();

                ic.setOnLoan(false);
                em.merge(ic);

                loanCopy.setReturned(true);
                em.merge(loanCopy);

                v.setLoanedItems(v.getLoanedItems() - 1);
                em.merge(v);

            }
        } catch (Exception e) {
            Logger.getLogger("").log(Level.SEVERE, "Exception triggered when returning items.");
            em.getTransaction().rollback();
            throw e;
        }
        em.getTransaction().commit();
        loanReturnFinalized = true;

    }


    /**
     * Returns a loan that is currently happening for a particular item
     *
     * @param barcode The barcode of the itemcopy
     * @return The LoanCopy that is currently out
     * @throws LibraryEntityNotFoundException Thrown when the item is not currently on loan
     */
    public LoanCopy getLoanForBarcode(String barcode) throws LibraryEntityNotFoundException {
        return LoanManager.getActiveLoanFromBarcode(barcode);
    }

    /**
     * Returns a list of loanCopies pending return
     *
     * @return The list of loanCopies
     */
    public List<LoanCopy> getPendingLoanReturns() {
        List<LoanCopy> results = new ArrayList<>(loanCopies.size());

        results.addAll(loanCopies);

        return Collections.unmodifiableList(results);

    }


}
