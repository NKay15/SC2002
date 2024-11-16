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
     * ID of appointment
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
     * Integer to store the status of appointment 1 - pending 2 - confirmed 3 - canceled 4 - completed 5 - reschedule
     */
    private int status;

    /**
     * Date of appointment
     */
    private Date date;

    /**
     * Time slot of appointment
     */
    private Time timeSlot;

    /**
     * Appointment Outcome Record to be created after appointment is completed
     */
    private AppointmentOutcomeRecord aop;
    private UUID rescheduled;

    /**
     * Constructor of appointment
     * @param patient ID of patient
     * @param doctor  ID of doctor
     * @param date    Date of appointment
     * @param time    Time Slot of appointment
     */
    public Appointment(Patient patient, Doctor doctor, Date date, Time time) {
        uuid = UUID.randomUUID();
        this.date = date;
        timeSlot = time;
        status = 1;
        aop = null;
        this.patient = patient;
        this.doctor = doctor;
        rescheduled = null;
    }

    /**
     * Constructor of appointment used to write appointment from file
     * @param uuid UUID of the appointment
     * @param patient Paitent of the appointment
     * @param doctor Doctor of the appointment
     * @param date Date of the appontment
     * @param time Time of the appointment
     * @param status Status of the appointment
     * @param rescheduled UUID of the rescheduled appointment if any
     */
    public Appointment(UUID uuid, Patient patient, Doctor doctor, int date, int time, int status,UUID rescheduled) {
        this.uuid = uuid;
        this.patient = patient;
        this.doctor = doctor;
        this.date = new Date(date);
        this.timeSlot = new Time(time);
        this.status = status;
        this.rescheduled = rescheduled;
    }

    /**
     * Create appointment outcome record
     */
    public void setAop() {
        aop = new AppointmentOutcomeRecord();
    }

    /**
     * Load appointment outcome record
     * @param aop appointment outcome record
     */
    public void setAOP(AppointmentOutcomeRecord aop) {
        this.aop = aop;
    }

    /**
     * Load rescheduled appointment
     * @param appointment rescheduled appointment
     */
    public void setRescheduled(Appointment appointment){
        rescheduled = appointment.getUuid();
    }

    /**
     * Remove rescheduled appointment
     */
    public void clearRescheduled(){
        rescheduled = null;
    }

    /**
     * Check if the appointment is rescheduled
     * @return true is appointment is rescheduled otherwise false
     */
    public boolean checkRescheduled(){
        return rescheduled != null;
    }

    /**
     * Accessor of patientID
     * @return patientID
     */
    public String getPatientID() {
        return patient.getID();
    }

    /**
     * Accessor of doctorID
     * @return doctorID
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
     * Accessor of date
     *
     * @return date
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
     * Change the date and time of appointment and change the status to pending
     *
     * @param date
     * @param time
     */
    public void changeDate(Date date, Time time) {
        this.date = date;
        timeSlot = time;
        status = 1;
    }

    /**
     * For doctor to accept the appointment
     */
    public void confirm() {
        status = 2;
    }

    /**
     * For canceling the appointment
     */
    public void cancel() {
        status = 3;
    }

    /**
     * Change the appointment to complete and generate the appointment outcome record
     */
    public void complete() {
        status = 4;
        aop = new AppointmentOutcomeRecord();
    }

    /**
     * Print all details of appointment
     */
    public void print(){
        System.out.println("Appointment UUID: " + uuid);
        System.out.println("Patient ID: " + patient.getID());
        System.out.println("Doctor ID: " + doctor.getID());
        System.out.print("Status: "); printStatus();
        System.out.println("Date: " + date.get());
        System.out.println("Time: " + timeSlot.get());
        if (status == 4) {
            System.out.println();
            if (aop != null) aop.print();
        }
    }

    /**
     * Print the status of the appointment
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

        }
    }

    /**
     * @return status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Accessor of recheduled appointment uuid
     * @return recheduled appointment uuid
     */
    public UUID getRescheduled(){
        return rescheduled;
    }

    /**
     * Print the Appointment Outcome Record if appointment has been completed
     */
    public void printAOP() {
        if (status == 4) aop.print();
        else System.out.println("Appointment has not been completed!");
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(uuid, that.uuid);
    }

}
