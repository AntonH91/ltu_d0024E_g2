package unittest.data.user;

import dbbg2.data.users.User;
import dbbg2.data.users.Visitor;
import dbbg2.data.users.visitorcategory.VisitorCategory;
import dbbg2.data.users.visitorcategory.VisitorCategoryType;

public class VisitorTest extends UserTest {

    @Override
    public User getDefaultUser() {
        return new Visitor(VisitorCategory.getDefaultCategory(VisitorCategoryType.GENERAL_PUBLIC));
    }
}
