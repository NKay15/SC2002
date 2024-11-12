package hms.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import hms.pharmacy.Inventory;
import hms.users.*;

public class TextFileService {
    /**
     * Get Patient Data
     * @return List of Patients
     */
    public static ArrayList<Patient> getPatientData() {
        ArrayList<Patient> patientArray = new ArrayList<Patient>();
        
        try {
            File myObj = new File("HMS/src/data/Patient_List.txt");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // Remove header line

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                Date dateFormat = new Date(Integer.valueOf(dataList[2].substring(0, 4)), Integer.valueOf(dataList[2].substring(5, 7)), Integer.valueOf(dataList[2].substring(8,10)));

                int genderNo = 0;
                if (dataList[3].equals("Male")) {
                	genderNo = 1;
                } else if (dataList[3].equals("Female")) {
                	genderNo = 2;
                }

                int bloodTypeNo = 0;
                if (dataList[4].equals("A+")) {
                	bloodTypeNo = 1;
                }
                if (dataList[4].equals("A-")) {
                	bloodTypeNo = 2;
                }
                if (dataList[4].equals("B+")) {
                	bloodTypeNo = 3;
                }
                if (dataList[4].equals("B-")) {
                	bloodTypeNo = 4;
                } 
                if (dataList[4].equals("AB+")) {
                	bloodTypeNo = 5;
                }
                if (dataList[4].equals("AB-")) {
                	bloodTypeNo = 6;
                } 
                if (dataList[4].equals("O+")) {
                	bloodTypeNo = 7;
                } 
                if (dataList[4].equals("O-")) {
                	bloodTypeNo = 8;
                }

                Patient newPatient = null;
                if (dataList.length != 9) {
                    newPatient = new Patient(dataList[0], dataList[1], genderNo, dateFormat, Integer.valueOf(dataList[6]), dataList[5], bloodTypeNo, null);
                } else {
                    newPatient = new Patient(dataList[0], dataList[1], genderNo, dateFormat, Integer.valueOf(dataList[6]), dataList[5], bloodTypeNo, new Password(dataList[7]));
                }

                patientArray.add(newPatient);
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        
        return patientArray;
    }

    /**
     * Get Staff Data
     * @return List of Staff
     */
    public static ArrayList<Staff> getStaffData() {
        ArrayList<Staff> staffArray = new ArrayList<Staff>();
        
        try {
            File myObj = new File("HMS/src/data/Staff_List.txt");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // Remove header line

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                int roleNo = 0;
                if (dataList[2].equals("Doctor")) {
                	roleNo = 2;
                }
                if (dataList[2].equals("Pharmacist")) {
                	roleNo = 3;
                }
                if (dataList[2].equals("Administrator")) {
                	roleNo = 4;
                }

                int genderNo = 0;
                if (dataList[3].equals("Male")) {
                	genderNo = 1;
                } else if (dataList[3].equals("Female")) {
                	genderNo = 2;
                }

                Staff newStaff = null;
                if (dataList.length != 6) {
                    newStaff = new Staff(dataList[0], dataList[1], roleNo, genderNo, Integer.valueOf(dataList[4]), null);
                } else {
                    newStaff = new Staff(dataList[0], dataList[1], roleNo, genderNo, Integer.valueOf(dataList[4]), new Password(dataList[5]));
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
	 * Read medicine from file and generate initial inventory
	 * @return initial inventory
	 */
    public static Inventory getInventory() {
        try {
            Inventory setup = new Inventory();

            File myObj = new File("HMS/src/data/Medicine_List.txt");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // Remove header line

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                setup.addNewMedicine(dataList[0], Integer.valueOf(dataList[1]), Integer.valueOf(dataList[2]));
            }
            myReader.close();

            return setup;
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            return new Inventory();
        }
    }
}

