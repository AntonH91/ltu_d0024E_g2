package unittest.data.user;

import dbbg2.data.users.User;
import org.junit.Assert;
import org.junit.Test;

public abstract class UserTest {

    public abstract User getDefaultUser();

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

    @Test
    public void userPersonNrIsChangeable() {
        final String PERSON_NR = "123456-7890";
        User a = getDefaultUser();

        a.setPersonNr(PERSON_NR);

        Assert.assertEquals(PERSON_NR, a.getPersonNr());
    }

    @Test
    public void userPhoneNrIsChangeable() {
        final String PHONE_NR = "0771 111 222 333";
        User a = getDefaultUser();

        a.setPhoneNr(PHONE_NR);

        Assert.assertEquals(PHONE_NR, a.getPhoneNr());
    }

    @Test
    public void userEqualsSelf() {
        User a = getDefaultUser();

        Assert.assertEquals(a, a);
    }


    @Test
    public void userDoesNotEqualOtherUser() {
        User a = getDefaultUser();
        User b = getDefaultUser();
        a.setFirstName("Jeff");
        b.setFirstName("Goff");

        Assert.assertNotEquals("User a equals different User b", a, b);
    }

}
