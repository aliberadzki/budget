package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-08-01.
 */
public interface CashFlowCategoryGroup {
    static final String EXPENSE = "EXPENSE";
    static final String INCOME = "INCOME";
    static final String INVESTMENT = "INVESTMENT";

    boolean isIncome();
    boolean isExpense();

    //Account sourceAccount();
    //Account targetAccount();

    String getCode();

    static CashFlowCategoryGroup create(String group) {
        if(group.equals(EXPENSE)) return new ExpenseGroup();
        if(group.equals(INCOME)) return new IncomeGroup();
        if(group.equals(INVESTMENT)) return new InvestmentGroup();
        return null;
    }
}
