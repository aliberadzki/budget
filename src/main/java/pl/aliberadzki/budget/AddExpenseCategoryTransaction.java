package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-25.
 */
public class AddExpenseCategoryTransaction extends AddCategoryTransaction implements Transaction {
    private int expenseCategoryId;
    private String expenseCategoryName;
    private Double plannedAmount;
    private DateRange expenseCategoryStartingFromDate;

    public AddExpenseCategoryTransaction(int budgetId, int expenseCategoryId, String expenseCategoryName, Double plannedAmount, DateRange expenseCategoryStartingFromDate) {
        this.budgetId = budgetId;
        this.expenseCategoryId = expenseCategoryId;
        this.expenseCategoryName = expenseCategoryName;
        this.plannedAmount = plannedAmount;
        this.expenseCategoryStartingFromDate = expenseCategoryStartingFromDate;
    }

    @Override
    protected CashFlowCategory getCashFlowCategory() throws Exception {
        return new ExpenseCategory(expenseCategoryId, expenseCategoryName, plannedAmount, expenseCategoryStartingFromDate);
    }
}
