package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-15.
 */
public interface Datasource {

    Budget getBudget(int budgetId);

    void addBudget(Budget budget);

    CashFlowCategory getCategory(int budgetId, int categoryId);

    void addCategory(int budgetId, CashFlowCategory category);
}
