package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-14.
 */
public class Expense implements Operation {
    private double amount;
    private String date;
    private String description;

    public Expense(double amount, String date, String description) {
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}
