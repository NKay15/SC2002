package hms.appointments;

import hms.utils.Date;
import hms.users.*;
import hms.medicalRecords.AppointmentOutcomeRecord;
import hms.utils.Time;

import java.util.*;

/**
 * This class contains all the details of an appointment
 */
public class Appointment {

    /**
     * Unique ID of the appointment
     */
    private UUID uuid;

    /**
     * Patient of the appointment
     */
    private Patient patient;

    /**
     * Doctor of the appointment
     */
    private Doctor doctor;


    /**
     * Integer to store the status of the appointment:
     * 1 - pending, 2 - confirmed, 3 - canceled, 4 - completed, 5 - rescheduled
     */
    private int status;

    /**
     * Date of the appointment
     */
    private Date date;

    /**
     * Time slot of the appointment
     */
    private Time timeSlot;

    /**
     * Appointment Outcome Record to be created after appointment is completed
     */
    private AppointmentOutcomeRecord aop;

    private UUID rescheduled;

    /**
     * Constructor to create a new appointment with given patient, doctor, date, and time.
     *
     * @param patient Patient making the appointment
     * @param doctor  Doctor assigned to the appointment
     * @param date    Date of the appointment
     * @param time    Time slot of the appointment
     */
    public Appointment(Patient patient, Doctor doctor, Date date, Time time) {
        uuid = UUID.randomUUID();
        this.date = date;
        this.timeSlot = time;
        status = 1; // Set status to pending
        aop = null;
        this.patient = patient;
        this.doctor = doctor;
        rescheduled = null;
    }

    /**
     * Constructor to create an appointment with specific attributes.
     *
     * @param uuid      Unique ID of the appointment
     * @param patient   Patient making the appointment
     * @param doctor    Doctor assigned to the appointment
     * @param date      Date of the appointment
     * @param time      Time of the appointment
     * @param status    Current status of the appointment
     * @param rescheduled UUID of the previously scheduled appointment if any
     */
    public Appointment(UUID uuid, Patient patient, Doctor doctor, int date, int time, int status, UUID rescheduled) {
        this.uuid = uuid;
        this.patient = patient;
        this.doctor = doctor;
        this.date = new Date(date);
        this.timeSlot = new Time(time);
        this.status = status;
        this.rescheduled = rescheduled;
    }

    /**
     * Initializes the Appointment Outcome Record.
     */
    public void setAop() {
        aop = new AppointmentOutcomeRecord();
    }

    /**
     * Sets the specified Appointment Outcome Record for this appointment.
     *
     * @param aop Appointment Outcome Record to associate with this appointment
     */
    public void setAOP(AppointmentOutcomeRecord aop) {
        this.aop = aop;
    }

    /**
     * Marks this appointment as rescheduled to another appointment.
     *
     * @param appointment Appointment that represents the new scheduling
     */
    public void setRescheduled(Appointment appointment) {
        rescheduled = appointment.getUuid();
    }

    /**
     * Clears the rescheduling information of this appointment.
     */
    public void clearRescheduled() {
        rescheduled = null;
    }

    /**
     * Checks if the appointment has been rescheduled.
     *
     * @return true if the appointment has been rescheduled, false otherwise
     */
    public boolean checkRescheduled(){
        return rescheduled != null;
    }

    /**
     * Accessor for the patient's ID.
     *
     * @return ID of the patient
     */
    public String getPatientID() {
        return patient.getID();
    }

    /**
     * Accessor for the doctor's ID.
     *
     * @return ID of the doctor
     */
    public String getDoctorID() {
        return doctor.getID();
    }

    /**
     * Accessor of patient
     * @return patient
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Accessor of uuid
     * @return uuid
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Accessor of doctor
     * @return doctor
     */
    public Doctor getDoctor() {
        return doctor;
    }

    /**
     * Accessor of aop
     * @return aop
     */
    public AppointmentOutcomeRecord getAop(){
        return aop;
    }

    /**
     * Accessor for the date of the appointment.
     *
     * @return Date object representing the appointment's date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Accessor of timeSlot
     * @return timeSlot
     */
    public Time getTimeSlot() {
        return timeSlot.getTime();
    }

    /**
     * Changes the date and time of the appointment, resetting the status to pending.
     *
     * @param date New date for the appointment
     * @param time New time for the appointment
     */
    public void changeDate(Date date, Time time) {
        this.date = date;
        timeSlot = time;
        status = 1; // Set status to pending
    }

    /**
     * Confirms the appointment.
     */
    public void confirm() {
        status = 2; // Set status to confirmed
    }

    /**
     * Cancels the appointment.
     */
    public void cancel() {
        status = 3; // Set status to canceled
    }

    /**
     * Completes the appointment and generates the appointment outcome record.
     */
    public void complete() {
        status = 4; // Set status to completed
        aop = new AppointmentOutcomeRecord();
    }

    /**
     * Prints all details of the appointment to the console.
     */
    public void print() {
        System.out.println("Appointment UUID: " + uuid);
        System.out.println("Patient ID: " + patient.getID());
        System.out.println("Doctor ID: " + doctor.getID());
        System.out.print("Status: ");
        printStatus();
        System.out.println("Date: " + date.get());
        System.out.println("Time: " + timeSlot.get());
        if (status == 4) { // If the appointment is completed
            System.out.println();
            if (aop != null) aop.print();
        }
    }

    /**
     * Prints the status of the appointment.
     */
    public void printStatus() {
        switch (status) {
            case 1:
                System.out.println("Pending");
                break;
            case 2:
                System.out.println("Confirmed");
                break;
            case 3:
                System.out.println("Cancelled");
                break;
            case 4:
                System.out.println("Completed");
                break;
            default:
                System.out.println("Unknown status");
                break;
        }
    }

    /**
     * Accessor for the current status of the appointment.
     *
     * @return current status of the appointment
     */
    public int getStatus() {
        return status;
    }

    /**
     * Accessor for the UUID of the rescheduled appointment, if applicable.
     *
     * @return UUID of the rescheduled appointment or null if not applicable
     */
    public UUID getRescheduled() {
        return rescheduled;
    }

    /**
     * Prints the Appointment Outcome Record if the appointment has been completed.
     */
    public void printAOP() {
        if (status == 4) { // If the appointment is completed
            aop.print();
        } else {
            System.out.println("Appointment has not been completed!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Check if same reference
        if (o == null || getClass() != o.getClass()) return false; // Check for null or different class
        Appointment that = (Appointment) o; // Cast to Appointment
        return Objects.equals(uuid, that.uuid); // Compare UUIDs for equality
    }
}
