package hms.medicalRecords;

import hms.GlobalData;
import hms.pharmacy.Medicine;
import hms.services.AOPFileService;
import hms.services.MedicalRecordFileService;
import hms.utils.Date;
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
        service = MedicalRecordFileService.nextLine();
        System.out.print("Enter Service Provided: ");
        service = MedicalRecordFileService.nextLine();
        prescription = GlobalData.getInstance().inventory.generatePrescription();
        System.out.print("Enter Consultation Notes: ");
        notes = MedicalRecordFileService.nextLine();
        status = 1;
        if (prescription.length == 0) status = 2;
    }

    public AppointmentOutcomeRecord(Date date, String service, Medicine[] prescription, int status, String notes) {
        this.date = date;
        this.service = service;
        this.prescription = prescription;
        this.status = status;
        this.notes = notes;
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

    public int getStatus() {
        return status;
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

    public String getNotes() {
        return notes;
    }

    public Date getDate() {
        return date;
    }

    /**
     * For pharmacist to dispense the medicine
     */
    public void dispense() {
        status = 2;
    }
}
