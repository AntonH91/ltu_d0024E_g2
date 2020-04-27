package dbbg2.data.users;

import dbbg2.data.users.visitorcategory.VisitorCategory;

import java.sql.SQLException;

/**
 * @Author Anton Högelin (anthge-7)
 * This type of user has visitor-specific properties, which are necessary to support loan quotas and the borrowing process
 */
public class Visitor extends User {
    private VisitorCategory category;
    private int loanedItems = 0;

    public Visitor(VisitorCategory category) {
        super();
        this.category = category;
    }

    public VisitorCategory getCategory() {
        return category;
    }

    public void setCategory(VisitorCategory category) {
        this.category = category;
    }

    public int getLoanedItems() {
        return loanedItems;
    }

    public void setLoanedItems(int loanedItems) {
        this.loanedItems = loanedItems;
    }

    public void increaseLoanedItems(int count) {
        this.loanedItems = this.loanedItems + count;
    }

    @Override
    public String getUserType() {
        return "Visitor";
    }

    @Override
    protected void saveSpecificDetails(String userId) throws SQLException {

    }
}
