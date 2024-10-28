package hms.appointments;

import java.util.ArrayList;
import java.util.List;

import hms.medicalRecords.*;
import hms.users.*;
import hms.utils.*;

public class PatientScheduleManager {

    private Patient patient;
    private MedicalRecord medicalRecord;
    private AppointmentScheduler scheduler = AppointmentScheduler.getInstance();
    private List<Appointment> appointmentList = new ArrayList<>();

    public PatientScheduleManager(Patient patient) {
        this.patient = patient;
        this.medicalRecord = new MedicalRecord(patient);
        appointmentList = scheduler.getAppointments(patient);
    }

    /**
     * schedule appointment for patient
     *
     * @param appointment
     */
    public void schedulePatientAppointment(Appointment appointment) {
        if (!patient.getPatientID().equals(appointment.getPatientID())) {
            System.out.println("This is not your appointment");
            return;
        }
        scheduler.scheduleAppointment(appointment);
    }

    /**
     * reschedule appointment for patient
     *
     * @param existingAppointment
     * @param newAppointment
     */
    public void reschedulePatientAppointment(Appointment existingAppointment, Appointment newAppointment) {
        if (!patient.getPatientID().equals(existingAppointment.getPatientID())) {
            System.out.println("This is not your appointment");
            return;
        }
        scheduler.rescheduleAppointment(existingAppointment, newAppointment);
    }

    /**
     * cancel appointment for patient
     *
     * @param appointment
     */
    public void cancelPatientAppointment(Appointment appointment) {
        if (!patient.getPatientID().equals(appointment.getPatientID())) {
            System.out.println("This is not your appointment");
            return;
        }
        scheduler.cancelAppointment(appointment);
    }

    /**
     * Prints all appointments for the patient with a given patient.
     */
    public void printPatientAppointment() {
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
     * Prints all medical records for the patient with a given patient.
     */
    public void printMedicalRecord() {
        medicalRecord.print();
    }

    public void printAvailableSlots(Date date, Doctor[] doctors) {
        scheduler.printAvailableSlot(date, doctors);
    }

    public void printAppointmentOutcomeRecord() {
        for (Appointment appointment : appointmentList) {
            if (appointment.getStatus() == 4) {
                System.out.println("Appointment Outcome Record for patient: " + appointment.getPatientID());
                appointment.printAOP();
                System.out.println("-------------");
            }
        }
    }
}
