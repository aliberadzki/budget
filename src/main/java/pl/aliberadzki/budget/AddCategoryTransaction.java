package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 26.07.16.
 */
public abstract class AddCategoryTransaction implements Transaction {
    protected int budgetId;
    private CashFlowCategory ec;

    protected abstract CashFlowCategory getCashFlowCategory() throws Exception;


    @Override
    public void execute() throws Exception {
            ec = getCashFlowCategory();
            //Budget b = DummyDatabase.instance().getBudget(budgetId);
            //b.addCategory(ec);
            DummyDatabase.instance().addCategory(budgetId, ec);
    }
}
