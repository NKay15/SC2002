package hms.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import hms.GlobalData;
import hms.users.Administrator;
import hms.users.Staff;
import hms.utils.Password;
import hms.utils.Role;

public class AdministratorFileService extends StaffFileService {
    /**
     * Get All Administrators Data
     * @return List of Administrators
     */
    public static ArrayList<Administrator> getAllAdministratorsData() {
        ArrayList<Administrator> administratorArray = new ArrayList<Administrator>();
        
        try {
            File myObj = new File("HMS/src/data/Staff_List.txt");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // Remove header line

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                if (dataList[2].equals(Role.ADMINISTRATOR.toString())) {
                    continue;
                }

                int genderNo = 0;
                if (dataList[3].equals("Male")) {
                	genderNo = 1;
                } else if (dataList[3].equals("Female")) {
                	genderNo = 2;
                }

                Administrator newStaff = (Administrator)GlobalData.getInstance().userInstances.getInstance(dataList[0]);
                if(newStaff != null) {
                    if (dataList.length != 6) {
                        newStaff.update(dataList[0], dataList[1], Role.ADMINISTRATOR, genderNo, Integer.valueOf(dataList[4]), null);
                    } else {
                        newStaff.update(dataList[0], dataList[1], Role.ADMINISTRATOR, genderNo, Integer.valueOf(dataList[4]), new Password(dataList[5]));
                    }
                }
                else {
                    if (dataList.length != 6) {
                        newStaff = new Administrator(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), null);
                    } else {
                        newStaff = new Administrator(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), new Password(dataList[5]));
                    }
                    GlobalData.getInstance().userInstances.add(newStaff);
                }

                administratorArray.add(newStaff);
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        
        return administratorArray;
    }

    /**
     * Get Administrator by ID
     * @param ID
     * @return Administrator
     */
    public static Administrator getAdministratorByID(String ID) {
        try {
            File myObj = new File("HMS/src/data/Staff_List.txt");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // Remove header line

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                if (!dataList[2].equals(Role.ADMINISTRATOR.toString()) && (!dataList[0].equals(ID))) {
                    continue;
                }

                int genderNo = 0;
                if (dataList[3].equals("Male")) {
                	genderNo = 1;
                } else if (dataList[3].equals("Female")) {
                	genderNo = 2;
                }

                myReader.close();

                Administrator newStaff =  (Administrator)GlobalData.getInstance().userInstances.getInstance(dataList[0]);
                if(newStaff != null) {
                    if (dataList.length != 6) {
                        newStaff.update(dataList[0], dataList[1], Role.ADMINISTRATOR, genderNo, Integer.valueOf(dataList[4]), null);
                    } else {
                        newStaff.update(dataList[0], dataList[1], Role.ADMINISTRATOR, genderNo, Integer.valueOf(dataList[4]), new Password(dataList[5]));
                    }
                }
                else {
                    if (dataList.length != 6) {
                        newStaff = new Administrator(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), null);
                    } else {
                        newStaff = new Administrator(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), new Password(dataList[5]));
                    }

                    GlobalData.getInstance().userInstances.add(newStaff);
                }

                return newStaff;
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
    public static boolean removeAdministratorByIDMenu(String ID, Administrator adminUsing) {
        if (adminUsing.getID().equals(ID)) return false;
        Scanner sc = GlobalData.getInstance().sc;
        Administrator administrator = getAdministratorByID(ID);

        if (administrator == null) return false;

        System.out.println("\nPlease ensure that all fields below are correct before confirming:");
        System.out.println("Administrator ID: " + administrator.getID());
        System.out.println("Name: " + administrator.getName());
        System.out.println("Gender: " + administrator.getGenderString());
        System.out.println("Age: " + administrator.getAge());
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
        removeStaffByID(ID);
        System.out.println("Administrator Successfully Removed! Returning to Menu...");
        return true;
    }

    /**
     * Update Administrator by ID
     * @param ID
     * @param adminUsing Administrator Using Update Operation
     */
    public static boolean updateAdministratorByIDMenu(String ID, Administrator adminUsing) {
        Scanner sc = GlobalData.getInstance().sc;
        Administrator administrator = getAdministratorByID(ID);

        /* If there is no administrator found */
        if (administrator == null) return false;

        String newAdministratorID = administrator.getID();
        String newAdministratorName = administrator.getName();
        int newAdministratorGender = administrator.getGender();
        String newAdministratorGenderString = administrator.getGenderString();
        int newAdministratorAge = administrator.getAge();
        int changeWhat;

        /* Menu */
        System.out.println("\n-----Administrator Update Menu-----");
        System.out.println("Administrator ID: " + administrator.getID());
        System.out.println("Name: " + administrator.getName());
        System.out.println("Gender: " + administrator.getGenderString());
        System.out.println("Age: " + administrator.getAge());
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
                    System.out.println("Current Name: " + administrator.getName());
                    System.out.print("Enter New Name: ");
                    newAdministratorName = sc.nextLine();
                    changeWhat = 1;
                    break;

                case "2":
                    System.out.println("Current Gender: " + administrator.getGenderString());
                    System.out.print("Enter New Gender (0: Unknown; 1: Male; 2: Female): ");
                    newAdministratorGenderString = sc.next(); sc.nextLine();
                    while (true){
                        switch (newAdministratorGenderString) {
                            case "0":
                            case "Unknown":
                            case "unknown":
                                newAdministratorGender = 0;
                                newAdministratorGenderString = "Unknown";
                                break;
                            case "1":
                            case "Male":
                            case "male":
                                newAdministratorGender = 1;
                                newAdministratorGenderString = "Male";
                                break;
                            case "2":
                            case "Female":
                            case "female":
                                newAdministratorGender = 2;
                                newAdministratorGenderString = "Female";
                                break;
                            default:
                                System.out.print("Invalid choice! Try again: ");
                                newAdministratorGenderString = sc.next(); sc.nextLine();
                                continue;
                        }
                        break;
                    }
                    changeWhat = 2;
                    break;

                case "3":
                    System.out.println("Current Age: " + administrator.getAge());
                    System.out.print("Enter New Age: ");
                    newAdministratorAge = sc.nextInt(); sc.nextLine();
                    changeWhat = 3;
                    break;

                case "4":
                    System.out.println("Operation Cancelled. Returning to Menu...");
                    return true;

                default:
                    System.out.print("Invalid Choice! Enter your choice: ");
                    continue;
            }
            break;
        }

        System.out.println("\nPlease ensure that all fields below are correct before confirming:");
        System.out.println("Administrator ID: " + newAdministratorID);
        System.out.println("Name: " + newAdministratorName);
        System.out.println("Gender: " + newAdministratorGenderString);
        System.out.println("Age: " + newAdministratorAge);
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
        switch (changeWhat){
            case 1:
                administrator.setName(newAdministratorName);
                break;
            case 2:
                administrator.setGender(newAdministratorGender);
                break;
            case 3:
                administrator.setAge(newAdministratorAge);
                break;
        }

        updateStaff((Staff) administrator);

        System.out.println("Administrator Successfully Updated! Returning to Menu...");
        return true;
    }
}
