package hms.appointments;

import hms.users.*;
import hms.utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

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
      public DoctorSchedule(Doctor doctor, Date date) {
          this.doctor = doctor;
          this.breaks = new ArrayList<>();
          this.date = date;
          setWorkingTime();
          setBreaks();
      }

      public void setWorkingTime() {
          Scanner sc = new Scanner(System.in);
          System.out.println("When do you want to work? Input your start time, in hh mm");
          int hour = sc.nextInt();
          int minute = sc.nextInt();
          startTime = new Time(hour, minute);

          System.out.println("Input your end time, in hh mm");
          hour = sc.nextInt();
          minute = sc.nextInt();
          endTime = new Time(hour, minute);
      }

      public void setBreaks() {
          Scanner sc = new Scanner(System.in);

          System.out.println("How many breaks would you like to add?");
          breakCount = sc.nextInt();
          for (int i = 0; i < breakCount; i++) {
              Time breakStart, breakEnd;

              do {
                  System.out.println("Input your break start time, in hh mm");
                  int hour = sc.nextInt();
                  int minute = sc.nextInt();
                  breakStart = new Time(hour, minute);

                  System.out.println("Input your break end time, in hh mm");
                  hour = sc.nextInt();
                  minute = sc.nextInt();
                  breakEnd = new Time(hour, minute);
              } while (breakStart.compareTo(breakEnd) >= 0);

              mergeBreaks(breakStart, breakEnd);
          }
      }

      private void mergeBreaks(Time newBreakStart, Time newBreakEnd) {
      List<Time[]> updatedBreaks = new ArrayList<>();
      int breakCountAdjustment = 0;
      boolean merged = false;

      for (Time[] existingBreak : breaks) {
          Time existingStart = existingBreak[0];
          Time existingEnd = existingBreak[1];

          if (newBreakEnd.compareTo(existingStart) < 0) {
              if (!merged) {
                  updatedBreaks.add(new Time[]{newBreakStart, newBreakEnd});
                  merged = true;
              }
              updatedBreaks.add(existingBreak);
          } else if (newBreakStart.compareTo(existingEnd) > 0) {
              updatedBreaks.add(existingBreak);
          } else {
              breakCountAdjustment++;
              newBreakStart = Time.min(existingStart, newBreakStart);
              newBreakEnd = Time.max(existingEnd, newBreakEnd);
          }
      }

      if (!merged) {
          updatedBreaks.add(new Time[]{newBreakStart, newBreakEnd});
      }
      breaks = updatedBreaks;
      breakCount -= breakCountAdjustment;

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
    public List<Time[]> getBreaks() {
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
            if (time.compareTo(breaks.get(i)[0]) > 0 && time.compareTo(breaks.get(i)[1]) < 0) {
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
