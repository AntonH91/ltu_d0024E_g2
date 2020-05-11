package dbbg2.data.genericexceptions;

/**
 * Exception that is thrown when one of the data manager services cannot locate the item to perform an operation on
 */
public class LibraryEntityNotFoundException extends Exception {
    public LibraryEntityNotFoundException(String message) {
        super(message);
    }
}
