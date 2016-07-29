package pl.aliberadzki.budget;

import java.time.LocalDate;
import java.util.*;

/**
 * Created by aliberadzki on 2016-07-14.
 */
public class ExpenseCategory implements CashFlowCategory {
    private Map<DateRange, Double> expectedBalancesOverridesForMonth;
    private Map<DateRange, Double> expectedBalancesOverridesSinceMonth;
    private List<Operation> expenses;
    private List<CashFlowCategory> subcatgories;

    private DateRange effectiveSince;
    private Double basicAmount = 0.0;

    private String name;
    private Integer id;
    private Integer masterId;

    public ExpenseCategory(int id, String name, Double expectedAmount) throws Exception {
        this(id, name, expectedAmount, null);
    }

    public ExpenseCategory(int id, String name, Double expectedAmount, DateRange date) throws Exception {
        if(date == null) {
            date = DateRange.now();
        }
        expectedBalancesOverridesForMonth = new HashMap<>();
        expectedBalancesOverridesSinceMonth = new HashMap<>();
        expenses = new ArrayList<>();
        subcatgories = new ArrayList<>();

        this.name = name;
        this.id = id;
        effectiveSince = date;
        basicAmount = expectedAmount == null ? 0.0 : expectedAmount;
    }

    public ExpenseCategory(int id, int masterCategoryId, String name, Double plannedAmount, DateRange date) throws Exception {
        this(id, name, plannedAmount, date);
        this.masterId = masterCategoryId;

    }


    private DateRange getOverrideSinceMonthFor(DateRange date) {
        return expectedBalancesOverridesSinceMonth.keySet().stream()
                .filter(s -> s.compareTo(date) < 1)
                .max(DateRange::compareTo)
                .orElse(null);
    }

    @Override
    public Double getExpectedBalanceAt(DateRange date) {
        Double fromSubcategories = 0.0;
        if(subcatgories.size() > 0) {
            fromSubcategories = subcatgories.stream()
                                .reduce(0.0, (sum, c) -> sum += c.getExpectedBalanceAt(date), (sum1, sum2) -> sum1 + sum2);
        }
        if(expectedBalancesOverridesForMonth.containsKey(date))
            return Math.max(expectedBalancesOverridesForMonth.get(date), fromSubcategories);

        DateRange key = getOverrideSinceMonthFor(date);
        if(key != null)
            return Math.max(expectedBalancesOverridesSinceMonth.get(key), fromSubcategories);

        if(effectiveSince.compareTo(date) < 1)
            return Math.max(basicAmount, fromSubcategories);

        return Math.max(0.0, fromSubcategories);
    }

    @Override
    public void setNewExpectedAmountFrom(DateRange date, Double amount) throws Exception {
        this.expectedBalancesOverridesSinceMonth.put(date.stripDays(),amount);
    }

    @Override
    public void setNewExpectedAmountFor(DateRange date, Double amount) throws Exception {
        this.expectedBalancesOverridesForMonth.put(date.stripDays(),amount);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addOperation(Operation operation) throws Exception {
        if(this.getExpectedBalanceAt(operation.getDate()) - this.getBalanceAt(operation.getDate()) - operation.getAmount() < 0) {
            //TODO : introduce unique exception
            throw new Exception("Przekroczono przewidywane wydatki");
        }
        this.expenses.add(operation);
    }

    @Override
    public void forceAddOperation(Operation operation) {
        this.expenses.add(operation);
    }

    @Override
    public Double getBalanceAt(DateRange date) {
        return this.expenses.stream()
                .filter(o -> o.getDate().isIncludedIn(date))
                .mapToDouble(Operation::getAmount).sum();
    }

    @Override
    public void addSubCategory(CashFlowCategory subcategory) throws Exception {
        boolean foundCfc = this.subcatgories.stream().anyMatch(cfc -> cfc.getName().equals(subcategory.getName()));
        //TODO exception
        if(foundCfc) throw new Exception("Już istnieje podkategoria o nazwie: " + subcategory.getName());
        subcatgories.add(subcategory);
    }

    @Override
    public CashFlowCategory getSubCategory(Integer id) {
        return subcatgories.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    @Override
    public CashFlowCategory getSubCategory(String name) {
        return subcatgories.stream().filter(c -> c.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public Integer getMasterCategoryId() {
        return masterId;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
