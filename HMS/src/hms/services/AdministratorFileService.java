package hms.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import hms.users.Administrator;
import hms.utils.Password;
import hms.utils.Role;

public class AdministratorFileService extends StaffFileService {
    /**
     * Get All Administrators Data
     * @return List of Administrators
     */
    public static ArrayList<Administrator> getAllAdministratorsData() {
        ArrayList<Administrator> adminstratorArray = new ArrayList<Administrator>();
        
        try {
            File myObj = new File("HMS/src/data/Staff_List.txt");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // Remove header line

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                if (dataList[2] != Role.ADMINISTRATOR.toString()) {
                    continue;
                }

                int genderNo = 0;
                if (dataList[3].equals("Male")) {
                	genderNo = 1;
                } else if (dataList[3].equals("Female")) {
                	genderNo = 2;
                }

                Administrator newStaff = null;
                if (dataList.length != 6) {
                    newStaff = new Administrator(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), null);
                } else {
                    newStaff = new Administrator(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), new Password(dataList[5]));
                }

                adminstratorArray.add(newStaff);
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        
        return adminstratorArray;
    }
}
