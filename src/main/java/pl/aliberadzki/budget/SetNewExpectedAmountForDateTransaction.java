package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-26.
 */
public class SetNewExpectedAmountForDateTransaction implements Transaction {
    private Integer budgetId;
    private Integer categoryId;
    private DateRange dateRange;
    private Double newAmount;
    
    public SetNewExpectedAmountForDateTransaction(Integer budgetId, Integer categoryId, DateRange dateRange, Double newAmount) {
        this.budgetId = budgetId;
        this.categoryId = categoryId;
        this.dateRange = dateRange;
        this.newAmount = newAmount;
    }

    @Override
    public void execute() throws Exception {
        CashFlowCategory cfc = DummyDatabase.instance().getCategory(budgetId, categoryId);
        cfc.setNewExpectedAmountFor(dateRange, newAmount);
    }

    @Override
    public void rollback() throws Exception {
        //TODO implement
    }
}
