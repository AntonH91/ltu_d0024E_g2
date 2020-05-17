package dbbg2.view.utils.nested;

public interface ParentController {

    /**
     * <p>Called by the child controller to indicate to the parent that it wishes to be closed and return control to the parent</p>
     */
    void notifyRequestReturn();

    /**
     * <p>Called by the child controller to notify the parent of an update.</p>
     */
    void notifyUpdate();
}
