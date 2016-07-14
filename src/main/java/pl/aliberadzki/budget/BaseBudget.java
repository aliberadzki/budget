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

    public BaseBudget() {
        this.expenseCategories = new ArrayList<>();
        this.investmentCategories = new ArrayList<>();
        this.incomeCategories = new ArrayList<>();

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

    public void addExpenseCategory(CashFlowCategory category) {
        this.expenseCategories.add(category);
    }

    public void addExpense(String categoryName, double amount, String date, String description) throws Exception {
        CashFlowCategory cfc = this.expenseCategories.stream().filter(ec -> ec.getName().equals(categoryName)).findFirst().orElse(null);
        if(cfc == null) throw new Exception("Nie ma takiej kategorii");

        Operation o = new Expense(amount, date, description);
        cfc.addOperation(o);

    }

    public double expensesSpentPercentage(String date) {
        double expected = this.expenseCategories.stream()
                .mapToDouble(ec -> ec.getExpectedBalanceAt(date))
                .sum();
        double actual = this.expenseCategories.stream()
                .mapToDouble(ec->ec.getBalanceAt(date))
                .sum();

        return expected == 0.0 ? 0 : actual/expected;
    }

}
