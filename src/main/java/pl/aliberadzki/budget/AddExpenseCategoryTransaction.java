package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-25.
 */
public class AddExpenseCategoryTransaction implements Transaction {
    private int budgetId;
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
    public void execute() {
        try {
            CashFlowCategory ec = new ExpenseCategory(expenseCategoryId, expenseCategoryName, plannedAmount, expenseCategoryStartingFromDate);
            Budget b = DummyDatabase.instance().getBudget(budgetId);
            b.addExpenseCategory(ec);
            DummyDatabase.instance().addCategory(ec);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
