package hms.appointments;

import hms.users.*;
import hms.utils.*;

import java.util.Objects;
import java.util.Scanner;

/**
 * The DoctorSchedule class is responsible for managing a doctor's schedule for a given date,
 * including working hours and breaks. It allows a doctor to input their availability, which
 * can be queried later to check if they are available at specific times.
 */
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
     * Constructs a DoctorSchedule object for a specified doctor.
     * Prompts the doctor to input their preferred schedule details including date, start time,
     * end time, and break times, and initializes the necessary fields.
     *
     * @param doctor The Doctor object representing the doctor whose schedule is being created.
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

    /**
     * Gets the date of the doctor's schedule.
     *
     * @return The Date object representing the scheduled day.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets the doctor of the doctor's schedule.
     *
     * @return The doctor object representing the scheduled doctor.
     */
    public Doctor getDoctor() {
        return doctor;
    }

    /**
     * Gets the start time of the doctor's work.
     *
     * @return The Time object representing the start time.
     */
    public Time getStartTime() {
        return startTime;
    }

    /**
     * Gets the end time of the doctor's work.
     *
     * @return The Time object representing the end time.
     */
    public Time getEndTime() {
        return endTime;
    }

    /**
     * Gets the breaks scheduled for the doctor.
     *
     * @return A 2D array of Time objects, where each row contains the start and end time of a break.
     */
    public Time[][] getBreaks() {
        return breaks;
    }

    /**
     * Gets the number of breaks scheduled for the doctor.
     *
     * @return The integer count of breaks.
     */
    public int getBreakCount() {
        return breakCount;
    }

    /**
     * Checks if the doctor is available at a specific time represented by an integer.
     * Note: The implementation does not currently perform any actions.
     *
     * @param time The time to check availability for, represented as an integer.
     */
    public void isDoctorAvailable(int time){
        Time temptTime = new Time(time);
    }

    /**
     * Checks if the doctor is available at a specific time.
     *
     * @param time The Time object representing the time to check availability for.
     * @return A boolean indicating whether the doctor is available (true) or not (false).
     */
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoctorSchedule that = (DoctorSchedule) o;
        return Objects.equals(date, that.date);
    }

}
