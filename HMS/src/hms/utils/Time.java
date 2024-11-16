package hms.utils;

import java.util.Scanner;

import hms.GlobalData;

/**
 * Represents a specific time of day with hours and minutes,
 * ensuring provided times are valid according to specific rules.
 */
public class Time implements Comparable<Time> {
    private int hour;
    private int minute;
    private int time;

    /**
     * Constructs a Time object using an integer representation (hhmm).
     * Prompts for a valid time if the input is incorrect.
     *
     * @param time the time in hhmm format
     */
    public Time(int time) {
        this.time = time;
        this.minute = time % 100;
        this.hour = time / 100;
        while (!checkTime()) {
            System.out.println("This time is not available.");
            System.out.println("Enter time in HHMM.");
            Scanner sc = GlobalData.getInstance().sc;
            time = sc.nextInt();
            this.time = time;
            this.minute = time % 100;
            this.hour = time / 100;
        }
    }

    /**
     * Constructs a Time object using separate hour and minute values.
     * Prompts for valid values if the input is incorrect.
     *
     * @param hour   the hour of the time
     * @param minute the minute of the time
     */
    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
        this.time = 100 * hour + minute;
        while (!checkTime()) {
            System.out.println("This time is not available.");
            System.out.println("Enter time in HH MM.");
            Scanner sc = GlobalData.getInstance().sc;
            hour = sc.nextInt();
            minute = sc.nextInt();
            this.hour = hour;
            this.minute = minute;
            this.time = 100 * this.hour + this.minute;
        }
    }

    /**
     * Returns the string representation of the time in hh:mm format.
     *
     * @return formatted time string
     */
    public String get() {
        String hourStr = (this.hour < 10) ? "0" + this.hour : String.valueOf(this.hour);
        String minuteStr = (this.minute < 10) ? "0" + this.minute : String.valueOf(this.minute);
        return hourStr + ":" + minuteStr;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public Time getTime() {
        return this;
    }

    public int getIntTime() {
        return time;
    }

    /**
     * Validates the time ensuring minutes are either 00 or 30,
     * and hours are within the range of 0 to 23.
     *
     * @return true if the time is valid, false otherwise
     */
    public boolean checkTime() {
        if ( (minute % 100 != 0 && minute % 100 != 30) || ( minute >= 60 || minute < 0)) {
            System.out.println("Only 00/30 is allowed for minute!");
            return false;
        }
        if (hour >= 24 || hour <= 0) {
            System.out.println("Invalid time!");
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Time cmpTime) {
        if (hour > cmpTime.getHour()) return 1;
        else if (hour < cmpTime.getHour()) return -1;
        else if (minute > cmpTime.getMinute()) return 1;
        else if (minute < cmpTime.getMinute()) return -1;
        else return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Time)) return false;
        Time cmpTime = (Time) obj;
        return compareTo(cmpTime) == 0;
    }

    /**
     * Returns the minimum of two Time objects.
     *
     * @param t1 the first Time object
     * @param t2 the second Time object
     * @return the earlier Time object
     */
    public static Time min(Time t1, Time t2) {
        return (t1.compareTo(t2) <= 0) ? t1 : t2;
    }

    /**
     * Returns the maximum of two Time objects.
     *
     * @param t1 the first Time object
     * @param t2 the second Time object
     * @return the later Time object
     */
    public static Time max(Time t1, Time t2) {
        return (t1.compareTo(t2) >= 0) ? t1 : t2;
    }

    /**
     * Prints the time in hh:mm format to the console.
     */
    public void print() {
        String timeFormatted = String.format("%02d:%02d", hour, minute);
        System.out.println(timeFormatted);
    }
}
