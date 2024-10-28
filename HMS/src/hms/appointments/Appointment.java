package hms.appointments;

import hms.utils.Date;
import hms.users.*;
import hms.medicalRecords.AppointmentOutcomeRecord;

import java.util.*;

public class Appointment {
    /**
     * ID of patient
     */

    private UUID uuid;
    private String patientID;
    /**
     * ID of doctor
     */
    private String doctorID;

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
    private int timeSlot;

    /**
     * Appointmet Outcome Record to be created after appointment is completed
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
    public Appointment(Patient patient, Doctor doctor, Date date, int time) {
        uuid = UUID.randomUUID();
        patientID = patient.getPatientID();
        doctorID = doctor.getDoctorID();
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
    /**
     * Accessor of patientID
     *
     * @return patientID
     */
    public String getPatientID() {
        return patientID;
    }

    /**
     * Accessor of doctorID
     *
     * @return doctorID
     */
    public String getDoctorID() {
        return doctorID;
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

    /**
     * Accessor of date
     *
     * @return date
     */
    public Date getDate() {
        return date;
    }

    public int getTimeSlot() {
        return timeSlot;
    }

    /**
     * Change the date and time of appointment and change the status to pending
     *
     * @param date
     * @param time
     */
    public void changeDate(Date date, int time) {
        this.date = date;
        timeSlot = time;
        status = 1;
    }

    /**
     * For doctor to accept the appointmemt
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
     * Print the status of the appointment
     */
    public void printStatus() {
        switch (status) {
            case 1:
                System.out.println("pending");
                break;
            case 2:
                System.out.println("confirmed");
                break;
            case 3:
                System.out.println("canceled");
                break;
            case 4:
                System.out.println("completed");
                break;
            case 5:
                System.out.println("rescheduled");
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
     * Print the Appointment Outcome Record if appointment is completed
     */
    public void printAOP() {
        if (status == 4) aop.print();
        else System.out.println("Appointment is not completed");
    }
}
