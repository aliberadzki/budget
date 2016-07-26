package pl.aliberadzki.budget;


/**
 * Created by aliberadzki on 26.07.16.
 */
public abstract class AddCategoryTransaction implements Transaction {
    protected int budgetId;
    protected Integer masterCategoryId = null;
    private CashFlowCategory ec;

    protected abstract CashFlowCategory getCashFlowCategory() throws Exception;


    @Override
    public void execute() throws Exception {
        ec = getCashFlowCategory();
        /*if(masterCategoryId != null) {
            CashFlowCategory master = DummyDatabase.instance().getCategory(budgetId, masterCategoryId);
            if(master.getSubCategory(ec.getName()) != null) throw new Exception("Category has subcategory with this name!");
        }*/
        //TODO: IF this is subcategory addition check for existence of one with the same name
        DummyDatabase.instance().addCategory(budgetId, ec);
    }
}
