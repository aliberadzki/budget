package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-25.
 */
public class AddExpenseCategoryTransaction extends AddCategoryTransaction implements Transaction {
    private Integer expenseCategoryId;
    private String expenseCategoryName;
    private Double plannedAmount;
    private DateRange expenseCategoryStartingFromDate;

    public AddExpenseCategoryTransaction(Integer budgetId, Integer expenseCategoryId, String expenseCategoryName, Double plannedAmount, DateRange expenseCategoryStartingFromDate) {
        this.budgetId = budgetId;
        this.expenseCategoryId = expenseCategoryId;
        this.expenseCategoryName = expenseCategoryName;
        this.plannedAmount = plannedAmount;
        this.expenseCategoryStartingFromDate = expenseCategoryStartingFromDate;
    }

    public AddExpenseCategoryTransaction(Integer budgetId, Integer expenseCategoryId, String expenseCategoryName, Double plannedAmount) throws Exception {
        this(budgetId, expenseCategoryId, expenseCategoryName, plannedAmount, DateRange.now());
    }

    @Override
    protected CashFlowCategory getCashFlowCategory() throws Exception {
        return new ExpenseCategory(expenseCategoryId, expenseCategoryName, plannedAmount, expenseCategoryStartingFromDate);
    }
}
