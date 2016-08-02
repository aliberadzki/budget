package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-14.
 */
public class Expense implements Operation {
    private Double amount;
    private DateRange date;
    private String description;
    private Integer id;


    public Expense(Integer id, Double amount, DateRange date, String description) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public DateRange getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getName() {
        return description;
    }
}
