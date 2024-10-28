package hms.users;

import hms.appointments.DoctorScheduleManager;
import hms.appointments.*;

import java.util.List;
import java.util.Scanner;

public class Doctor extends User {
    private String doctorID;
    private String name;
    private int gender;
    private int age;
    private DoctorScheduleManager doctorScheduler;
    private List<Patient> patientList;
    
    public Doctor(String doctorID, String name, int gender, int age) {
    	this.doctorID = doctorID;
    	this.name = name;
    	this.gender = gender;
    	this.age = age;
    	doctorScheduler = new DoctorScheduleManager(this);
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
    
    public void setAvailabilityforAppointments(Date date) {
    	doctorScheduler.addOneSlot(Date);
    }
    
    public void acceptOrDeclineAppointmentRequests(AppointmentScheduler APPS) {
    	
    }
}