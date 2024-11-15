package hms.users;

import hms.GlobalData;
import hms.appointments.Appointment;
import hms.medicalRecords.AppointmentOutcomeRecord;
import hms.pharmacy.Medicine;
import hms.services.PatientFileService;
import hms.utils.Password;
import hms.utils.Role;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Pharmacist extends Staff {

	/**
	 * Constructor
	 */
	public Pharmacist(String ID, String name, int gender, int age, Password password) {
		super(ID, name, Role.PHARMACIST, gender, age, password);
	}

	public void menu() {
		boolean patientFound;
		boolean alreadyTried;
		String patientID = "";
		Patient patient;
		Appointment appointment;

		Scanner sc = GlobalData.getInstance().sc;
		int choice;
		boolean inputError = false;
		while (true) {
			patientFound = false;
			alreadyTried = false;
			patient = null;

			try {
				if (!inputError) {
					System.out.println("\n-----Pharmacist Menu-----");
					System.out.println("1. Search for Appointment Outcome Record by Patient ID");
					System.out.println("2. Dispense Medication & Update Prescription Status");
					System.out.println("3. View Medication Inventory");
					System.out.println("4. Submit Replenishment Requests");
					super.menu(5);
					System.out.println("-----End of Menu-----");
					System.out.print("Enter your choice: ");
				}
				else inputError = false;
				choice = sc.nextInt();
				switch (choice) {
					case 1:
						sc.nextLine();
						System.out.print("Enter Patient ID (0 to Cancel): ");
						while (!patientFound) {
							if (alreadyTried) System.out.print("Patient Does Not Exist! Try again: ");
							patientID = sc.nextLine();
							if (patientID.equals("0")) {
								System.out.println("Operation Cancelled. Returning to Menu...");
								break;
							}
							for (Patient temPatient : PatientFileService.getAllPatientData()) {
								if (temPatient.getID().equals(patientID)) {
									patient = temPatient;
									patientFound = true;
								}
							}
							alreadyTried = true;
						}

						if (patient != null) {
							if (patient.getPatientSchedule().getAppointments().isEmpty()) {
								System.out.println("Patient Has No Completed Appointments! Returning to Menu...");
								break;
							} else {
								System.out.println("Completed Appointments for Patient " + patient.getID() + ":");
								int k = 1;
								for (int i = 0; i < patient.getPatientSchedule().getAppointments().size(); i++) {
									appointment = patient.getPatientSchedule().getAppointments().get(i);
									if (appointment.getStatus() == 4) {
										int time = appointment.getTimeSlot().getIntTime();
										String slotTime = String.format("%02d:%02d", time / 100, time % 100);
										System.out.println((k) + ". " + appointment.getDate().get()
												+ " " + slotTime + ": " + appointment.getAop().getService());
										k++;
									}
								}
								System.out.print("Select Appointment for which to View Appointment Outcome Record: ");
								int appointmentChoice = sc.nextInt(); sc.nextLine();
								while (appointmentChoice < 1 || appointmentChoice >= k) {
									System.out.print("Invalid choice! Try again: ");
									appointmentChoice = sc.nextInt(); sc.nextLine();
								}
								appointment = patient.getPatientSchedule().getAppointments().get(appointmentChoice - 1);
								AppointmentOutcomeRecord aop = appointment.getAop();
								System.out.println("Appointment ID: " + appointment.getUuid());
								System.out.println("Patient ID: " + patient.getID());
								System.out.println("---------------------------");
								aop.print();
								System.out.print("Enter anything to Return to Menu: ");
								sc.nextLine();
								System.out.println("Returning to Menu...");
							}
						}
						break;

					case 2:
						sc.nextLine();
						System.out.print("Enter Patient ID (0 to Cancel): ");
						while (!patientFound) {
							if (alreadyTried) System.out.print("Patient Does Not Exist! Try again: ");
							patientID = sc.nextLine();
							if (patientID.equals("0")) {
								System.out.println("Operation Cancelled. Returning to Menu...");
								break;
							}
							for (Patient temPatient : PatientFileService.getAllPatientData()) {
								if (temPatient.getID().equals(patientID)) {
									patient = temPatient;
									patientFound = true;
								}
							}
							alreadyTried = true;
						}

						if (patient != null) {
							if (patient.getPatientSchedule().getAppointments().isEmpty()) {
								System.out.println("Patient Has No Completed Appointments! Returning to Menu...");
								break;
							} else {
								boolean hasPendingMedication = false;
								for (int i = 0; i < patient.getPatientSchedule().getAppointments().size(); i++) {
									appointment = patient.getPatientSchedule().getAppointments().get(i);
									if (appointment.getStatus() == 4) {
										if (!appointment.getAop().isDispensed()) hasPendingMedication = true;
									}
								}
								if (!hasPendingMedication) {
									System.out.println("Patient Has No Completed Appointments Awaiting Dispensation of Medication!\nReturning to Menu...");
									break;
								}
								System.out.println("Completed Appointments Requiring Medication for Patient " + patient.getID() + ":");
								int k = 1;
								for (int i = 0; i < patient.getPatientSchedule().getAppointments().size(); i++) {
									appointment = patient.getPatientSchedule().getAppointments().get(i);
									if (appointment.getStatus() == 4) {
										if (!appointment.getAop().isDispensed()) {
											if (appointment.getAop().getPrescription().length != 0) {
												int time = appointment.getTimeSlot().getIntTime();
												String slotTime = String.format("%02d:%02d", time / 100, time % 100);
												System.out.println((k) + ". " + appointment.getDate().get()
														+ " " + slotTime + ": " + appointment.getAop().getService());
												k++;
											}
										}
									}
								}
								System.out.print("Select Appointment for which to Dispense Medication: ");
								int appointmentChoice = sc.nextInt(); sc.nextLine();
								while (appointmentChoice < 1 || appointmentChoice >= k) {
									System.out.print("Invalid choice! Try again: ");
									appointmentChoice = sc.nextInt(); sc.nextLine();
								}
								appointment = patient.getPatientSchedule().getAppointments().get(appointmentChoice - 1);
								AppointmentOutcomeRecord aop = appointment.getAop();
								Medicine[] prescription = aop.getPrescription();
								System.out.println("\nPlease ensure that all fields below are correct before confirming:");
								System.out.println("Appointment ID: " + appointment.getUuid());
								System.out.println("Patient ID: " + patientID);
								System.out.println("Medication for Dispensation: ");
								for (int i = 0; i < prescription.length; i++) {
									System.out.println((i + 1) + ". " + prescription[i].name() + " " + "(Quantity: " + prescription[i].amount() + ")");
								}
								System.out.println("\nConfirm to Dispense Medication?");
								System.out.println("Enter 1 to Confirm; or 2 to Cancel.");
								System.out.print("Enter your choice: ");
								String confirmDispense;

								while (true) {
									confirmDispense = sc.next(); sc.nextLine();
									switch (confirmDispense) {
										case "1":
											if (GlobalData.getInstance().inventory.dispense(aop)) {
												System.out.println("Medication Successfully Dispensed! " +
														"Prescription Status Updated. Returning to Menu...");
											} else {
												System.out.println("Insufficient Medication. Please Restock! Returning to Menu...");
											}
											break;

										case "2":
											System.out.println("Operation Cancelled. Returning to Menu...");
											break;

										default:
											System.out.print("Invalid choice! Try again: ");
											continue;
									}
									break;
								}
							}
						}
						break;

					case 3:
						sc.nextLine();
						System.out.println("Current Inventory:");
						GlobalData.getInstance().inventory.printCurrentInventory();
						System.out.print("Enter anything to Return to Menu: ");
						sc.nextLine();
						System.out.println("Returning to Menu...");
						break;

					case 4:
						int submitMore;
						while (true) {
							GlobalData.getInstance().inventory.createRequest();
							System.out.print("\nWould you like to Submit More Restock Requests? " +
									"1. Yes; 2. No\nEnter your choice: ");
							submitMore = sc.nextInt(); sc.nextLine();
							while (submitMore != 1 && submitMore != 2) {
								System.out.print("Invalid choice! Try again: ");
								submitMore = sc.nextInt(); sc.nextLine();
							}
							if (submitMore == 2) {
								System.out.println("Returning to Menu...");
								break;
							}
						}
						break;

					default:
						if (!super.userOptions(choice - 4)) {
							return;
						}
						break;
				}
			}
			catch (InputMismatchException e) {
				System.out.print("Invalid choice! Try again: ");
				inputError = true;
				sc.nextLine();
			}
		}
	}

	public void printRole() {
        System.out.print("Pharmacist");
    }
}
