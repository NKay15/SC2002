package hms.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import hms.users.Patient;
import hms.utils.InputValidation;

public class MedicalRecordFileService extends InputValidation {
    /**
     * Read medical history and load it in
     */
    public static void loadMedicalHistory() {
        try {
            File myObj = new File("HMS/src/data/Medical_History_List.txt");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // Remove header line

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                Patient patient = PatientFileService.getPatientByID(dataList[0]);
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
}
