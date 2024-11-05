package hms.users;

import hms.appointments.*;
import hms.utils.*;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Doctor extends Staff {

	private DoctorSchedules doctorSchedules;

	/**
	 * doctor scheduler
	 */
    private DoctorScheduleManager doctorScheduler;

	/**
	 * patient list of doctor
	 */
    private List<Patient> patientList;
    
    public Doctor(String doctorID, String name, int gender, int age) {
    	super(doctorID, name, 2, gender, age);
		doctorSchedules = new DoctorSchedules(this);
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
    	super.menu(8);
    	System.out.println("-----End of Menu-----");

		Scanner sc = new Scanner(System.in);
		int choice = 1;

		while (choice != 8) {
			choice = sc.nextInt();
			switch(choice) {
				case 1 : 
					viewPatientMedicalRecords();
					break;

				case 2 :
					System.out.println("Select patient (0 : exit): ");
					for(int i = 0; i < patientList.size(); i++) {
						System.out.println((i+1) + " : " + patientList.get(i).getName());
					}
					choice = sc.nextInt();
					if(choice < 1 || choice > patientList.size()) {
						choice = 2;
						break;
					}
					updatePatientMedicalRecords(patientList.get(choice-1));
					choice = 2;
					break;

				case 3 :
					viewPersonalSchedule();
					break;

				case 4 :
					setAvailabilityforAppointments();
					break;

				case 5 :
					acceptOrDeclineAppointmentRequests();
					break;

				case 6 :
					viewUpcomingAppointments();
					break;

				case 7 :
					System.out.println("Select Appointment (0 : exit) :");
					viewUpcomingAppointments();
					choice = sc.nextInt();
					Appointment choiceAppointment = doctorScheduler.getUpcomingAppointment(choice-1);
					if(choiceAppointment == null) {
						choice = 7;
						break;
					}
					recordAppointmentOutcome(choiceAppointment);
					choice = 7;
					break;

				default :
					if(!super.useroptions(choice-7)) {
						System.out.println("Logging out");
						return;
					}
			}
		}
    }
    
    public void viewPatientMedicalRecords() {
    	System.out.println("Hi, Doc. " + this.getName() + ". Here is your patient list:");
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
	 */
    public void setAvailabilityforAppointments() {
		Scanner scanner = new Scanner(System.in);
		Date date =  new Date(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
    	doctorSchedules.setDoctorSchedule(date);
    }

	private boolean isInPatientList(Patient keyPatient){
		for(Patient patient : patientList){
			if(Objects.equals(patient.getID(), keyPatient.getID()))return true;
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
	 */
    public void acceptOrDeclineAppointmentRequests() {
		List<Appointment> appointmentList = doctorScheduler.returnPendingList();
		int id = 0;
		Scanner scan = new Scanner(System.in);
		for(Appointment appointment : appointmentList){
			Patient patient = appointment.getPatient();
			Date date = appointment.getDate();
			Time Time = appointment.getTimeSlot();

			++id;
			System.out.println("-----Request " + id + "-----");
			System.out.println("patient:" + patient.getName());
			date.print();
			System.out.println("Time: " + Time.getHour() + ":" + Time.getMinute());
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