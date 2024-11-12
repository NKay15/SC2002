package hms.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import hms.users.Patient;
import hms.utils.Date;
import hms.utils.InputValidation;
import hms.utils.Password;

public class PatientFileService extends InputValidation {
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
}
