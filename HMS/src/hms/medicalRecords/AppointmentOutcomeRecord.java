package hms.medicalRecords;

import hms.utils.Date;
import hms.Medicine;
import hms.GlobalData;

import java.security.Provider;
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
        Scanner sc = GlobalData.getInstance().sc;
        System.out.print("Enter Date of Appointment (ddmmyyyy): ");
        int t = sc.nextInt();
        date = new Date(t);
        service = sc.nextLine();
        System.out.print("Enter Service Provided: ");
        service = sc.nextLine();
        prescription = GlobalData.getInstance().inventory.generatePrescription();
        System.out.print("Enter Consultation Notes: ");
        notes = sc.nextLine();
        status = 1;
        if (prescription.length == 0) status = 2;
    }

    /**
     * Accessor of prescription
     * @return prescription
     */
    public Medicine[] getprescription() {
        return prescription;
    }

    public boolean isDispensed() {
        return (status == 2);
    }

    /**
     * Print the content of Appointment Outcome Record
     */
    public void print() {
        System.out.println("Appointment Outcome Record:");
        System.out.print("Date: ");
        date.print();
        System.out.println("Service provided: " + service);
        if (prescription.length == 0) System.out.println("Prescription: None");
        else {
            System.out.println("Prescription: ");
            for (Medicine n : prescription) n.print();
            System.out.print("Status of Prescription: ");
            if (status == 1) System.out.println("Pending");
            else System.out.println("Dispensed");
        }
        System.out.println("Consultation notes: " + notes);
        System.out.println("End of Appointment Outcome Record.");
    }

    /**
     * Get the service provided in the Appointment Outcome Record
     */
    public String getService() {
        return service;
    }

    /**
     * For pharmacist to dispense the medicine
     */
    public void dispense() {
        status = 2;
    }
}
