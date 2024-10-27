package hms.appointments;

import java.util.*;

import hms.utils.Date;

public class AppointmentScheduler {
    private List<Appointment> appointments;

    public AppointmentScheduler() {
        this.appointments = new ArrayList<>();
    }

    /**
     * Schedule a new appointment if the slot is available.
     * @param appointment The appointment to be scheduled
     * @return true if scheduled successfully, false otherwise
     */
    public boolean scheduleAppointment(Appointment appointment) {
        if (isSlotAvailable(appointment)) {
            appointments.add(appointment);
            System.out.println("Successfully scheduled.");
            return true;
        } else {
            System.out.println("Current slot is not available.");
            return false;
        }
    }

    /**
     * Reschedule an existing appointment to a new slot.
     * @param existingAppointment The existing appointment to be rescheduled
     * @param newAppointment The new appointment with updated time slot
     * @return true if rescheduled successfully, false otherwise
     */
    public boolean rescheduleAppointment(Appointment existingAppointment, Appointment newAppointment) {
        if (isSlotAvailable(newAppointment)) {
            if (cancelAppointment(existingAppointment)) {
                scheduleAppointment(newAppointment);
                System.out.println("Successfully rescheduled.");
                return true;
            }
            return false;
        }
        System.out.println("New time slot is not available.");
        return false;
    }

    /**
     * Cancel an existing appointment.
     * @param appointment The appointment to be canceled
     * @return true if canceled successfully, false otherwise
     */
    public boolean cancelAppointment(Appointment appointment) {
        int appointmentNum = findAppointment(appointment);
        if (appointmentNum != -1) {
            appointments.remove(appointmentNum);
            System.out.println("Successfully cancelled.");
            return true;
        }
        System.out.println("Can't find slot.");
        return false;

    }

    /**
     * Check if a slot is available for a certain appointment.
     * @param appointment The appointment to check availability for
     * @return true if the slot is available, false otherwise
     */
    private boolean isSlotAvailable(Appointment appointment) {
        return isSlotAvailable(appointment.getDoctorID(), appointment.getTimeSlot());
    }

    /**
     * Check if a slot is available for a specific doctor at a specific time.
     * @param doctorID The ID of the doctor
     * @param time The date and time of the appointment
     * @return true if the slot is available, false otherwise
     */

    private boolean isSlotAvailable(String doctorID, int time) {
        for (Appointment appointment : appointments) {
            if (appointment.getDoctorID().equals(doctorID) && appointment.getTimeSlot() == time) {
                return false;
            }
        }
        return true;
    }

    /**
     * Find the index of an appointment in the list.
     * @param appointment The appointment to search for
     * @return The index of the appointment if found, or -1 if not found
     */
    private int findAppointment(Appointment appointment) {
        return findAppointment(appointment.getPatientID(), appointment.getTimeSlot());
    }

    /**
     * Find the index of an appointment in the list by patient ID and date.
     * @param patientID The ID of the patient
     * @param time The date and time of the appointment
     * @return The index of the appointment if found, or -1 if not found
     */
    private int findAppointment(String patientID, int time) {
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            if (appointment.getPatientID().equals(patientID) && appointment.getTimeSlot() == time) {
                return i;
            }
        }
        return -1;
    }


}
