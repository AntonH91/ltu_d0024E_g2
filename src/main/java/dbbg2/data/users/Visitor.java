package dbbg2.data.users;

import dbbg2.data.users.visitorcategory.VisitorCategory;
import dbbg2.data.users.visitorcategory.VisitorCategoryType;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * @Author Anton Högelin (anthge-7)
 * This type of user has visitor-specific properties, which are necessary to support loan quotas and the borrowing process
 */
@Entity
public class Visitor extends User {

    @OneToOne(optional = false, cascade = {CascadeType.REFRESH, CascadeType.DETACH})
    private VisitorCategory category;
    private int loanedItems = 0;

    public Visitor() {
        super();
    }

    public Visitor(VisitorCategory category) {
        super();
        this.category = category;
    }

    /**
     * Creates a new user with a given category type constant
     *
     * @param category The new visitor category to provide the Visitor with
     */
    public Visitor(VisitorCategoryType category) {
        this(VisitorCategory.getDefaultCategory(category));
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
    public String toString() {
        return "Visitor{" +
                "category=" + category +
                ", loanedItems=" + loanedItems +
                "} " + super.toString();
    }
}
