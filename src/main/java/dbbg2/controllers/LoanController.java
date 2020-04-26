package dbbg2.controllers;

import dbbg2.data.loans.Loan;
import dbbg2.data.loans.LoanCopies;
import dbbg2.data.users.Visitor;
import dbbg2.data.users.visitorcategory.Student;
import dbbg2.data.users.visitorcategory.VisitorCategory;
import dbbg2.persistence.Database;

import java.sql.*;

public class LoanController {
    private Visitor client;
    private Loan loan;


    public void getAuthenticatedUser(String userName, String pw) throws Exception {
        //Prepare statement for authentication (maybe should be moved)?
        Statement st = Database.getDefaultInstance().getStatement();
        PreparedStatement pst = Database.getDefaultInstance().getPreparedStatement("SELECT * FROM USERS WHERE EMAIL = ? LIMIT 1;");
        Database.addParam(pst, 1, userName);
        ResultSet rs = pst.executeQuery();
        // Get the result and check if a user exsist
        if(rs.next()) {
            // Found user
            String userID = rs.getString("user_id");
            Statement st_cat = Database.getDefaultInstance().getStatement();
            PreparedStatement pst_cat = Database.getDefaultInstance().getPreparedStatement("SELECT * FROM VISITOR_categories WHERE category_id = ? LIMIT 1;");
            Database.addParam(pst_cat, 1, rs.getInt("visitor_category"));
            ResultSet rs_cat = pst_cat.executeQuery();
            if(rs_cat.next()){
                VisitorCategory vc;
                switch (rs_cat.getString("categoryTitle")) {
                    case "Student":
                        vc = new Student();
                        break;
                    default:
                        throw new Exception("You have to be a student!");
                }
                client = new Visitor(vc);
                loan = new Loan();
            }else {
                throw new Exception("You are not in any of the visitor categories!");
            }


            // Insert all the loans
            Statement st_loan = Database.getDefaultInstance().getStatement();
            PreparedStatement pst_loan = Database.getDefaultInstance().getPreparedStatement("SELECT * loan WHERE user_id = ?;");
            Database.addParam(pst_loan, 1, userID);
            ResultSet rs_loan = pst_loan.executeQuery();
            while (rs_loan.next())  {
                // Add the copies to the client
                Statement st_copies = Database.getDefaultInstance().getStatement();
                PreparedStatement pst_copies = Database.getDefaultInstance().getPreparedStatement("SELECT * loan_copies WHERE loan_loan_id = ?;");
                Database.addParam(pst_copies, 1, rs_loan.getInt("loan_id"));
                ResultSet rs_copies = pst_copies.executeQuery();
                LoanCopies lc = new LoanCopies();
                lc.setFined(rs_copies.getInt("fined") == 1 ? true : false);
                lc.setReturnDate(rs_copies.getDate("return_date"));
                loan.addCopy(lc);
            }
        } else {
            throw new Exception("No user found");
        }

        // TODO Make this good. It could fetch a user and authenticate them - throwing exceptions if it fails


    }

    /**
     * Begin the loan process for the user
     * @throws IllegalStateException Thrown if a loan is attempted when one is already in progress
     */
    public void startLoan(String barcode) throws Exception {
        if (loan != null) {
            throw new IllegalStateException("Cannot start a loan while one is active");
        }

        // Add logic
        if(client.getLoanedItems() > 3) {
            throw new Exception("Can not borrow more than 3 items!");
        }
 //Get book with the right barcode
        Statement st = Database.getDefaultInstance().getStatement();
        PreparedStatement pst = Database.getDefaultInstance().getPreparedStatement("SELECT * FROM inventory_copies WHERE barcode = ? LIMIT 1;");
        Database.addParam(pst, 1, barcode);
        ResultSet rs = pst.executeQuery();
        if(rs.next()) {
            if(rs.getInt("onLoan") == 0) {
                if(rs.getInt("lendable") == 1) {
                    int copy_id = rs.getInt("copy_id");
                    // This should change to beginTransaction since there are multiple INSERTS (but for now we have it like this)
                    PreparedStatement stmt = Database.getDefaultInstance().getConnection().prepareStatement("INSERT INTO loan (user_id) VALUES (?);", Statement.RETURN_GENERATED_KEYS);
                    Database.addParam(stmt, 1, client.getUserId());
                    int rows = stmt.executeUpdate();
                    if(rows == 0) {
                        throw new SQLException("Could not create loan");
                    }
                    //Create loan
                    rs = stmt.getGeneratedKeys();
                    if(rs.next()) {
                        int loan_id = rs.getInt(1);
                        stmt = Database.getDefaultInstance().getPreparedStatement("INSERT INTO loan_copies (return_date, returned, fined, loan_loan_id, inventory_copies_copy_id) VALUES (?,?,?,?,?);");
                        stmt.setDate(1, new Date(0)); // Fix so time is matching
                        stmt.setInt(2, 0);
                        stmt.setInt(3, 0);
                        stmt.setInt(4, loan_id);
                        stmt.setInt(5, copy_id);
                        rows = stmt.executeUpdate();
                        if(rows == 0) {
                            throw new SQLException("Could not create loan copy");
                        }
                        //Update loan entry so I cant be loaned twice
                        stmt = Database.getDefaultInstance().getPreparedStatement("UPDATE inventory_copies SET onLoan = ? WHERE copy_id = ?;");
                        Database.addParam(stmt, 1, 1);
                        Database.addParam(stmt, 2, copy_id);
                        rows = stmt.executeUpdate();
                        if(rows == 0) {
                            throw new SQLException("Could not update invertory");
                        }

                    }else {
                        throw new Exception("No id!!");
                    }
                }
            }

        } else {
            throw new Exception("No book with that Barcode");
        }




    }

}
