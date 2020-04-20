package dbbg2.data.users.category;

/**
 * Data container for holding category information for a visitor.
 */
public abstract class VisitorCategory {
    private String categoryTitle = "";
    private int maxLoanedAmount = 0;

    /**
     * Creates a new VisitorCategory
     * @param categoryTitle The title of the category to use
     * @param maxLoanedAmount The max amount of lendable items a user can have. Domain is [0 .. *]
     * @throws IndexOutOfBoundsException Thrown if the maxLendableItems parameter is out of bounds
     */

    public VisitorCategory(String categoryTitle, int maxLoanedAmount) throws IndexOutOfBoundsException {
        this.categoryTitle = categoryTitle;
        if (maxLoanedAmount < 0) {
            throw new IndexOutOfBoundsException("maxLoanedAmount cannot be less than zero");
        }
        this.maxLoanedAmount = maxLoanedAmount;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public int getMaxLoanedAmount() {
        return maxLoanedAmount;
    }
}
