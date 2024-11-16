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

    private List<DoctorSchedule> doctorSchedules; // List to hold doctor's schedules
    private Doctor doctor; // The doctor for whom the schedules are managed

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
     *
     * If a schedule for the specified date already exists, the user is prompted to rewrite it or exit.
     *
     * @param date The date for which to set the schedule.
     */
    public void setDoctorSchedule(Date date) {
        DoctorSchedule doctorSchedule = findDateSchedule(date, doctor);
        if (doctorSchedule != null) {
            System.out.println("Schedule Already Exists! Do you want to: \n" +
                    "1. Rewrite it; 2. Exit");
            System.out.print("Enter your choice: ");
            Scanner scanner = GlobalData.getInstance().sc;
            int choice;
            do {
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Rewriting Schedule...");
                        doctorSchedules.remove(findDateSchedule(date, doctor));
                        doctorSchedule = new DoctorSchedule(doctor, date);
                        doctorSchedules.add(doctorSchedule);
                        break;

                    case 2:
                        System.out.println("Returning to Menu...");
                        break;

                    default:
                        System.out.print("Invalid choice! Try again: ");
                        break;
                }
            } while (choice != 1 && choice != 2);
        } else {
            // Creating and adding a new schedule if none exists
            doctorSchedule = new DoctorSchedule(doctor, date);
            doctorSchedules.add(doctorSchedule);
        }
    }

    /**
     * Finds the doctor's schedule for a specific date.
     *
     * @param date The date for which to find the schedule.
     * @param doctor The doctor whose schedule is being searched for.
     * @return The DoctorSchedule for the specified date, or null if no schedule exists.
     */
    public DoctorSchedule findDateSchedule(Date date, Doctor doctor) {
        for (DoctorSchedule doctorSchedule : doctorSchedules) {
            // Check for matching date and doctor ID
            if (doctorSchedule.getDate().equals(date) && doctorSchedule.getDoctor().getID().equals(doctor.getID()))
                return doctorSchedule;
        }
        return null; // No matching schedule found
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
            return false; // No schedule means not working
        }
        return schedule.isDoctorWorking(time); // Check if the doctor is working at the specified time
    }

    /**
     * Prints available time slots for the doctor on a specific date.
     *
     * @param date The date for which to print available slots.
     */
    public void printAvailableSlot(Date date) {
        printAvailableSlot(date, doctor.getDoctorScheduler());
    }

    /**
     * Prints available time slots for the doctor on a specific date using a given scheduler.
     *
     * @param date The date for which to print available slots.
     * @param scheduler The DoctorScheduleManager to check slot availability.
     */
    protected void printAvailableSlot(Date date, DoctorScheduleManager scheduler) {

        DoctorSchedule schedule = findDateSchedule(date, doctor);
        if (schedule == null) {
            System.out.println("No available slots.");
            return; // No schedule, hence no slots
        }
        Time startTime = schedule.getStartTime(); // Get the start time from the schedule
        Time endTime = schedule.getEndTime(); // Get the end time from the schedule
        for (int time = startTime.getIntTime(); time < endTime.getIntTime(); time += 30) {
            if (time % 100 == 60) time += 40; // Adjust for minute overflow
            // Check if the slot is available
            if (scheduler.isSlotAvailable(time, date)) {
                String slotTime = String.format("%02d:%02d", time / 100, time % 100);
                System.out.println(slotTime); // Print available slot
            } else {
                System.out.println("Doctor is not available."); // Inform about unavailability
            }
        }

        System.out.println("-----------------------------------"); // Separator for clarity
    }

    /**
     * Adds a DoctorSchedule to the list of doctor schedules.
     *
     * @param schedule The DoctorSchedule to be added.
     */
    public void addSchedule(DoctorSchedule schedule) {
        doctorSchedules.add(schedule);
    }

    /**
     * Returns the list of DoctorSchedules associated with the doctor.
     *
     * @return A list of DoctorSchedule objects.
     */
    public List<DoctorSchedule> getDoctorSchedules() {
        return doctorSchedules; // Return the list of schedules
    }
}
