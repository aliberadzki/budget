package pl.aliberadzki.budget;

import java.util.Objects;

/**
 * Created by aliberadzki on 2016-07-14.
 */
public interface CashFlowCategory {
    Double getExpectedBalanceAt(String date);

    void setNewExpectedAmountFrom(String date, double amount);

    void setNewExpectedAmountFor(String date, double amount);

    String getName();

    void addOperation(Operation operation) throws Exception;

    void forceAddOperation(Operation operation);

    double getBalanceAt(String date);

    void addSubCategory(CashFlowCategory subcategory) throws Exception;

    String getId();
}
