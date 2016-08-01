package pl.aliberadzki.budget;


/**
 * Created by aliberadzki on 26.07.16.
 */
public abstract class AddCategoryTransaction implements Transaction {
    protected Integer budgetId;
    protected Integer masterCategoryId;
    private CashFlowCategory ec;
    private CashFlowCategoryGroup group;

    protected Integer categoryId;
    protected String categoryName;
    protected Double plannedAmount;
    protected DateRange categoryStartingFromDate;

    protected abstract CashFlowCategoryGroup getCashFlowCategoryGroup() throws Exception;


    @Override
    public void execute() throws Exception {
        group = getCashFlowCategoryGroup();
        ec = new CashFlowCategoryImpl(categoryId, masterCategoryId, categoryName, group, plannedAmount, categoryStartingFromDate);
        DummyDatabase.instance().addCategory(budgetId, masterCategoryId,  ec);
    }

    @Override
    public void rollback() throws Exception {
        //TODO: implement
        //ec = ec == null ? getCashFlowCategory() : ec;
    }
}
