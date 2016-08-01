package pl.aliberadzki.budget;


import java.util.*;

/**
 * Created by aliberadzki on 2016-07-14.
 */
public class BaseBudget implements Budget{
    private Integer id;
    private String name;

    private List<CashFlowCategory> categories;

    private Map<Integer, CashFlowCategory> flatCategoriesMap;

    public BaseBudget() {
        this.categories = new ArrayList<>();
        this.flatCategoriesMap = new HashMap<>();
    }

    public BaseBudget(Integer budgetId, String budgetName) {
        this();
        this.id = budgetId;
        this.name = budgetName;
    }

    public Double monthlyExpenses(DateRange date) {
        return 0.0;
    }

    public Double monthlyInvestments(DateRange date) {
        return 0.0;
    }

    public Double monthlyIncomes(DateRange date) {
        return 0.0;
    }

    @Override
    public Double plannedMonthlyExpenses(DateRange date) {
        return categories.stream()
                .filter(c -> c.getGroup().getCode().equals(CashFlowCategoryGroup.EXPENSE))
                .reduce(0.0, (sum, c) -> sum += c.getExpectedBalanceAt(date), (sum1, sum2) -> sum1 + sum2);
    }

    public Double plannedMonthlyInvestments(DateRange date) {
        return categories.stream()
                .filter(c -> c.getGroup().getCode().equals(CashFlowCategoryGroup.INVESTMENT))
                .reduce(0.0, (sum, c) -> sum += c.getExpectedBalanceAt(date), (sum1, sum2) -> sum1 + sum2);
    }

    public Double plannedMonthlyIncomes(DateRange date) {
        return categories.stream()
                .filter(c -> c.getGroup().getCode().equals(CashFlowCategoryGroup.INCOME))
                .reduce(0.0, (sum, c) -> sum += c.getExpectedBalanceAt(date), (sum1, sum2) -> sum1 + sum2);
    }

    @Override
    public void addCategory(CashFlowCategory category) throws Exception {
        if(categories == null || alreadyHasCategory(category))
            throw  new Exception("Kategoria już istnieje, lub nie ma kolekcji do której mozna ja dodac");

        if(category.getMasterCategoryId() != null) {
            //TODO: addSubcategory to this masterCategory
        }
        else {
            categories.add(category);
        }
        flatCategoriesMap.put(category.getId(), category);
    }

    private boolean alreadyHasCategory(CashFlowCategory category) {
        return flatCategoriesMap.containsKey(category.getId());
    }

    @Override
    public CashFlowCategory getCategory(Integer categoryId) {
        return flatCategoriesMap.get(categoryId);
    }

    public Double expensesSpentPercentage(DateRange date) {
        Double expected = categories.stream()
                .mapToDouble(ec -> ec.getExpectedBalanceAt(date))
                .sum();
        Double actual = categories.stream()
                .mapToDouble(ec->ec.getBalanceAt(date))
                .sum();

        return expected == 0.0 ? 0 : actual/expected;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getId() {
        return this.id;
    }
}
