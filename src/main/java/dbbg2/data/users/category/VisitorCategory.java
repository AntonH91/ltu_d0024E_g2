package dbbg2.data.users.category;

public abstract class VisitorCategory {
    private String categoryTitle = "";
    private int maxLoanedAmount = 0;

    public VisitorCategory(String categoryTitle, int maxLoanedAmount) {
        this.categoryTitle = categoryTitle;
        this.maxLoanedAmount = maxLoanedAmount;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public int getMaxLoanedAmount() {
        return maxLoanedAmount;
    }
}
