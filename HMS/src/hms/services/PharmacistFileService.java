package hms.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import hms.users.Pharmacist;
import hms.utils.Password;
import hms.utils.Role;

public class PharmacistFileService extends StaffFileService {
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
     * Get Pharmacist by ID
     * @param ID
     * @return Pharmacist
     */
    public static Pharmacist getPharmacistByID(String ID) {
        try {
            File myObj = new File("HMS/src/data/Staff_List.txt");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // Remove header line

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                if (dataList[2] != Role.PHARMACIST.toString() && (!dataList[0].equals(ID))) {
                    continue;
                }

                int genderNo = 0;
                if (dataList[3].equals("Male")) {
                	genderNo = 1;
                } else if (dataList[3].equals("Female")) {
                	genderNo = 2;
                }

                myReader.close();
                if (dataList.length != 6) {
                    return new Pharmacist(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), null);
                } else {
                    return new Pharmacist(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), new Password(dataList[5]));
                }
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        
        return null;
    }
}
