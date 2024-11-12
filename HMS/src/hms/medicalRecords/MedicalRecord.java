package hms.medicalRecords;

import hms.utils.Date;
import hms.users.*;
import hms.utils.BloodType;

import java.util.ArrayList;

public class MedicalRecord {
    /**
     * Name
     */
    private String name;

    /**
     * Date of birth
     */
    private Date dob;

    /**
     * Gender see User
     */
    private int gender;

    /**
     * Phone number
     */
    private int phone;

    /**
     * Email address
     */
    private String email;

    /**
     * Enum of blood type
     */
    private BloodType bloodType;

    /**
     * List to store pass diagnosis and treatment
     */
    private ArrayList<String> medicalHistory;

    /**
     * Constructor of Medical Record
     * @param patient the patient whose this medical record will belong to
     */
    public MedicalRecord(Patient patient) {
        medicalHistory = new ArrayList<String>();

        name = patient.getName();
        dob = patient.getDob();
        gender = patient.getGender();
        phone = patient.getPhone();
        email = patient.getEmail();
        bloodType = patient.getBloodType();
    }

    /**
     * Print content of medical record
     */
    public void print() {
        System.out.println("-----Medical Record-----");
        System.out.println("Name : " + name);
        System.out.print("Date of Birth : ");
        dob.print();

        System.out.print("Gender : ");
        switch (gender) {
            case 1 : System.out.println("Male");
            break;
            case 2 : System.out.println("Female");
            break;
            default : System.out.println("Unknow");
        }

        System.out.println("Phone Number : " + phone);
        System.out.println("Email Address : " + email);
        System.out.println("Blood Type : " + bloodType.toString());

        System.out.println("Medical History : ");
        for(int i = 0; i < medicalHistory.size(); i++) {
            System.out.println((i+1) + " : " + medicalHistory.get(i));
        }

        System.out.println("-----End of Medical Record-----");
    }

    /**
     * Mutator of phone
     * @param newPhone new phone number
     */
    public void setPhone(int newPhone) {
        phone = newPhone;
    }

    /**
     * Mutator of email
     * @param newEmail new email address
     */
    public void setEmail(String newEmail) {
        email = newEmail;
    }

    /**
     * Add to medical history
     * @param add text to be added
     */
    public void newMedicalHistory(String add) {
        medicalHistory.add(add);
    }

    /**
     * accessor of medical history
     * @return medical history
     */
    public ArrayList<String> getMedicalHistory(){
        return medicalHistory;
    }
}
