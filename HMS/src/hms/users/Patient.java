package hms.users;

import hms.medicalRecords.MedicalRecord;
import hms.utils.Date;
import hms.appointments.*;

import java.util.Scanner;

public class Patient extends User{
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
	 * appointmentScheduler for a specific patient
	 * */
	private PatientScheduleManager patientSchedule;
	
	/**
	 * medical record
	 * */
	private MedicalRecord mr;
	
	/**
	 * Constructor for patient
	 * */
	public Patient(String patientID, String name, Date dob, int gender, int phone, String email, int bloodType) {
		this.patientID = patientID;
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		this.phone = phone;
		this.email = email;
		this.bloodType = bloodType;
		mr = new MedicalRecord(this);
		patientSchedule = new PatientScheduleManager(this);
	}
	
	/**
	 * return patientID
	 * */
	public String getPatientID() {
		return patientID;
	}
	
	/**
	 * return name of patient
	 * */
	public String getName() {
		return name;
	}
	
	/**
	 * return Date of birth
	 * */
	public Date getDob() {
		return dob;
	}
	
	/**
	 * return gender of patient
	 * */
	public int getGender() {
		return gender;
	}
	
	/**
	 * return phone number
	 * */
	public int getPhone() {
		return phone;
	}
	
	/**
	 * return email
	 * */
	public String getEmail() {
		return email;
	}
	
	/**
	 * return blood type of patient
	 * */
	public int getBloodType() {
		return bloodType;
	}
	
	/**
	 * print patient menu
	 * */
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
		System.out.println("9.Logout ");
		System.out.println("-----End of Menu-----");
	}
	
	/**
	 * update email
	 * @param new email
	 * */
	public void updateEmail(String email) {
		this.email = email;
		mr.setEmail(email);
	}
	
	/**
	 * update phone number
	 * */
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
     * */
    public void updatePersonalInformation() {
    	System.out.println("What information do you want to update?");
    	System.out.println("1. Update Email");
    	System.out.println("2. Update Phone Number");
    	
    	int ch;
    	Scanner scan = new Scanner(System.in);
    	ch = scan.nextInt();
    	
    	if(ch == 1) {
    		String email;
    		email = scan.nextLine();
    		this.updateEmail(email);
    	}else if (ch == 2) {
    		int phone;
    		phone = scan.nextInt();
    		this.updatePhone(phone);
    	}else {
    		System.out.println("Invalid Input!");
    	}
    	
    	scan.close();
    }
    
    /**
     * View Available Appointment Slots
     * @param APPS Global AppointmentScheduler
     * */
    public void viewAvailableAppointmentSlots(AppointmentScheduler APPS) {
    	APPS.printAvailableSlot();
    }
    
    /**
     * Schedule appointment
     * @param APPS Global AppointmentScheduler
     * @param appointment The appointment to be scheduled
     * */
    public void scheduleAppointment(AppointmentScheduler APPS, Appointment appointment) {
    	if(APPS.scheduleAppointment(appointment) != null) {
    		patientSchedule.schedulePatientAppointment(appointment);
    	}
    }
    
    /**
     * Reschedule appointment
     * @param APPS Global AppointmentScheduler
     * @param existingAppointment The existing appointment to be rescheduled
     * @param newAppointment The new appointment with updated time slot
     * */
    public void rescheduleAppointment(AppointmentScheduler APPS, Appointment existingAppointment, Appointment newAppointment) {
    	if(APPS.rescheduleAppointment(existingAppointment, newAppointment) == true) {
    		patientSchedule.reschedulePatientAppointment(existingAppointment, newAppointment);
    	}
    }
    
    /**
     * Cancle appointment
     * @param APPS Global AppointmentScheduler
     * @param appointment The appointment to be canceled
     * */
    public void cancelAppointment(AppointmentScheduler APPS, Appointment appointment) {
    	if(APPS.cancelAppointment(appointment) != null) {
    		patientSchedule.cancelPatientAppointment(appointment);
    	}
    }
    
    /**
     * View status of scheduled appointments
     * */
    public void viewScheduledAppointments() {
    	patientSchedule.printPatientAppointment();
    }
    
    /**
     * View past appointment outcome records
     * */
    public void viewPastAppointmentOutcomeRecords() {
    	patientSchedule.printAppointmentOutcomeRecord();
    }
}
