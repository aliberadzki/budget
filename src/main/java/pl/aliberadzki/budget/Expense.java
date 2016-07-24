package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-14.
 */
public class Expense implements Operation {
    private double amount;
    private DateRange date;
    private String description;


    public Expense(double amount, DateRange date, String description) {
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public DateRange getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}
