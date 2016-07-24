package pl.aliberadzki.budget;

/**
 * Created by aliberadzki on 24.07.16.
 */
public class DateRangeImpl implements DateRange {

    private int granularity;

    private int currentYear;
    private int currentMonth;
    private int currentDay;

    public DateRangeImpl(String startingFrom) {
        String year = startingFrom.substring(0,4);
        String month = "01";
        String day = "01";
        this.granularity = this.YEARLY;

        if(startingFrom.length() >= 6) {
            month = startingFrom.substring(4,6);
            this.granularity = this.MONTHLY;
        }

        if(startingFrom.length() >= 8) {
            day = startingFrom.substring(6,8);
            this.granularity = this.DAILY;
        }

        this.currentDay = Integer.valueOf(day);
        this.currentMonth = Integer.valueOf(month);
        this.currentYear = Integer.valueOf(year);

    }
    public DateRangeImpl(int year) {
        this.currentYear = year;
        granularity = YEARLY;
    }
    public DateRangeImpl(int year, int month) throws Exception {
        if(month > 12 || month < 1) throw new Exception("nie ma takiego miesiaca: " + month);
        this.currentYear = year;
        this.currentMonth = month;
        granularity = MONTHLY;
    }
    public DateRangeImpl(int year, int month, int day) throws Exception {
        if(month > 12 || month < 1) throw new Exception("nie ma takiego miesiaca: " + month);
        if(day < 1 || day > daysInMonth(month,year)) throw new Exception("miesiac " + month + " nie ma takiego dnia: " + day);
        this.currentDay = day;
        this.currentYear = year;
        this.currentMonth = month;
        granularity = DAILY;

    }

    @Override
    public DateRange increment(Integer cycle) {
        if(cycle == YEARLY) { currentYear++; return this; }
        if(cycle == MONTHLY) {
            this.currentMonth++;
            int daysInThisMonth = daysInMonth(currentMonth, currentYear);
            if(currentDay > daysInThisMonth) {
                currentDay = daysInThisMonth;
            }
            return this;
        }

        if(cycle == WEEKLY) {
            int daysInThisMonth = daysInMonth(currentMonth, currentYear);
            currentDay += 7;
            if(currentDay> daysInThisMonth) {
                if(currentMonth == 12) {
                    currentYear++;
                    currentMonth = 0;
                }
                currentMonth++;
                currentDay = currentDay - daysInThisMonth;
            }
            return this;
        }

        if(cycle == DAILY) {
            if(currentDay+1 > daysInMonth(currentMonth, currentYear)) {
                currentDay = 0;
                if(currentMonth == 12) {
                    currentYear++;
                    currentMonth = 0;
                }
                currentMonth++;
            }
            currentDay++;
        }
        return this;
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

    public boolean equals(Object o) {
        return ((DateRange)o).equalsDate(this);
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
