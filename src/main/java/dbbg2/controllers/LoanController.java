package dbbg2.controllers;

import dbbg2.data.loans.Loan;
import dbbg2.data.users.Visitor;

public class LoanController {
    private Visitor client;
    private Loan loan;


    public void getAuthenticatedUser(String userName, String pw) {
        // TODO Make this good. It could fetch a user and authenticate them - throwing exceptions if it fails


    }

    /**
     * Begin the loan process for the user
     * @throws IllegalStateException Thrown if a loan is attempted when one is already in progress
     */
    public void startLoan() throws IllegalStateException {
        if (loan != null) {
            throw new IllegalStateException("Cannot start a loan while one is active");
        }
        loan = new Loan();
    }

}
