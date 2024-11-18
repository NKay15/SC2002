package hms.users;

import hms.GlobalData;
import hms.appointments.*;
import hms.medicalRecords.MedicalRecord;
import hms.services.DoctorFileService;
import hms.services.PatientFileService;
import hms.utils.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class of Patients and its Functionality
 */
public class Patient extends User{

    /**
     * Date of Birth
     */
    private Date dob;

    /**
     * Phone Number
     */
    private int phone;

    /**
     * Email Address
     */
    private String email;

    /**
     * Enum containing Blood Type
     */
    private BloodType bloodType;

    /**
     * Appointment Scheduler for a Specific Patient
     */
    private PatientScheduleManager patientSchedule;

    /**
     * Medical Record
     */
    private MedicalRecord mr;

    /**
     * Constructor for Patient
     * @param patientID ID of patient
     * @param name name of patient
     * @param gender gender of patient
     * @param dob date of birth of patient
     * @param phone phone number of patient
     * @param email email of patient
     * @param bloodType blood type of patient
     * @param password password if patient
     */
    public Patient(String patientID, String name, int gender, Date dob, int phone, String email, BloodType bloodType, Password password) {
        super(patientID, name, Role.PATIENT, gender, password);
        this.dob = dob;
        this.phone = phone;
        this.email = email;
        this.bloodType = bloodType;
        mr = new MedicalRecord(this);
        patientSchedule = new PatientScheduleManager(this);
    }

    /**
     * Update Patient's User Data
     *
     * @param patientID ID of patient
     * @param name name of patient
     * @param gender gender of patient
     * @param dob date of birth of patient
     * @param phone phone number of patient
     * @param email email of patient
     * @param bloodType blood type of patient
     * @param password password if patient
     * @return true if a data is changed otherwise false
     */
    public boolean update(String patientID, String name, int gender, Date dob, int phone, String email, BloodType bloodType, Password password) {
        boolean change = super.update(patientID, name, Role.PATIENT, gender, password);

        if(!this.dob.equals(dob)) {
            this.dob = dob;
            change = true;
        }

        if(this.phone != phone) {
            this.phone = phone;
            change = true;
        }

        if(!this.email.equals(email)) {
            this.email = email;
            change = true;
        }

        if(!this.bloodType.equals(bloodType)) {
            this.bloodType = bloodType;
            change = true;
        }

        return change;
    }

    /**
     * Return Date of Birth
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Set Date of Birth
     */
    public void setDob(int day, int month, int year) {
        this.dob = new Date(day, month, year);
    }

    /**
     * Return Phone Number
     */
    public int getPhone() {
        return phone;
    }

    /**
     * Set Phone Number
     */
    public void setPhone(int phone) {
        this.phone = phone;
    }

    /**
     * Return Email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set Email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Return Blood Type
     */
    public BloodType getBloodType() {
        return bloodType;
    }

    /**
     * Get Blood Type
     */
    public String getBloodTypeString() {
        return bloodType.toString();
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    /**
     * Return Schedule of Patient
     */
    public PatientScheduleManager getPatientSchedule() {
        return patientSchedule;
    }

    /**
     * Convert int to BloodType enum
     * @return ENUM of Blood Type
     */
    public BloodType intToBloodType(int bloodType)
    {
        switch (bloodType) {
            case 0:
                return BloodType.UNKNOWN;
            case 1:
                return BloodType.A_PLUS;
            case 2:
                return BloodType.A_MINUS;
            case 3:
                return BloodType.B_PLUS;
            case 4:
                return BloodType.B_MINUS;
            case 5:
                return BloodType.AB_PLUS;
            case 6:
                return BloodType.AB_MINUS;
            case 7:
                return BloodType.O_PLUS;
            case 8:
                return BloodType.O_MINUS;
            default:
                return null;
        }
    }

    /**
     * Main Menu for Patient (Appears Once Login Successful)
     * @return break
     */
    public void menu() {    
        Scanner sc = GlobalData.getInstance().sc;
        int choice;
        boolean inputError = false;

        while(true) {
            try {
                if (!inputError) {
                    System.out.println("\n-----Patient Menu-----");
                    System.out.println("1. View Medical Record ");
                    System.out.println("2. Update Personal Information ");
                    System.out.println("3. View Available Appointment Slots ");
                    System.out.println("4. Schedule an Appointment ");
                    System.out.println("5. Reschedule an Appointment ");
                    System.out.println("6. Cancel an Appointment ");
                    System.out.println("7. View Scheduled Appointments ");
                    System.out.println("8. View Past Appointment Outcome Records");
                    super.menu(9);
                    System.out.println("-----End of Menu-----");
                    System.out.print("Enter your choice: ");
                }
                else inputError = false;
                choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        viewMedicalRecord();
                        break;

                    case 2:
                        updatePersonalInformation();
                        break;

                    case 3:
                        int date3;
                        System.out.print("Enter Date to View Slots (in DDMMYYYY): ");
                        date3 = sc.nextInt();
                        viewAvailableAppointmentSlots(DoctorFileService.getAllDoctorData(), new Date(date3));
                        break;

                    case 4:
                        int time, date;
                        // sc.nextLine();
                        Doctor doc = null;
                        for (int i = 0; i < DoctorFileService.getAllDoctorData().size(); i++) {
                            System.out.println((i + 1) + ". Dr. " + DoctorFileService.getAllDoctorData().get(i).getName());
                        }
                        System.out.print("Select Doctor (0 to Exit): ");
                        int whichDoc = sc.nextInt();
                        while (whichDoc < 1 || whichDoc > DoctorFileService.getAllDoctorData().size()) {
                            if (whichDoc == 0) break;
                            System.out.print("Invalid choice! Try again: ");
                            whichDoc = sc.nextInt();
                        }
                        if (whichDoc == 0) break;
                        doc = DoctorFileService.getAllDoctorData().get(whichDoc - 1);
                        //doc.getDoctorScheduler().updateDoctorData();
                        System.out.print("Enter Date in DDMMYYYY (O to Exit): ");
                        date = sc.nextInt();
                        if (date == 0) break;
                        System.out.print("Enter Time in HHMM (O to Exit): ");
                        time = sc.nextInt();
                        if (time == 0) break;
                        Appointment toSchedule = patientSchedule.generateAppointment(this, doc, new Date(date), new Time(time));
                        if (toSchedule == null) {
                            System.out.println("Dr. " + doc.getName() + " " +
                                    "is Not Available at your chosen time!");
                            break;
                        }
                        scheduleAppointment(toSchedule);
                        // System.out.println("Appointment is scheduled and pending to be approved by the doctor!");
                        // sc.nextLine();
                        break;

                    case 5:
                        System.out.println("Select Appointment to Reschedule (0 to Exit):");
                        patientSchedule.printPatientAppointment();
                        choice = sc.nextInt();
                        Appointment old = patientSchedule.getUpcomingAppointment(choice - 1);
                        if (old == null) break;
                        /*
                         * Get new appointment
                         */
                        int newtime, newdate;
                        System.out.print("Enter Date in DDMMYYYY (O to Exit): ");
                        newdate = sc.nextInt();
                        if (newdate == 0) break;
                        System.out.print("Enter Time in HHMM (O to Exit): ");
                        newtime = sc.nextInt();
                        if (newtime == 0) break;
                        Appointment toReschedule = patientSchedule.generateAppointment(this, old.getDoctor(), new Date(newdate), new Time(newtime));
                        if (toReschedule == null) {
                            System.out.println(old.getDoctor().getName() + "is Not Available at your chosen time. Rescheduling failed.");
                            break;
                        }
                        rescheduleAppointment(old, toReschedule);
                        System.out.println("Appointment Successfully Rescheduled! Pending Doctor's Approval.");
                        break;

                    case 6:
                        System.out.println("Select Appointment to Cancel (0 to Exit):");
                        patientSchedule.printPatientAppointment();
                        choice = sc.nextInt();
                        Appointment cancel = patientSchedule.getUpcomingAppointment(choice - 1);
                        if (cancel == null) break;
                        cancelAppointment(cancel);
                        System.out.println("Appointment Successfully Cancelled!");
                        break;

                    case 7:
                        viewScheduledAppointments();
                        break;

                    case 8:
                        viewPastAppointmentOutcomeRecords();
                        break;

                    default:
                        if (!super.userOptions(choice - 8)) {
                            return;
                        }
                        else if (choice < 1 || choice > 11) inputError = true;
                        break;
                }
            }
            catch (InputMismatchException e) {
                sc.nextLine();
                System.out.print("Invalid choice! Returned to menu: ");
            }
        }
    }

    /**
     * Update Email
     *
     */
    @Deprecated
    public void updateEmail(String email) {
        this.email = email;
        mr.setEmail(email);
    }

    /**
     * Update Phone Number
     */
    public void updatePhone(int phone) {
        this.phone = phone;
        mr.setPhone(phone);
    }

    /**
     * Print Medical Record
     */
    public void viewMedicalRecord() {
        mr.print();
    }

    /**
     * Write to Medical Record
     * @param add Text to be Added
     */
    public void addMedicalRecord(String add) {
        mr.newMedicalHistory(add);
    }

    /**
     * Accessor of Medical History
     * @return Medical History
     */
    public ArrayList<String> getMedicalHistory(){
        return mr.getMedicalHistory();
    }

    /**
     * Update Personal Information
     */
    public Patient updatePersonalInformation() {
        System.out.println("Choose One to Update:");
        System.out.println("1. Update Email");
        System.out.println("2. Update Phone Number");

        int ch;
        Scanner scan = GlobalData.getInstance().sc;

        ch = scan.nextInt();

        if (ch == 1) {
            String email;
            scan.nextLine();
            System.out.print("Enter New Email: ");
            email = scan.nextLine();
            this.updateEmail(email);
        } else if (ch == 2) {
            int phone;
            System.out.print("Enter New Phone: ");
            phone = scan.nextInt();
            this.updatePhone(phone);
        } else {
            System.out.println("Invalid Input!");
        }

        return this;
    }

    /**
     * View Available Appointment Slots
     * @param doctors Global doctor user list
     * @param date    Date that patient want to make appointment
     */
    public void viewAvailableAppointmentSlots(ArrayList<Doctor> doctors, Date date) {
        for (Doctor doctor : doctors) {
            System.out.println("Dr. " + doctor.getName() + "'s Available Appointment Slots are: ");
            patientSchedule.printAvailableSlots(date, doctor);
            System.out.println("-----End of Dr. " + doctor.getName() + "'s Available slots-----");
        }
    }

    /**
     * Schedule Appointment
     *
     * @param appointment The appointment to be scheduled
     */
    public void scheduleAppointment(Appointment appointment) {
        patientSchedule.schedulePatientAppointment(appointment);
    }

    /**
     * Reschedule Appointment
     *
     * @param existingAppointment The existing appointment to be rescheduled
     * @param newAppointment      The new appointment with updated time slot
     */
    public void rescheduleAppointment(Appointment existingAppointment, Appointment newAppointment) {
        patientSchedule.reschedulePatientAppointment(existingAppointment, newAppointment);
    }

    /**
     * Cancel Appointment
     *
     * @param appointment     The appointment to be canceled
     */
    public void cancelAppointment(Appointment appointment) {
        patientSchedule.cancelPatientAppointment(appointment);
    }

    /**
     * View Status of Scheduled Appointments
     */
    public void viewScheduledAppointments() {
        patientSchedule.printPatientAppointment();
    }

    /**
     * View Past Appointment Outcome Records
     */
    public void viewPastAppointmentOutcomeRecords() {
        patientSchedule.printAppointmentOutcomeRecord();
    }

    /**
     * Print Role of Patient
     */
    public void printRole() {
        System.out.print("Patient");
    }

    @Override
    protected void changePassword() {
        super.changePassword();
        PatientFileService.updatePatient(this);
    }
}
