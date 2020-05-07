package dbbg2.controllers.user;

import dbbg2.data.users.UserManager;
import dbbg2.data.users.Visitor;
import dbbg2.data.users.visitorcategory.VisitorCategory;

public class VisitorController extends UserController{
    private Visitor subjectToChange;

    @Override
    public void selectExistingUser(String userId) {
        this.subjectToChange = UserManager.getVisitor(userId);
        super.setUser(this.subjectToChange);
    }

    @Override
    public void createUser() {
        this.subjectToChange = new Visitor();
    }

    public void setVisitorCategory(VisitorCategory vc) {
        this.subjectToChange.setCategory(vc);
    }

}
