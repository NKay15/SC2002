package hms.users;

import hms.GlobalData;
import hms.Medicine;
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
		boolean alreadyTried;
		String patientID = "";
		Patient patient;
		Appointment appointment;

		System.out.println("-----Pharmacist Menu-----");
		System.out.println("1. Search for Appointment Outcome Record by Patient ID");
		System.out.println("2. Update Prescription Status");
		System.out.println("3. View Medication Inventory");
		System.out.println("4. Submit Replenishment Requests");
		super.menu(5);
		System.out.println("-----End of Menu-----");
		System.out.print("Enter your choice: ");
		Scanner sc = GlobalData.getInstance().sc;
		int choice = 0;

		while (true) {
			patientFound = false;
			alreadyTried = false;
			patient = null;

			if (choice >= 1 && choice <= 6) {
				System.out.println("-----Pharmacist Menu-----");
				System.out.println("1. Search for Appointment Outcome Record by Patient ID");
				System.out.println("2. Update Prescription Status");
				System.out.println("3. View Medication Inventory");
				System.out.println("4. Submit Replenishment Requests");
				super.menu(5);
				System.out.println("-----End of Menu-----");
				System.out.print("Enter your choice: ");
			}
			choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
				case 1:
					System.out.print("Enter Patient ID (0 to Cancel): ");
					while (!patientFound) {
						if (alreadyTried) System.out.print("Patient Does Not Exist! Try again: ");
						patientID = sc.nextLine();
						if (patientID.equals("0")) {
							System.out.println("Operation Cancelled. Returning to Menu...\n");
							break;
						}
						for (Patient temPatient : GlobalData.getInstance().userList.getPatients()) {
							if (temPatient.getID().equals(patientID)) {
								patient = temPatient;
								patientFound = true;
							}
						}
						alreadyTried = true;
					}

					if (patient != null) {
						if (patient.getPatientSchedule().getAppointments().isEmpty()){
							System.out.println("Patient Has No Completed Appointments! Returning to Menu...\n");
							break;
						}
						else {
							System.out.println("Completed Appointments for Patient " + patient.getID() + ":");
							for (int i = 0, k = 1; i < patient.getPatientSchedule().getAppointments().size(); i++) {
								appointment = patient.getPatientSchedule().getAppointments().get(i);
								if (appointment.getStatus() == 4) {
									int time = appointment.getTimeSlot().getIntTime();
									String slotTime = String.format("%02d:%02d", time / 100, time % 100);
									System.out.println((k) + ". " + appointment.getDate().get()
											+ " " + slotTime + ": " + appointment.getAop().getService());
									k++;
								}
							}
							System.out.print("Enter your choice: ");
							int appointmentChoice = sc.nextInt(); sc.nextLine();
							while (appointmentChoice > patient.getPatientSchedule().getAppointments().size()){
								System.out.print("Invalid choice! Try again: ");
								appointmentChoice = sc.nextInt(); sc.nextLine();
							}
							appointment = patient.getPatientSchedule().getAppointments().get(appointmentChoice-1);
							AppointmentOutcomeRecord aop = appointment.getAop();
							aop.print();
							System.out.print("Enter anything to Return to Menu: ");
							sc.nextLine();
							System.out.println("Returning to Menu...\n");
						}
					}
					break;

				case 2:
					System.out.print("Enter Patient ID (0 to Cancel): ");
					while (!patientFound) {
						if (alreadyTried) System.out.print("Patient Does Not Exist! Try again: ");
						patientID = sc.nextLine();
						if (patientID.equals("0")) {
							System.out.println("Operation Cancelled. Returning to Menu...\n");
							break;
						}
						for (Patient temPatient : GlobalData.getInstance().userList.getPatients()) {
							if (temPatient.getID().equals(patientID)) {
								patient = temPatient;
								patientFound = true;
							}
						}
						alreadyTried = true;
					}

					if (patient != null) {
						if (patient.getPatientSchedule().getAppointments().isEmpty()){
							System.out.println("Patient Has No Completed Appointments! Returning to Menu...\n");
							break;
						}
						else {
							System.out.println("Completed Appointments Requiring Medication for Patient " + patient.getID() + ":");
							for (int i = 0, k = 1; i < patient.getPatientSchedule().getAppointments().size(); i++) {
								appointment = patient.getPatientSchedule().getAppointments().get(i);
								if (appointment.getStatus() == 4) {
									if (!appointment.getAop().isDispensed()) {
										if (appointment.getAop().getprescription().length != 0) {
											int time = appointment.getTimeSlot().getIntTime();
											String slotTime = String.format("%02d:%02d", time / 100, time % 100);
											System.out.println((k) + ". " + appointment.getDate().get()
													+ " " + slotTime + ": " + appointment.getAop().getService());
											k++;
										}
									}
								}
							}
							System.out.print("Enter your choice: ");
							int appointmentChoice = sc.nextInt(); sc.nextLine();
							appointment = patient.getPatientSchedule().getAppointments().get(appointmentChoice-1);
							AppointmentOutcomeRecord aop = appointment.getAop();
							Medicine[] prescription = aop.getprescription();
							System.out.println("Please ensure that all fields below are correct before confirming:");
							System.out.println("Patient ID: " + patientID);
							System.out.println("Appointment ID: " + appointment.getUuid());
							System.out.println("Medication for Dispensation: ");
							for (int i = 0; i < prescription.length; i++){
								System.out.println((i+1) + ". " + prescription[i].name() + ": " + prescription[i].amount());
							}
							System.out.println("\nConfirm to Dispense Medication?");
							System.out.println("Enter 1 to Confirm; or 2 to Cancel.");
							System.out.print("Enter your choice: ");
							int confirmDispense;

							while (true) {
								confirmDispense = sc.nextInt();
								sc.nextLine();
								switch (confirmDispense) {
									case 1:
										if (GlobalData.getInstance().inventory.dispense(aop)) {
											System.out.println("Medication Successfully Dispensed! " +
													"Prescription Status Updated. Returning to Menu... \n");
										} else {
											System.out.println("Insufficient Medication. Please Restock! Returning to Menu...\n");
										}
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
					}
					break;

                case 3:
                    System.out.println("Current Inventory:");
                    GlobalData.getInstance().inventory.printCurrentInventory();
                    System.out.print("Enter anything to Return to Menu: ");
                    sc.nextLine();
                    System.out.println("Returning to Menu...\n");
                    break;

                case 4:
                    System.out.println("Current Inventory:");
                    GlobalData.getInstance().inventory.printCurrentInventory();
                    int submitMore;
                    while (true) {
                        GlobalData.getInstance().inventory.createRequest();
                        System.out.print("Would you like to Submit More Restock Requests? " +
                                "1. Yes; 2. No\nEnter your choice: ");
                        submitMore = sc.nextInt();
                        sc.nextLine();
                        while (submitMore != 1 && submitMore != 2) {
                            System.out.print("Invalid choice! Try again: ");
                            submitMore = sc.nextInt();
                            sc.nextLine();
                        }
                        if (submitMore == 2) {
                            System.out.println("Returning to Menu...\n");
                            break;
                        }
                    }
                    break;

                default:
                    if (!super.useroptions(choice - 4)) {
                        if (choice == 6) {
                            System.out.print("Confirm Log Out? Enter 1 to Log Out; " +
                                    "or Enter any other number to Return to Menu.\nEnter your choice: ");
                            int confirmLogOut = sc.nextInt();
                            sc.nextLine();
                            if (confirmLogOut == 1) {
                                System.out.println("Logging out...\n");
                                return;
                            } else {
                                System.out.println("Returning to Menu...\n");
                                break;
                            }
                        } else {
                            System.out.print("Invalid choice! Try again: ");
                            break;
                        }
                    }
                    break;
            }
		}
	}
}
