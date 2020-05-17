package unittest.data.user;

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

    @Test
    public void userFirstNameIsChangeable() {
        final String NAME = "Test";
        User a = getDefaultUser();

        a.setFirstName(NAME);

        Assert.assertEquals(NAME, a.getFirstName());
    }

    @Test
    public void userLastNameIsChangeable() {
        final String NAME = "Test";
        User a = getDefaultUser();

        a.setLastName(NAME);

        Assert.assertEquals(NAME, a.getLastName());
    }

    @Test
    public void userStreetAddressIsChangeable() {
        final String STREET = "Streetname";
        User a = getDefaultUser();

        a.setStreetAddress(STREET);

        Assert.assertEquals(STREET, a.getStreetAddress());
    }

    @Test
    public void userPostCodeIsChangeable() {
        final String POST_CODE = "12345";
        User a = getDefaultUser();

        a.setPostCode(POST_CODE);

        Assert.assertEquals(POST_CODE, a.getPostCode());
    }

    @Test
    public void userPostAreaIsChangeable() {
        final String POST_AREA = "Orten";
        User a = getDefaultUser();

        a.setPostArea(POST_AREA);

        Assert.assertEquals(POST_AREA, a.getPostArea());
    }


}
