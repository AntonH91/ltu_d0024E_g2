package dbbg2.data.genericexceptions;

/**
 * Exception that is thrown when one of the data manager services cannot locate the item to perform an operation on
 */
public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
