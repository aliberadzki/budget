package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-28.
 */
public class SetNewExpectedAmountCyclicForTransaction implements Transaction {
    private Integer budgetId;
    private Integer categoryId;
    private Integer cycle;
    private Double amount;
    private DateRange date;
    private String description;

    public SetNewExpectedAmountCyclicForTransaction(Integer budgetId, Integer categoryId, Double amount, Integer cycle, DateRangeImpl date, String description) {
        this.budgetId = budgetId;
        this.categoryId = categoryId;
        this.cycle = cycle;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    @Override
    public void execute() throws Exception {
        CashFlowCategory cfc = Datasource.getInstance().getCategory(budgetId, categoryId);
        //TODO: 10? definitely it should not be hardcoded
        for(int i=0; i <10 ; i++) {
            cfc.setNewExpectedAmountFor(date, amount);
            date = date.increment(cycle);
        }
    }

    @Override
    public void rollback() throws Exception {
        //TODO implement
    }
}
