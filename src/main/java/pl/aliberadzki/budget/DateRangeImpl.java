package pl.aliberadzki.budget;

import java.time.LocalDate;

/**
 * Created by aliberadzki on 24.07.16.
 */
public final class DateRangeImpl implements DateRange {

    private final int granularity;

    private final int currentYear;
    private final int currentMonth;
    private final int currentDay;

    public DateRangeImpl(String startingFrom) {
        String year = startingFrom.substring(0,4);
        String month = "00";
        String day = "00";
        int granularity = this.YEARLY;

        if(startingFrom.length() >= 6) {
            month = startingFrom.substring(4,6);
            granularity = this.MONTHLY;
        }
        if(startingFrom.length() >= 8) {
            day = startingFrom.substring(6,8);
            granularity = this.DAILY;
        }

        this.currentDay = Integer.valueOf(day);
        this.currentMonth = Integer.valueOf(month);
        this.currentYear = Integer.valueOf(year);
        this.granularity = granularity;

    }

    public DateRangeImpl(int year) {
        this.currentDay = 0;
        this.currentMonth = 0;
        this.currentYear = year;
        granularity = YEARLY;
    }

    public DateRangeImpl(int year, int month) throws Exception {
        if(month > 12 || month < 1) throw new Exception("nie ma takiego miesiaca: " + month);
        this.currentYear = year;
        this.currentMonth = month;
        this.currentDay = 0;
        granularity = MONTHLY;
    }

    public DateRangeImpl(int year, int month, int day) throws Exception {
        this(year, month, day, DAILY);
    }


    public DateRangeImpl(int year, int month, int day, int granularity) throws Exception {
        if(month > 12 || month < 1) throw new Exception("nie ma takiego miesiaca: " + month);
        if(day < 0 || day > daysInMonth(month,year)) throw new Exception("miesiac " + month + " nie ma takiego dnia: " + day);
        this.currentDay = day;
        this.currentYear = year;
        this.currentMonth = month;
        this.granularity = granularity;
    }

    public DateRangeImpl() throws Exception {
        this(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), LocalDate.now().getDayOfMonth());
    }

    @Override
    public DateRange increment(Integer cycle) throws Exception {
        if(cycle < this.granularity) return this;
        if(cycle == YEARLY) {
            return incrementYear();
        }
        if(cycle == MONTHLY) {
            return incrementMonth();
        }

        if(cycle == WEEKLY) {
            return incrementWeek();
        }

        if(cycle == DAILY) {
            return incrementDay();
        }
        throw new Exception("nie ma takiego cyku: " + cycle);
    }

    private DateRange incrementYear() throws Exception {
        int newYear = currentYear,
                newMonth = currentMonth,
                newDay = currentDay;

        newYear++;
        int daysInThisMonth = daysInMonth(newMonth, newYear);
        if(newDay > daysInThisMonth) {
            newDay = daysInThisMonth;
        }
        return new DateRangeImpl(newYear, newMonth, newDay, granularity);
    }

    private DateRange incrementMonth() throws Exception {
        int newYear = currentYear,
                newMonth = currentMonth,
                newDay = currentDay;

        if(newMonth == 12) {
            newYear++;
            newMonth = 0;
        }
        newMonth++;
        int daysInThisMonth = daysInMonth(newMonth, newYear);
        if(newDay > daysInThisMonth) {
            newDay = daysInThisMonth;
        }
        return new DateRangeImpl(newYear, newMonth, newDay, granularity);
    }

    private DateRange incrementWeek() throws Exception {
        int newYear = currentYear,
                newMonth = currentMonth,
                newDay = currentDay;

        int daysInThisMonth = daysInMonth(newMonth, newYear);
        newDay += 7;
        if(newDay> daysInThisMonth) {
            if(newMonth == 12) {
                newYear++;
                newMonth = 0;
            }
            newMonth++;
            newDay = newDay - daysInThisMonth;
        }
        return new DateRangeImpl(newYear, newMonth, newDay, granularity);
    }

    private DateRange incrementDay() throws Exception {
        int newYear = currentYear,
                newMonth = currentMonth,
                newDay = currentDay;
        if(newDay+1 > daysInMonth(newMonth, newYear)) {
            newDay = 0;
            if(newMonth == 12) {
                newYear++;
                newMonth = 0;
            }
            newMonth++;
        }
        newDay++;
        return new DateRangeImpl(newYear, newMonth, newDay, granularity);
    }

    @Override
    public Integer getYear() {
        return currentYear;
    }

    @Override
    public Integer getMonth() {
        return currentMonth;
    }

    @Override
    public Integer getDay() {
        return currentDay;
    }

    @Override
    public Integer getGranularity() {
        return granularity;
    }

    @Override
    public boolean isIncludedIn(DateRange another) {
        return another.includes(this);
    }

    @Override
    public boolean includes(DateRange another) {
        if (granularity == YEARLY) {
            return currentYear == another.getYear();
        }

        if (granularity == MONTHLY) {
            return currentYear == another.getYear() && currentMonth == another.getMonth();
        }

        return this.equalsDate(another);
    }

    @Override
    public int compareTo(DateRange another) {
        int thisBigger = 1;
        int thisLower= -1;
        if(this.equalsDate(another) || this.includes(another) || another.includes(this)) return 0;
        if(currentYear > another.getYear()) return thisBigger; //lub -1?
        if(currentYear < another.getYear()) return thisLower;
        if(currentMonth > another.getMonth()) return thisBigger;
        if(currentMonth < another.getMonth()) return thisLower;
        if(currentDay > another.getDay()) return thisBigger;
        if(currentDay < another.getDay()) return thisLower;
        return 1;

    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder
                .append(currentYear < 10 ? "0" + currentYear : currentYear)
                .append(currentMonth < 10 ? "0" + currentMonth : currentMonth)
                .append(currentDay < 10 ? "0" + currentDay : currentDay)
                .toString();
    }

    @Override
    public boolean equalsDate(DateRange another) {
        return currentYear == another.getYear()
                && currentMonth == another.getMonth()
                && currentDay == another.getDay();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof DateRange && (o == this || ((DateRange) o).equalsDate(this));
    }

    @Override
    public int hashCode() {
        return granularity * 100000000 + currentYear * 10000 + currentMonth * 100 + currentDay;
    }

    private int daysInMonth(int month, int year) {
        switch(month) {
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                return isLeapYear(year) ? 29 : 28;
            default:
                return 31;
        }
    }

    private boolean isLeapYear(int year) {
        return year % 4 == 0;
    }

}
