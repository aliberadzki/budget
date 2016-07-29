package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-29.
 */
public class AddIncomeCategoryTransaction extends AddCategoryTransaction implements Transaction {
    private Integer categoryId;
    private String description;
    private Double amount;
    private DateRange startingFrom;

    public AddIncomeCategoryTransaction(Integer budgetId, Integer categoryId, String description, Double amount, DateRange startingFrom) {
        this.budgetId = budgetId;
        this.categoryId = categoryId;
        this.description = description;
        this.amount = amount;
        this.startingFrom = startingFrom;
    }

    @Override
    protected CashFlowCategory getCashFlowCategory() throws Exception {
        return new IncomeCategory(categoryId, description, amount, startingFrom);
    }
}
