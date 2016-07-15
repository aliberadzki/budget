package pl.aliberadzki.budget;

import javafx.util.Pair;

import java.util.*;

/**
 * Created by aliberadzki on 2016-07-14.
 */
public class BaseBudget {
    private List<CashFlowCategory> expenseCategories;
    private List<CashFlowCategory> investmentCategories;
    private List<CashFlowCategory> incomeCategories;

    private Map<String, CashFlowCategory> flatCategoriesMap;

    public BaseBudget() {
        this.expenseCategories = new ArrayList<>();
        this.investmentCategories = new ArrayList<>();
        this.incomeCategories = new ArrayList<>();
        this.flatCategoriesMap = new HashMap<>();

    }
    public double monthlyExpenses(String date) {
        return 0;
    }

    public double monthlyInvestments(String date) {
        return 0;
    }

    public double monthlyIncomes(String date) {
        return 0;
    }

    public double plannedMonthlyExpenses(String date) {
        return expenseCategories.stream()
                .filter(c -> c instanceof ExpenseCategory)
                .reduce(0.0, (sum, c) -> sum += c.getExpectedBalanceAt(date), (sum1, sum2) -> sum1 + sum2);
    }

    public double plannedMonthlyInvestments(String date) {
        return 0;
    }

    public double plannedMonthlyIncomes(String date) {
        return 0;
    }

    public void addExpenseCategory(String masterId, CashFlowCategory slave) throws Exception {
        if(existsCategory(slave.getId())) {
            throw new Exception("Już istnieje kategoria o id: " + slave.getId());
        }

        CashFlowCategory foundCfc = getCategory(masterId, expenseCategories);

        if(foundCfc == null) {
            throw new Exception("Nie ma kategorii o id: " + masterId);
        }
        foundCfc.addSubCategory(slave);
        flatCategoriesMap.put(slave.getId(), slave);
    }

    public void addExpenseCategory(CashFlowCategory category) {
        this.expenseCategories.add(category);
        flatCategoriesMap.put(category.getId(), category);
    }

    public void addExpense(String categoryId, double amount, String date, String description) throws Exception {
        performAddExpense(categoryId, amount, date, description, false);
    }

    public void forceAddExpense(String categoryId, double amount, String date, String description) throws Exception {
        performAddExpense(categoryId, amount, date, description, true);
    }

    private void performAddExpense(String categoryId, double amount, String date, String description, boolean force) throws Exception {
        CashFlowCategory cfc = getCategory(categoryId, expenseCategories);
        if(cfc == null) throw new Exception("Nie ma kategorii o id: " + categoryId);

        Operation o = new Expense(amount, date, description);
        if(force) {
            cfc.forceAddOperation(o);
        }
        else {
            cfc.addOperation(o);
        }
    }

    public double expensesSpentPercentage(String date) {
        return calculateSpentPercentage(this.expenseCategories, date);
    }

    private double calculateSpentPercentage(List<CashFlowCategory> categories, String date) {
        double expected = categories.stream()
                .mapToDouble(ec -> ec.getExpectedBalanceAt(date))
                .sum();
        double actual = categories.stream()
                .mapToDouble(ec->ec.getBalanceAt(date))
                .sum();

        return expected == 0.0 ? 0 : actual/expected;
    }

    private CashFlowCategory getCategory(String categoryId, List<CashFlowCategory> categories) {
        return flatCategoriesMap.get(categoryId);
        /*return categories.stream()
                .filter(ec -> ec.getId().equals(categoryId))
                .findFirst()
                .orElse(null);*/
    }
    private boolean existsCategory(String categoryId) {
        return flatCategoriesMap.containsKey(categoryId);
    }

}
