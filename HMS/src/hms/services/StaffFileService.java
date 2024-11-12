package hms.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import hms.users.Staff;
import hms.utils.Password;
import hms.utils.Role;

public class StaffFileService extends UserFileService {
    /**
     * Get All Staff Data
     * @return List of Staff
     */
    public static ArrayList<Staff> getAllStaffData() {
        ArrayList<Staff> staffArray = new ArrayList<Staff>();
        
        try {
            File myObj = new File("HMS/src/data/Staff_List.txt");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // Remove header line

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                Role role = Role.UNKNOWN;
                if (dataList[2].equals("Doctor")) {
                	role = Role.DOCTOR;
                }
                if (dataList[2].equals("Pharmacist")) {
                	role = Role.PHARMACIST;
                }
                if (dataList[2].equals("Administrator")) {
                	role = Role.ADMINISTRATOR;
                }

                int genderNo = 0;
                if (dataList[3].equals("Male")) {
                	genderNo = 1;
                } else if (dataList[3].equals("Female")) {
                	genderNo = 2;
                }

                Staff newStaff = null;
                if (dataList.length != 6) {
                    newStaff = new Staff(dataList[0], dataList[1], role, genderNo, Integer.valueOf(dataList[4]), null);
                } else {
                    newStaff = new Staff(dataList[0], dataList[1], role, genderNo, Integer.valueOf(dataList[4]), new Password(dataList[5]));
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

    /**
     * Get All Staff Data of only a role
     * @param Role
     * @return List of Staff
     */
    public static ArrayList<Staff> getRoleStaffData(Role role) {
        ArrayList<Staff> staffArray = new ArrayList<Staff>();
        
        try {
            File myObj = new File("HMS/src/data/Staff_List.txt");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // Remove header line

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                if (dataList[2] != role.toString()) {
                    break;
                }

                int genderNo = 0;
                if (dataList[3].equals("Male")) {
                	genderNo = 1;
                } else if (dataList[3].equals("Female")) {
                	genderNo = 2;
                }

                Staff newStaff = null;
                if (dataList.length != 6) {
                    newStaff = new Staff(dataList[0], dataList[1], role, genderNo, Integer.valueOf(dataList[4]), null);
                } else {
                    newStaff = new Staff(dataList[0], dataList[1], role, genderNo, Integer.valueOf(dataList[4]), new Password(dataList[5]));
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

    /**
     * Get all staff by Name
     * @return list of staff
     */
    public static ArrayList<Staff> getStaffNameSorted() {
        ArrayList<Staff> staffArray = getAllStaffData();

        // Sort by name in ascending order
        Collections.sort(staffArray, new Comparator<Staff>() {
            @Override
            public int compare(Staff p1, Staff p2) {
                return p1.getName().compareTo(p2.getName());
            }
        });

        return staffArray;
    }

    /**
     * Get all staff by Role
     * @return list of staff
     */
    public static ArrayList<Staff> getStaffRoleSorted() {
        ArrayList<Staff> staffArray = getAllStaffData();

        // Sort by name in ascending order
        Collections.sort(staffArray, new Comparator<Staff>() {
            @Override
            public int compare(Staff p1, Staff p2) {
                return p1.getRole().compareTo(p2.getRole());
            }
        });

        return staffArray;
    }

    /**
     * Get all staff by ID
     * @return list of staff
     */
    public static ArrayList<Staff> getStaffIDSorted() {
        ArrayList<Staff> staffArray = getAllStaffData();

        // Sort by name in ascending order
        Collections.sort(staffArray, new Comparator<Staff>() {
            @Override
            public int compare(Staff p1, Staff p2) {
                return p1.getID().compareTo(p2.getID());
            }
        });

        return staffArray;
    }

    /**
     * Get all staff by gender
     * @return list of users
     */
    public static ArrayList<Staff> getStaffGenderSorted() {
        ArrayList<Staff> staffArray = getAllStaffData();

        // Sort by name in ascending order
        Collections.sort(staffArray, new Comparator<Staff>() {
            @Override
            public int compare(Staff p1, Staff p2) {
                return Integer.compare(p1.getGender(), p2.getGender());
            }
        });

        return staffArray;
    }

    /**
     * Get all staff by age
     * @return list of users
     */
    public static ArrayList<Staff> getStaffAgeSorted() {
        ArrayList<Staff> userArray = getAllStaffData();

        // Sort by name in ascending order
        Collections.sort(userArray, new Comparator<Staff>() {
            @Override
            public int compare(Staff p1, Staff p2) {
                if (p1 instanceof Staff && p2 instanceof Staff) {
                    return Integer.compare(p1.getAge(), p2.getAge());
                }
                else return 0;
            }
        });

        return userArray;
    }

    /**
     * Get all users (By selection)
     * @param choice Choice of sorting
     * @return list of users
     */
    public static ArrayList<Staff> getStaffSorted(int choice) {
        switch (choice) {
            case 1:
                return getStaffRoleSorted();
            case 2:
                return getStaffIDSorted();
            case 3:
                return getStaffNameSorted();
            case 4:
                return getStaffGenderSorted();
            case 5:
                return getStaffAgeSorted();
            default:
                return null;
        }
    }
}
