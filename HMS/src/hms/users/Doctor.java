package hms.users;

import hms.appointments.DoctorScheduleManager;
import hms.appointments.*;
import hms.utils.*;

import java.util.List;
import java.util.Scanner;

public class Doctor extends User {
	/**
	 * Doctor's ID
	 */
    private String doctorID;

	/**
	 * name of doctor
	 */
    private String name;
	
	/**
	 * gender of doctor
	 */
    private int gender;

	/**
	 * age of doctor
	 */
    private int age;
	private DoctorSchedules doctorSchedules;

	/**
	 * doctor scheduler
	 */
    private DoctorScheduleManager doctorScheduler;

	/**
	 * patient list of doctor
	 */
    private List<Patient> patientList;
    
    public Doctor(String doctorID, String name,	int role, int gender, int age) {
    	super(doctorID, name, role, gender);
    	this.age = age;
		doctorSchedules = new DoctorSchedules(this);
    }
    
    public String getDoctorID() {
        return doctorID;
    }
   
    public String getName() {
    	return name;
    }
    
    public int getGender() {
    	return gender;
    }
    
    public int getAge() {
    	return age;
    }

	public DoctorSchedules getDoctorSchedules() {
		return doctorSchedules;
	}

	public void menu() {
    	System.out.println("-----Doctor Menu-----");
    	System.out.println("1.View Patient Medical Records ");
    	System.out.println("2.Update Patient Medical Records");
    	System.out.println("3.View Personal Schedule ");
    	System.out.println("4.Set Availability for Appointments");
    	System.out.println("5.Accept or Decline Appointment Requests");
    	System.out.println("6.View Upcoming Appointments");
    	System.out.println("7.Record Appointment Outcome ");
    	System.out.println("8.Logout ");
    	System.out.println("-----End of Menu-----");
    }
    
    public void viewPatientMedicalRecords() {
    	System.out.println("Hi, Doc. " + this.name + ". Here is your patient list:");
    	int id = 0;
    	for(Patient patient : patientList) {
    		++id;
    		System.out.println(id + ". " + patient.getName());
    	}
    	System.out.println("Choose one you want to view.");
    	Scanner scan = new Scanner(System.in);
    	int ch = scan.nextInt();
    	patientList.get(ch-1).viewMedicalRecord();
    	scan.close();
    }
    
	/**
	 * Update Patient Medical Records
	 * @param patient Patient that the doctor want to add
	 */
    public void updatePatientMedicalRecords(Patient patient) {
    	Scanner scan = new Scanner(System.in);
    	
    	System.out.println("Add a new medical record:");
    	String message = scan.nextLine();
    	patient.addMedicalRecord(message);
    	scan.close();
    }
    
    public void viewPersonalSchedule() {
    	doctorScheduler.printUpcomingSlots(this);
    }
    
	/**
	 * Set Availability for Appointments
	 * @param date Date that doctor want to set
	 */
    public void setAvailabilityforAppointments(Date date) {
    	doctorSchedules.setDoctorSchedule();
    }

	private boolean isInPatientList(Patient keyPatient){
		for(Patient patient : patientList){
			if(patient.getID() == keyPatient.getID())return true;
		}
		return false;
	}

	private void updatePatientList(Patient patient, int op){
		if( ( op == 1 ) && ( isInPatientList(patient) == false ) ){
			patientList.add(patient);
		}else if( ( op == -1 ) && ( isInPatientList(patient) == true ) ) {
			patientList.remove(patient);
		}
	}
    
	/**
	 * Accept Or Decline Appointment Requests
	 * @param APPS Global Scheduler
	 */
    public void acceptOrDeclineAppointmentRequests(AppointmentScheduler APPS) {
		List<Appointment> appointmentList = APPS.getPendingAppointments(this);
		int id = 0;
		Scanner scan = new Scanner(System.in);
		for(Appointment appointment : appointmentList){
			Patient patient = appointment.getPatient();
			Date date = appointment.getDate();
			int Time = appointment.getTimeSlot();

			++id;
			System.out.println("-----Request " + id + "-----");
			System.out.println("patient:" + patient.getName());
			date.print();
			System.out.println("Time: " + Time);
			System.out.println("-----End of Request " + id + "-----");
			System.out.println("1. Accept");
			System.out.println("2. Decline");
			System.out.println("3. Later");
			int ch = scan.nextInt();
			if(ch == 1){
				doctorScheduler.acceptAppointments(appointment);
				updatePatientList(patient, 1);
				System.out.println("Successfully Accepted!");
			}else if(ch == 2){
				doctorScheduler.declineAppointments(appointment);
				System.out.println("Successfully Declined!");
			}else{
				System.out.println("This request is still in your pending list. Please don't forget to reply later");
			}
		}
		scan.close();
    }

	public void viewUpcomingAppointments(){
		doctorScheduler.printUpcomingSlots(this);
	}

	public void recordAppointmentOutcome(Appointment appointment){
		doctorScheduler.updateAppointmentOutcomeRecord(appointment);
	}

	public void viewAvailability(Date date){
		doctorSchedules.printAvailableSlot(date);
	}
}