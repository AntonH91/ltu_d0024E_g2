package dbbg2.data.users;

import dbbg2.data.users.visitorcategory.VisitorCategory;

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

}
