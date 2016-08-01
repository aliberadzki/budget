package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-08-01.
 */
public class IncomeGroup implements CashFlowCategoryGroup {
    @Override
    public boolean isIncome() {
        return true;
    }

    @Override
    public boolean isExpense() {
        return false;
    }

    @Override
    public String getCode() {
        return INCOME;
    }
}
