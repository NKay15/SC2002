package hms.users;

import hms.GlobalData;
import hms.appointments.Appointment;
import hms.appointments.AppointmentScheduler;
import hms.medicalRecords.AppointmentOutcomeRecord;

import java.util.Scanner;
import java.util.UUID;

public class Pharmacist extends Staff {

	/**
	 * Constructor
	 */
	public Pharmacist(String ID, String name, int gender, int age) {
		super(ID, name, 3, gender, age);
	}

	public void menu() {
		boolean patientFound;
		boolean appointmentFound;
		boolean alreadyTried;
		String patientID = "";
		String appointmentID = "";
		Appointment appointment;

		Scanner sc = new Scanner(System.in);
		int choice = 1;
		while (true) {
			if (choice >= 1 && choice <= 6) {
				System.out.println("-----Pharmacist Menu-----");
				System.out.println("1. Search for Appointment Outcome Record by Appointment ID");
				System.out.println("2. Update Prescription Status");
				System.out.println("3. View Medication Inventory");
				System.out.println("4. Submit Replenishment Requests");
				super.menu(5);
				System.out.println("-----End of Menu-----");
				System.out.print("Enter your choice: ");
			}

			patientFound = false;
			appointmentFound = false;
			alreadyTried = false;
			appointment = null;

			System.out.println("-----Pharmacist Menu-----");
			System.out.println("1.Search for Appointment Outcome Record by Appointment ID");
			System.out.println("2.Update Prescription Status");
			System.out.println("3.View Medication Inventory");
			System.out.println("4.Submit Replenishment Requests");
			super.menu(5);
			System.out.println("-----End of Menu-----");
			System.out.print("Enter your choice: ");

			choice = sc.nextInt(); sc.nextLine();
			System.out.println();

			switch(choice) {
				case 1:
					// Search for Appointment by UUID; requiring Patient ID for security
					while (!patientFound) {
						if (alreadyTried) { System.out.println("Patient Does Not Exist! Try again: "); }
						System.out.print("Enter Patient ID (0 to Cancel): ");
						patientID = sc.nextLine();
						if (patientID.equals("0")) {
							System.out.println("Operation Cancelled. Returning to Menu...\n");
							break;
						}
						for (Patient temPatient : GlobalData.getInstance().userList.getPatients()){
							if(temPatient.getID().equals(patientID)){
								patientFound = true;
							}
						}
						alreadyTried = true;
					}

					// Patient found; Search for Appointment
					if (patientFound){
						alreadyTried = false;
						while (!appointmentFound) {
							if (alreadyTried) { System.out.println("Invalid Appointment ID! Try again: "); }
							System.out.print("Enter Appointment ID (0 to Cancel): ");
							appointmentID = sc.nextLine();
							if (appointmentID.equals("0")) {
								System.out.println("Operation Cancelled. Returning to Menu...\n");
								break;
							}
							if (AppointmentScheduler.getInstance().findAppointment(UUID.fromString(appointmentID),
									AppointmentScheduler.getInstance().getAppointments()) != null){
								appointment = AppointmentScheduler.getInstance().findAppointment(UUID.fromString(appointmentID),
										AppointmentScheduler.getInstance().getAppointments());
								appointmentFound = true;
							}
							alreadyTried = true;
						}
					}

					// Print Record
					if (appointmentFound){
						System.out.println("Record Found!\n");
						appointment.printAOP();
						System.out.print("\nEnter any number to Return to Menu: ");
						sc.nextInt(); sc.nextLine();
						System.out.println("Returning to Menu...\n");
					}
					break;

				case 2:
					while (!patientFound) {
						if (alreadyTried) { System.out.println("Patient Does Not Exist! Try again: "); }
						System.out.print("Enter Patient ID (0 to Cancel): ");
						patientID = sc.nextLine();
						if (patientID.equals("0")) {
							System.out.println("Operation Cancelled. Returning to Menu...\n");
							break;
						}
						for (Patient temPatient : GlobalData.getInstance().userList.getPatients()){
							if(temPatient.getID().equals(patientID)){
								patientFound = true;
							}
						}
						alreadyTried = true;
					}

					if (patientFound){
						alreadyTried = false;
						while (!appointmentFound) {
							if (alreadyTried) { System.out.println("Invalid Appointment ID! Try again: "); }
							System.out.print("Enter Appointment ID (0 to Cancel): ");
							appointmentID = sc.nextLine();
							if (appointmentID.equals("0")) {
								System.out.println("Operation Cancelled. Returning to Menu...\n");
								break;
							}
							if (AppointmentScheduler.getInstance().findAppointment(UUID.fromString(appointmentID),
									AppointmentScheduler.getInstance().getAppointments()) != null){
								appointment = AppointmentScheduler.getInstance().findAppointment(UUID.fromString(appointmentID),
										AppointmentScheduler.getInstance().getAppointments());
								appointmentFound = true;
							}
							alreadyTried = true;
						}
					}

					// Dispense Medication
					if (appointmentFound){
						AppointmentOutcomeRecord aop = appointment.getAop();
						if (aop != null) {
							if (!aop.isDispensed()) {
								System.out.println("Please ensure that all fields below are correct before confirming:");
								System.out.println("Patient ID: " + patientID);
								System.out.println("Appointment ID: " + appointmentID);
								System.out.println("\nConfirm to Dispense Medication?");
								System.out.println("Enter 1 to Confirm; or 2 to Cancel.");
								System.out.print("Enter your choice: ");
								int confirmDispense;

								while (true) {
									confirmDispense = sc.nextInt(); sc.nextLine();
									switch (confirmDispense) {
										case 1:
											if (GlobalData.getInstance().inventory.dispense(aop)) {
												System.out.println("Medication Successfully Dispensed! Returning to Menu... \n");
											}
											else { System.out.println("Insufficient Medication. Please Restock! Returning to Menu...\n"); }
											break;

										case 2:
											System.out.println("Operation Cancelled.");
											break;

										default:
											System.out.print("Invalid choice! Try again: ");
											continue;
									}
									break;
								}
							}
							else {
								System.out.println("Medication Already Dispensed! Returning to Menu...\n");
								}
						}
						else {
							System.out.println("Appointment has not been completed! Returning to Menu...\n");
						}
					}
					break;

				case 3:
					System.out.println("Current Inventory:");
					GlobalData.getInstance().inventory.printCurrentInventory();
					System.out.print("\nEnter any number to Return to Menu: ");
					sc.nextInt(); sc.nextLine();
					System.out.println("Returning to Menu...\n");
					break;

				case 4:
					System.out.println("Current Inventory:");
					GlobalData.getInstance().inventory.printCurrentInventory();
					int submitMore;
					while (true) {
						System.out.println("\n");
						GlobalData.getInstance().inventory.createRequestMenu(sc);
						System.out.print("Would you like to Submit More Restock Requests? " +
								"1. Yes; 2. No\nEnter your choice: ");
						submitMore = sc.nextInt(); sc.nextLine();
						while (submitMore != 1 && submitMore != 2) {
							System.out.print("Invalid choice! Try again: ");
							submitMore = sc.nextInt(); sc.nextLine();
						}
						if (submitMore == 2) {
							System.out.println("Returning to Menu...\n");
							break;
						}
					}
					break;
					
				default:
					if(!super.useroptions(choice-4, sc)) {
						if (choice == 6) {
							System.out.print("Confirm Log Out? Enter 1 to Log Out; " +
									"or Enter any other number to Return to Menu.\nEnter your choice: ");
							int confirmLogOut = sc.nextInt();
							sc.nextLine();
							if (confirmLogOut == 1) {
								System.out.println("Logging out...\n");
								return;
							}
							else {
								System.out.println("Returning to Menu...\n");
								break;
							}
						}
						else {
							System.out.print("Invalid choice! Try again: ");
							break;
						}
					}
					break;
			}
		}
	}
}
