package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-29.
 */
public class AddIvestmentCategoryTransaction extends AddCategoryTransaction implements Transaction {

    public AddIvestmentCategoryTransaction(Integer budgetId, Integer categoryId, String description, Double amount, DateRange startingFrom) throws Exception {
        this(budgetId,categoryId,description,amount);
        this.categoryStartingFromDate = DateRange.now();
    }

    public AddIvestmentCategoryTransaction(Integer budgetId, Integer categoryId, String description, Double amount) {
        this.budgetId = budgetId;
        this.categoryId = categoryId;
        this.categoryName = description;
        this.plannedAmount = amount;
    }

    @Override
    protected CashFlowCategoryGroup getCashFlowCategoryGroup() throws Exception {
        return new InvestmentGroup();
    }
}
