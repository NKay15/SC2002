package hms.appointments;

import hms.utils.Date;
import hms.users.*;
import hms.medicalRecords.AppointmentOutcomeRecord;
import hms.utils.Time;

import java.util.*;

public class Appointment {
    /**
     * ID of patient
     */

    private UUID uuid;

    private Patient patient;
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
    private Appointment rescheduled;

    /**
     * Constructor of appointment
     *
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

    public void setAop() {
        aop = new AppointmentOutcomeRecord();
    }

    public void setRescheduled(Appointment appointment){
        rescheduled = appointment;
    }

    public boolean checkRscheduled(){
        if(rescheduled!=null) return true;
        else return false;
    }

    /**
     * Accessor of patientID
     *
     * @return patientID
     */
    public String getPatientID() {
        return patient.getID();
    }

    /**
     * Accessor of doctorID
     *
     * @return doctorID
     */
    public String getDoctorID() {
        return doctor.getID();
    }

    public Patient getPatient() {
        return patient;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Doctor getDoctor() {
        return doctor;
    }

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

    public void reschedule() {
        status = 5;
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
            aop.print();
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
            case 5:
                System.out.println("Rescheduled");
                break;
        }
    }

    /**
     * @return status
     */
    public int getStatus() {
        return status;
    }
    public Appointment getRescheduled(){
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
