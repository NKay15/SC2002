package hms.appointments;

import hms.users.*;
import hms.utils.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the scheduling of appointments for a doctor.
 */
public class DoctorScheduleManager {
    private Doctor doctor;
    private AppointmentScheduler scheduler = AppointmentScheduler.getInstance();
    private List<Appointment> appointmentList = new ArrayList<>();
    private List<Appointment> pendingList = new ArrayList<>();

    /**
     * Constructs a DoctorScheduleManager for a specified doctor.
     *
     * @param doctor the Doctor object to manage appointments for.
     */
    public DoctorScheduleManager(Doctor doctor) {
        updateDoctorData();
        this.doctor = doctor;
        appointmentList = scheduler.getAppointments(doctor);
        pendingList = scheduler.getPendingAppointments(doctor);
    }

    /**
     * Accepts a given appointment if it is pending.
     *
     * @param appointment the Appointment object to accept.
     */
    public void acceptAppointments(Appointment appointment) {
        updateDoctorData();
        if (scheduler.findAppointment(appointment, pendingList) != null) {
            scheduler.acceptAppointment(appointment);
        }
    }

    /**
     * Declines a given appointment if it is pending.
     *
     * @param appointment the Appointment object to decline.
     */
    public void declineAppointments(Appointment appointment) {
        updateDoctorData();
        if (scheduler.findAppointment(appointment, pendingList) != null) {
            scheduler.declineAppointment(appointment);
        }
    }

    /**
     * Marks a given appointment as complete and updates its outcome record.
     *
     * @param appointment the Appointment object to complete.
     */
    public void completeAppointments(Appointment appointment) {
        appointment.complete();
        updateAppointmentOutcomeRecord(appointment);
    }

    /**
     * Updates the appointment outcome record for a given appointment and set it as complete.
     *
     * @param appointment the Appointment object to update.
     */
    public void updateAppointmentOutcomeRecord(Appointment appointment) {
        appointment.complete();
    }

    /**
     * Prints the pending slots for the specified doctor.
     *
     * @param doctor the Doctor object whose pending slots are printed.
     */
    public void printPendingSlots(Doctor doctor) {
        updateDoctorData();
        for (Appointment appointment : pendingList) {
            System.out.println("Doctor ID: " + doctor.getID() + "'s pending slots are:");
            if (appointment.getDoctorID().equals(doctor.getID())) {
                int time = appointment.getTimeSlot().getIntTime();
                String slotTime = String.format("%02d:%02d", time / 100, time % 100);
                System.out.println(appointment.getDate().get() + slotTime);
            }
            System.out.println("-----------------------------------");
        }
    }

    /**
     * Prints upcoming slots for the specified doctor.
     *
     * @param doctor the Doctor object whose upcoming slots are printed.
     */
    public void printUpcomingSlots(Doctor doctor) {
        updateDoctorData();
        int i = 1;
        for (Appointment appointment : appointmentList) {
            if (appointment.getStatus() == 2) {
                System.out.println("Doctor ID: " + doctor.getID() + "'s upcoming slots are:");
                int time = appointment.getTimeSlot().getIntTime();
                String slotTime = String.format("%02d:%02d", time / 100, time % 100);
                System.out.println(i + " " +appointment.getDate().get() + slotTime);
                i++;
            }
        }
    }

    /**
     * Updates the doctor's appointment data for the current instance.
     */
    public void updateDoctorData() {
        appointmentList = scheduler.getAppointments(doctor);
        pendingList = scheduler.getPendingAppointments(doctor);
    }

    /**
     * Prints available slots for a given date for the specified doctor.
     *
     * @param date   the date to check for available slots.
     * @param doctor the Doctor object whose available slots are printed.
     */
    public void printAvailableSlot(Date date, Doctor doctor) {
        doctor.getDoctorSchedules().printAvailableSlot(date);
    }

    /**
     * Accessor of upcoming appointment
     * @param i index of appointmnet
     * @return null if index does not exist
     */
    public Appointment getUpcomingAppointment(int i) {
        if(i < 0 || i > appointmentList.size()) return null;
        else return appointmentList.get(i);
    }
}
