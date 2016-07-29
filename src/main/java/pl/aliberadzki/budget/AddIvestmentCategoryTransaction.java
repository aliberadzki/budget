package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-29.
 */
public class AddIvestmentCategoryTransaction extends AddCategoryTransaction implements Transaction {
    private Integer categoryId;
    private String description;
    private Double amount;
    private DateRange startingFrom;

    public AddIvestmentCategoryTransaction(Integer budgetId, Integer categoryId, String description, Double amount, DateRange startingFrom) throws Exception {
        this(budgetId,categoryId,description,amount);
        this.startingFrom = DateRange.now();
    }

    public AddIvestmentCategoryTransaction(Integer budgetId, Integer categoryId, String description, Double amount) {
        this.budgetId = budgetId;
        this.categoryId = categoryId;
        this.description = description;
        this.amount = amount;
    }

    @Override
    protected CashFlowCategory getCashFlowCategory() throws Exception {
        return new InvestmentCategory(categoryId, description, amount, startingFrom);
    }
}
