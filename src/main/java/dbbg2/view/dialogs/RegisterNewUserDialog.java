package dbbg2.view.dialogs;

import dbbg2.data.users.Visitor;
import dbbg2.data.users.visitorcategory.VisitorCategory;
import dbbg2.data.users.visitorcategory.VisitorCategoryManager;
import dbbg2.view.controllers.user.details.UserDetailController;
import dbbg2.view.controllers.user.details.VisitorDetailController;

public class RegisterNewUserDialog extends UserDialog {

    public RegisterNewUserDialog() {
        super(new Visitor());

    }

    @Override
    protected void setRestrictions(UserDetailController udc) {
        setForcedCategory((VisitorDetailController) udc.getChildController());
    }

    private void setForcedCategory(VisitorDetailController vdc) {
        VisitorCategory defaultCategory = VisitorCategoryManager.getDefaultCategory();

        vdc.cbxVisitorCategory.setDisable(true);
        vdc.cbxVisitorCategory.setValue(defaultCategory);
    }

}
