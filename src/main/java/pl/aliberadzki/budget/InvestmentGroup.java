package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-08-01.
 */
public class InvestmentGroup implements CashFlowCategoryGroup {
    @Override
    public boolean isIncome() {
        return false;
    }

    @Override
    public boolean isExpense() {
        return true;
    }

    @Override
    public String getCode() {
        return INVESTMENT;
    }
}
