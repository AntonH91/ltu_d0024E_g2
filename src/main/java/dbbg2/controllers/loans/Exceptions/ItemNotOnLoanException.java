package dbbg2.controllers.loans.Exceptions;

public class ItemNotOnLoanException extends LoanException {
    public ItemNotOnLoanException(String message) {
        super(message);
    }
}
