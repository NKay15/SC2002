package hms.services;

import com.sun.jdi.PathSearchingVirtualMachine;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;

import hms.users.Patient;
import hms.users.Staff;
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
                if (dataList.length != 9) {
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
    public void addPatient(Patient patient) {
        try {
            BufferedWriter f_writer = new BufferedWriter(new FileWriter(temFileName));

            File myObj = new File(originalFileName);
            Scanner myReader = new Scanner(myObj);
            f_writer.write(myReader.nextLine());

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                if (dataList[0].equals(patient.getID())) {
                    f_writer.write(patient.getID() + "," + patient.getName() + "," + patient.getDob().year() + "-" + patient.getDob().month() + "-" + patient.getDob().day() + "," + patient.getGenderString() + "," + patient.getBloodTypeString() + "," + patient.getEmail() + "," + patient.getPhone() + "," + patient.getPassword());
                } else {
                    f_writer.write(data);
                }
            }
            myReader.close();

            Path source = Paths.get(temFileName);
            Path toDir = Paths.get(originalFileName);
            Files.move(source, toDir.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        }
        catch (Exception e) {
            System.out.println("An error occured");
        }
    }

    /**
     * Remove Patient by ID
     * @param ID
     */
    public void removePatientByID(String ID) {
        try {
            BufferedWriter f_writer = new BufferedWriter(new FileWriter(temFileName));

            File myObj = new File(originalFileName);
            Scanner myReader = new Scanner(myObj);
            f_writer.write(myReader.nextLine());

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                if (!dataList[0].equals(ID)) {
                    f_writer.write(data);
                }
            }
            myReader.close();

            Path source = Paths.get(temFileName);
            Path toDir = Paths.get(originalFileName);
            Files.move(source, toDir.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        }
        catch (Exception e) {
            System.out.println("An error occured");
        }
    }
}
