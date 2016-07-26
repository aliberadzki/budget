package pl.aliberadzki.budget;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by aliberadzki on 2016-07-15.
 */
public class DummyDatabase implements Datasource {
    private static DummyDatabase db;

    private Map<Integer, CashFlowCategory> allCategories;
    private Map<Integer, Integer> categoriesToBudgets;
    private Map<Integer, Integer> categoriesToParents;
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
        this.categoriesToBudgets = new HashMap<>();
        this.categoriesToParents = new HashMap<>();
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
        return this.allCategories.get(categoryId);
    }

    @Override
    public void addCategory(int budgetId, CashFlowCategory category) throws Exception {
        //TODO: throw specific Exception
        if(!allBudgets.containsKey(budgetId)) throw new Exception("There is no budget with id " + budgetId);

        //jesli istnieje jakakolwiek kategoria z ta sama nazwa w tym samym budzecie, na tym samym poziomie
        //TODO: REFACTOR!
        boolean doThrow = this.allCategories.entrySet().stream()
                .filter(e -> e.getValue().getName().equals(category.getName()))
                .map(e -> {
                    Integer parent = categoriesToParents.get(e.getValue().getId());
                    Integer budget = categoriesToBudgets.get(e.getValue().getId());
                    return budget != null && parent != null && budget == budgetId && parent == category.getMasterCategoryId();
                }
                ).anyMatch(f -> f);
        if(doThrow) throw new Exception("There is already category with this name on this level");

        this.allCategories.put(category.getId(), category);
        this.categoriesToBudgets.put(budgetId, category.getId());
    }
}
