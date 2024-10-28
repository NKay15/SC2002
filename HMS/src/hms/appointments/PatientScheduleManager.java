package hms.appointments;

import java.util.ArrayList;
import java.util.List;

import hms.users.*;
import hms.utils.*;

public class PatientScheduleManager {

    private Patient patient;
    private AppointmentScheduler scheduler = AppointmentScheduler.getInstance();
    private List<Appointment> appointmentList = new ArrayList<>();

    public PatientScheduleManager(Patient patient) {
        this.patient = patient;
        appointmentList = scheduler.getAppointments(patient);
    }

    /**
     * schedule appointment for patient
     *
     * @param appointment
     */
    public void schedulePatientAppointment(Appointment appointment) {
        updatePatientData();
        if (scheduler.findAppointment(appointment, appointmentList) != null) {
            scheduler.scheduleAppointment(appointment);
        }
    }

    /**
     * reschedule appointment for patient
     *
     * @param existingAppointment
     * @param newAppointment
     */
    public void reschedulePatientAppointment(Appointment existingAppointment, Appointment newAppointment) {
        updatePatientData();
        if (scheduler.findWhichList(existingAppointment) != null)
            scheduler.rescheduleAppointment(existingAppointment, newAppointment);
    }

    /**
     * cancel appointment for patient
     *
     * @param appointment
     */
    public void cancelPatientAppointment(Appointment appointment) {
        updatePatientData();
        if (scheduler.findAppointment(appointment, scheduler.findWhichList(appointment)) != null) {
                scheduler.cancelAppointment(appointment, scheduler.findWhichList(appointment));
        } else {
            System.out.println("Slot not found");
        }
    }

    /**
     * Prints all appointments for the patient with a given patient.
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


    public void printAvailableSlots(Date date, Doctor[] doctors) {
        scheduler.printAvailableSlot(date, doctors);
    }

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

    public void updatePatientData() {
        appointmentList = scheduler.getAppointments(patient);
    }
}
