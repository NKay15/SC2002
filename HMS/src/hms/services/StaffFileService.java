package hms.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import hms.users.Administrator;
import hms.users.Doctor;
import hms.users.Pharmacist;
import hms.users.Staff;
import hms.utils.InputValidation;
import hms.utils.Password;
import hms.utils.Role;
import javax.management.relation.Role;

public class StaffFileService extends InputValidation {
    /**
     * Get All Staff Data
     * @return List of Staff
     */
    public static ArrayList<Staff> getAllStaffData() {
        ArrayList<Staff> staffArray = new ArrayList<Staff>();
        
        try {
            File myObj = new File("HMS/src/data/Staff_List.txt");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // Remove header line

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                Role role = Role.UNKNOWN;
                if (dataList[2].equals("Doctor")) {
                	role = Role.DOCTOR;
                }
                if (dataList[2].equals("Pharmacist")) {
                	role = Role.PHARMACIST;
                }
                if (dataList[2].equals("Administrator")) {
                	role = Role.ADMINISTRATOR;
                }

                int genderNo = 0;
                if (dataList[3].equals("Male")) {
                	genderNo = 1;
                } else if (dataList[3].equals("Female")) {
                	genderNo = 2;
                }

                Staff newStaff = null;
                if (dataList.length != 6) {
                    newStaff = new Staff(dataList[0], dataList[1], role, genderNo, Integer.valueOf(dataList[4]), null);
                } else {
                    newStaff = new Staff(dataList[0], dataList[1], role, genderNo, Integer.valueOf(dataList[4]), new Password(dataList[5]));
                }

                staffArray.add(newStaff);
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        
        return staffArray;
    }

    /**
     * Get All Staff Data
     * @param Role
     * @return List of Staff
     */
    public static ArrayList<Staff> getRoleStaffData(Role role) {
        ArrayList<Staff> staffArray = new ArrayList<Staff>();
        
        try {
            File myObj = new File("HMS/src/data/Staff_List.txt");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // Remove header line

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                if (dataList[2] != role.toString()) {
                    break;
                }

                int genderNo = 0;
                if (dataList[3].equals("Male")) {
                	genderNo = 1;
                } else if (dataList[3].equals("Female")) {
                	genderNo = 2;
                }

                Staff newStaff = null;
                if (dataList.length != 6) {
                    newStaff = new Staff(dataList[0], dataList[1], role, genderNo, Integer.valueOf(dataList[4]), null);
                } else {
                    newStaff = new Staff(dataList[0], dataList[1], role, genderNo, Integer.valueOf(dataList[4]), new Password(dataList[5]));
                }

                staffArray.add(newStaff);
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        
        return staffArray;
    }

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
     * Get All Administrators Data
     * @return List of Administrators
     */
    public static ArrayList<Administrator> getAllAdministratorsData() {
        ArrayList<Administrator> adminstratorArray = new ArrayList<Administrator>();
        
        try {
            File myObj = new File("HMS/src/data/Staff_List.txt");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // Remove header line

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                if (dataList[2] != Role.ADMINISTRATOR.toString()) {
                    continue;
                }

                int genderNo = 0;
                if (dataList[3].equals("Male")) {
                	genderNo = 1;
                } else if (dataList[3].equals("Female")) {
                	genderNo = 2;
                }

                Administrator newStaff = null;
                if (dataList.length != 6) {
                    newStaff = new Administrator(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), null);
                } else {
                    newStaff = new Administrator(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), new Password(dataList[5]));
                }

                adminstratorArray.add(newStaff);
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        
        return adminstratorArray;
    }
}
