package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-15.
 */
public interface Datasource {

    Budget getBudget(Integer budgetId);

    void addBudget(Budget budget);

    CashFlowCategory getCategory(Integer budgetId, Integer categoryId);

    void addCategory(Integer budgetId, CashFlowCategory category) throws Exception;

    void addCategory(Integer budgetId, Integer masterCategoryId, CashFlowCategory category) throws Exception;

    void clear();
}
