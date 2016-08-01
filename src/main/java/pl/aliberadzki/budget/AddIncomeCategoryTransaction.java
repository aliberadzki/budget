package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-29.
 */
public class AddIncomeCategoryTransaction extends AddCategoryTransaction implements Transaction {

    public AddIncomeCategoryTransaction(Integer budgetId, Integer categoryId, String description, Double amount, DateRange startingFrom) {
        this.budgetId = budgetId;
        this.categoryId = categoryId;
        this.categoryName = description;
        this.plannedAmount = amount;
        this.categoryStartingFromDate = startingFrom;
    }

    @Override
    protected CashFlowCategoryGroup getCashFlowCategoryGroup() throws Exception {
        return new IncomeGroup();
    }
}
