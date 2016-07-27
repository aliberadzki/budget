package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-27.
 */
public class AddExpenseTransaction implements Transaction {
    private Integer budgetId;
    private Integer categoryId;
    private Double amount;
    private DateRange date;
    private String description;

    public AddExpenseTransaction(int budgetId, int categoryId, double amount, DateRange date, String description) {
        this.budgetId = budgetId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    @Override
    public void execute() throws Exception {
        Operation o = new Expense(amount,date,description);
        DummyDatabase.instance().getCategory(budgetId,categoryId).addOperation(o);
    }

    @Override
    public void rollback() throws Exception {
        //TODO: implement
    }
}
