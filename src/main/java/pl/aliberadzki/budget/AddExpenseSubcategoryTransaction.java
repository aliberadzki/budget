package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 26.07.16.
 */
public class AddExpenseSubcategoryTransaction extends AddCategoryTransaction implements Transaction {
    private Integer categoryId;
    private String categoryName;
    private Double plannedAmount;
    private DateRange startingFrom;

    public AddExpenseSubcategoryTransaction(Integer budgetId, Integer categoryId, Integer masterCategoryId, String categoryName, Double amount, DateRange startingFrom) {
        this.budgetId = budgetId;
        this.categoryId = categoryId;
        this.masterCategoryId = masterCategoryId;
        this.categoryName = categoryName;
        this.plannedAmount = amount;
        this.startingFrom = startingFrom;
    }

    public AddExpenseSubcategoryTransaction(Integer budgetId, Integer categoryId, Integer masterCategoryId, String categoryName, Double amount) throws Exception {
        this(budgetId, categoryId, masterCategoryId, categoryName, amount, DateRange.now());
        try {
            this.startingFrom = DateRange.now();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected CashFlowCategory getCashFlowCategory() throws Exception {
        return new ExpenseCategory(categoryId, masterCategoryId, categoryName, plannedAmount, startingFrom);
    }

}
