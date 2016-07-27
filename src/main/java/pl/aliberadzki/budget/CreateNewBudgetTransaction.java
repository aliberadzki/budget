package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-25.
 */
public class CreateNewBudgetTransaction implements Transaction {
    private int id;
    private String name;

    public CreateNewBudgetTransaction(int budgetId, String budgetName) {
        this.id = budgetId;
        this.name = budgetName;
    }

    @Override
    public void execute() {
        Budget b = new BaseBudget(id, name);
        DummyDatabase.instance().addBudget(b);
    }

    @Override
    public void rollback() throws Exception {
        //TODO implement
    }
}
