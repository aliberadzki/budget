package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 24.07.16.
 */
public interface DateRange {
    Integer YEARLY = 4;
    Integer MONTHLY = 3;
    Integer WEEKLY = 2;
    Integer DAILY = 1;

    DateRange increment(Integer cycle) throws Exception;
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
