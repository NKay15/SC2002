package hms.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

import hms.users.*;

public class SercureFileService {
    public static void load2FA() {
        try {
            File myObj = new File("HMS/src/data/Secure_List.txt");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // Remove header line

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                User user = null;
                user = PatientFileService.getPatientByID(dataList[0]);
                if(user == null) user = StaffFileService.getStaffByID(dataList[0]);
                if(user != null) user.getPassword().set2FA(dataList[1]);

            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred. Location SercureFileService");
        }
    }

    public static void write2FA() {
        try {
            FileWriter fw = new FileWriter("HMS/src/data/Secure_List.txt");
            fw.write("User ID,Key\n");

            for(Patient patient : PatientFileService.getAllPatientData()) {
                if(patient.getPassword().has2FA()) fw.write(patient.getID() + "," + patient.getPassword().get2FAKey() + "\n");
            }

            for(Staff staff : StaffFileService.getAllStaffData()) {
                if(staff.getPassword().has2FA()) fw.write(staff.getID() + "," + staff.getPassword().get2FAKey() + "\n");
            }
            
            fw.close();
        }
        catch (Exception e){
            System.out.println("An error occurred. Location SercureFileService");
        }
    }
}
