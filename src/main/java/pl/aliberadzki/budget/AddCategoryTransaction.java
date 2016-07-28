package pl.aliberadzki.budget;


/**
 * Created by aliberadzki on 26.07.16.
 */
public abstract class AddCategoryTransaction implements Transaction {
    protected Integer budgetId;
    protected Integer masterCategoryId = null;
    private CashFlowCategory ec = null;

    protected abstract CashFlowCategory getCashFlowCategory() throws Exception;


    @Override
    public void execute() throws Exception {
        ec = getCashFlowCategory();
        DummyDatabase.instance().addCategory(budgetId, masterCategoryId,  ec);
    }

    @Override
    public void rollback() throws Exception {
        //TODO: implement
        ec = ec == null ? getCashFlowCategory() : ec;
    }
}
