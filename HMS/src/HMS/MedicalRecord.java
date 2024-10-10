package hms;

import java.util.ArrayList;

public class MedicalRecord {
    /**
     * PatientID
     */
    private String patientID;

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
     * Integer to store blood type 0 - unknown 1 - A+ 2 - A- 3 - B+ 4 - B-
     * 5 - AB+ 6 - AB- 7 - O+ 8 - O-
     */
    private int bloodType;

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
        // who ever writing patient class pls finish this;
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
        System.out.print("Blood Type : ");
        switch (bloodType) {
            case 1 : System.out.println("A+");
            break;
            case 2 : System.out.println("A-");
            break;
            case 3 : System.out.println("B+");
            break;
            case 4 : System.out.println("B-");
            break;
            case 5 : System.out.println("AB+");
            break;
            case 6 : System.out.println("AB-");
            break;
            case 7 : System.out.println("O+");
            break;
            case 8 : System.out.println("O-");
            break;
        }

        System.out.println("Medical History : ")
        for(int i = 0; i < medicalHistory.size(); i++) {
            System.out.println(i + " : " + medicalHistory.get(i));
        }

        System.out.println("-----End of Medical Record-----")
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
}
