package dbbg2.view.utils.nested;

public class ChildController {
    private ParentController parentController;

    /**
     * Fetches the given parent controller
     *
     * @return The parent controller of the child form
     */
    public ParentController getParentController() {
        return parentController;
    }

    /**
     * Sets the parent controller for this child form
     *
     * @param parentController The parent controller
     */
    public void setParentController(ParentController parentController) {
        this.parentController = parentController;
    }

    /**
     * Triggers an input update in the parent controller to force re-validation
     */
    protected void triggerParentUpdate() {
        if (parentController != null) {
            parentController.notifyUpdate();
        }
    }

    protected void triggerReturnRequest() {
        if (parentController != null) {
            parentController.notifyRequestReturn();
        }
    }
}
