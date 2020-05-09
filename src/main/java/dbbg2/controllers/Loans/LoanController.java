package dbbg2.controllers.Loans;

import dbbg2.controllers.Loans.Exceptions.ItemNotLendableException;
import dbbg2.controllers.Loans.Exceptions.TooManyItemsOnLoanException;
import dbbg2.data.inventory.InventoryCopy;
import dbbg2.data.inventory.InventoryManager;
import dbbg2.data.loans.Loan;
import dbbg2.data.loans.LoanCopies;
import dbbg2.data.users.UserManager;
import dbbg2.data.users.Visitor;
import dbbg2.persistence.Database;
import dbbg2.persistence.JpaPersistence;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.sql.*;
import java.util.Calendar;

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

    public void getUser(String userName, String pw) throws NoResultException, ClassCastException {
        client = (Visitor) UserManager.getAuthenticatedUser(userName, pw);
        loan.setClient(client);


    }


    public void addLoanToUser(String userID) throws Exception {
        PreparedStatement pst_loan = Database.getDefaultInstance().getPreparedStatement("SELECT * loan WHERE user_id = ?;");
        Database.addParam(pst_loan, 1, userID);
        ResultSet rs_loan = pst_loan.executeQuery();
        while (rs_loan.next()) {
            // Add the copies to the client
            PreparedStatement pst_copies = Database.getDefaultInstance().getPreparedStatement("SELECT * loan_copies WHERE loan_loan_id = ?;");
            Database.addParam(pst_copies, 1, rs_loan.getInt("loan_id"));
            ResultSet rs_copies = pst_copies.executeQuery();
            LoanCopies lc = new LoanCopies();
            lc.setFined(rs_copies.getInt("fined") == 1);
            lc.setReturnDate(rs_copies.getDate("return_date"));
            loan.addCopy(lc);

        }
    }


    //TODO Fix ItemNotLendable Excepetion

    /**
     * Checks ifs book is available
     * @param barcode
     * @return Returns lenable inventorycopy
     * @throws ItemNotLendableException throws exception if item is not lendable
     * @throws NoResultException throws if item cannot be found
     */

    public InventoryCopy getBookWithRightBarCode(String barcode) throws ItemNotLendableException, NoResultException {
        InventoryCopy copy = InventoryManager.getInventoryCopy(barcode);
        if (copy.getLendable() && !copy.getOnLoan()) {
            return copy;
        } else {
            throw new ItemNotLendableException("Item is not lendable");
        }


    }

    public int createLoan() throws Exception {
        // This should change to beginTransaction since there are multiple INSERTS (but for now we have it like this)
        PreparedStatement stmt = Database.getDefaultInstance().getConnection().prepareStatement("INSERT INTO loan (user_id) VALUES (?);", Statement.RETURN_GENERATED_KEYS);
        Database.addParam(stmt, 1, client.getUserId());
        int rows = stmt.executeUpdate();
        if (rows == 0) {
            throw new SQLException("Could not create loan");
        }
        //Create loan//
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            throw new Exception("Could not get loan id");
        }
    }

    public void insertLoanCopies(int loan_id, int copy_id) throws Exception {
        PreparedStatement stmt = Database.getDefaultInstance().getPreparedStatement("INSERT INTO loan_copies (return_date, returned, fined, loan_loan_id, inventory_copies_copy_id) VALUES (?,?,?,?,?);");
        stmt.setDate(1, new Date(Calendar.getInstance().getTime().getTime())); // Fix so time is matching
        stmt.setInt(2, 0);
        stmt.setInt(3, 0);
        stmt.setInt(4, loan_id);
        stmt.setInt(5, copy_id);
        int rows = stmt.executeUpdate();
        if (rows == 0) {
            throw new SQLException("Could not create loan copy");
        }
    }

    public void updateInventoryCopies(int copy_id) throws SQLException {
        PreparedStatement stmt = Database.getDefaultInstance().getPreparedStatement("UPDATE inventory_copies SET onLoan = ? WHERE copy_id = ?;");
        Database.addParam(stmt, 1, 1);
        Database.addParam(stmt, 2, copy_id);
        int rows = stmt.executeUpdate();
        if (rows == 0) {
            throw new SQLException("Could not update invertory");
        }


    }

    public void addItemToLoan(String barcode) throws ItemNotLendableException, TooManyItemsOnLoanException {
        if (client.getLoanedItems() + loan.getCopies().size() >= client.getCategory().getMaxLoanedAmount()){
            throw new TooManyItemsOnLoanException("There are too many items on loan. You cannot borrow more at this time");
        }
        InventoryCopy invItem = getBookWithRightBarCode(barcode);
        loan.addCopy(invItem);
    }

    public void finalizeLoan(){
        EntityManager em = JpaPersistence.getEntityManager();
        em.getTransaction().begin();

        //TODO mark inventory copies as "on loan"
        //TODO increment count of users loan item
        //TODO persist loan
        //TODO mark item "isAvailable"

        try {
            for (LoanCopies ic: loan.getCopies()){
                ic.getCopy().setOnLoan(true);

            }

            client.setLoanedItems(client.getLoanedItems() + loan.getCopies().size());

            em.persist(loan);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
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

}
