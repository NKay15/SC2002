package hms.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import hms.GlobalData;
import hms.users.Administrator;
import hms.users.Pharmacist;
import hms.users.Staff;
import hms.utils.Password;
import hms.utils.Role;

public class PharmacistFileService extends StaffFileService {
    /**
     * Get All Pharmacist Data
     * @return List of Pharmacists
     */
    public static ArrayList<Pharmacist> getAllPharmacistData() {
        ArrayList<Pharmacist> pharmacistArray = new ArrayList<Pharmacist>();
        
        try {
            File myObj = new File("HMS/src/data/Staff_List.txt");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // Remove header line

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                if (dataList[2] != Role.PHARMACIST.toString()) {
                    continue;
                }

                int genderNo = 0;
                if (dataList[3].equals("Male")) {
                	genderNo = 1;
                } else if (dataList[3].equals("Female")) {
                	genderNo = 2;
                }

                Pharmacist newStaff = null;
                if (dataList.length != 6) {
                    newStaff = new Pharmacist(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), null);
                } else {
                    newStaff = new Pharmacist(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), new Password(dataList[5]));
                }

                pharmacistArray.add(newStaff);
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        
        return pharmacistArray;
    }

    /**
     * Get Pharmacist by ID
     * @param ID
     * @return Pharmacist
     */
    public static Pharmacist getPharmacistByID(String ID) {
        try {
            File myObj = new File("HMS/src/data/Staff_List.txt");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // Remove header line

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                if (dataList[2] != Role.PHARMACIST.toString() && (!dataList[0].equals(ID))) {
                    continue;
                }

                int genderNo = 0;
                if (dataList[3].equals("Male")) {
                	genderNo = 1;
                } else if (dataList[3].equals("Female")) {
                	genderNo = 2;
                }

                myReader.close();
                if (dataList.length != 6) {
                    return new Pharmacist(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), null);
                } else {
                    return new Pharmacist(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), new Password(dataList[5]));
                }
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        
        return null;
    }

    /**
     * Menu to Remove Pharmacist by ID (for Admins)
     * @param ID
     * @param adminUsing Administrator Using Remove Operation
     */
    public static boolean removePharmacistByIDMenu(String ID, Administrator adminUsing) {
        Scanner sc = GlobalData.getInstance().sc;
        Pharmacist pharmacist = getPharmacistByID(ID);
        
        if (pharmacist == null) return false;

        System.out.println("\nPlease ensure that all fields below are correct before confirming:");
        System.out.println("Pharmacist ID: " + pharmacist.getID());
        System.out.println("Name: " + pharmacist.getName());
        System.out.println("Gender: " + pharmacist.getGenderString());
        System.out.println("Age: " + pharmacist.getAge());
        System.out.print("\nEnter your Password to Confirm (0 to Cancel): ");
        String password = sc.nextLine();
        while (!adminUsing.checkPassword(password)) {
            if (password.equals("0")) {
                System.out.println("Operation Cancelled. Returning to Menu...");
                return true;
            }
            else {
                System.out.print("Wrong Password! Try again: ");
                password = sc.nextLine();
            }
        }
        removeStaffbyID(ID);
        System.out.println("Pharmacist Successfully Removed! Returning to Menu...");
        return true;
    }

    /**
     * Update Pharmacist by ID
     * @param ID
     * @param adminUsing Administrator Using Update Operation
     */
    public static boolean updatePharmacistByIDMenu(String ID, Administrator adminUsing) {
        Scanner sc = GlobalData.getInstance().sc;
        Pharmacist pharmacist = getPharmacistByID(ID);

        /* If there are is doctor found */
        if (pharmacist == null) return false;

        String newPharmacistID = pharmacist.getID();
        String newPharmacistName = pharmacist.getName();
        int newPharmacistGender = pharmacist.getGender();
        String newPharmacistGenderString = pharmacist.getGenderString();
        int newPharmacistAge = pharmacist.getAge();
        int changeWhat;

        /* Menu */
        System.out.println("\n-----Pharmacist Update Menu-----");
        System.out.println("Pharmacist ID: " + pharmacist.getID());
        System.out.println("Name: " + pharmacist.getName());
        System.out.println("Gender: " + pharmacist.getGenderString());
        System.out.println("Age: " + pharmacist.getAge());
    	System.out.println("\n1. Update Name");
    	System.out.println("2. Update Gender");
        System.out.println("3. Update Age");
        System.out.println("4. Cancel");
        System.out.println("----------------------------");
        System.out.print("Enter your choice: ");
        String choice;

        while (true) {
            choice = sc.next(); sc.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Current Name: " + pharmacist.getName());
                    System.out.print("Enter New Name: ");
                    newPharmacistName = sc.nextLine();
                    changeWhat = 1;
                    break;

                case "2":
                    System.out.println("Current Gender: " + pharmacist.getGenderString());
                    System.out.print("Enter New Gender (0: Unknown; 1: Male; 2: Female): ");
                    newPharmacistGenderString = sc.next(); sc.nextLine();
                    while (true){
                        switch (newPharmacistGenderString) {
                            case "0":
                            case "Unknown":
                            case "unknown":
                                newPharmacistGender = 0;
                                newPharmacistGenderString = "Unknown";
                                break;
                            case "1":
                            case "Male":
                            case "male":
                                newPharmacistGender = 1;
                                newPharmacistGenderString = "Male";
                                break;
                            case "2":
                            case "Female":
                            case "female":
                                newPharmacistGender = 2;
                                newPharmacistGenderString = "Female";
                                break;
                            default:
                                System.out.print("Invalid choice! Try again: ");
                                newPharmacistGenderString = sc.next(); sc.nextLine();
                                continue;
                        }
                        break;
                    }
                    changeWhat = 2;
                    break;

                case "3":
                    System.out.println("Current Age: " + pharmacist.getAge());
                    System.out.print("Enter New Age: ");
                    newPharmacistAge = sc.nextInt(); sc.nextLine();
                    changeWhat = 3;
                    break;

                case "4":
                    System.out.println("Operation Cancelled. Returning to Menu...");
                    return true;

                default:
                    System.out.print("Invalid choice! Enter your choice: ");
                    continue;
            }
            break;
        }

        System.out.println("\nPlease ensure that all fields below are correct before confirming:");
        System.out.println("Pharmacist ID: " + newPharmacistID);
        System.out.println("Name: " + newPharmacistName);
        System.out.println("Gender: " + newPharmacistGenderString);
        System.out.println("Age: " + newPharmacistAge);
        System.out.print("\nEnter your Password to Confirm (0 to Cancel): ");
        String password = sc.nextLine();
        while (!adminUsing.checkPassword(password)) {
            if (password.equals("0")) {
                System.out.println("Operation Cancelled. Returning to Menu...");
                return true;
            }
            else {
                System.out.print("Wrong Password! Try again: ");
                password = sc.nextLine();
            }
        }
        switch (changeWhat) {
            case 1:
                pharmacist.setName(newPharmacistName);
            case 2:
                pharmacist.setGender(newPharmacistGender);
            case 3:
                pharmacist.setAge(newPharmacistAge);
        }

        updateStaff((Staff) pharmacist);
        
        System.out.println("Pharmacist Successfully Updated! Returning to Menu...");
        return true;
    }
}
