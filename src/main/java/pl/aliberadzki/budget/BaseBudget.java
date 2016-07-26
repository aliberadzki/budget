package pl.aliberadzki.budget;


import java.util.*;

/**
 * Created by aliberadzki on 2016-07-14.
 */
public class BaseBudget implements Budget{
    private int id;
    private String name;

    private List<CashFlowCategory> expenseCategories;
    private List<CashFlowCategory> investmentCategories;
    private List<CashFlowCategory> incomeCategories;

    private Map<Integer, CashFlowCategory> flatCategoriesMap;

    public BaseBudget() {
        this.expenseCategories = new ArrayList<>();
        this.investmentCategories = new ArrayList<>();
        this.incomeCategories = new ArrayList<>();
        this.flatCategoriesMap = new HashMap<>();

    }

    public BaseBudget(int budgetId, String budgetName) {
        this();
        this.id = budgetId;
        this.name = budgetName;
    }

    public double monthlyExpenses(DateRange date) {
        return 0;
    }

    public double monthlyInvestments(DateRange date) {
        return 0;
    }

    public double monthlyIncomes(DateRange date) {
        return 0;
    }

    @Override
    public double plannedMonthlyExpenses(DateRange date) {
        return expenseCategories.stream()
                .filter(c -> c instanceof ExpenseCategory)
                .reduce(0.0, (sum, c) -> sum += c.getExpectedBalanceAt(date), (sum1, sum2) -> sum1 + sum2);
    }

    public double plannedMonthlyInvestments(DateRange date) {
        return 0;
    }

    public double plannedMonthlyIncomes(DateRange date) {
        return 0;
    }

    public void addExpenseCategory(int masterId, CashFlowCategory slave) throws Exception {
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

    @Override
    public void addCategory(CashFlowCategory category) throws Exception {
        List<CashFlowCategory> list = null;
        if(category instanceof ExpenseCategory) {
            list = this.expenseCategories;
        }

        if(list == null || alreadyHasCategory(category)) throw  new Exception("Kategoria już istnieje, lub nie ma kolekcji do której mozna ja dodac");
        if(category.getMasterCategoryId() != null) {
            //TODO: addSubcategory to this masterCategory
        }
        else {
            list.add(category);
        }
        flatCategoriesMap.put(category.getId(), category);
    }

    private boolean alreadyHasCategory(CashFlowCategory category) {
        return flatCategoriesMap.containsKey(category.getId());
    }

    @Override
    public CashFlowCategory getCategory(int categoryId) {
        //TODO : one collection for all categories?
        CashFlowCategory ecfc = expenseCategories.stream().filter(c -> c.getId() == categoryId).findFirst().orElse(null);
        if(ecfc != null) return ecfc;
        CashFlowCategory icfc = investmentCategories.stream().filter(c -> c.getId() == categoryId).findFirst().orElse(null);
        if(icfc != null) return icfc;
        CashFlowCategory inccfc = incomeCategories.stream().filter(c -> c.getId() == categoryId).findFirst().orElse(null);
        return inccfc;
    }

    public void addExpense(int categoryId, double amount, DateRange date, String description) throws Exception {
        performAddExpense(categoryId, amount, date, description, false);
    }

    public void forceAddExpense(int categoryId, double amount, DateRange date, String description) throws Exception {
        performAddExpense(categoryId, amount, date, description, true);
    }

    private void performAddExpense(int categoryId, double amount, DateRange date, String description, boolean force) throws Exception {
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

    public double expensesSpentPercentage(DateRange date) {
        return calculateSpentPercentage(this.expenseCategories, date);
    }

    private double calculateSpentPercentage(List<CashFlowCategory> categories, DateRange date) {
        double expected = categories.stream()
                .mapToDouble(ec -> ec.getExpectedBalanceAt(date))
                .sum();
        double actual = categories.stream()
                .mapToDouble(ec->ec.getBalanceAt(date))
                .sum();

        return expected == 0.0 ? 0 : actual/expected;
    }

    private CashFlowCategory getCategory(int categoryId, List<CashFlowCategory> categories) {
        return flatCategoriesMap.get(categoryId);
    }
    private boolean existsCategory(int categoryId) {
        return flatCategoriesMap.containsKey(categoryId);
    }

    public void addCyclicExpense(int categoryId, double amount, Integer cycle, DateRange startingFrom, String description) throws Exception {

        CashFlowCategory cfc = this.getCategory(categoryId, expenseCategories);

        for(int i=0; i <10 ; i++) {
            cfc.setNewExpectedAmountFor(startingFrom, amount);
            startingFrom = startingFrom.increment(cycle);
        }
    }

    @Override
    public Integer getId() {
        return this.id;
    }
}
