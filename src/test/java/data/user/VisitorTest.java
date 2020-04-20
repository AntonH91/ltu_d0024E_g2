package data.user;

import dbbg2.data.users.User;
import dbbg2.data.users.Visitor;
import dbbg2.data.users.visitorcategory.GeneralPublic;

public class VisitorTest extends UserTest {

    @Override
    public User getDefaultUser() {
        return new Visitor(new GeneralPublic());
    }
}
