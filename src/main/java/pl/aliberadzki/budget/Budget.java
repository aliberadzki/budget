package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-25.
 */
public interface Budget {
    Integer getId();

    //void addExpenseCategory(CashFlowCategory ec);

    Double plannedMonthlyExpenses(DateRange dateRange);

    Double monthlyExpenses(DateRange dateRange);

    Double monthlyInvestments(DateRange dateRange);

    Double monthlyIncomes(DateRange dateRange);

    Double plannedMonthlyInvestments(DateRange dateRange);

    Double plannedMonthlyIncomes(DateRange dateRange);

    void addCategory(CashFlowCategory ec) throws Exception;

    CashFlowCategory getCategory(Integer categoryId);

    Double expensesSpentPercentage(DateRange dateRange);
}
