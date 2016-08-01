package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-14.
 */
public interface CashFlowCategory {
    Double getExpectedBalanceAt(DateRange date);

    void setNewExpectedAmountFrom(DateRange date, Double amount) throws Exception;

    void setNewExpectedAmountFor(DateRange date, Double amount) throws Exception;

    String getName();

    void addOperation(Operation operation) throws Exception;

    void forceAddOperation(Operation operation);

    Double getBalanceAt(DateRange date);

    void addSubCategory(CashFlowCategory subcategory) throws Exception;

    CashFlowCategory getSubCategory(Integer id);

    @Deprecated
    CashFlowCategory getSubCategory(String name);

    Integer getMasterCategoryId();

    Integer getId();

    CashFlowCategoryGroup getGroup();
}
