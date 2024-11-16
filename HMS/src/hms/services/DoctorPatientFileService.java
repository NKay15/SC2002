package hms.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

import hms.users.Doctor;
import hms.users.Patient;

public class DoctorPatientFileService {
    public static void loadPatientList() {
        try {
            File myObj = new File("HMS/src/data/Doctor_Patients_List.txt");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // Remove header line

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                DoctorFileService.getDoctorByID(dataList[0]).addPatient(PatientFileService.getPatientByID(dataList[1]));

            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred. Location DoctorPatientFileService");
        }
    }

    public static void writePatientList() {
        try {
            FileWriter fw = new FileWriter("HMS/src/data/Doctor_Patients_List.txt");
            fw.write("Doctor,Patient\n");

            for(Doctor doctor : DoctorFileService.getAllDoctorData()) {
                for(Patient patient : doctor.getPatientList()) {
                    fw.write(doctor.getID() + "," + patient.getID() + "\n");
                }
            }
            
            fw.close();
        }
        catch (Exception e){
            System.out.println("An error occurred. Location DoctorPatientFileService");
        }
    }
}
