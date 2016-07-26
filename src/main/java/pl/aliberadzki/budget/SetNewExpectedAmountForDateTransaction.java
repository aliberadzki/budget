package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-26.
 */
public class SetNewExpectedAmountForDateTransaction implements Transaction {
    private int budgetId;
    private int categoryId;
    private DateRange dateRange;
    private double newAmount;
    public SetNewExpectedAmountForDateTransaction(int budgetId, int categoryId, DateRange dateRange, double newAmount) {
        this.budgetId = budgetId;
        this.categoryId = categoryId;
        this.dateRange = dateRange;
        this.newAmount = newAmount;
    }

    @Override
    public void execute() {
        CashFlowCategory cfc = DummyDatabase.instance().getCategory(budgetId, categoryId);
        cfc.setNewExpectedAmountFor(dateRange, newAmount);
    }
}
