package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 2016-07-14.
 */
public interface Operation {
    double getAmount();
    DateRange getDate();
    String getDescription();
}
