package dbbg2.data.users.visitorcategory;

import javax.persistence.*;

/**
 * Data container for holding category information for a visitor.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class VisitorCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long uid;

    @Basic(optional = false)
    private String categoryTitle = "";

    @Basic(optional = false)
    private int maxLoanedAmount = 0;

    @Basic(optional = false)
    private boolean defaultNewUser = false;

    public VisitorCategory() {

    }

    /**
     * Creates a new VisitorCategory
     *
     * @param categoryTitle   The title of the category to use
     * @param maxLoanedAmount The max amount of lendable items a user can have. Domain is [0 .. *]
     * @throws IllegalArgumentException Thrown if the maxLendableItems parameter is out of bounds
     */

    public VisitorCategory(String categoryTitle, int maxLoanedAmount) throws IllegalArgumentException {
        this.categoryTitle = categoryTitle;
        if (maxLoanedAmount < 0) {
            throw new IllegalArgumentException("maxLoanedAmount cannot be less than zero");
        }
        this.maxLoanedAmount = maxLoanedAmount;
    }

    public VisitorCategory(String categoryTitle, int maxLoanedAmount, boolean defaultNewUser) {
        this(categoryTitle, maxLoanedAmount);
        this.defaultNewUser = defaultNewUser;
    }

    @Override
    public String toString() {
        return "VisitorCategory{" +
                ", categoryTitle='" + categoryTitle + '\'' +
                ", maxLoanedAmount=" + maxLoanedAmount +
                '}';
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public int getMaxLoanedAmount() {
        return maxLoanedAmount;
    }

    public boolean isDefaultNewUser() {
        return defaultNewUser;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VisitorCategory) {
            VisitorCategory otherVc = (VisitorCategory) obj;
            return this.getCategoryTitle().equals(otherVc.getCategoryTitle());
        }


        return false;
    }

}


