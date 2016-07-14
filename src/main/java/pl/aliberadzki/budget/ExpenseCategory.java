package pl.aliberadzki.budget;

import java.time.LocalDate;
import java.util.*;

/**
 * Created by aliberadzki on 2016-07-14.
 */
public class ExpenseCategory implements CashFlowCategory {
    private Collection<CashFlow> cf;
    private Map<String, Double> expectedBalancesOverridesForMonth;
    private Map<String, Double> expectedBalancesOverridesSinceMonth;
    private List<Operation> expenses;

    private String effectiveSince;
    private double basicAmount = 0.0;

    private String name;

    public ExpenseCategory(String name, double expectedAmount) {
        this(name, expectedAmount, null);
    }

    public ExpenseCategory(String name, double expectedAmount, String date) {
        if(date == null) {
            int month = LocalDate.now().getMonth().getValue();
            date = String.valueOf(LocalDate.now().getYear()) + (month < 10 ? "0" + month : month);
        }
        expectedBalancesOverridesForMonth = new HashMap<>();
        expectedBalancesOverridesSinceMonth = new HashMap<>();
        expenses = new ArrayList<>();

        this.name = name;
        effectiveSince = date;
        basicAmount = expectedAmount;
    }

    private String getOverrideSinceMonthFor(String date) {
        return expectedBalancesOverridesSinceMonth.keySet().stream()
                .filter(s -> s.compareTo(date) < 1)
                .max(String::compareTo)
                .orElse("");
    }

    @Override
    public Double getExpectedBalanceAt(String date) {
        if(expectedBalancesOverridesForMonth.containsKey(date))
            return expectedBalancesOverridesForMonth.get(date);

        String key = getOverrideSinceMonthFor(date);
        if(key != "") return expectedBalancesOverridesSinceMonth.get(key);

        if(effectiveSince.compareTo(date) < 1) return basicAmount;

        return 0.0;
    }

    @Override
    public void setNewExpectedAmountFrom(String date, double amount) {
        this.expectedBalancesOverridesSinceMonth.put(date,amount);
    }

    @Override
    public void setNewExpectedAmountFor(String date, double amount) {
        this.expectedBalancesOverridesForMonth.put(date,amount);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addOperation(Operation operation) {
        this.expenses.add(operation);
    }

    @Override
    public double getBalanceAt(String date) {
        return this.expenses.stream()
                .filter(o -> o.getDate().startsWith(date))
                .mapToDouble(o-> o.getAmount()).sum();
    }
}
