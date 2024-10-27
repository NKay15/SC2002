package hms.medicalRecords;

import hms.utils.Date;
import hms.Medicine;

import java.util.Scanner;

public class AppointmentOutcomeRecord {
    /**
     * Date of appointment
     */
    private Date date;

    /**
     * Type of service provided
     */
    private String service;

    /**
     * Prescription
     */
    private Medicine[] prescription;

    /**
     * integer to store the status of prescription 1 - pending 2 - dispense
     */
    private int status;

    /**
     * Consultation notes
     */
    private String notes;

    /**
     * Contructor of Appointment Outcome Record triggered by the completion of appointment
     */
    public AppointmentOutcomeRecord() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the date of appointment (ddmmyyyy) : ");
        int t = sc.nextInt();
        date = new Date(t);
        System.out.print("Enter the service provided : ");
        service = sc.nextLine();
        /*
         * Generate Medicine object for prescription
         */
        System.out.print("Enter the consultation notes : ");
        notes = sc.nextLine();
        status = 1;
    }

    /**
     * Accessor of prescription
     * @return prescription
     */
    public Medicine[] getprescription() {
        return prescription;
    }

    public Boolean isDispensed() {
        return (status == 2);
    }

    /**
     * Print the content of Appointment Outcome Record
     */
    public void print() {
        System.out.println("Appointment Outcome Record");
        System.out.print("Date : ");
        date.print();
        System.out.println("Service provided : " + service);
        System.out.print("Prescription : ");
        for(Medicine n : prescription) n.print();
        System.out.print("Status of prescription : ");
        if (status == 1) System.out.println("pending");
        else System.out.println("dispensed");

        System.out.println("Consultation notes : " + notes);
        System.out.println("End of Appointment Outcome Record");
    }

    /**
     * For pharamacist to dispense the medicine
     */
    public void dispense() {
        status = 2;
    }
}
