package hms.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import hms.users.Staff;
import hms.utils.InputValidation;
import hms.utils.Password;

public class StaffFileService extends InputValidation {
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
}
