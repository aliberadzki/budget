package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-14.
 */
public interface CashFlowCategory {
    Double getExpectedBalanceAt(DateRange date);

    void setNewExpectedAmountFrom(DateRange date, double amount);

    void setNewExpectedAmountFor(DateRange date, double amount);

    String getName();

    void addOperation(Operation operation) throws Exception;

    void forceAddOperation(Operation operation);

    double getBalanceAt(DateRange date);

    void addSubCategory(CashFlowCategory subcategory) throws Exception;

    CashFlowCategory getSubCategory(int id);

    @Deprecated
    CashFlowCategory getSubCategory(String name);

    Integer getMasterCategoryId();

    int getId();

}
