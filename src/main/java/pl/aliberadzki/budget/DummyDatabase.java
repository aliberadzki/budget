package pl.aliberadzki.budget;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Map.*;

/**
 * Created by aliberadzki on 2016-07-15.
 */
public class DummyDatabase implements Datasource {
    private static DummyDatabase db;

    private Map<Integer, CashFlowCategory> allCategories;
    private Map<Integer, Integer> categoriesToBudgets;
    private Map<Integer, Integer> categoriesToParents;
    private Map<Integer, Budget> allBudgets;
    private Map<Integer, Operation> allOperations;

    public static Datasource instance() {
        if(db == null) {
            db = new DummyDatabase();
        }
        return db;
    }

    private DummyDatabase() {
        this.allCategories = new HashMap<>();
        this.allBudgets = new HashMap<>();
        this.allOperations = new HashMap<>();
        this.categoriesToBudgets = new HashMap<>();
        this.categoriesToParents = new HashMap<>();
    }

    @Override
    public Budget getBudget(Integer budgetId) {
        return allBudgets.get(budgetId);
    }

    @Override
    public void addBudget(Budget budget) {
        this.allBudgets.put(budget.getId(), budget);
    }

    @Override
    public CashFlowCategory getCategory(Integer budgetId, Integer categoryId) {
        return this.allCategories.get(categoryId);
    }

    @Override
    public void addCategory(Integer budgetId, CashFlowCategory category) throws Exception {
        addCategory(budgetId, null, category);
    }

    @Override
    public void addCategory(Integer budgetId, Integer masterCategoryId, CashFlowCategory category) throws Exception {
        //TODO: throw specific Exception
        if(!allBudgets.containsKey(budgetId)) throw new Exception("There is no budget with id " + budgetId);

        if(masterCategoryId != null && alreadyHasSubcategoryOnThisLevel(category)) {
            throw new Exception("There is already category with this name on this level");
        }

        if(allCategories.containsKey(category.getId())) {
            throw new Exception("There already exists category with id: " + category.getId());
        }

        this.allCategories.put(category.getId(), category);
        this.categoriesToBudgets.put(budgetId, category.getId());

        if(masterCategoryId!=null) {
            this.categoriesToParents.put(category.getId(), masterCategoryId);
            this.allCategories.get(masterCategoryId).addSubCategory(category);
        }
        else {
            this.allBudgets.get(budgetId).addCategory(category);
        }
    }

    @Override
    public void clear() {
        this.allCategories = new HashMap<>();
        this.allBudgets = new HashMap<>();
        this.allOperations = new HashMap<>();
        this.categoriesToBudgets = new HashMap<>();
        this.categoriesToParents = new HashMap<>();
    }

    @Override
    public void addOperation(Integer budgetId, Integer categoryId, Operation operation) throws Exception {
        //TODO: throw custom exception
        if(allOperations.containsKey(operation.getId()))
            throw new Exception("There already is operation with id: " + operation.getId());

        this.allBudgets.get(budgetId).getCategory(categoryId).addOperation(operation);
        this.allOperations.put(operation.getId(), operation);
    }

    @Override
    public void forceAddOperation(Integer budgetId, Integer categoryId, Operation operation) throws Exception {
        //TODO: throw custom exception
        if(allOperations.containsKey(operation.getId()))
            throw new Exception("There already is operation with id: " + operation.getId());

        this.allBudgets.get(budgetId).getCategory(categoryId).forceAddOperation(operation);
        this.allOperations.put(operation.getId(), operation);
    }

    private boolean alreadyHasSubcategoryOnThisLevel(CashFlowCategory category) {
        //TODO: refactor
        Object[] cfcArr = this.allCategories.values().stream()
                .filter(e -> e.getName().equals(category.getName()))
                .toArray();

        for(int i=0; i<cfcArr.length; i++) {
            Integer parent = categoriesToParents.get(((CashFlowCategory)cfcArr[i]).getId());
            Integer budget = categoriesToBudgets.get(((CashFlowCategory)cfcArr[i]).getId());
            for(int j=i+1; j<cfcArr.length; j++) {
                Integer jParent = categoriesToParents.get(((CashFlowCategory)cfcArr[j]).getId());
                Integer jBudget = categoriesToBudgets.get(((CashFlowCategory)cfcArr[j]).getId());
                if(jParent == parent && jBudget == budget)
                    return true;
            }
        }
        return false;
    }
}
