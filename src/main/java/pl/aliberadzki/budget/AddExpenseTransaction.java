package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-27.
 */
public class AddExpenseTransaction implements Transaction {
    private Integer budgetId;
    private Integer id;
    private Integer categoryId;
    private Double amount;
    private DateRange date;
    private String description;

    public AddExpenseTransaction(Integer id, Integer budgetId, Integer categoryId, Double amount, DateRange date, String description) {
        this.budgetId = budgetId;
        this.id = id;
        this.categoryId = categoryId;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    @Override
    public void execute() throws Exception {
        Operation o = new Expense(id, amount,date,description);
        DummyDatabase.instance().addOperation(budgetId,categoryId, o);
    }

    @Override
    public void rollback() throws Exception {
        //TODO: implement
    }
}
