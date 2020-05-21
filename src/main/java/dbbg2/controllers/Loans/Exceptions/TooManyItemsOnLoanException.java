package dbbg2.controllers.Loans.Exceptions;

public class TooManyItemsOnLoanException extends LoanException {
    public TooManyItemsOnLoanException(String message) {
        super(message);
    }
}
