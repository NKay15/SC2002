package hms.appointments;

import java.util.ArrayList;
import java.util.List;

import hms.users.*;
import hms.utils.*;

public class PatientScheduleManager {

    private Patient patient;
    private AppointmentScheduler scheduler = AppointmentScheduler.getInstance();
    private List<Appointment> appointmentList = new ArrayList<>();

    /**
     * Constructs a PatientScheduleManager for the given patient and retrieves their appointments.
     *
     * @param patient the patient whose appointments are managed
     */
    public PatientScheduleManager(Patient patient) {
        this.patient = patient;
        appointmentList = scheduler.getAppointments(patient);
    }

    /**
     * Schedules a new appointment for the patient if it doesn't already exist in their appointment list.
     *
     * @param appointment the appointment to schedule
     */
    public void schedulePatientAppointment(Appointment appointment) {
        updatePatientData();
        if (scheduler.findAppointment(appointment, appointmentList) != null) {
            scheduler.scheduleAppointment(appointment);
        }
    }

    /**
     * Reschedules an existing appointment for the patient with a new appointment.
     *
     * @param existingAppointment the appointment to be rescheduled
     * @param newAppointment the new appointment details
     */
    public void reschedulePatientAppointment(Appointment existingAppointment, Appointment newAppointment) {
        updatePatientData();
        if (scheduler.findWhichList(existingAppointment) != null)
            scheduler.rescheduleAppointment(existingAppointment, newAppointment);
    }

    /**
     * Cancels an existing appointment for the patient if it is found in the appointment lists.
     *
     * @param appointment the appointment to cancel
     */
    public void cancelPatientAppointment(Appointment appointment) {
        updatePatientData();
        if (scheduler.findAppointment(appointment, scheduler.findWhichList(appointment)) != null) {
            scheduler.cancelAppointment(appointment);
        } else {
            System.out.println("Slot not found");
        }
    }

    /**
     * Prints all appointments for the patient in a formatted manner.
     */
    public void printPatientAppointment() {
        updatePatientData();
        for (Appointment appointment : appointmentList) {
            System.out.println("Doctor ID: " + appointment.getDoctorID());
            System.out.println("Date: " + appointment.getDate());
            System.out.println("Time Slot: " + appointment.getTimeSlot());
            System.out.print("Status: ");
            appointment.printStatus();
            System.out.println("-------------");
        }
    }

    /**
     * Prints available time slots for the doctors on a given date.
     *
     * @param date the date for which available slots are printed
     * @param doctors the list of doctors to check availability for
     */
    public void printAvailableSlots(Date date, List<Doctor> doctors) {
        for (Doctor doctor : doctors) {
            doctor.getDoctorSchedules().printAvailableSlot(date);
        }
    }

    /**
     * Prints outcome records for appointments with status indicating completion.
     */
    public void printAppointmentOutcomeRecord() {
        updatePatientData();
        for (Appointment appointment : appointmentList) {
            if (appointment.getStatus() == 4) {
                System.out.println("Appointment Outcome Record for patient: " + appointment.getPatientID());
                appointment.printAOP();
                System.out.println("-------------");
            }
        }
    }

    /**
     * Updates the list of appointments for the patient from the scheduler.
     */
    public void updatePatientData() {
        appointmentList = scheduler.getAppointments(patient);
    }
}
