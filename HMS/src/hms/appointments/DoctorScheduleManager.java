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
        if (scheduler.findAppointment(appointment, pendingList)) {
            scheduler.acceptAppointment(appointment);
            return;
        }
        System.out.println("Appointment not pending.");
    }

    /**
     * Declines a given appointment if it is pending.
     *
     * @param appointment the Appointment object to decline.
     */
    public void declineAppointments(Appointment appointment) {
        updateDoctorData();
        if (scheduler.findAppointment(appointment, pendingList)) {
            scheduler.declineAppointment(appointment);
            return;
        }
        System.out.println("Appointment not pending.");
    }


    /**
     * Updates the appointment outcome record for a given appointment and set it as complete.
     *
     * @param appointment the Appointment object to update.
     */
    public void updateAppointmentOutcomeRecord(Appointment appointment) {
        if (appointment.getStatus() != 2 && appointment.getStatus() != 4) {
            System.out.println("Appointment is not confirmed/completed.");
            return;
        }
        appointment.complete();
    }

    /**
     * Prints the pending slots for the specified doctor.
     *
     * @param doctor the Doctor object whose pending slots are printed.
     */
    public void printPendingSlots(Doctor doctor) {
        updateDoctorData();
        System.out.println("Doctor ID: " + doctor.getID() + "'s pending slots are:");
        for (Appointment appointment : pendingList) {
            if (appointment.getDoctorID().equals(doctor.getID())) {
                int time = appointment.getTimeSlot().getIntTime();
                String slotTime = String.format("%02d:%02d", time / 100, time % 100);
                System.out.println(appointment.getDate().get() + slotTime);
            }
        }
        System.out.println("-----------------------------------");
    }

    /**
     * Prints upcoming slots for the specified doctor.
     *
     * @param doctor the Doctor object whose upcoming slots are printed.
     */
    public void printUpcomingSlots(Doctor doctor) {
        updateDoctorData();
        int i = 1;
        System.out.println("Doctor " + doctor.getName() + "'s upcoming slots are:");
        for (Appointment appointment : appointmentList) {
            if (appointment.getStatus() == 2) {
                System.out.println("Appointment " + i + ":");
                appointment.print();
                System.out.println();
                i++;
            }
        }
    }

    public boolean isSlotAvailable(int time, Date date) {
        Time tempTime = new Time(time);
        return scheduler.isSlotAvailable(doctor, tempTime, date);
    }

    public boolean isSlotAvailable(Time time, Date date) {
        return scheduler.isSlotAvailable(doctor, time, date);
    }

    /**
     * Retrieves the list of appointments for this doctor.
     */
    public List<Appointment> getAppointmentsDoctor() {
        return scheduler.getAppointments(doctor);
    }

    /**
     * Retrieves the list of pending appointments for this doctor.
     */
    public List<Appointment> getPendingAppointmentsDoctor() {
        return scheduler.getPendingAppointments(doctor);
    }

    /**
     * Updates the doctor's appointment data for the current instance.
     */
    public void updateDoctorData() {
        appointmentList = scheduler.getAppointments(doctor);
        pendingList = scheduler.getPendingAppointments(doctor);
    }


    /**
     * Accessor of upcoming appointment
     *
     * @param i index of appointment
     * @return null if index does not exist
     */
    public Appointment getUpcomingAppointment(int i) {
        if (i < 0 || i > appointmentList.size()) return null;
        else return appointmentList.get(i);
    }
}
