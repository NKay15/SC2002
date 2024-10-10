package hms;

public class Date {

    /**
     * Day of the date
     */
    private int day;

    /**
     * Month of the date
     */
    private int month;

    /**
     * Year of the date
     */
    private int year;

    /**
     * Contructor of date
     * @param ddmmyyyy date in ddmmyyyy format
     */
    public Date(int ddmmyyyy) {
        year = ddmmyyyy%10000;
        ddmmyyyy /= 10000;
        month = ddmmyyyy%100;
        ddmmyyyy /= 100;
        day = ddmmyyyy;
    }

    /**
     * Contructor of date
     * @param dd day
     * @param mm month
     * @param yyyy year
     */
    public Date(int dd, int mm, int yyyy) {
        day = dd;
        month = mm;
        year = yyyy;
    }

    /**
     * Accessor of day
     * @return day
     */
    public int day() {
        return day;
    }

    /**
     * Accessor of month
     * @return month
     */
    public int month() {
        return month;
    }

    /**
     * Accessor of year
     * @return yaer
     */
    public int year() {
        return year;
    }

    /**
     * Copy the date
     * @return A copy of date
     */
    public Date copyDate() {
        return new Date(day, month, year);
    }

    /**
     * Compare this date with another date
     * @param cmpDate date to compare to
     * @return 1 if this date is earlier, -1 if later and 0 if they are the same
     */
    public int compare(Date cmpDate) {
        if (year > cmpDate.year()) return 1;
        else if (year < cmpDate.year()) return -1;
        else if (month > cmpDate.month()) return 1;
        else if (month < cmpDate.month()) return -1;
        else if (day > cmpDate.day()) return 1;
        else if (day < cmpDate.day()) return -1;
        else return 0;
    }

    /**
     * Print date in dd/mm/yyyy format
     */
    public void print() {
        System.out.println(day + "/" + month + "/" + year);
    }
}
