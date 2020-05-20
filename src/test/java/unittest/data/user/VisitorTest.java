package unittest.data.user;

import dbbg2.data.users.User;
import dbbg2.data.users.Visitor;
import dbbg2.data.users.visitorcategory.VisitorCategory;
import org.junit.Assert;
import org.junit.Test;

public class VisitorTest extends UserTest {

    @Override
    public User getDefaultUser() {
        VisitorCategory vc = new VisitorCategory("General Public", 3);
        return new Visitor(vc);
    }

    private Visitor getDefaultVisitor() {
        return (Visitor) getDefaultUser();
    }

    @Test
    public void userTypeIsVisitor() {
        final String EXPECTED_TYPE = "Visitor";
        Visitor v = getDefaultVisitor();

        Assert.assertEquals(EXPECTED_TYPE, v.getUserType());

    }

    @Test
    public void loanedItemsCountDefaultsToZero() {
        final int EXPECTED = 0;
        Visitor v = getDefaultVisitor();

        Assert.assertEquals(EXPECTED, v.getLoanedItems());

    }

    @Test
    public void loanedItemsCanChange() {
        final int EXPECTED = 7;
        Visitor v = getDefaultVisitor();

        v.setLoanedItems(EXPECTED);

        Assert.assertEquals(EXPECTED, v.getLoanedItems());

    }

    @Test
    public void loanedItemsCanBeIncremented() {
        final int BASE_AMOUNT = 2;
        final int ADD_AMOUNT = 7;
        final int EXPECTED = BASE_AMOUNT + ADD_AMOUNT;
        Visitor v = getDefaultVisitor();

        v.setLoanedItems(BASE_AMOUNT);
        v.increaseLoanedItems(ADD_AMOUNT);

        Assert.assertEquals(EXPECTED, v.getLoanedItems());
    }

    @Test
    public void visitorCategoryIsChangeable() {
        VisitorCategory vc = new VisitorCategory("Test User", 111);
        Visitor v = getDefaultVisitor();

        v.setCategory(vc);

        Assert.assertSame(vc, v.getCategory());

    }


}
