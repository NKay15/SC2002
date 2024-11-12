package hms.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;

import hms.GlobalData;
import hms.users.Administrator;
import hms.users.Patient;
import hms.utils.BloodType;
import hms.utils.Date;
import hms.utils.InputValidation;
import hms.utils.Password;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class PatientFileService extends InputValidation {
    private static final String originalFileName = "HMS/src/data/Patient_List.txt";
    private static final String temFileName = "HMS/src/data/tem_Patient_List.txt";

    /**
     * Get All Patient Data
     * @return List of Patients
     */
    public static ArrayList<Patient> getAllPatientData() {
        ArrayList<Patient> patientArray = new ArrayList<Patient>();
        
        try {
            File myObj = new File(originalFileName);
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

                BloodType bloodTypeNo = BloodType.UNKNOWN;
                if (dataList[4].equals("A+")) {
                	bloodTypeNo = BloodType.A_PLUS;
                }
                if (dataList[4].equals("A-")) {
                	bloodTypeNo = BloodType.A_MINUS;
                }
                if (dataList[4].equals("B+")) {
                	bloodTypeNo = BloodType.B_PLUS;
                }
                if (dataList[4].equals("B-")) {
                	bloodTypeNo = BloodType.B_MINUS;
                } 
                if (dataList[4].equals("AB+")) {
                	bloodTypeNo = BloodType.AB_PLUS;
                }
                if (dataList[4].equals("AB-")) {
                	bloodTypeNo = BloodType.AB_MINUS;
                } 
                if (dataList[4].equals("O+")) {
                	bloodTypeNo = BloodType.O_PLUS;
                } 
                if (dataList[4].equals("O-")) {
                	bloodTypeNo = BloodType.O_MINUS;
                }

                Patient newPatient = null;
                if (dataList.length != 8) {
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
     * Get Patient by ID
     * @param ID of patient
     * @return patient
     */
    public static Patient getPatientByID(String ID) {
        try {
            File myObj = new File(originalFileName);
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // Remove header line

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                if (!dataList[0].equals(ID)) {
                    continue;
                }

                Date dateFormat = new Date(Integer.valueOf(dataList[2].substring(0, 4)), Integer.valueOf(dataList[2].substring(5, 7)), Integer.valueOf(dataList[2].substring(8,10)));

                int genderNo = 0;
                if (dataList[3].equals("Male")) {
                	genderNo = 1;
                } else if (dataList[3].equals("Female")) {
                	genderNo = 2;
                }

                BloodType bloodTypeNo = BloodType.UNKNOWN;
                if (dataList[4].equals("A+")) {
                	bloodTypeNo = BloodType.A_PLUS;
                }
                if (dataList[4].equals("A-")) {
                	bloodTypeNo = BloodType.A_MINUS;
                }
                if (dataList[4].equals("B+")) {
                	bloodTypeNo = BloodType.B_PLUS;
                }
                if (dataList[4].equals("B-")) {
                	bloodTypeNo = BloodType.B_MINUS;
                } 
                if (dataList[4].equals("AB+")) {
                	bloodTypeNo = BloodType.AB_PLUS;
                }
                if (dataList[4].equals("AB-")) {
                	bloodTypeNo = BloodType.AB_MINUS;
                } 
                if (dataList[4].equals("O+")) {
                	bloodTypeNo = BloodType.O_PLUS;
                } 
                if (dataList[4].equals("O-")) {
                	bloodTypeNo = BloodType.O_MINUS;
                }

                myReader.close();
                if (dataList.length != 8) {
                    return new Patient(dataList[0], dataList[1], genderNo, dateFormat, Integer.valueOf(dataList[6]), dataList[5], bloodTypeNo, null);
                } else {
                    return new Patient(dataList[0], dataList[1], genderNo, dateFormat, Integer.valueOf(dataList[6]), dataList[5], bloodTypeNo, new Password(dataList[7]));
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
     * Get all patients by Name
     * @return list of patients
     */
    public static ArrayList<Patient> getPatientsNameSorted() {
        ArrayList<Patient> patientArray = getAllPatientData();

        // Sort by name in ascending order
        Collections.sort(patientArray, new Comparator<Patient>() {
            @Override
            public int compare(Patient p1, Patient p2) {
                return p1.getName().compareTo(p2.getName());
            }
        });

        return patientArray;
    }

    /**
     * Get all patients by ID
     * @return list of patients
     */
    public static ArrayList<Patient> getPatientsIDSorted() {
        ArrayList<Patient> patientArray = getAllPatientData();

        // Sort by name in ascending order
        Collections.sort(patientArray, new Comparator<Patient>() {
            @Override
            public int compare(Patient p1, Patient p2) {
                return p1.getID().compareTo(p2.getID());
            }
        });

        return patientArray;
    }

    /**
     * Get all patients by ID
     * @return list of patients
     */
    public static ArrayList<Patient> getPatientsGenderSorted() {
        ArrayList<Patient> patientArray = getAllPatientData();

        // Sort by name in ascending order
        Collections.sort(patientArray, new Comparator<Patient>() {
            @Override
            public int compare(Patient p1, Patient p2) {
                return Integer.compare(p1.getGender(), p2.getGender());
            }
        });

        return patientArray;
    }

    /**
     * Get all patients (By selection)
     * @param choice Choice of sorting
     * @return list of patients
     */
    public static ArrayList<Patient> getPatientsSorted(int choice) {
        switch (choice) {
            case 1:
                return getPatientsIDSorted();
            case 2:
                return getPatientsNameSorted();
            case 3:
                return getPatientsGenderSorted();
            default:
                return null;
        }
    }

    /**
     * Add a new Patient
     * @param Patient
     */
    public static void addPatient(Patient patient) {
        try {
            BufferedWriter f_writer = new BufferedWriter(new FileWriter(temFileName));

            File myObj = new File(originalFileName);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                f_writer.write(data);
                f_writer.newLine();
            }

            f_writer.write(patient.getID() + "," + patient.getName() + "," + patient.getDob().year() + "-" + patient.getDob().month() + "-" + patient.getDob().day() + "," + patient.getGenderString() + "," + patient.getBloodTypeString() + "," + patient.getEmail() + "," + patient.getPhone() + "," + patient.getPassword());
            myReader.close();
            f_writer.close();

            Path source = Paths.get(temFileName);
            Path toDir = Paths.get(originalFileName);
            Files.move(source, toDir.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        }
        catch (Exception e) {
            System.out.println("An error occured");
        }
    }

    /**
     * Update a Patient
     * @param Patient
     */
    public static void updatePatient(Patient patient) {
        try {
            BufferedWriter f_writer = new BufferedWriter(new FileWriter(temFileName));

            File myObj = new File(originalFileName);
            Scanner myReader = new Scanner(myObj);
            f_writer.write(myReader.nextLine());
            f_writer.newLine();

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                if (dataList[0].equals(patient.getID())) {
                    String dob = "";
                    if (String.valueOf(patient.getDob().day()).length() == 1) {
                        dob += "0";
                    }
                    dob += patient.getDob().day() + "-";
                    if (String.valueOf(patient.getDob().month()).length() == 1) {
                        dob += "0";
                    }
                    dob += patient.getDob().month() + "-";
                    dob += patient.getDob().year();
                    f_writer.write(patient.getID() + "," + patient.getName() + "," + dob + "," + patient.getGenderString() + "," + patient.getBloodTypeString() + "," + patient.getEmail() + "," + patient.getPhone() + "," + patient.getPassword().getPassword());
                } else {
                    f_writer.write(data);
                }
                f_writer.newLine();
            }
            myReader.close();
            f_writer.close();

            Path source = Paths.get(temFileName);
            Path toDir = Paths.get(originalFileName);
            Files.move(source, toDir, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Remove Patient by ID
     * @param ID
     */
    public static void removePatientByID(String ID) {
        try {
            BufferedWriter f_writer = new BufferedWriter(new FileWriter(temFileName));

            File myObj = new File(originalFileName);
            Scanner myReader = new Scanner(myObj);
            f_writer.write(myReader.nextLine());
            f_writer.newLine();

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                if (!dataList[0].equals(ID)) {
                    f_writer.write(data);
                    f_writer.newLine();
                }
            }
            myReader.close();
            f_writer.close();

            Path source = Paths.get(temFileName);
            Path toDir = Paths.get(originalFileName);
            Files.move(source, toDir, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (Exception e) {
            System.out.println("An error occured");
        }
    }

    /**
     * Menu to Remove Patient by ID (for Admins)
     * @param ID
     * @param adminUsing Administrator Using Remove Operation
     */
    public boolean removePatientByIDMenu(String ID, Administrator adminUsing) {
        Scanner sc = GlobalData.getInstance().sc;
        Patient patient = getPatientByID(ID);

        if(patient == null) return false;

        System.out.println("\nPlease ensure that all fields below are correct before confirming:");
        System.out.println("Patient ID: " + patient.getID());
        System.out.println("Name: " + patient.getName());
        System.out.println("Gender: " + patient.getGenderString());
        System.out.println("DOB: " + patient.getDob().day() + "/" + patient.getDob().month() + "/" + patient.getDob().year());
        System.out.println("HP: " + patient.getPhone());
        System.out.println("Email: " + patient.getEmail());
        System.out.println("Blood Type: " + patient.getBloodType());
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
        removePatientByID(ID);
        System.out.println("Patient Successfully Removed! Returning to Menu...");
        return true;
    }

    /**
     * Update Patient by ID
     * @param ID
     * @param adminUsing Administrator Using Update Operation
     */
    public boolean updatePatientByIDMenu(String ID, Administrator adminUsing) {
        Scanner sc = GlobalData.getInstance().sc;
        Patient patient = getPatientByID(ID);

        /* If there is no patient found */
        if (patient == null) return false;

        String newPatientID = patient.getID();
        String newPatientName = patient.getName();
        int newPatientGender = patient.getGender();
        String newPatientGenderString = patient.getGenderString();
        int newPatientDay = patient.getDob().day();
        int newPatientMonth = patient.getDob().month();
        int newPatientYear = patient.getDob().year();
        int newPatientHP = patient.getPhone();
        String newPatientEmail = patient.getEmail();
        String newPatientBloodTypeString = patient.getBloodTypeString();
        BloodType newPatientBloodType = patient.getBloodType();
        int changeWhat;

        /* Menu */
        System.out.println("\n-----Patient Update Menu-----");
        System.out.println("Patient ID: " + patient.getID());
        System.out.println("Name: " + patient.getName());
        System.out.println("Gender: " + patient.getGenderString());
        System.out.println("DOB: " + newPatientDay + "/" + newPatientMonth + "/" + newPatientYear);
        System.out.println("HP: " + newPatientHP);
        System.out.println("Email: " + newPatientEmail);
        System.out.println("Blood Type: " + newPatientBloodTypeString);
        System.out.println("\n1. Update Name");
        System.out.println("2. Update Gender");
        System.out.println("3. Update DOB");
        System.out.println("4. Update HP");
        System.out.println("5. Update Email");
        System.out.println("6. Update Blood Type");
        System.out.println("7. Cancel");
        System.out.println("----------------------------");
        System.out.print("Enter your choice: ");
        String choice;

        while (true) {
            choice = sc.next(); sc.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Current Name: " + patient.getName());
                    System.out.print("Enter New Name: ");
                    newPatientName = sc.nextLine();
                    changeWhat = 1;
                    break;

                case "2":
                    System.out.println("Current Gender: " + patient.getGenderString());
                    System.out.print("Enter New Gender (0: Unknown; 1: Male; 2: Female): ");
                    newPatientGenderString = sc.next(); sc.nextLine();
                    while (true){
                        switch (newPatientGenderString) {
                            case "0":
                            case "Unknown":
                            case "unknown":
                                newPatientGender = 0;
                                newPatientGenderString = "Unknown";
                                break;
                            case "1":
                            case "Male":
                            case "male":
                                newPatientGender = 1;
                                newPatientGenderString = "Male";
                                break;
                            case "2":
                            case "Female":
                            case "female":
                                newPatientGender = 2;
                                newPatientGenderString = "Female";
                                break;
                            default:
                                System.out.print("Invalid choice! Try again: ");
                                newPatientGenderString = sc.next(); sc.nextLine();
                                continue;
                        }
                        break;
                    }
                    changeWhat = 2;
                    break;

                case "3":
                    System.out.println("Current Date Of Birth: " + newPatientDay + "/" + newPatientMonth + "/" + newPatientYear);
                    System.out.print("Enter New Date of Birth (in DDMMYYYY): ");
                    String newPatientDOBString;
                    while (true) {
                        if (sc.hasNextInt()) {
                            newPatientDOBString = sc.nextLine();
                            if (newPatientDOBString.length() == 8) {
                                newPatientDay = Integer.parseInt(newPatientDOBString.substring(0, 2));
                                newPatientMonth = Integer.parseInt(newPatientDOBString.substring(2, 4));
                                newPatientYear = Integer.parseInt(newPatientDOBString.substring(4, 8));
                                if (newPatientDay > 0 && newPatientDay < 32 && newPatientMonth > 0
                                        && newPatientMonth < 13 && newPatientYear > 1900 && newPatientYear < 2025) {
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
                    changeWhat = 3;
                    break;

                case "4":
                    System.out.println("Current HP: " + patient.getPhone());
                    System.out.print("Enter New HP: ");
                    while (true) {
                        try {
                            newPatientHP = sc.nextInt();
                            sc.nextLine();
                            if (String.valueOf(newPatientHP).length() == 8){
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
                    changeWhat = 4;
                    break;

                case "5":
                    System.out.println("Current Email: " + newPatientEmail);
                    System.out.print("Enter New Email: ");
                    newPatientEmail = sc.next(); sc.nextLine();
                    changeWhat = 5;
                    break;

                case "6":
                    System.out.println("Current Blood Type: " + newPatientBloodTypeString);
                    System.out.println("----List of Blood Types----");
                    System.out.println("0: Unknown\n1: A+\n2: A-\n3: B+\n4: B-\n" +
                            "5: AB+\n6: AB-\n7: O+\n8: O-");
                    System.out.println("---------------------------");
                    System.out.print("Enter New Blood Type (Digit Only): ");
                    boolean bloodTypeFound;
                    do {
                        try {
                            newPatientBloodType = patient.intToBloodType(sc.nextInt());
                            sc.nextLine();
                            switch (newPatientBloodType) {
                                case UNKNOWN:
                                    newPatientBloodTypeString = BloodType.UNKNOWN.toString();
                                    bloodTypeFound = true;
                                    break;
                                case A_PLUS:
                                    newPatientBloodTypeString = BloodType.A_PLUS.toString();
                                    bloodTypeFound = true;
                                    break;
                                case A_MINUS:
                                    newPatientBloodTypeString = BloodType.A_MINUS.toString();
                                    bloodTypeFound = true;
                                    break;
                                case B_PLUS:
                                    newPatientBloodTypeString = BloodType.B_PLUS.toString();
                                    bloodTypeFound = true;
                                    break;
                                case B_MINUS:
                                    newPatientBloodTypeString = BloodType.B_MINUS.toString();
                                    bloodTypeFound = true;
                                    break;
                                case AB_PLUS:
                                    newPatientBloodTypeString = BloodType.AB_PLUS.toString();
                                    bloodTypeFound = true;
                                    break;
                                case AB_MINUS:
                                    newPatientBloodTypeString = BloodType.AB_MINUS.toString();
                                    bloodTypeFound = true;
                                    break;
                                case O_PLUS:
                                    newPatientBloodTypeString = BloodType.O_PLUS.toString();
                                    bloodTypeFound = true;
                                    break;
                                case O_MINUS:
                                    newPatientBloodTypeString = BloodType.O_MINUS.toString();
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
                    changeWhat = 6;
                    break;

                case "7":
                    System.out.println("Operation Cancelled. Returning to Menu...");
                    return true;

                default:
                    System.out.print("Invalid Choice! Enter your choice: ");
                    continue;
            }
            break;
        }

        System.out.println("\nPlease ensure that all fields below are correct before confirming:");
        System.out.println("Administrator ID: " + newPatientID);
        System.out.println("Name: " + newPatientName);
        System.out.println("Gender: " + newPatientGenderString);
        System.out.println("DOB: " + newPatientDay + "/" + newPatientMonth + "/" + newPatientYear);
        System.out.println("HP: " + newPatientHP);
        System.out.println("Email: " + newPatientEmail);
        System.out.println("Blood Type: " + newPatientBloodTypeString);
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
                patient.setName(newPatientName);
                break;
            case 2:
                patient.setGender(newPatientGender);
                break;
            case 3:
                patient.setDob(newPatientDay, newPatientMonth, newPatientYear);
                break;
            case 4:
                patient.setPhone(newPatientHP);
                break;
            case 5:
                patient.setEmail(newPatientEmail);
                break;
            case 6:
                patient.setBloodType(newPatientBloodType);
                break;
        }

        updatePatient(patient);

        System.out.println("Administrator Successfully Updated! Returning to Menu...");
        return true;
    }
}
