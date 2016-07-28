package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-28.
 */
public class AddForcefulExpenseTransaction implements Transaction {
    private Integer id;
    private Integer budgetId;
    private Integer categoryId;
    private Double amount;
    private DateRange date;
    private String description;

    public AddForcefulExpenseTransaction(Integer id, Integer budgetId, Integer categoryId, Double amount, DateRange date, String description) {
        this.id = id;
        this.budgetId = budgetId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    @Override
    public void execute() throws Exception {
        Operation o = new Expense(id,amount,date,description);
        Datasource.getInstance().forceAddOperation(budgetId, categoryId, o);
    }

    @Override
    public void rollback() throws Exception {

    }
}
