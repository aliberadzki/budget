package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-14.
 */
public interface CashFlowCategory {
    Double getExpectedBalanceAt(String date);

    void setNewExpectedAmountFrom(String date, double amount);

    void setNewExpectedAmountFor(String date, double amount);

    String getName();

    void addOperation(Operation operation);

    double getBalanceAt(String date);
}
