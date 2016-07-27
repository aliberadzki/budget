package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-25.
 */
public class SetNewExpectedAmountFromDateTransaction implements Transaction {
    private int budgetId;
    private int categoryId;
    private DateRange dateRange;
    private double newAmount;

    public SetNewExpectedAmountFromDateTransaction(int budgetId, int categoryId, DateRange dateRange, double newAmount) {
        this.budgetId = budgetId;
        this.categoryId = categoryId;
        this.dateRange = dateRange;
        this.newAmount = newAmount;
    }

    @Override
    public void execute() {
        CashFlowCategory cfc = DummyDatabase.instance().getCategory(budgetId, categoryId);
        cfc.setNewExpectedAmountFrom(dateRange, newAmount);
    }

    @Override
    public void rollback() throws Exception {
        //TODO implement
    }
}
