package dbbg2.controllers.loans.Exceptions;

public class ItemNotLendableException extends LoanException {
    public ItemNotLendableException(String message) {
        super(message);
    }
}
