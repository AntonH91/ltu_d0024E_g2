package dbbg2.data.loans;

import dbbg2.data.genericexceptions.LibraryEntityNotFoundException;
import dbbg2.utils.persistence.JpaPersistence;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class LoanManager {


    /**
     * Gets all the loans for a particular user ID
     *
     * @param userId The user ID
     * @return A list of loans related to this user
     * @throws LibraryEntityNotFoundException Thrown if no loans can be found
     */
    public static List<Loan> getLoansFromUser(String userId) throws LibraryEntityNotFoundException {
        EntityManager em = JpaPersistence.getEntityManager();

        TypedQuery<Loan> lq = em.createQuery("SELECT l " +
                "FROM Loan l " +
                "INNER JOIN Visitor v ON l.client = v " +
                "WHERE v.userId=:userId", Loan.class);

        List<Loan> loanList = null;
        try {
            loanList = lq.setParameter("userId", userId).getResultList();
        } catch (NoResultException e) {
            throw new LibraryEntityNotFoundException("No loans found for this User");
        }


        return loanList;


    }


    /**
     * Gets a list of all loan copies from the user
     *
     * @param userId The user ID to get loan copies from
     * @return A list of all Loan Copies borrowed by the user
     * @throws LibraryEntityNotFoundException Thrown if the user has no loans
     */
    public static List<LoanCopy> getLoanCopiesFromUser(String userId) throws LibraryEntityNotFoundException {
        EntityManager em = JpaPersistence.getEntityManager();

        TypedQuery<LoanCopy> tq = em.createQuery("SELECT lc " +
                "FROM LoanCopy lc " +
                "INNER JOIN Loan l ON lc.parentLoan = l " +
                "INNER JOIN Visitor v ON l.client = v " +
                "WHERE v.userId = :userId", LoanCopy.class);


        try {
            return tq.setParameter("userId", userId).getResultList();
        } catch (NoResultException e) {
            throw new LibraryEntityNotFoundException("No loan copies found for this user.");
        }
    }

    /**
     * Finds a single loan entity based on the item barcode.
     *
     * @param barcode The barcode of the item
     * @return The Loan object
     * @throws LibraryEntityNotFoundException Thrown if no active loan can be found for the barcode
     */
    public static LoanCopy getActiveLoanFromBarcode(String barcode) throws LibraryEntityNotFoundException {
        EntityManager em = JpaPersistence.getEntityManager();
        TypedQuery<LoanCopy> lq = em.createQuery("SELECT lc FROM LoanCopy lc " +
                "INNER JOIN InventoryCopy ic ON lc.copy = ic " +
                "WHERE ic.barcode =:barcode AND lc.returned = false", LoanCopy.class);


        try {
            return lq.setParameter("barcode", barcode).getSingleResult();
        } catch (NoResultException e) {
            throw new LibraryEntityNotFoundException("No loan found for this barcode.");
        }


    }


}
