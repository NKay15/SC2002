package hms.appointments;

import hms.GlobalData;
import hms.users.*;
import hms.utils.*;
import hms.utils.Date;

import javax.print.Doc;
import java.util.*;

/**
 * The DoctorSchedule class is responsible for managing a doctor's schedule for a given date,
 * including working hours and breaks. It allows a doctor to input their availability, which
 * can be queried later to check if they are available at specific times.
 */
public class DoctorSchedule {

    private Time startTime;
    private Time endTime;
    private Date date;
    private Doctor doctor;
    private List<Time[]> breaks;

    private int breakCount;
    public Object getDoctor; // Unused variable, should be removed.


    public DoctorSchedule(Doctor doctor, Date date, Time startTime, Time endTime, int breakCount, List<Time[]> breaks) {
        this.doctor = doctor;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.breaks = breaks;
        this.breakCount = breakCount;
    }

    public DoctorSchedule(Doctor doctor, Date date) {
        this.doctor = doctor;
        this.breaks = new ArrayList<>();
        this.date = date;
        setWorkingTime();
        setBreaks();
    }


    /**
     * Sets the working time by prompting the user for the start and end times.
     * The user is asked to input the times in the format of hours and minutes.
     * The input values are then used to create Time objects for startTime and endTime.
     */
    protected void setWorkingTime() {
        Scanner sc = GlobalData.getInstance().sc;
        do {
            System.out.println("When do you want to work?");
            startTime = new Time();

            System.out.println("When do you want to finish work?");
            endTime = new Time();

        } while (startTime.compareTo(endTime) >= 0);
    }

    /**
     * Prompts the user to input the number of breaks they would like to add,
     * and collects the start and end times for each break. The method ensures
     * that the break start time is before the break end time and calls the
     * {@link #mergeBreaks(Time, Time)} method to process each valid break.
     */
    protected void setBreaks() {
        Scanner sc = GlobalData.getInstance().sc;

        System.out.println("How many breaks would you like to add?");
        boolean validInput = false;
        while (!validInput) {
            try {
                breakCount = sc.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for break count.");
                sc.next();
            }
        }

        int breakCountAdjustment = 0;
        for (int i = 0; i < breakCount; i++) {
            Time breakStart, breakEnd;
            boolean flag = false;
            do {
                if (flag)
                    System.out.println("Invalid break time. Breaks must be within working hours and not overlap.");
                System.out.println("When do you want to start your break?");
                breakStart = new Time();

                System.out.println("When do you want to end your break?");
                breakEnd = new Time();
                flag = breakStart.compareTo(breakEnd) >= 0 || breakStart.compareTo(startTime) < 0 || breakEnd.compareTo(endTime) > 0;
            } while (flag);

            breakCountAdjustment += mergeBreaks(breakStart, breakEnd);
        }
        breakCount -= breakCountAdjustment;
    }

    /**
     * Merges a new break time into the existing breaks.
     * <p>
     * This method checks for overlaps between the new break represented by
     * {@code newBreakStart} and {@code newBreakEnd} with the existing breaks.
     * If overlaps are found, it merges the overlapping breaks and adjusts the
     * list of breaks accordingly. It also keeps track of the number of breaks,
     * updating the count after the merge.
     *
     * @param newBreakStart the start time of the new break
     * @param newBreakEnd   the end time of the new break
     * @return The number of breaks that were merged.
     */
    private int mergeBreaks(Time newBreakStart, Time newBreakEnd) {
        List<Time[]> updatedBreaks = new ArrayList<>();
        int breakCountAdjustment = 0;

        if (breaks.isEmpty()) {
            breaks.add(new Time[]{newBreakStart, newBreakEnd});
            return 0;
        }

        for (Time[] existingBreak : breaks) {
            Time existingStart = existingBreak[0];
            Time existingEnd = existingBreak[1];

            if (newBreakEnd.compareTo(existingStart) < 0) {
                updatedBreaks.add(existingBreak);
            } else if (newBreakStart.compareTo(existingEnd) > 0) {
                updatedBreaks.add(existingBreak);
            } else {
                breakCountAdjustment++;
                newBreakStart = Time.min(existingStart, newBreakStart);
                newBreakEnd = Time.max(existingEnd, newBreakEnd);
            }
        }

        updatedBreaks.add(new Time[]{newBreakStart, newBreakEnd});
        breaks = updatedBreaks;

        return breakCountAdjustment;
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
     * Gets the count of breaks scheduled for the doctor.
     *
     * @return The number of breaks.
     */
    public int getBreakCount() {
        return breakCount;
    }

    /**
     * Gets the breaks scheduled for the doctor.
     *
     * @return A List of Time arrays, where each array contains the start and end time of a break.
     */
    public List<Time[]> getBreaks() {
        return breaks;
    }

    /**
     * Checks if the doctor is available at a specific time.
     *
     * @param time The Time object representing the time to check availability for.
     * @return A boolean indicating whether the doctor is available (true) or not (false).
     */
    protected boolean isDoctorWorking(Time time) {
        for (int i = 0; i < breaks.size(); i++) {
            if (time.compareTo(breaks.get(i)[0]) >= 0 && time.compareTo(breaks.get(i)[1]) < 0) {
                return false;
            }
        }
        return time.compareTo(startTime) >= 0 && time.compareTo(endTime) <= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoctorSchedule that = (DoctorSchedule) o;
        return Objects.equals(date, that.date);
    }

}
