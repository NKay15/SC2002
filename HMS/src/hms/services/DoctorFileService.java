package hms.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import hms.users.Doctor;
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
}
