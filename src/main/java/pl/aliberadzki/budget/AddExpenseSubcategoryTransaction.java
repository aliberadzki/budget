package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 26.07.16.
 */
public class AddExpenseSubcategoryTransaction extends AddCategoryTransaction implements Transaction {
    private int categoryId;
    private String categoryName;
    private Double plannedAmount;
    private DateRange startingFrom;

    public AddExpenseSubcategoryTransaction(int budgetId, int categoryId, int masterCategoryId, String categoryName, Double amount, DateRange startingFrom) {
        this.budgetId = budgetId;
        this.categoryId = categoryId;
        this.masterCategoryId = masterCategoryId;
        this.categoryName = categoryName;
        this.plannedAmount = amount;
        this.startingFrom = startingFrom;
    }

    @Override
    protected CashFlowCategory getCashFlowCategory() throws Exception {
        return new ExpenseCategory(categoryId, masterCategoryId, categoryName, plannedAmount, startingFrom);
    }

}
