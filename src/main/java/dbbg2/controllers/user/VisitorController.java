package dbbg2.controllers.user;

import dbbg2.data.users.User;
import dbbg2.data.users.UserManager;
import dbbg2.data.users.Visitor;
import dbbg2.data.users.visitorcategory.VisitorCategory;

import java.lang.instrument.IllegalClassFormatException;

public class VisitorController extends UserController{
    private Visitor subjectToChange;

    @Override
    public void setUser(User user) {
        super.setUser(user);

        if (user instanceof Visitor) {
            this.subjectToChange = (Visitor) user;
        }
    }

    @Override
    public void selectExistingUser(String userId) {
        this.setUser(UserManager.getVisitor(userId));
    }

    @Override
    public void createUser() {
        this.setUser(new Visitor());
    }

    public void setVisitorCategory(VisitorCategory vc) {
        this.subjectToChange.setCategory(vc);
    }

}
