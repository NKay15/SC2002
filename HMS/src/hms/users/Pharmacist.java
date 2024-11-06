package hms.users;

import hms.GlobalData;
import hms.appointments.Appointment;
import hms.appointments.AppointmentScheduler;

import javax.swing.*;
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
		String patientID;
		String appointmentID;
		Appointment appointment;

		Scanner sc = new Scanner(System.in);
		int choice = 1;

		while (choice != 5) {
			patientFound = false;
			appointmentFound = false;
			alreadyTried = false;
			patientID = "0";
			appointmentID = "0";
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

			switch(choice) {
				case 1:
					// Search for Appointment by UUID; requiring Patient ID for security
					while (!patientFound) {
						if (alreadyTried) { System.out.println("Patient does not exist! Try again: "); }
						System.out.print("Enter Patient ID (0 to exit): ");
						patientID = sc.nextLine();
						if (patientID.equals("0")) {
							System.out.println("Operation cancelled. Returning to menu...\n");
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
							System.out.print("Enter Appointment ID (0 to exit): ");
							appointmentID = sc.nextLine();
							if (appointmentID.equals("0")) {
								System.out.println("Operation cancelled. Returning to menu...\n");
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
						System.out.println("Record found!");
						appointment.printAOP();
					}
					// So the menu doesn't pop back up before pharmacist is done reading
					System.out.print("\nEnter any number to return to menu: ");
					sc.nextInt(); sc.nextLine();
					System.out.println("Returning to menu...\n");
					break;

				case 2:
					while (!patientFound) {
						if (alreadyTried) { System.out.println("Patient does not exist! Try again: "); }
						System.out.print("Enter Patient ID (0 to exit): ");
						patientID = sc.nextLine();
						if (patientID.equals("0")) {
							System.out.println("Operation cancelled. Returning to menu...\n");
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
							System.out.print("Enter Appointment ID (0 to exit): ");
							appointmentID = sc.nextLine();
							if (appointmentID.equals("0")) {
								System.out.println("Operation cancelled. Returning to menu...\n");
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
						if (appointment.getAop() != null) {
							appointment.getAop().dispense();
							System.out.println("Successfully dispensed medication! Returning to menu... \n");
						}
						else {
							System.out.println("Operation cancelled. Returning to menu...\n");
						}
					}
					break;

				case 3:
					System.out.println("Here's the current inventory:");
					GlobalData.getInstance().inventory.printCurrentInventory();
					System.out.print("\nEnter any number to return to menu: ");
					sc.nextInt(); sc.nextLine();
					System.out.println("Returning to menu...\n");
					break;

				case 4:
					System.out.println("Current inventory:");
					GlobalData.getInstance().inventory.printCurrentInventory();
					int choice2;
					while (true) {
						System.out.println("\n");
						GlobalData.getInstance().inventory.createRequest();
						System.out.print("Would you like to submit more restock requests? " +
								"1. Yes; 2. No\nEnter your choice: ");
						choice2 = sc.nextInt();
						sc.nextLine();
						while (choice2 != 1 && choice2 != 2) {
							System.out.print("Invalid input! Try again: ");
							choice2 = sc.nextInt(); sc.nextLine();
						}
						if (choice2 == 2) {
							System.out.println("Returning to menu...\n");
							break;
						}
					}
					break;

				/*
				default:
					System.out.print("Confirm Log Out? Enter 1 to Log Out; " +
							"or Enter any other number to return to menu.\nYour choice: ");
					int confirmLogOut = sc.nextInt(); sc.nextLine();
					if (confirmLogOut == 1) {
						choice = 5;
						System.out.println("Logging out...");
					}
					else {
						choice = 1;
						System.out.println("Returning to menu...\n");
					}
				*/

				default:
                    if(!super.useroptions(choice-4)) {
                        System.out.println("Logging out");
                        return;
                    }
			}
		}
	}
}
