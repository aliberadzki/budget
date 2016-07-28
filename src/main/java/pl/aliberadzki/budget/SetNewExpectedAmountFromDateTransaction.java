package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-25.
 */
public class SetNewExpectedAmountFromDateTransaction implements Transaction {
    private Integer budgetId;
    private Integer categoryId;
    private DateRange dateRange;
    private Double newAmount;

    public SetNewExpectedAmountFromDateTransaction(Integer budgetId, Integer categoryId, DateRange dateRange, Double newAmount) {
        this.budgetId = budgetId;
        this.categoryId = categoryId;
        this.dateRange = dateRange;
        this.newAmount = newAmount;
    }

    @Override
    public void execute() throws Exception {
        CashFlowCategory cfc = DummyDatabase.instance().getCategory(budgetId, categoryId);
        cfc.setNewExpectedAmountFrom(dateRange, newAmount);
    }

    @Override
    public void rollback() throws Exception {
        //TODO implement
    }
}
