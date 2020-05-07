package dbbg2.controllers;

public class ItemNotLendableException extends Exception{
    public ItemNotLendableException(String message) {
        super(message);
    }
}
