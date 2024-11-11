package hms.appointments;

import hms.utils.Date;
import hms.utils.Time;
import hms.GlobalData;
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
    public void setDoctorSchedule(Date date) {
        DoctorSchedule doctorSchedule = findDateSchedule(date, doctor);
        if (doctorSchedule != null) {
            System.out.println("Schedule exists, do you want to \n" +
                    "1. add breaks." +
                    "2. change working time." +
                    "3. rewrite it.");
            Scanner scanner = GlobalData.getInstance().sc;
            int sc = scanner.nextInt();
            switch (sc) {
                case 1:
                    doctorSchedule.setBreaks();
                    break;

                case 2:
                    System.out.println("Changing working time...");
                    doctorSchedule.setWorkingTime();
                    break;

                case 3:
                    // Code to rewrite the schedule
                    System.out.println("Rewriting schedule...");
                    doctorSchedules.remove(findDateSchedule(date, doctor));
                    doctorSchedule = new DoctorSchedule(doctor, date);
                    doctorSchedules.add(doctorSchedule);
                    break;

                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
                    break;
            }
        } else {
            doctorSchedule = new DoctorSchedule(doctor, date);
            doctorSchedules.add(doctorSchedule);
        }
    }

    /**
     * Returns the list of all doctor schedules.
     *
     * @return A list of DoctorSchedule objects.
     */
    public List<DoctorSchedule> getDoctorSchedules() {
        return doctorSchedules;
    }

    public DoctorSchedule findDateSchedule(Date date, Doctor doctor) {
        for (DoctorSchedule doctorSchedule : doctorSchedules) {
            if (doctorSchedule.getDate().equals(date) && doctorSchedule.getDoctor().getID().equals(doctor.getID()))
                return doctorSchedule;
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
    public boolean isDoctorWorking(Date date, int time) {
        Time tempTime = new Time(time);
        return (isDoctorWorking(date, tempTime));
    }

    /**
     * Checks if the doctor is available at a specific date and time.
     *
     * @param date The date to check availability.
     * @param time The time to check availability.
     * @return true if the doctor is available, false otherwise.
     */
    public boolean isDoctorWorking(Date date, Time time) {
        DoctorSchedule schedule = findDateSchedule(date, doctor);
        if (schedule == null) {
            return false;
        }
        return schedule.isDoctorWorking(time);
    }

    /**
     * Prints available time slots for the doctor on a specific date.
     *
     * @param date The date for which to print available slots.
     */
    public void printAvailableSlot(Date date) {
        printAvailableSlot(date, doctor.getDoctorScheduler());
    }

    public void printAvailableSlot(Date date, DoctorScheduleManager scheduler) {

        //System.out.println("Doctor ID: " + doctor.getID() + "'s available slots are:");
        DoctorSchedule schedule = findDateSchedule(date, doctor);
        if (schedule == null) {
            System.out.println("No available slots.");
            return;
        }
        Time startTime = schedule.getStartTime();
        Time endTime = schedule.getEndTime();
        for (int time = startTime.getIntTime(); time < endTime.getIntTime(); time += 30) {
            if (time % 100 == 60) time += 40;
            if (scheduler.isSlotAvailable(time, date)) {
                String slotTime = String.format("%02d:%02d", time / 100, time % 100);
                System.out.println(slotTime);
            }
        }

        System.out.println("-----------------------------------");
    }

}
