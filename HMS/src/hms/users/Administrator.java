package hms.users;

import hms.GlobalData;
import hms.appointments.Appointment;
import hms.appointments.AppointmentScheduler;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Administrator extends Staff {
	
	/**
	 * Constructor
	 */
	public Administrator(String ID, String name, int gender, int age) {
		super(ID, name, 4, gender, age);
	}

	public void menu() {
		System.out.println("-----Administrator Menu-----");
		System.out.println("1. Manage Staff");
		System.out.println("2. View Appointment Records");
		System.out.println("3. Manage Inventory");
		super.menu(4);
		System.out.println("-----End of Menu-----");
		System.out.print("Enter your choice: ");
		Scanner sc = GlobalData.getInstance().sc;
		int choice = 0;

		while (true) {
			if (choice >= 1 && choice <= 5) {
				System.out.println("-----Administrator Menu-----");
				System.out.println("1. Manage Staff");
				System.out.println("2. View Appointment Records");
				System.out.println("3. Manage Inventory");
				super.menu(4);
				System.out.println("-----End of Menu-----");
				System.out.print("Enter your choice: ");
			}
			choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
				case 1:
					staffMenu();
					break;

				case 2:
					boolean appointmentFound = false;
					boolean alreadyTried = false;
					Appointment appointment = null;
					String appointmentID;

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

					if (appointmentFound) appointment.print();

					break;

				case 3:
					inventoryMenu();
					break;

				default:
					if(!super.useroptions(choice-3)) {
						if (choice == 5) {
							System.out.print("Confirm Log Out? Enter 1 to Log Out; " +
									"or Enter any other number to Return to Menu.\nEnter your choice: ");
							int confirmLogOut = sc.nextInt(); sc.nextLine();
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

	public void staffMenu() {
		Scanner sc = GlobalData.getInstance().sc;
		int staffChoice = 1;

		while (true) {
			if (staffChoice >= 1 && staffChoice <= 5) {
				System.out.println("\n-----Staff Management-----");
				System.out.println("1. Add Staff Member");
				System.out.println("2. Update Existing Staff Member");
				System.out.println("3. Remove Staff Member");
				System.out.println("4. Display All Staff Members");
				System.out.println("5. Return to Main Menu");
				System.out.println("--------------------------");
				System.out.print("Enter your choice: ");
			}
			staffChoice = sc.nextInt(); sc.nextLine();
			switch (staffChoice) {
				case 1:
					int addWho;
					System.out.println("Select Role of New Staff Member:");
					System.out.println("1. Doctor; 2. Pharmacist; 3. Administrator");
					System.out.println("Enter any other number to Return to Menu.");
					System.out.print("Enter your choice: ");
					addWho = sc.nextInt(); sc.nextLine();
					if (addWho < 1 || addWho > 3) {
						System.out.println("Returning to Menu...\n");
						break;
					}

					System.out.print("Enter ID: ");
					String newID = sc.nextLine();
					System.out.print("Enter Name: ");
					String newName = sc.nextLine();
					System.out.print("Enter Gender: ");
					int newGender = sc.nextInt(); sc.nextLine();
					System.out.print("Enter Age: ");
					int newAge = sc.nextInt(); sc.nextLine();

					System.out.println("\nPlease ensure that all fields below are correct before confirming:");
					System.out.println("ID: " + newID);
					System.out.println("Name: " + newName);
					System.out.println("Gender: " + newGender);
					System.out.println("Age: " + newAge);

					int confirmAdd;
					switch (addWho) {
						case 1:
							System.out.println("Confirm to Add New Doctor?");
							System.out.println("Enter 1 to Confirm; or 2 to Cancel.");
							System.out.print("Enter your choice: ");
							confirmAdd = sc.nextInt(); sc.nextLine();
							while (true) {
								switch (confirmAdd) {
									case 1:
										Doctor newDoctor = new Doctor(newID, newName, newGender, newAge);
										GlobalData.getInstance().userList.addDoctor(newDoctor);
										System.out.println("New Doctor Successfully Added! Returning to Menu...\n");
										break;
									case 2:
										System.out.println("Operation Cancelled. Returning to Menu...\n");
										break;
									default:
										System.out.print("Invalid choice! Try again: ");
										continue;
								}
								break;
							}
							break;

						case 2:
							System.out.println("Confirm to Add New Pharmacist?");
							System.out.println("Enter 1 to Confirm; or 2 to Cancel.");
							System.out.print("Enter your choice: ");
							confirmAdd = sc.nextInt(); sc.nextLine();
							while (true) {
								switch (confirmAdd) {
									case 1:
										Pharmacist newPharmacist = new Pharmacist(newID, newName, newGender, newAge);
										GlobalData.getInstance().userList.addPharmacist(newPharmacist);
										System.out.println("New Pharmacist Successfully Added! Returning to Menu...\n");
										break;
									case 2:
										System.out.println("Operation Cancelled. Returning to Menu...\n");
										break;
									default:
										System.out.print("Invalid choice! Try again: ");
										continue;
								}
								break;
							}
							break;

						case 3:
							System.out.println("Confirm to Add New Administrator?");
							System.out.println("Enter 1 to Confirm; or 2 to Cancel.");
							System.out.print("Enter your choice: ");
							confirmAdd = sc.nextInt(); sc.nextLine();
							while (true) {
								switch (confirmAdd) {
									case 1:
										Administrator newAdministrator = new Administrator(newID, newName, newGender, newAge);
										GlobalData.getInstance().userList.addAdministrator(newAdministrator);
										System.out.println("New Administrator Successfully Added! Returning to Menu...\n");
										break;
									case 2:
										System.out.println("Operation Cancelled. Returning to Menu...\n");
										break;
									default:
										System.out.print("Invalid choice! Try again: ");
										continue;
								}
								break;
							}
							break;
					}
					break;

				case 2:
					System.out.print("Enter 1 to Update Doctor; 2 to Update Pharmacist; 3 to Update Administrator; " +
							"or Enter any other number to Return to Menu.\nEnter your choice: ");
					int updateWho = sc.nextInt(); sc.nextLine();
					switch (updateWho) {
						case 1:
							System.out.print("Enter Doctor's ID (0 to Cancel): ");
							String doctorID = sc.nextLine();
							if (doctorID.equals("0")) {
								System.out.print("Operation Cancelled. Returning to menu...\n");
								break;
							}
							while (!GlobalData.getInstance().userList.updateDoctorByIDMenu(doctorID, this)){
								System.out.print("Doctor Does Not Exist! Try again: ");
								doctorID = sc.nextLine();
								if (doctorID.equals("0")) {
									System.out.print("Operation Cancelled. Returning to Menu...\n");
									break;
								}
							}
							break;

						case 2:
							System.out.print("Enter Pharmacist's ID (0 to Cancel): ");
							String pharmacistID = sc.nextLine();
							if (pharmacistID.equals("0")) {
								System.out.print("Operation Cancelled. Returning to menu...\n");
								break;
							}
							while (!GlobalData.getInstance().userList.updatePharmacistByIDMenu(pharmacistID, this)){
								System.out.print("Pharmacist Does Not Exist! Try again: ");
								pharmacistID = sc.nextLine();
								if (pharmacistID.equals("0")) {
									System.out.print("Operation Cancelled. Returning to Menu...\n");
									break;
								}
							}
							break;

						case 3:
							System.out.print("Enter Administrator's ID (0 to Cancel): ");
							String administratorID = sc.nextLine();
							if (administratorID.equals("0")) {
								System.out.print("Operation Cancelled. Returning to menu...\n");
								break;
							}
							while (!GlobalData.getInstance().userList.updateAdministratorByIDMenu(administratorID, this)){
								System.out.print("Administrator Does Not Exist! Try again: ");
								administratorID = sc.nextLine();
								if (administratorID.equals("0")) {
									System.out.print("Operation Cancelled. Returning to Menu...\n");
									break;
								}
							}
							break;

						default:
							System.out.println("Returning to Menu...\n");
							break;
					}
					break;

				case 3:
					System.out.print("Enter 1 to Remove Doctor; 2 to Remove Pharmacist; 3 to Remove Administrator; " +
							"or Enter any other number to Return to Menu.\nEnter your choice: ");
					int removeWho = sc.nextInt(); sc.nextLine();
					switch (removeWho) {
						case 1:
							System.out.print("Enter Doctor's ID (0 to Cancel): ");
							String doctorID = sc.nextLine();
							if (doctorID.equals("0")) {
								System.out.print("Operation Cancelled. Returning to menu...\n");
								break;
							}
							while (!GlobalData.getInstance().userList.removeDoctorByIDMenu(doctorID, this)){
								System.out.print("Doctor Does Not Exist! Try again: ");
								doctorID = sc.nextLine();
								if (doctorID.equals("0")) {
									System.out.print("Operation Cancelled. Returning to Menu...\n");
									break;
								}
							}
							break;

						case 2:
							System.out.print("Enter Pharmacist's ID (0 to Cancel): ");
							String pharmacistID = sc.nextLine();
							if (pharmacistID.equals("0")) {
								System.out.print("Operation Cancelled. Returning to Menu...\n");
								break;
							}
							while (!GlobalData.getInstance().userList.removePharmacistByIDMenu(pharmacistID, this)){
								System.out.print("Pharmacist Does Not Exist! Try again: ");
								pharmacistID = sc.nextLine();
								if (pharmacistID.equals("0")) {
									System.out.print("Operation Cancelled. Returning to Menu...\n");
									break;
								}
							}
							break;

						case 3:
							System.out.print("Enter Administrator's ID (0 to Cancel): ");
							String administratorID = sc.nextLine();
							if (administratorID.equals("0")) {
								System.out.print("Operation Cancelled. Returning to Menu...\n");
								break;
							}
							while (!GlobalData.getInstance().userList.removeAdministratorByIDMenu(administratorID, this)){
								if (administratorID.equals(this.getID())) System.out.print("You May Not Remove Yourself! Try again: ");
								else System.out.print("Administrator Does Not Exist! Try again: ");
								administratorID = sc.nextLine();
								if (administratorID.equals("0")) {
									System.out.print("Operation Cancelled. Returning to Menu...\n");
									break;
								}
							}
							break;

						default:
							System.out.println("Returning to Menu...\n");
							break;
					}
					break;

				case 4:
					int sorting;
					System.out.println("Display Staff Members by:");
					System.out.println("1. Role; 2. Gender; 3. Name; 4. ID; 5. Age");
					System.out.print("Enter your choice: ");
					sorting = sc.nextInt(); sc.nextLine();
					while (sorting < 1 || sorting > 5) {
						System.out.print("Invalid choice! Try again: ");
						sorting = sc.nextInt(); sc.nextLine();
					}

					ArrayList<User> sortedUserList = GlobalData.getInstance().userList.getStaffSorted(sorting);
					if (sortedUserList == null || sortedUserList.isEmpty()) {
						System.out.println("No Staff Members to Display! Returning to Menu...\n");
						break;
					}

					System.out.println("\nSorted Staff List:\n------------------");
					switch (sorting){
						case 1:
							for (int i = 0; i < sortedUserList.size(); i++) {
								System.out.println((i + 1) + ". Name: " + sortedUserList.get(i).getName()
										+ "\tRole: " + sortedUserList.get(i).getRole());
							}
							break;

						case 2:
							for (int i = 0; i < sortedUserList.size(); i++) {
								System.out.println((i + 1) + ". Name: " + sortedUserList.get(i).getName()
										+ "\tGender: " + sortedUserList.get(i).getGender());
							}
							break;

						case 3:
							for (int i = 0; i < sortedUserList.size(); i++) {
								System.out.println((i + 1) + ". Name: " + sortedUserList.get(i).getName()
										+ "\tID: " + sortedUserList.get(i).getID());
							}
							break;

						case 4:
							for (int i = 0; i < sortedUserList.size(); i++) {
								System.out.println((i + 1) + ". ID: " + sortedUserList.get(i).getID()
										+ "\tName: " + sortedUserList.get(i).getName());
							}
							break;

						case 5:
							for (int i = 0; i < sortedUserList.size(); i++) {
								System.out.println((i + 1) + ". Name: " + sortedUserList.get(i).getName()
										+ "\tAge: " + sortedUserList.get(i).getAge());
							}
							break;
					}
					System.out.print("\nEnter anything to Return to Menu: ");
					sc.nextLine();
					System.out.println("Returning to Menu...\n");
					break;

				case 5:
					System.out.println("Returning to Main Menu...\n");
					return;

				default:
					System.out.print("Invalid choice! Try again: ");
					break;
			}
		}
	}

	public void inventoryMenu() {
		Scanner sc = GlobalData.getInstance().sc;
		int inventoryChoice = 1;

		while (true) {
			if (inventoryChoice >= 1 && inventoryChoice <= 6) {
				System.out.println("\n-----Inventory Management-----");
				System.out.println("1. View Medication Inventory");
				System.out.println("2. Add New Medication to Inventory");
				System.out.println("3. Update Medication Stock Levels");
				System.out.println("4. Update Low Stock Level Alert Line");
				System.out.println("5. Manage Restock Requests");
				System.out.println("6. Return to Main Menu");
				System.out.println("------------------------------");
				System.out.print("Enter your choice: ");
			}
			inventoryChoice = sc.nextInt(); sc.nextLine();
			switch (inventoryChoice) {
				case 1:
					System.out.println("Current Inventory:");
					GlobalData.getInstance().inventory.printCurrentInventory();
					System.out.print("Enter anything to Return to Menu: ");
					sc.nextLine();
					System.out.println("Returning to Menu...\n");
					break;

				case 2:
					int addMore;
					while (true) {
						System.out.println("\n");
						GlobalData.getInstance().inventory.addNewMedicine();
						System.out.print("Would you like to Add More Medicines to the Inventory? " +
								"1. Yes; 2. No\nEnter your choice: ");
						addMore = sc.nextInt(); sc.nextLine();
						while (addMore != 1 && addMore != 2) {
							System.out.print("Invalid choice! Try again: ");
							addMore = sc.nextInt(); sc.nextLine();
						}
						if (addMore == 2) {
							System.out.println("Returning to Menu...\n");
							break;
						}
					}
					break;

				case 3:
					int updateMoreMeds;
					while (true) {
						System.out.println("\n");
						GlobalData.getInstance().inventory.updateStockLevelMenu();
						System.out.print("Would you like to Update More Medication Stock Levels? " +
								"1. Yes; 2. No\nEnter your choice: ");
						updateMoreMeds = sc.nextInt(); sc.nextLine();
						while (updateMoreMeds != 1 && updateMoreMeds != 2) {
							System.out.print("Invalid choice! Try again: ");
							updateMoreMeds = sc.nextInt(); sc.nextLine();
						}
						if (updateMoreMeds == 2) {
							System.out.println("Returning to Menu...\n");
							break;
						}
					}
					break;

				case 4:
					int updateMoreAlerts;
					while (true) {
						System.out.println("\n");
						GlobalData.getInstance().inventory.setNewLowLevel();
						System.out.print("Would you like to Update More Low Levels? " +
								"1. Yes; 2. No\nEnter your choice: ");
						updateMoreAlerts = sc.nextInt(); sc.nextLine();
						while (updateMoreAlerts != 1 && updateMoreAlerts != 2) {
							System.out.print("Invalid choice! Try again: ");
							updateMoreAlerts = sc.nextInt(); sc.nextLine();
						}
						if (updateMoreAlerts == 2) {
							System.out.println("Returning to Menu...\n");
							break;
						}
					}
					break;

				case 5:
					int manageMoreRequests;
					while (true) {
						GlobalData.getInstance().inventory.manageRestockRequests();
						System.out.print("Would you like to Manage More Requests? " +
								"1. Yes; 2. No\nEnter your choice: ");
						manageMoreRequests = sc.nextInt(); sc.nextLine();
						while (manageMoreRequests != 1 && manageMoreRequests != 2) {
							System.out.print("Invalid choice! Try again: ");
							manageMoreRequests = sc.nextInt(); sc.nextLine();
						}
						if (manageMoreRequests == 2) {
							System.out.println("Returning to Menu...\n");
							break;
						}
					}
					break;

				case 6:
					System.out.println("Returning to Main Menu...\n");
					return;

				default:
					System.out.print("Invalid choice! Try again: ");
					break;
			}
		}
	}
}
