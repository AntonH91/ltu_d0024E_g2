package dbbg2.data.users.visitorcategory;

import javax.persistence.*;

/**
 * Data container for holding category information for a visitor.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class VisitorCategory {
    @Id
    @Basic(optional = false)
    private String categoryTitle = "";

    @Basic(optional = false)
    private int maxLoanedAmount = 0;

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

    public static VisitorCategory getDefaultCategory(VisitorCategoryType category) {
        VisitorCategory vc;
        switch (category) {
            case GENERAL_PUBLIC:
                vc = new VisitorCategory("General Public", 3);
                break;
            case STUDENT:
                vc = new VisitorCategory("Student", 5);
                break;
            case UNIVERSITY_STAFF:
                vc = new VisitorCategory("University Staff", 20);
                break;
            case RESEARCHER:
                vc = new VisitorCategory("Researcher", 10);
                break;
            default:
                throw new IllegalArgumentException("This is not a valid category type");
        }

        return vc;
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VisitorCategory) {
            VisitorCategory otherVc = (VisitorCategory) obj;
            return this.getCategoryTitle().equals(otherVc.getCategoryTitle());
        }


        return false;
    }

}


