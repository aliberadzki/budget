package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 24.07.16.
 */
public interface DateRange {
    Integer YEARLY = 1;
    Integer MONTHLY = 2;
    Integer WEEKLY = 3;
    Integer DAILY = 4;

    DateRange increment(Integer cycle);
    Integer getYear();
    Integer getMonth();
    Integer getDay();
    Integer getGranularity();

    boolean equalsDate(DateRange another);
    boolean isIncludedIn(DateRange another);
    boolean includes(DateRange another);
    int compareTo(DateRange another);

    @Override
    String toString();

}
