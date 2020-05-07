package dbbg2.controllers;

import dbbg2.data.loans.Loan;
import dbbg2.data.loans.LoanCopies;
import dbbg2.data.users.Visitor;
import dbbg2.data.users.visitorcategory.*;
import dbbg2.persistence.Database;
import dbbg2.data.users.visitorcategory.VisitorCategory;
import java.sql.*;
import java.util.Calendar;

public class LoanController {
    private Visitor client;
    private Loan loan;
    

    public ResultSet getUser(String userName, String pw) throws Exception {
        //Prepare statement for authentication (maybe should be moved)?
        PreparedStatement pst = Database.getDefaultInstance().getPreparedStatement("SELECT * FROM USERS WHERE EMAIL = ? LIMIT 1;");
        Database.addParam(pst, 1, userName);
        ResultSet rs = pst.executeQuery();
        // Get the result and check if a user exist
        if(rs.next()) {
            return rs;
            }
         else {
             throw new Exception("userName or password is incorrect");


        }



    }





    public VisitorCategory userGetCategory (int VisitorCategory) throws Exception {
        PreparedStatement pst_cat = Database.getDefaultInstance().getPreparedStatement("SELECT * FROM VISITOR_categories WHERE category_id = ? LIMIT 1;");
        Database.addParam(pst_cat, 1, VisitorCategory);
        ResultSet rs_cat = pst_cat.executeQuery();
        if(rs_cat.next()){
            VisitorCategory vc;
            switch (rs_cat.getString("categoryTitle")) {
                case "GeneralPublic":
                    vc = dbbg2.data.users.visitorcategory.VisitorCategory.getDefaultCategory(VisitorCategoryType.GENERAL_PUBLIC);
                    break;
                case "Researcher":
                        vc = dbbg2.data.users.visitorcategory.VisitorCategory.getDefaultCategory(VisitorCategoryType.RESEARCHER);
                    break;
                case "UniversityStaff":
                    vc = dbbg2.data.users.visitorcategory.VisitorCategory.getDefaultCategory(VisitorCategoryType.UNIVERSITY_STAFF);
                    break;
                case "Student":
                    vc = dbbg2.data.users.visitorcategory.VisitorCategory.getDefaultCategory(VisitorCategoryType.STUDENT);
                    break;
                default:
                    throw new Exception("ERROR, Couldn't find matching category!");
            }
            return vc;
        }else {
            throw new Exception("You are not in any of the visitor categories!");
        }





    }

    public void addLoanToUser(String userID) throws Exception {
        PreparedStatement pst_loan = Database.getDefaultInstance().getPreparedStatement("SELECT * loan WHERE user_id = ?;");
        Database.addParam(pst_loan, 1, userID);
        ResultSet rs_loan = pst_loan.executeQuery();
        while (rs_loan.next())  {
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


    public void getAuthenticatedUser(String userName, String pw) throws Exception {
        // Insert all the loans
        ResultSet rs = getUser(userName, pw);
        String userID = rs.getString("user_id");
        int Vc = rs.getInt("visitor_category");
        VisitorCategory userVc = userGetCategory(Vc);
        client = new Visitor(userVc);
        loan = new Loan();
        addLoanToUser(userID);






        // TODO Make this good. It could fetch a user and authenticate them - throwing exceptions if it fails


    }


    public int getBookWithRightBarCode(String barcode) throws Exception {
        PreparedStatement pst = Database.getDefaultInstance().getPreparedStatement("SELECT * FROM inventory_copies WHERE barcode = ? LIMIT 1;");
        Database.addParam(pst, 1, barcode);
        ResultSet rs = pst.executeQuery();
        if(rs.next()) {
            if (rs.getInt("onLoan") == 0) {
                if (rs.getInt("lendable") == 1) {
                    return rs.getInt("copy_id");
                } else {
                    throw new Exception("This book is not lendable");
                }
            } else {
                throw new Exception("This book is already loaned");
            }
        } else {
            throw new Exception("Barcode doesnt match any books in store");
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
        if(rows == 0) {
            throw new SQLException("Could not update invertory");
        }


    }



    /**
     * Begin the loan process for the user
     * @throws IllegalStateException Thrown if a loan is attempted when one is already in progress
     */
    public void startLoan(String barcode) throws Exception {
        if (loan != null) {
            throw new IllegalStateException("Cannot start a loan while one is active");
        }

        // Checks user for maxloan permission
        if(client.getLoanedItems() >= client.getCategory().getMaxLoanedAmount()) {
            throw new Exception("Can not borrow more than "+client.getCategory().getMaxLoanedAmount()+" items!");
        }
                    int copy_id = getBookWithRightBarCode(barcode);
                        int loan_id = createLoan();
                        insertLoanCopies(loan_id, copy_id);
                        updateInventoryCopies(copy_id);


    }

}
