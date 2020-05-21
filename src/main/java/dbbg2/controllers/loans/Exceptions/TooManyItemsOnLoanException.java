package dbbg2.controllers.loans.Exceptions;

public class TooManyItemsOnLoanException extends LoanException {
    public TooManyItemsOnLoanException(String message) {
        super(message);
    }
}
