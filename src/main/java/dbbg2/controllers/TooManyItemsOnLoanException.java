package dbbg2.controllers;

public class TooManyItemsOnLoanException extends Exception{
    public TooManyItemsOnLoanException(String message) {
        super(message);
    }
}
