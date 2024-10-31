package hms.appointments;

import hms.utils.Date;
import hms.utils.Time;
import hms.users.*;

import java.util.*;

/**
 * Represents the doctor's schedules, including the management of available slots.
 */
public class DoctorSchedules {

    private List<DoctorSchedule> doctorSchedules;
    private Doctor doctor;

    /**
     * Constructs a DoctorSchedules instance for a specific doctor.
     *
     * @param doctor The doctor whose schedules are to be managed.
     */
    public DoctorSchedules(Doctor doctor) {
        this.doctor = doctor;
        doctorSchedules = new ArrayList<DoctorSchedule>();
    }

    /**
     * Sets the doctor's schedule by adding a new DoctorSchedule for the doctor.
     */
    public void setDoctorSchedule() {
        DoctorSchedule doctorSchedule = new DoctorSchedule(doctor);
        doctorSchedules.add(doctorSchedule);
    }

    /**
     * Returns the list of all doctor schedules.
     *
     * @return A list of DoctorSchedule objects.
     */
    public List<DoctorSchedule> getDoctorSchedules() {
        return doctorSchedules;
    }

    /**
     * Finds and returns the doctor's schedule for a specific date.
     *
     * @param date The date for which to find the schedule.
     * @return The DoctorSchedule for the specified date, or null if none exists.
     */
    public DoctorSchedule findDateSchedule(Date date) {
        for (DoctorSchedule doctorSchedule : doctorSchedules) {
            if (doctorSchedule.getDate().equals(date)) return doctorSchedule;
        }
        return null;
    }

    /**
     * Checks if the doctor is available at a specific date and time given in integer format.
     *
     * @param date The date to check availability.
     * @param time The time in integer format (HHMM).
     * @return true if the doctor is available, false otherwise.
     */
    public boolean isDoctorAvailable(Date date, int time) {
        Time tempTime = new Time(time);
        return (isDoctorAvailable(date, tempTime));
    }

    /**
     * Checks if the doctor is available at a specific date and time.
     *
     * @param date The date to check availability.
     * @param time The time to check availability.
     * @return true if the doctor is available, false otherwise.
     */
    public boolean isDoctorAvailable(Date date, Time time) {
        DoctorSchedule schedule = findDateSchedule(date);
        if (schedule == null) {
            return false;
        }
        return schedule.isDoctorAvailable(time);
    }

    /**
     * Prints available time slots for the doctor on a specific date.
     *
     * @param date The date for which to print available slots.
     */
    public void printAvailableSlot(Date date) {
        System.out.println("Doctor ID: " + doctor.getDoctorID() + "'s available slots are:");
        DoctorSchedule schedule = findDateSchedule(date);
        Time startTime = schedule.getStartTime();
        Time endTime = schedule.getEndTime();
        for (int time = startTime.getIntTime(); time < endTime.getIntTime(); time += 30) {
            if (time % 100 == 60) time += 40;
            if (isDoctorAvailable(date, time)) {
                String slotTime = String.format("%02d:%02d", time / 100, time % 100);
                System.out.println(slotTime);
            }
        }
        System.out.println("-----------------------------------");
    }

}
