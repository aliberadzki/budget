package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-25.
 */
public interface Budget {
    Integer getId();

    //void addExpenseCategory(CashFlowCategory ec);

    double plannedMonthlyExpenses(DateRange dateRange);

    double monthlyExpenses(DateRange dateRange);

    double monthlyInvestments(DateRange dateRange);

    double monthlyIncomes(DateRange dateRange);

    double plannedMonthlyInvestments(DateRange dateRange);

    double plannedMonthlyIncomes(DateRange dateRange);

    void addCategory(CashFlowCategory ec);

    CashFlowCategory getCategory(int categoryId);
}
