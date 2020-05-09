package dbbg2.controllers.Loans.Exceptions;

public class TooManyItemsOnLoanException extends Exception{
    public TooManyItemsOnLoanException(String message) {
        super(message);
    }
}
