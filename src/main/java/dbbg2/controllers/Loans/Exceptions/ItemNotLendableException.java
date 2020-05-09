package dbbg2.controllers.Loans.Exceptions;

public class ItemNotLendableException extends Exception{
    public ItemNotLendableException(String message) {
        super(message);
    }
}
