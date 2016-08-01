package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-25.
 */
public class AddExpenseCategoryTransaction extends AddCategoryTransaction implements Transaction {

    public AddExpenseCategoryTransaction(Integer budgetId, Integer expenseCategoryId, String expenseCategoryName, Double plannedAmount, DateRange expenseCategoryStartingFromDate) {
        this.budgetId = budgetId;
        this.categoryId = expenseCategoryId;
        this.categoryName = expenseCategoryName;
        this.plannedAmount = plannedAmount;
        this.categoryStartingFromDate = expenseCategoryStartingFromDate;
    }

    public AddExpenseCategoryTransaction(Integer budgetId, Integer expenseCategoryId, String expenseCategoryName, Double plannedAmount) throws Exception {
        this(budgetId, expenseCategoryId, expenseCategoryName, plannedAmount, DateRange.now());
    }

    @Override
    protected CashFlowCategoryGroup getCashFlowCategoryGroup() throws Exception {
        return new ExpenseGroup();
    }
}
