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

    Boolean equalsDate(DateRange another);
    Boolean isIncludedIn(DateRange another);
    Boolean includes(DateRange another);
    Integer compareTo(DateRange another);

    @Override
    String toString();

    DateRange stripDays() throws Exception;
}
