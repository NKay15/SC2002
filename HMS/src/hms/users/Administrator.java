package hms.users;

import hms.GlobalData;
import hms.Password;
import hms.appointments.Appointment;
import hms.appointments.AppointmentScheduler;
import hms.utils.Date;
import java.util.*;

public class Administrator extends Staff {
	
	/**
	 * Constructor
	 */
	public Administrator(String ID, String name, int gender, int age, Password password) {
		super(ID, name, 4, gender, age, password);
	}

	public void menu() {
		Scanner sc = GlobalData.getInstance().sc;
		int choice;
		boolean inputError = false;

		while (true) {
			try {
				if (!inputError) {
					System.out.println("-----Administrator Menu-----");
					System.out.println("1. View & Manage Hospital Staff");
					System.out.println("2. View Appointment Details");
					System.out.println("3. View & Manage Hospital Patients");
					System.out.println("4. View & Manage Medication Inventory");
					super.menu(5);
					System.out.println("-----End of Menu-----");
					System.out.print("Enter your choice: ");
				}
				else inputError = false;
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
							if (alreadyTried) {
								System.out.println("Invalid Appointment ID! Try again: ");
							}
							System.out.print("Enter Appointment ID (0 to Cancel): ");
							appointmentID = sc.nextLine();
							if (appointmentID.equals("0")) {
								System.out.println("Operation Cancelled. Returning to Menu...\n");
								break;
							}
							if (AppointmentScheduler.getInstance().findAppointment(UUID.fromString(appointmentID),
									AppointmentScheduler.getInstance().getAppointments()) != null) {
								appointment = AppointmentScheduler.getInstance().findAppointment(UUID.fromString(appointmentID),
										AppointmentScheduler.getInstance().getAppointments());
								appointmentFound = true;
							}
							alreadyTried = true;
						}

						if (appointmentFound) appointment.print();

						break;

					case 3:
						patientMenu();
						break;

					case 4:
						inventoryMenu();
						break;

					default:
						if (!super.useroptions(choice - 4)) {
							if (choice == 6) {
								System.out.print("Confirm Log Out? Enter 1 to Log Out; " +
										"or Enter anything else to Return to Menu.\nEnter your choice: ");
								String confirmLogOut = sc.next(); sc.nextLine();
								if (confirmLogOut.equals("1")) {
									System.out.println("Logging out...\n");
									return;
								} else {
									System.out.println("Returning to Menu...\n");
									break;
								}
							} else {
								System.out.print("Invalid choice! Try again: ");
								inputError = true;
								break;
							}
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

	public void staffMenu() {
		Scanner sc = GlobalData.getInstance().sc;
		String staffChoice = "1";

		while (true) {
			if (staffChoice.equals("1") || staffChoice.equals("2") || staffChoice.equals("3")
					|| staffChoice.equals("4")) {
				System.out.println("\n-----Staff Management-----");
				System.out.println("1. View All Staff Members");
				System.out.println("2. Add New Staff Member");
				System.out.println("3. Update Existing Staff Member");
				System.out.println("4. Remove Existing Staff Member");
				System.out.println("5. Return to Main Menu");
				System.out.println("--------------------------");
				System.out.print("Enter your choice: ");
			}
			staffChoice = sc.next(); sc.nextLine();
			switch (staffChoice) {
				case "1":
					System.out.println("Display Staff Members by:");
					System.out.println("1. Role; 2. ID; 3. Name; 4. Gender; 5. Age");
					System.out.println("Enter anything else to Return to Menu.");
					System.out.print("Enter your choice: ");
					String sorting = sc.next(); sc.nextLine();

					if (sorting.equals("1") || sorting.equals("2") || sorting.equals("3") || sorting.equals("4") || sorting.equals("5")) {
						ArrayList<User> sortedUserList = GlobalData.getInstance().userList.getStaffSorted(Integer.parseInt(sorting));
						if (sortedUserList == null || sortedUserList.isEmpty()) {
							System.out.println("No Staff Members to Display! Returning to Menu...");
							break;
						}
						else {
							System.out.println("\nSorted Staff List:\n------------------");
							switch (sorting){
								case "1":
									for (int i = 0; i < sortedUserList.size(); i++) {
										System.out.print((i + 1) + ". Role: ");
										sortedUserList.get(i).printRole();
										System.out.println("\tName: " + sortedUserList.get(i).getName());
									}
									System.out.println("------------------");
									System.out.print("Enter anything to Return to Menu: ");
									sc.nextLine();
									System.out.println("Returning to Menu...");
									break;

								case "2":
									for (int i = 0; i < sortedUserList.size(); i++) {
										System.out.println((i + 1) + ". ID: " + sortedUserList.get(i).getID()
												+ "\tName: " + sortedUserList.get(i).getName());
									}
									System.out.println("------------------");
									System.out.print("Enter anything to Return to Menu: ");
									sc.nextLine();
									System.out.println("Returning to Menu...");
									break;

								case "3":
									for (int i = 0; i < sortedUserList.size(); i++) {
										System.out.println((i + 1) + ". Name: " + sortedUserList.get(i).getName()
												+ "\tID: " + sortedUserList.get(i).getID());
									}
									System.out.println("------------------");
									System.out.print("Enter anything to Return to Menu: ");
									sc.nextLine();
									System.out.println("Returning to Menu...");
									break;

								case "4":
									for (int i = 0; i < sortedUserList.size(); i++) {
										System.out.println((i + 1) + ". Gender: " + sortedUserList.get(i).getGenderString()
												+ "\tName: " + sortedUserList.get(i).getName());
									}
									System.out.println("------------------");
									System.out.print("Enter anything to Return to Menu: ");
									sc.nextLine();
									System.out.println("Returning to Menu...");
									break;

								case "5":
									for (int i = 0; i < sortedUserList.size(); i++) {
										System.out.println((i + 1) + ". Age: " + sortedUserList.get(i).getAge()
												+ "\tName: " + sortedUserList.get(i).getName());
									}
									System.out.println("------------------");
									System.out.print("Enter anything to Return to Menu: ");
									sc.nextLine();
									System.out.println("Returning to Menu...");
									break;
							}
						}
					}
					else {
						System.out.println("Returning to Menu...");
					}
					break;

				case "2":
					System.out.println("Select Role of New Staff Member:");
					System.out.println("1. Doctor; 2. Pharmacist; 3. Administrator");
					System.out.println("Enter anything else to Return to Menu.");
					System.out.print("Enter your choice: ");
					String addWho = sc.next(); sc.nextLine();
					if (!addWho.equals("1") && !addWho.equals("2") && !addWho.equals("3")) {
						System.out.println("Returning to Menu...");
						break;
					}
					boolean alreadyExists;
					System.out.print("Enter ID: ");
					String newID;
					while (true) {
						alreadyExists = false;
						newID = sc.nextLine();
						if (!Character.isUpperCase(newID.charAt(0))) {
							System.out.print("ID Must Begin With an Uppercase Letter! Try again: ");
							continue;
						}
						for (User user : GlobalData.getInstance().userList.getUsersRoleSorted()) {
							if (user.getID().equals(newID)) {
								System.out.print("User " + newID + " Already Exists! Try again: ");
								alreadyExists = true;
								break;
							}
						}
						if (!alreadyExists) break;
					}

					System.out.print("Enter Name: ");
					String newName = sc.nextLine();
					System.out.print("Enter Gender (0: Unknown; 1: Male; 2: Female): ");
					int newGender;
					String newGenderString = sc.next(); sc.nextLine();
					while (true){
						switch (newGenderString) {
							case "0":
							case "Unknown":
							case "unknown":
								newGenderString = "Unknown";
								newGender = 0;
								break;
							case "1":
							case "Male":
							case "male":
								newGenderString = "Male";
								newGender = 1;
								break;
							case "2":
							case "Female":
							case "female":
								newGenderString = "Female";
								newGender = 2;
								break;
							default:
								System.out.print("Invalid choice! Try again: ");
								newGenderString = sc.next(); sc.nextLine();
								continue;
						}
						break;
					}
					System.out.print("Enter Age: ");
					int newAge = sc.nextInt(); sc.nextLine();

					System.out.println("\nPlease ensure that all fields below are correct before confirming:");
					System.out.println("ID: " + newID);
					System.out.println("Name: " + newName);
					System.out.println("Gender: " + newGenderString);
					System.out.println("Age: " + newAge);

					String confirmAdd;
					switch (addWho) {
						case "1":
							System.out.println("\nConfirm to Add New Doctor? You May Edit Details Later.");
							System.out.println("Enter 1 to Confirm; or 2 to Cancel.");
							System.out.print("Enter your choice: ");
							confirmAdd = sc.next(); sc.nextLine();
							while (true) {
								switch (confirmAdd) {
									case "1":
										Doctor newDoctor = new Doctor(newID, newName, newGender, newAge, null);
										GlobalData.getInstance().userList.addDoctor(newDoctor);
										System.out.println("New Doctor Successfully Added! Returning to Menu...");
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
							break;

						case "2":
							System.out.println("\nConfirm to Add New Pharmacist?  You May Edit Details Later.");
							System.out.println("Enter 1 to Confirm; or 2 to Cancel.");
							System.out.print("Enter your choice: ");
							confirmAdd = sc.next(); sc.nextLine();
							while (true) {
								switch (confirmAdd) {
									case "1":
										Pharmacist newPharmacist = new Pharmacist(newID, newName, newGender, newAge, null);
										GlobalData.getInstance().userList.addPharmacist(newPharmacist);
										System.out.println("New Pharmacist Successfully Added! Returning to Menu...");
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
							break;

						case "3":
							System.out.println("\nConfirm to Add New Administrator?  You May Edit Details Later.");
							System.out.println("Enter 1 to Confirm; or 2 to Cancel.");
							System.out.print("Enter your choice: ");
							confirmAdd = sc.next(); sc.nextLine();
							while (true) {
								switch (confirmAdd) {
									case "1":
										Administrator newAdministrator = new Administrator(newID, newName, newGender, newAge, null);
										GlobalData.getInstance().userList.addAdministrator(newAdministrator);
										System.out.println("New Administrator Successfully Added! Returning to Menu...");
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
							break;
					}
					break;

				case "3":
					System.out.println("Enter Role of Staff Member to Update:");
					System.out.println("1. Doctor; 2. Pharmacist; 3. Administrator");
					System.out.println("Enter anything else to Return to Menu.");
					System.out.print("Enter your choice: ");
					String updateWho = sc.next(); sc.nextLine();
					switch (updateWho) {
						case "1":
							System.out.print("Enter Doctor's ID (0 to Cancel): ");
							String doctorID = sc.nextLine();
							if (doctorID.equals("0")) {
								System.out.println("Operation Cancelled. Returning to menu...");
								break;
							}
							while (!GlobalData.getInstance().userList.updateDoctorByIDMenu(doctorID, this)){
								System.out.print("Doctor Does Not Exist! Try again: ");
								doctorID = sc.nextLine();
								if (doctorID.equals("0")) {
									System.out.println("Operation Cancelled. Returning to Menu...");
									break;
								}
							}
							break;

						case "2":
							System.out.print("Enter Pharmacist's ID (0 to Cancel): ");
							String pharmacistID = sc.nextLine();
							if (pharmacistID.equals("0")) {
								System.out.println("Operation Cancelled. Returning to menu...");
								break;
							}
							while (!GlobalData.getInstance().userList.updatePharmacistByIDMenu(pharmacistID, this)){
								System.out.print("Pharmacist Does Not Exist! Try again: ");
								pharmacistID = sc.nextLine();
								if (pharmacistID.equals("0")) {
									System.out.println("Operation Cancelled. Returning to Menu...");
									break;
								}
							}
							break;

						case "3":
							System.out.print("Enter Administrator's ID (0 to Cancel): ");
							String administratorID = sc.nextLine();
							if (administratorID.equals("0")) {
								System.out.println("Operation Cancelled. Returning to menu...");
								break;
							}
							while (!GlobalData.getInstance().userList.updateAdministratorByIDMenu(administratorID, this)){
								System.out.print("Administrator Does Not Exist! Try again: ");
								administratorID = sc.nextLine();
								if (administratorID.equals("0")) {
									System.out.println("Operation Cancelled. Returning to Menu...");
									break;
								}
							}
							break;

						default:
							System.out.println("Returning to Menu...");
							break;
					}
					break;

				case "4":
					System.out.println("Enter Role of Staff Member to Remove:");
					System.out.println("1. Doctor; 2. Pharmacist; 3. Administrator");
					System.out.println("Enter anything else to Return to Menu.");
					System.out.print("Enter your choice: ");
					String removeWho = sc.next(); sc.nextLine();
					switch (removeWho) {
						case "1":
							System.out.print("Enter Doctor's ID (0 to Cancel): ");
							String doctorID = sc.nextLine();
							if (doctorID.equals("0")) {
								System.out.println("Operation Cancelled. Returning to menu...");
								break;
							}
							while (!GlobalData.getInstance().userList.removeDoctorByIDMenu(doctorID, this)){
								System.out.print("Doctor Does Not Exist! Try again: ");
								doctorID = sc.nextLine();
								if (doctorID.equals("0")) {
									System.out.println("Operation Cancelled. Returning to Menu...");
									break;
								}
							}
							break;

						case "2":
							System.out.print("Enter Pharmacist's ID (0 to Cancel): ");
							String pharmacistID = sc.nextLine();
							if (pharmacistID.equals("0")) {
								System.out.println("Operation Cancelled. Returning to Menu...");
								break;
							}
							while (!GlobalData.getInstance().userList.removePharmacistByIDMenu(pharmacistID, this)){
								System.out.print("Pharmacist Does Not Exist! Try again: ");
								pharmacistID = sc.nextLine();
								if (pharmacistID.equals("0")) {
									System.out.println("Operation Cancelled. Returning to Menu...");
									break;
								}
							}
							break;

						case "3":
							System.out.print("Enter Administrator's ID (0 to Cancel): ");
							String administratorID = sc.nextLine();
							if (administratorID.equals("0")) {
								System.out.println("Operation Cancelled. Returning to Menu...");
								break;
							}
							while (!GlobalData.getInstance().userList.removeAdministratorByIDMenu(administratorID, this)){
								if (administratorID.equals(this.getID())) System.out.print("You May Not Remove Yourself! Try again: ");
								else System.out.print("Administrator Does Not Exist! Try again: ");
								administratorID = sc.nextLine();
								if (administratorID.equals("0")) {
									System.out.println("Operation Cancelled. Returning to Menu...");
									break;
								}
							}
							break;

						default:
							System.out.println("Returning to Menu...");
							break;
					}
					break;

				case "5":
					System.out.println("Returning to Main Menu...\n");
					return;

				default:
					System.out.print("Invalid choice! Try again: ");
					break;
			}
		}
	}

	public void patientMenu() {
		Scanner sc = GlobalData.getInstance().sc;
		String patientChoice = "1";

		while (true) {
			if (patientChoice.equals("1") || patientChoice.equals("2") || patientChoice.equals("3")
					|| patientChoice.equals("4")) {
				System.out.println("\n-----Patient Management-----");
				System.out.println("1. View All Patients");
				System.out.println("2. Add New Patient");
				System.out.println("3. Update Existing Patient");
				System.out.println("4. Remove Existing Patient");
				System.out.println("5. Return to Main Menu");
				System.out.println("--------------------------");
				System.out.print("Enter your choice: ");
			}
			patientChoice = sc.next(); sc.nextLine();
			switch (patientChoice) {
				case "1":
					System.out.println("Display Patients by:");
					System.out.println("1. ID; 2. Name; 3. Gender");
					System.out.println("Enter anything else to Return to Menu.");
					System.out.print("Enter your choice: ");
					String sorting = sc.next(); sc.nextLine();

					if (sorting.equals("1") || sorting.equals("2") || sorting.equals("3")) {
						ArrayList<User> sortedUserList = GlobalData.getInstance().userList.getPatientsSorted(Integer.parseInt(sorting));
						if (sortedUserList == null || sortedUserList.isEmpty()) {
							System.out.println("No Patients to Display! Returning to Menu...");
							break;
						}
						else {
							System.out.println("\nSorted Patient List:\n------------------");
							switch (sorting){
								case "1":
									for (int i = 0; i < sortedUserList.size(); i++) {
										System.out.println((i + 1) + ". ID: " + sortedUserList.get(i).getID()
												+ "\tName: " + sortedUserList.get(i).getName());
									}
									System.out.println("------------------");
									System.out.print("Enter anything to Return to Menu: ");
									sc.nextLine();
									System.out.println("Returning to Menu...");
									break;

								case "2":
									for (int i = 0; i < sortedUserList.size(); i++) {
										System.out.println((i + 1) + ". Name: " + sortedUserList.get(i).getName()
												+ "\tID: " + sortedUserList.get(i).getID());
									}
									System.out.println("------------------");
									System.out.print("Enter anything to Return to Menu: ");
									sc.nextLine();
									System.out.println("Returning to Menu...");
									break;

								case "3":
									for (int i = 0; i < sortedUserList.size(); i++) {
										System.out.println((i + 1) + ". Gender: " + sortedUserList.get(i).getGenderString()
												+ "\tName: " + sortedUserList.get(i).getName());
									}
									System.out.println("------------------");
									System.out.print("Enter anything to Return to Menu: ");
									sc.nextLine();
									System.out.println("Returning to Menu...");
									break;
							}
						}
					}
					else {
						System.out.println("Returning to Menu...");
					}
					break;

				case "2":
					boolean alreadyExists;
					System.out.print("Enter ID: ");
					String newID;
					while (true) {
						alreadyExists = false;
						newID = sc.nextLine();
						if (!Character.isUpperCase(newID.charAt(0))) {
							System.out.print("ID Must Begin With an Uppercase Letter! Try again: ");
							continue;
						}
						for (User user : GlobalData.getInstance().userList.getUsersRoleSorted()) {
							if (user.getID().equals(newID)) {
								System.out.print("User " + newID + " Already Exists! Try again: ");
								alreadyExists = true;
								break;
							}
						}
						if (!alreadyExists) break;
					}

					System.out.print("Enter Name: ");
					String newName = sc.nextLine();
					System.out.print("Enter Gender (0: Unknown; 1: Male; 2: Female): ");
					int newGender;
					String newGenderString = sc.next(); sc.nextLine();
					while (true){
						switch (newGenderString) {
							case "0":
							case "Unknown":
							case "unknown":
								newGenderString = "Unknown";
								newGender = 0;
								break;
							case "1":
							case "Male":
							case "male":
								newGenderString = "Male";
								newGender = 1;
								break;
							case "2":
							case "Female":
							case "female":
								newGenderString = "Female";
								newGender = 2;
								break;
							default:
								System.out.print("Invalid choice! Try again: ");
								newGenderString = sc.next(); sc.nextLine();
								continue;
						}
						break;
					}
					System.out.print("Enter Date of Birth (in DDMMYYYY): ");
					Date newDOB;
					String newDOBString;
					int day;
					int month;
					int year;
					while (true) {
						if (sc.hasNextInt()) {
							newDOBString = sc.nextLine();
							if (newDOBString.length() == 8) {
								day = Integer.parseInt(newDOBString.substring(0, 2));
								month = Integer.parseInt(newDOBString.substring(2, 4));
								year = Integer.parseInt(newDOBString.substring(4, 8));
								if (day > 0 && day < 32 && month > 0 && month < 13 && year > 1900 && year < 2025) {
									newDOB = new hms.utils.Date(day, month, year);
									break;
								} else {
									System.out.print("Invalid date! Try again: ");
								}
							}
							else System.out.print ("Invalid date! Try again: ");
						} else {
							System.out.print("Invalid input! Try again, With Digits Only: ");
							sc.nextLine();
						}
					}
					System.out.print("Enter HP: ");
					int newHP;
					while (true) {
						try {
							newHP = sc.nextInt();
							sc.nextLine();
							if (String.valueOf(newHP).length() == 8){
								break;
							}
							else {
								System.out.print("Invalid HP! Try again: ");
							}
						} catch (InputMismatchException e) {
							System.out.print("Invalid input! Try again, With Digits Only: ");
							sc.nextLine();
						}
					}
					System.out.print("Enter Email: ");
					String newEmail = sc.next(); sc.nextLine();
					System.out.println("----List of Blood Types----");
					System.out.println("0: Unknown\n1: A+\n2: A-\n3: B+\n4: B-\n" +
							"5: AB+\n6: AB-\n7: O+\n8: O-");
					System.out.println("---------------------------");
					System.out.print("Enter Blood Type (Digit Only): ");
					int newBloodType = -1;
					String newBloodTypeString = "";
					boolean bloodTypeFound;
					do {
						try {
							newBloodType = sc.nextInt();
							sc.nextLine();
							switch (newBloodType) {
								case 0:
									newBloodTypeString = "Unknown";
									bloodTypeFound = false;
									break;
								case 1:
									newBloodTypeString = "A+";
									bloodTypeFound = true;
									break;
								case 2:
									newBloodTypeString = "A-";
									bloodTypeFound = true;
									break;
								case 3:
									newBloodTypeString = "B+";
									bloodTypeFound = true;
									break;
								case 4:
									newBloodTypeString = "B-";
									bloodTypeFound = true;
									break;
								case 5:
									newBloodTypeString = "AB+";
									bloodTypeFound = true;
									break;
								case 6:
									newBloodTypeString = "AB-";
									bloodTypeFound = true;
									break;
								case 7:
									newBloodTypeString = "O+";
									bloodTypeFound = true;
									break;
								case 8:
									newBloodTypeString = "O-";
									bloodTypeFound = true;
									break;
								default:
									bloodTypeFound = false;
									System.out.print("Invalid choice! Try again: ");
							}
						} catch (InputMismatchException e) {
							bloodTypeFound = false;
							System.out.print("Invalid input! Try again, With Digit Only: ");
							sc.nextLine();
						}
					} while (!bloodTypeFound);

					System.out.println("\nPlease ensure that all fields below are correct before confirming:");
					System.out.println("ID: " + newID);
					System.out.println("Name: " + newName);
					System.out.println("Gender: " + newGenderString);
					System.out.println("DOB: " + day + "/" + month + "/" + year);
					System.out.println("HP: " + newHP);
					System.out.println("Email: " + newEmail);
					System.out.println("Blood Type: " + newBloodTypeString);

					String confirmAdd;
					System.out.println("\nConfirm to Add New Patient? You May Edit Details Later.");
					System.out.println("Enter 1 to Confirm; or 2 to Cancel.");
					System.out.print("Enter your choice: ");
					confirmAdd = sc.next(); sc.nextLine();
					while (true) {
						switch (confirmAdd) {
							case "1":
								Patient newPatient = new Patient(newID, newName, newGender, newDOB, newHP, newEmail, newBloodType, null);
								GlobalData.getInstance().userList.addPatient(newPatient);
								System.out.println("New Doctor Successfully Added! Returning to Menu...");
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
					break;

				case "3":
					System.out.print("Enter Patient's ID (0 to Cancel): ");
					String patientID = sc.nextLine();
					if (patientID.equals("0")) {
						System.out.println("Operation Cancelled. Returning to menu...");
						break;
					}
					while (!GlobalData.getInstance().userList.updatePatientByIDMenu(patientID, this)){
						System.out.print("Patient Does Not Exist! Try again: ");
						patientID = sc.nextLine();
						if (patientID.equals("0")) {
							System.out.println("Operation Cancelled. Returning to Menu...");
							break;
						}
					}
					break;

				case "4":
					System.out.print("Enter Patient's ID (0 to Cancel): ");
					String patientToRemoveID = sc.nextLine();
					if (patientToRemoveID.equals("0")) {
						System.out.println("Operation Cancelled. Returning to menu...");
						break;
					}
					while (!GlobalData.getInstance().userList.removePatientByIDMenu(patientToRemoveID, this)){
						System.out.print("Doctor Does Not Exist! Try again: ");
						patientToRemoveID = sc.nextLine();
						if (patientToRemoveID.equals("0")) {
							System.out.println("Operation Cancelled. Returning to Menu...");
							break;
						}
					}
					break;

				case "5":
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
		String inventoryChoice = "1";

		while (true) {
			if (inventoryChoice.equals("1") || inventoryChoice.equals("2") || inventoryChoice.equals("3")
					|| inventoryChoice.equals("4") || inventoryChoice.equals("5")) {
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
			inventoryChoice = sc.next(); sc.nextLine();
			switch (inventoryChoice) {
				case "1":
					System.out.println("Current Inventory:");
					GlobalData.getInstance().inventory.printCurrentInventory();
					System.out.print("Enter anything to Return to Menu: ");
					sc.nextLine();
					System.out.println("Returning to Menu...");
					break;

				case "2":
					int addMore;
					while (true) {
						GlobalData.getInstance().inventory.addNewMedicine();
						System.out.print("\nWould you like to Add More Medicines to the Inventory? " +
								"1. Yes; 2. No\nEnter your choice: ");
						addMore = sc.nextInt(); sc.nextLine();
						while (addMore != 1 && addMore != 2) {
							System.out.print("Invalid choice! Try again: ");
							addMore = sc.nextInt(); sc.nextLine();
						}
						if (addMore == 2) {
							System.out.println("Returning to Menu...");
							break;
						}
					}
					break;

				case "3":
					int updateMoreMeds;
					while (true) {
						GlobalData.getInstance().inventory.updateStockLevelMenu();
						System.out.print("\nWould you like to Update More Medication Stock Levels? " +
								"1. Yes; 2. No\nEnter your choice: ");
						updateMoreMeds = sc.nextInt(); sc.nextLine();
						while (updateMoreMeds != 1 && updateMoreMeds != 2) {
							System.out.print("Invalid choice! Try again: ");
							updateMoreMeds = sc.nextInt(); sc.nextLine();
						}
						if (updateMoreMeds == 2) {
							System.out.println("Returning to Menu...");
							break;
						}
					}
					break;

				case "4":
					int updateMoreAlerts;
					while (true) {
						System.out.println("Current Inventory:");
						GlobalData.getInstance().inventory.setNewLowLevel();
						System.out.print("\nWould you like to Update More Low Levels? " +
								"1. Yes; 2. No\nEnter your choice: ");
						updateMoreAlerts = sc.nextInt(); sc.nextLine();
						while (updateMoreAlerts != 1 && updateMoreAlerts != 2) {
							System.out.print("Invalid choice! Try again: ");
							updateMoreAlerts = sc.nextInt(); sc.nextLine();
						}
						if (updateMoreAlerts == 2) {
							System.out.println("Returning to Menu...");
							break;
						}
					}
					break;

				case "5":
					int manageMoreRequests;
					while (true) {
						GlobalData.getInstance().inventory.manageRestockRequests();
						if (GlobalData.getInstance().inventory.isRequestsEmpty()) {
							System.out.println("No Pending Restock Requests!\nReturning to Menu...");
							break;
						}
						System.out.print("\nWould you like to Manage More Requests? " +
								"1. Yes; 2. No\nEnter your choice: ");
						manageMoreRequests = sc.nextInt(); sc.nextLine();
						while (manageMoreRequests != 1 && manageMoreRequests != 2) {
							System.out.print("Invalid choice! Try again: ");
							manageMoreRequests = sc.nextInt(); sc.nextLine();
						}
						if (manageMoreRequests == 2) {
							System.out.println("Returning to Menu...");
							break;
						}
					}
					break;

				case "6":
					System.out.println("Returning to Main Menu...\n");
					return;

				default:
					System.out.print("Invalid choice! Try again: ");
					break;
			}
		}
	}

	public void printRole() {
        System.out.print("Adminstrator");
    }
}
