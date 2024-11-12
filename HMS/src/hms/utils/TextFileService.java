package hms.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import hms.pharmacy.Inventory;
import hms.users.*;
import hms.GlobalData;
import hms.UserList;

public class TextFileService {
    private static boolean checkString(String check){
        for(int i = 0; i < check.length(); i++) {
            if(check.charAt(i) == ',') return false;
        }

        return true;
    }

    /**
     * Use this input function to ensure no comma
     * @return checked input
     */
    public static String next(){
        Scanner sc = GlobalData.getInstance().sc;
        String check = sc.next();

        while(!checkString(check)) {
            System.out.print("This input is not allow to have ','. Enter again :");
            check = sc.next();
        }

        return check;
    } 

    /**
     * Use this input function to ensure no comma
     * @return checked input
     */
    public static String nextLine(){
        Scanner sc = GlobalData.getInstance().sc;
        String check = sc.nextLine();

        while(!checkString(check)) {
            System.out.print("This input is not allow to have ','. Enter again :");
            check = sc.nextLine();
        }

        return check;
    } 

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
     * Read medical history and load it in
     */
    public static void loadMedicalHistory(UserList userList) {
        try {
            File myObj = new File("HMS/src/data/Medical_History_List.txt");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // Remove header line

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                Patient patient = userList.getPatientByID(dataList[0]);
                if(patient != null) {
                    patient.addMedicalRecord(dataList[1]);
                }
            }

            myReader.close();
        }

        catch(FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
    }

    public static void writeMedicalHistory(ArrayList<Patient> patients) {
        try {
            FileWriter fw = new FileWriter("HMS/src/data/Medical_History_List.txt");
            fw.write("Patient ID,Medical Record\n");
            for(Patient patient : patients) {
                for(String s : patient.getMedicalHistory()) {
                    fw.write(patient.getID() + "," + s +"\n");
                }
            }
            fw.close();
        }

        catch (Exception e){
            System.out.println("An error occurred. Cannot write inventory");
        }
    }

    /**
	 * Read medicine from file and generate initial inventory
	 * @return initial inventory
	 */
    public static Inventory getInventory() {
        Inventory setup = new Inventory();

        try {
            File myObj = new File("HMS/src/data/Medicine_List.txt");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // Remove header line

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                setup.addNewMedicine(dataList[0], Integer.valueOf(dataList[1]), Integer.valueOf(dataList[2]));
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            return new Inventory();
        }

        try {
            File myObj = new File("HMS/src/data/Restock_List.txt");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // Remove header line

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                setup.addRequest(dataList[0], Integer.valueOf(dataList[1]));
            }

            myReader.close();

            return setup;
        }

        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            return setup;
        }

    }

    /**
     * write inventory
     * @param inventory inventory to be written
     */
    public static void writeInventory(Inventory inventory) {
        try {
            FileWriter fw = new FileWriter("HMS/src/data/Medicine_List.txt");
            fw.write("Medicine Name,Initial Stock,Low Stock Level Alert\n");
            for(int i = 0; i < inventory.getSize(); i++) {
                fw.write(inventory.getName(i) + "," + inventory.getAmount(i) + "," + inventory.getLowLevel(i) +"\n");
            }
            fw.close();
            
        }
        catch (Exception e){
            System.out.println("An error occurred. Cannot write inventory");
        }

        try {
            FileWriter fw = new FileWriter("HMS/src/data/Restock_List.txt");
            fw.write("Medicine Name,Request Amount\n");
            for(int i = 0; i < inventory.getRequestSize(); i++) {
                fw.write(inventory.getRequestName(i) + "," + inventory.getRequestAmount(i) +"\n");
            }
            fw.close();
        }
        catch (Exception e){
            System.out.println("An error occurred. Cannot write restock");
        }
    }
}

