package hms.appointments;

import hms.users.*;
import hms.utils.*;

import java.util.Scanner;

public class DoctorSchedule {
    private Time breakStart;
    private Time breakEnd;
    private Time startTime;
    private Time endTime;
    private Date date;
    private Doctor doctor;
    private int breakCount;
    private Time[][] breaks;

    /**
     *
     * @param doctor
     */
    public DoctorSchedule(Doctor doctor) {
        this.doctor = doctor;
        System.out.println("Hello! How would you like to schedule your day.");
        System.out.println("What day you want to schedule? Input your date in dd mm yy");
        Scanner sc = new Scanner(System.in);
        int day = sc.nextInt();
        int month = sc.nextInt();
        int year = sc.nextInt();
        date = new Date(day, month, year);
        date.print();
        System.out.println("When do you want prefer to work?");
        int hour, minute;
        System.out.println("Input your start time, in hh mm");
        hour = sc.nextInt();
        minute = sc.nextInt();
        startTime = new Time(hour, minute);

        System.out.println("Input your end time, in hh mm");
        hour = sc.nextInt();
        minute = sc.nextInt();
        endTime = new Time(hour, minute);

        System.out.println("How many breaks would you have?");
        breakCount = sc.nextInt();
        breaks = new Time[breakCount][2];
        for (int i = 0; i <= breakCount; i++) {
            do {
                System.out.println("Input your break start time, in hh mm");
                hour = sc.nextInt();
                minute = sc.nextInt();
                breakStart = new Time(hour, minute);

                System.out.println("Input your break end time, in hh mm");
                hour = sc.nextInt();
                minute = sc.nextInt();
                breakEnd = new Time(hour, minute);

            } while (breakStart.compareTo(breakEnd) < 0);
            breaks[i][0] = breakStart;
            breaks[i][1] = breakEnd;
        }
    }

    public Date getDate() {
        return date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public Time[][] getBreaks() {
        return breaks;
    }

    public int getBreakCount() {
        return breakCount;
    }

    public void isDoctorAvailable(int time){
        Time temptTime = new Time(time);
    }
    public boolean isDoctorAvailable(Time time) {
        for (int i = 0; i < breakCount; i++) {
            if (time.compareTo(breaks[i][0]) > 0 && time.compareTo(breaks[i][1]) < 0) {
                System.out.println("Doctor at break!");
                return false;
            }
        }
        if (time.compareTo(startTime) < 0 || time.compareTo(endTime) > 0) {
            System.out.println("Doctor not working!");
            return false;
        }
        return true;
    }



}
