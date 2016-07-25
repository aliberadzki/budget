package pl.aliberadzki.budget;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aliberadzki on 2016-07-15.
 */
public class DummyDatabase implements Datasource {
    private static DummyDatabase db;

    private Map<Integer, CashFlowCategory> allCategories;
    private Map<Integer, Budget> allBudgets;

    public static Datasource instance() {
        if(db == null) {
            db = new DummyDatabase();
        }
        return db;
    }

    private DummyDatabase() {
        this.allCategories = new HashMap<>();
        this.allBudgets = new HashMap<>();
    }

    @Override
    public Budget getBudget(int budgetId) {
        return allBudgets.get(budgetId);
    }

    @Override
    public void addBudget(Budget budget) {
        this.allBudgets.put(budget.getId(), budget);
    }

    @Override
    public CashFlowCategory getCategory(int budgetId, int categoryId) {
        return this.allBudgets.get(budgetId).getCategory(categoryId);
    }

    @Override
    public void addCategory(int budgetId, CashFlowCategory category) {
        this.allBudgets.get(budgetId).addCategory(category);
    }
}
