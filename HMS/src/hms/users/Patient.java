package hms.users;

import hms.medicalRecords.MedicalRecord;
import hms.utils.*;
import hms.appointments.*;
import hms.GlobalData;

import java.util.ArrayList;
import java.util.Scanner;

public class Patient extends User {

    /**
     * Date of birth
     */
    private Date dob;

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
     * appointmentScheduler for a specific patient
     */
    private PatientScheduleManager patientSchedule;

    /**
     * medical record
     */
    private MedicalRecord mr;

    /**
     * Constructor for patient
     */
    public Patient(String patientID, String name, int gender, Date dob, int phone, String email, int bloodType) {
        super(patientID, name, 1, gender);
        this.dob = dob;
        this.phone = phone;
        this.email = email;
        this.bloodType = bloodType;
        mr = new MedicalRecord(this);
        patientSchedule = new PatientScheduleManager(this);
    }

    /**
     * return Date of birth
     */
    public Date getDob() {
        return dob;
    }

    /**
     * return phone number
     */
    public int getPhone() {
        return phone;
    }

    /**
     * return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * return blood type of patient
     */
    public int getBloodType() {
        return bloodType;
    }

    /**
     * return schedule of patient
     */
    public PatientScheduleManager getPatientSchedule() {
        return patientSchedule;
    }

    /**
     * print patient menu
     * @return break
     */
    public void menu() {    
        System.out.println("-----Patient Menu-----");
        System.out.println("1.View Medical Record ");
        System.out.println("2.Update Personal Information ");
        System.out.println("3.View Available Appointment Slots ");
        System.out.println("4.Schedule an Appointment ");
        System.out.println("5.Reschedule an Appointment ");
        System.out.println("6.Cancel an Appointment ");
        System.out.println("7.View Scheduled Appointments ");
        System.out.println("8.View Past Appointment Outcome Records");
        super.menu(9);
        System.out.println("-----End of Menu-----");

        Scanner sc = new Scanner(System.in);
        int choice = 1;
        while(choice != 9) {
            choice = sc.nextInt();
            switch(choice) {
                case 1:
                    viewMedicalRecord();
                    break;

                case 2:
                    updatePersonalInformation();
                    break;

                case 3:
                    int date3;
                    System.out.print("Enter date to view slots (ddmmyyyy): ");
                    date3 = sc.nextInt();
                    viewAvailableAppointmentSlots(GlobalData.getInstance().userList.getDoctors(), new Date(date3));
                    break;

                case 4:
                    int time, date;
                    String docname;
                    Doctor doc = null;
                    System.out.print("Enter doctor name (! : exit): ");
                    docname = sc.nextLine();
                    for(Doctor doctor : GlobalData.getInstance().userList.getDoctors()) {
                        if(doctor.getName().equals(docname)) {
                            doc = doctor;
                            break;
                        }
                    }
                    if(doc == null) {
                        if(!docname.equals("!")) System.out.print("Doctor does not exists ! ");
                        break;
                    }
                    System.out.print("Enter date in ddmmyyyy (O : exit): ");
                    date = sc.nextInt();
                    if(date == 0) break;
                    System.out.print("Enter time in (O : exit): ");
                    time = sc.nextInt();
                    if(time == 0) break;
                    Appointment toSchedule = patientSchedule.generateAppointment(this, doc, new Date(date), new Time(time));
                    if(toSchedule == null) {
                        System.out.println(docname + "is not avaiable at your chosen time");
                        break;
                    }
                    scheduleAppointment(toSchedule);
                    System.out.println("Appointment is scheduled and pending to be approved by the doctor");
                    break;

                case 5:
                    System.out.println("Select appointment to reschedule (0 : exit):");
                    patientSchedule.printPatientAppointment();
                    choice = sc.nextInt();
                    Appointment old = patientSchedule.getUpcomingAppointment(choice-1);
                    if (old == null) break;
                    /*
                     * Get new appointment
                     */
                    int newtime, newdate;
                    System.out.print("Enter date in ddmmyyyy (O : exit): ");
                    newdate = sc.nextInt();
                    if(newdate == 0) break;
                    System.out.print("Enter time in (O : exit): ");
                    newtime = sc.nextInt();
                    if(newtime == 0) break;
                    Appointment toReschedule = patientSchedule.generateAppointment(this, old.getDoctor(), new Date(newdate), new Time(newtime));
                    if(toReschedule == null) {
                        System.out.println(old.getDoctor().getName() + "is not available at your choosen time. Rescheduling failed.");
                        break;
                    }
	    			rescheduleAppointment(old, toReschedule);
                    System.out.println("Appointment has successfully been rescheduled and pending to be approved by the doctor");
	    			break;

	    		case 6:
                    System.out.println("Select appointment to cancel (0 : exit):");
                    patientSchedule.printPatientAppointment();
                    choice = sc.nextInt();
                    Appointment cancel = patientSchedule.getUpcomingAppointment(choice-1);
                    if(cancel == null) break;
	    			cancelAppointment(cancel);
                    System.out.println("Appointment has been cancelled");
	    			break;

	    		case 7:
	    			viewScheduledAppointments();
	    			break;

	    		case 8:
	    			viewPastAppointmentOutcomeRecords();
	    			break;

	    		default:
                    if(!super.useroptions(choice-8)) {
                        System.out.println("Logging out");
                        return;
                    }
	    	}
        }
    }

    /**
     * update email
     *
     */
    public void updateEmail(String email) {
        this.email = email;
        mr.setEmail(email);
    }

    /**
     * update phone number
     */
    public void updatePhone(int phone) {
        this.phone = phone;
        mr.setPhone(phone);
    }

    public void viewMedicalRecord() {
        mr.print();
    }

    public void addMedicalRecord(String add) {
        mr.newMedicalHistory(add);
    }

    /**
     * Update Personal Information
     */
    public Patient updatePersonalInformation() {
        System.out.println("What information do you want to update?");
        System.out.println("1. Update Email");
        System.out.println("2. Update Phone Number");

        int ch;
        Scanner scan = new Scanner(System.in);
        ch = scan.nextInt();

        if (ch == 1) {
            String email;
            email = scan.nextLine();
            this.updateEmail(email);
        } else if (ch == 2) {
            int phone;
            phone = scan.nextInt();
            this.updatePhone(phone);
        } else {
            System.out.println("Invalid Input!");
        }

        scan.close();
        return this;
    }

    /**
     * View Available Appointment Slots
     * @param doctors Global doctor user list
     * @param date    Date that patient want to make appointment
     */
    public void viewAvailableAppointmentSlots(ArrayList<Doctor> doctors, Date date) {
        for (Doctor doctor : doctors) {
            System.out.println("Dr. " + doctor.getName() + "'s available appointment slots are: ");
            patientSchedule.printAvailableSlots(date, doctor);
            System.out.println("-----End of Dr. " + doctor.getName() + "'s Available slots-----");
        }
    }

    /**
     * Schedule appointment
     *
     * @param appointment The appointment to be scheduled
     */
    public void scheduleAppointment(Appointment appointment) {
        patientSchedule.schedulePatientAppointment(appointment);
    }

    /**
     * Reschedule appointment
     *
     * @param existingAppointment The existing appointment to be rescheduled
     * @param newAppointment      The new appointment with updated time slot
     */
    public void rescheduleAppointment(Appointment existingAppointment, Appointment newAppointment) {
        patientSchedule.reschedulePatientAppointment(existingAppointment, newAppointment);
    }

    /**
     * Cancle appointment
     *
     * @param appointment     The appointment to be canceled
     */
    public void cancelAppointment(Appointment appointment) {
        patientSchedule.cancelPatientAppointment(appointment);
    }

    /**
     * View status of scheduled appointments
     */
    public void viewScheduledAppointments() {
        patientSchedule.printPatientAppointment();
    }

    /**
     * View past appointment outcome records
     */
    public void viewPastAppointmentOutcomeRecords() {
        patientSchedule.printAppointmentOutcomeRecord();
    }
}
