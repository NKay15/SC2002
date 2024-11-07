package hms.users;

import hms.GlobalData;

import java.util.Scanner;

public class Administrator extends Staff {
	
	/**
	 * Constructor
	 */
	public Administrator(String ID, String name, int gender, int age) {
		super(ID, name, 4, gender, age);
	}

	public void menu() {
		Scanner sc = new Scanner(System.in);
		int choice = 1;

		while (true) {
			if (choice >= 1 && choice <= 3) {
				System.out.println("-----Administrator Menu-----");
				System.out.println("1. Manage Staff");
				System.out.println("2. View Appointment Records");
				System.out.println("3. Manage Inventory");
				super.menu(4);
				System.out.println("-----End of Menu-----");
				System.out.print("Enter your choice: ");
			}
			choice = sc.nextInt(); sc.nextLine();
			System.out.println();

			switch(choice) {
				case 1:
					// This sub-menu will not auto-loop
					staffMenu(sc);
					break;

				case 2:

					break;

				case 3:
					// This sub-menu will not auto-loop
					inventoryMenu(sc);
					break;

				default:
					if(!super.useroptions(choice-3)) {
						if (choice == 5) {
							System.out.print("Confirm Log Out? Enter 1 to Log Out; " +
									"or Enter any other number to Return to Menu.\nEnter your choice: ");
							int confirmLogOut = sc.nextInt(); sc.nextLine();
							if (confirmLogOut == 1) {
								System.out.println("Logging out...\n");
								sc.close();
								return;
							}
							else {
								System.out.println("Returning to Menu...\n");
								break;
							}
						}
						else {
							System.out.println("Invalid choice! Try again: ");
							break;
						}
					}
					break;
			}
		}
	}

	public void staffMenu(Scanner sc) {
		System.out.println("-----Staff Management-----");
		System.out.println("1. Add Staff Member");
		System.out.println("2. Update Existing Staff Member");
		System.out.println("3. Remove Staff Member");
		System.out.println("4. Display All Staff Members");
		System.out.println("5. Return to Main Menu");
		System.out.println("--------------------------");
		System.out.print("Enter your choice: ");
		int staffChoice;

		while (true) {
			staffChoice = sc.nextInt(); sc.nextLine();
			switch (staffChoice) {
				case 1:

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
							while (!GlobalData.getInstance().userList.updateDoctorByID(doctorID)){
								System.out.print("Doctor Does Not Exist! Try again: ");
								doctorID = sc.nextLine();
							}
							break;

						case 2:
							System.out.print("Enter Pharmacist's ID (0 to Cancel): ");
							String pharmacistID = sc.nextLine();
							if (pharmacistID.equals("0")) {
								System.out.print("Operation Cancelled. Returning to menu...\n");
								break;
							}
							while (!GlobalData.getInstance().userList.updatePharmacistByID(pharmacistID)){
								System.out.print("Pharmacist Does Not Exist! Try again: ");
								pharmacistID = sc.nextLine();
							}
							break;

						case 3:
							System.out.print("Enter Administrator's ID (0 to Cancel): ");
							String administratorID = sc.nextLine();
							if (administratorID.equals("0")) {
								System.out.print("Operation Cancelled. Returning to menu...\n");
								break;
							}
							while (!GlobalData.getInstance().userList.updatePharmacistByID(administratorID)){
								System.out.print("Administrator Does Not Exist! Try again: ");
								administratorID = sc.nextLine();
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
							while (!GlobalData.getInstance().userList.removeDoctorByIDMenu(doctorID)){
								System.out.print("Administrator Does Not Exist! Try again: ");
								doctorID = sc.nextLine();
							}
							break;
						case 2:
							System.out.print("Enter Pharmacist's ID (0 to Cancel): ");
							String pharmacistID = sc.nextLine();
							if (pharmacistID.equals("0")) {
								System.out.print("Operation Cancelled. Returning to menu...\n");
								break;
							}
							while (!GlobalData.getInstance().userList.removePharmacistByIDMenu(pharmacistID)){
								System.out.print("Administrator Does Not Exist! Try again: ");
								pharmacistID = sc.nextLine();
							}
							break;

						case 3:
							System.out.print("Enter Administrator's ID (0 to Cancel): ");
							String administratorID = sc.nextLine();
							if (administratorID.equals("0")) {
								System.out.print("Operation Cancelled. Returning to menu...\n");
								break;
							}
							while (!GlobalData.getInstance().userList.removeAdministratorByIDMenu(administratorID)){
								System.out.print("Administrator Does Not Exist! Try again: ");
								administratorID = sc.nextLine();
							}
							break;

						default:
							System.out.println("Returning to Menu...\n");
							break;
					}
					break;

				case 4:

					break;

				case 5:
					System.out.println("Returning to Menu...\n");
					break;

				default:
					System.out.print("Invalid choice! Try again: ");
					continue;
			}
			return;
		}
	}

	public void inventoryMenu(Scanner sc) {
		System.out.println("-----Inventory Management-----");
		System.out.println("1. View Medication Inventory");
		System.out.println("2. Add New Medication to Inventory");
		System.out.println("3. Update Medication Stock Levels");
		System.out.println("4. Update Low Stock Level Alert Line");
		System.out.println("5. Manage Restock Requests");
		System.out.println("6. Return to Main Menu");
		System.out.println("------------------------------");
		System.out.print("Enter your choice: ");
		int inventoryChoice;

		while (true) {
			inventoryChoice = sc.nextInt(); sc.nextLine();
			switch (inventoryChoice) {
				case 1:
					System.out.println("Here's the Current Inventory:");
					GlobalData.getInstance().inventory.printCurrentInventory();
					System.out.print("\nEnter any number to Return to Menu: ");
					sc.nextInt(); sc.nextLine();
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

					break;

				case 6:
					System.out.println("Returning to Menu...\n");
					break;

				default:
					System.out.print("Invalid choice! Try again: ");
					continue;
			}
			return;
		}
	}
}
