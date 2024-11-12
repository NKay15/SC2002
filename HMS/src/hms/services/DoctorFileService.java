package hms.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import hms.GlobalData;
import hms.users.Administrator;
import hms.users.Doctor;
import hms.users.Staff;
import hms.utils.Password;
import hms.utils.Role;

public class DoctorFileService extends StaffFileService {
    /**
     * Get All Doctor Data
     * @return List of Doctors
     */
    public static ArrayList<Doctor> getAllDoctorData() {
        ArrayList<Doctor> doctorArray = new ArrayList<Doctor>();
        
        try {
            File myObj = new File("HMS/src/data/Staff_List.txt");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // Remove header line

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                if (dataList[2] != Role.DOCTOR.toString()) {
                    continue;
                }

                int genderNo = 0;
                if (dataList[3].equals("Male")) {
                	genderNo = 1;
                } else if (dataList[3].equals("Female")) {
                	genderNo = 2;
                }

                Doctor newStaff = null;
                if (dataList.length != 6) {
                    newStaff = new Doctor(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), null);
                } else {
                    newStaff = new Doctor(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), new Password(dataList[5]));
                }

                doctorArray.add(newStaff);
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        
        return doctorArray;
    }

    /**
     * Get Doctor by ID
     * @param ID
     * @return Doctor
     */
    public static Doctor getDoctorByID(String ID) {
        try {
            File myObj = new File("HMS/src/data/Staff_List.txt");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // Remove header line

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                if (dataList[2] != Role.DOCTOR.toString() || (!dataList[0].equals(ID))) {
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
                    return new Doctor(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), null);
                } else {
                    return new Doctor(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), new Password(dataList[5]));
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
     * Menu to Remove Doctor by ID (for Admins)
     * @param ID
     * @param adminUsing Administrator Using Remove Operation
     */
    public static boolean removeDoctorByIDMenu(String ID, Administrator adminUsing) {
        Scanner sc = GlobalData.getInstance().sc;
        Doctor doctor = getDoctorByID(ID);

        if (doctor == null) return false;

        System.out.println("\nPlease ensure that all fields below are correct before confirming:");
        System.out.println("Doctor ID: " + doctor.getID());
        System.out.println("Name: " + doctor.getName());
        System.out.println("Gender: " + doctor.getGenderString());
        System.out.println("Age: " + doctor.getAge());
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
        System.out.println("Doctor Successfully Removed! Returning to Menu...");
        return true;
    }

    /**
     * Update Doctor by ID
     * @param ID
     * @param adminUsing Administrator Using Update Operation
     */
    public static boolean updateDoctorByIDMenu(String ID, Administrator adminUsing) {
        Scanner sc = GlobalData.getInstance().sc;
        Doctor doctor = getDoctorByID(ID);

        /* If there is no doctor found */
        if (doctor == null) return false;

        String newDoctorID = doctor.getID();
        String newDoctorName = doctor.getName();
        int newDoctorGender = doctor.getGender();
        String newDoctorGenderString = doctor.getGenderString();
        int newDoctorAge = doctor.getAge();
        int changeWhat;

        /* Menu */
        System.out.println("\n-----Doctor Update Menu-----");
        System.out.println("Doctor ID: " + doctor.getID());
        System.out.println("Name: " + doctor.getName());
        System.out.println("Gender: " + doctor.getGenderString());
        System.out.println("Age: " + doctor.getAge());
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
                    System.out.println("Current Name: " + doctor.getName());
                    System.out.print("Enter New Name: ");
                    newDoctorName = sc.nextLine();
                    changeWhat = 1;
                    break;

                case "2":
                    System.out.println("Current Gender: " + doctor.getGenderString());
                    System.out.print("Enter New Gender (0: Unknown; 1: Male; 2: Female): ");
                    newDoctorGenderString = sc.next(); sc.nextLine();
                    while (true) {
                        switch (newDoctorGenderString) {
                            case "0":
                            case "Unknown":
                            case "unknown":
                                newDoctorGender = 0;
                                newDoctorGenderString = "Unknown";
                                break;
                            case "1":
                            case "Male":
                            case "male":
                                newDoctorGender = 1;
                                newDoctorGenderString = "Male";
                                break;
                            case "2":
                            case "Female":
                            case "female":
                                newDoctorGender = 2;
                                newDoctorGenderString = "Female";
                                break;
                            default:
                                System.out.print("Invalid choice! Try again: ");
                                newDoctorGenderString = sc.next(); sc.nextLine();
                                continue;
                        }
                        break;
                    }
                    changeWhat = 2;
                    break;

                case "3":
                    System.out.println("Current Age: " + doctor.getAge());
                    System.out.print("Enter New Age: ");
                    newDoctorAge = sc.nextInt(); sc.nextLine();
                    changeWhat = 3;
                    break;

                case "4":
                    System.out.println("Operation Cancelled. Returning to Menu...");
                    return true;

                default:
                    System.out.print("Invalid choice! Try again: ");
                    continue;
            }
            break;
        }

        System.out.println("\nPlease ensure that all fields below are correct before confirming:");
        System.out.println("Doctor ID: " + newDoctorID);
        System.out.println("Name: " + newDoctorName);
        System.out.println("Gender: " + newDoctorGenderString);
        System.out.println("Age: " + newDoctorAge);
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
                doctor.setName(newDoctorName);
                break;
            case 2:
                doctor.setGender(newDoctorGender);
                break;
            case 3:
                doctor.setAge(newDoctorAge);
                break;
        }

        updateStaff((Staff) doctor);

        System.out.println("Doctor Successfully Updated! Returning to Menu...");
        return true;
    }
}
