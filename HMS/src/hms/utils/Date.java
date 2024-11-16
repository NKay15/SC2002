package hms.utils;

import java.util.Scanner;

import hms.GlobalData;

public class Date implements Comparable<Date> {

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
     * Constructor of date
     * @param ddmmyyyy date in ddmmyyyy format
     */
    public Date(int ddmmyyyy) {
        Scanner sc = GlobalData.getInstance().sc;
        while(!checkDate(ddmmyyyy)){
            System.out.println("Enter Date in DDMMYYYY:");
            ddmmyyyy = sc.nextInt();
        }

        year = ddmmyyyy%10000;
        ddmmyyyy /= 10000;
        month = ddmmyyyy%100;
        ddmmyyyy /= 100;
        day = ddmmyyyy;
    }

    private boolean checkDate(int ddmmyyyy){
        int[] days = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if(ddmmyyyy >= 1000000 && ddmmyyyy <= 99999999){
            int dd = ddmmyyyy / 1000000;
            int mm = ddmmyyyy / 10000 % 100;
            if(mm > 12){
                System.out.println("Invalid Date!");
                System.out.println("Month out of range.");
                return false;
            }
            int yyyy = ddmmyyyy % 10000;
                if ((yyyy % 4 == 0 && yyyy % 100 != 0) || (yyyy % 400 == 0)) {
                days[2] = 29; 
            }
            if(dd > days[mm]){
                System.out.println("Invalid Date!");
                System.out.println(mm + "/" + yyyy + " only has " + days[mm] + " days");
                return false;
            }
            return true;
        }
        System.out.println("Invalid input! Please enter data in DDMMYYYY");
        return false;
    }

    /**
     * Constructor of date
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
     * @return year
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
     * Implements compareTo from Comparable
     * @param cmpDate date to compare to
     * @return -1 if this date is earlier, 1 if later and 0 if they are the same
     */
    public int compareTo(Date cmpDate) {
        if (year > cmpDate.year()) return -1;
        else if (year < cmpDate.year()) return 1;
        else if (month > cmpDate.month()) return -1;
        else if (month < cmpDate.month()) return 1;
        else if (day > cmpDate.day()) return -1;
        else if (day < cmpDate.day()) return 1;
        else return 0;
    }

    public boolean equals(Date cmpDate) {
        return compareTo(cmpDate) == 0;
    }

    /**
     * Print date in dd/mm/yyyy format
     */
    public String get() {
        String day;
        if (this.day < 10) day = "0" + this.day;
        else day = this.day + "";
        String month;
        if (this.month < 10) month = "0" + this.month;
        else month = this.month + "";
        return day + "/" + month + "/" + year;
    }
    public int getIntDate(){
        return day * 1000000 + month * 10000 + year;
    }
    public void print() {
        System.out.println(get());
    }
}
