package dbbg2.controllers.Loans.Exceptions;

public class ItemNotLendableException extends LoanException {
    public ItemNotLendableException(String message) {
        super(message);
    }
}
