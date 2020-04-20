package data.user;

import dbbg2.data.users.User;
import org.junit.Assert;
import org.junit.Test;

public abstract class UserTest {

    public abstract User getDefaultUser();

    @Test
    public void newUserIdIsDistinctFromOld() {
        User a = getDefaultUser();
        User b = getDefaultUser();

        Assert.assertNotEquals(a.getUserId(), b.getUserId());
    }

    @Test public void userFirstNameIsChangeable() {
        final String NAME = "Test";
        User a = getDefaultUser();

        a.setFirstName(NAME);

        Assert.assertEquals(NAME, a.getFirstName());
    }



}
