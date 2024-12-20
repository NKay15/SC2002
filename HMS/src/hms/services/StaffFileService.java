package hms.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

import hms.GlobalData;
import hms.users.*;
import hms.utils.InputValidation;
import hms.utils.Password;
import hms.utils.Role;
import java.io.BufferedWriter;

public class StaffFileService extends UserFileService {
    private static final String originalFileName = "HMS/src/data/Staff_List.txt";
    private static final String temFileName = "HMS/src/data/tem_Staff_List.txt";
    
    /**
     * Get All Staff Data
     * @return List of Staff
     */
    public static ArrayList<Staff> getAllStaffData() {
        ArrayList<Staff> staffArray = new ArrayList<Staff>();
        
        try {
            File myObj = new File(originalFileName);
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

                Staff newStaff = (Staff)GlobalData.getInstance().userInstances.getInstance(dataList[0]);

                if(newStaff != null) {
                    if (dataList.length != 6) {
                        newStaff.update(dataList[0], dataList[1], role, genderNo, Integer.valueOf(dataList[4]), null);
                    } else {
                        newStaff.update(dataList[0], dataList[1], role, genderNo, Integer.valueOf(dataList[4]), new Password(dataList[5]));
                    }
                }

                else {
                    if (dataList.length != 6) {
                        switch(role) {
                            case DOCTOR :
                                newStaff = new Doctor(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), null);
                            break;
                            case PHARMACIST :
                                newStaff = new Pharmacist(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), null);
                            break;
                            case ADMINISTRATOR :
                                newStaff = new Administrator(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), null);
                            break;
                            default :
                                newStaff = new Staff(dataList[0], dataList[1], role, genderNo, Integer.valueOf(dataList[4]), null);
                        }
                    } else {
                        switch(role) {
                            case DOCTOR :
                                newStaff = new Doctor(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), new Password(dataList[5]));
                            break;
                            case PHARMACIST :
                                newStaff = new Pharmacist(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), new Password(dataList[5]));
                            break;
                            case ADMINISTRATOR :
                                newStaff = new Administrator(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), new Password(dataList[5]));
                            break;
                            default :
                                newStaff = new Staff(dataList[0], dataList[1], role, genderNo, Integer.valueOf(dataList[4]), new Password(dataList[5]));
                        }
                    }
                    GlobalData.getInstance().userInstances.add(newStaff);
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
     * Get Staff of a certain ID
     * @param ID
     * @return Staff
     */
    public static Staff getStaffByID(String ID) {
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

                myReader.close();

                Staff newStaff = (Staff)GlobalData.getInstance().userInstances.getInstance(dataList[0]);
                if(newStaff != null) {
                    if (dataList.length != 6) {
                        newStaff.update(dataList[0], dataList[1], role, genderNo, Integer.valueOf(dataList[4]), null);
                    } else {
                        newStaff.update(dataList[0], dataList[1], role, genderNo, Integer.valueOf(dataList[4]), new Password(dataList[5]));
                    }
                }
                else {
                    if (dataList.length != 6) {
                        switch(role) {
                            case DOCTOR :
                                newStaff = new Doctor(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), null);
                            break;
                            case PHARMACIST :
                                newStaff = new Pharmacist(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), null);
                            break;
                            case ADMINISTRATOR :
                                newStaff = new Administrator(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), null);
                            break;
                            default :
                                newStaff = new Staff(dataList[0], dataList[1], role, genderNo, Integer.valueOf(dataList[4]), null);
                        }
                    } else {
                        switch(role) {
                            case DOCTOR :
                                newStaff = new Doctor(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), new Password(dataList[5]));
                            break;
                            case PHARMACIST :
                                newStaff = new Pharmacist(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), new Password(dataList[5]));
                            break;
                            case ADMINISTRATOR :
                                newStaff = new Administrator(dataList[0], dataList[1], genderNo, Integer.valueOf(dataList[4]), new Password(dataList[5]));
                            break;
                            default :
                                newStaff = new Staff(dataList[0], dataList[1], role, genderNo, Integer.valueOf(dataList[4]), new Password(dataList[5]));
                        }
                    }
                    GlobalData.getInstance().userInstances.add(newStaff);
                }

                return newStaff;
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        
        return null;
    }

    /**
     * Get All Staff Data of only a role
     * @param role
     * @return List of Staff
     */
    public static ArrayList<Staff> getRoleStaffData(Role role) {
        ArrayList<Staff> staffArray = new ArrayList<Staff>();
        
        try {
            File myObj = new File(originalFileName);
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

                Staff newStaff = (Staff)GlobalData.getInstance().userInstances.getInstance(dataList[0]);

                if(newStaff != null) {
                    if (dataList.length != 6) {
                        newStaff.update(dataList[0], dataList[1], role, genderNo, Integer.valueOf(dataList[4]), null);
                    } else {
                        newStaff.update(dataList[0], dataList[1], role, genderNo, Integer.valueOf(dataList[4]), new Password(dataList[5]));
                    }
                }

                else {
                    if (dataList.length != 6) {
                        newStaff = new Staff(dataList[0], dataList[1], role, genderNo, Integer.valueOf(dataList[4]), null);
                    } else {
                        newStaff = new Staff(dataList[0], dataList[1], role, genderNo, Integer.valueOf(dataList[4]), new Password(dataList[5]));
                    }
                    GlobalData.getInstance().userInstances.add(newStaff);
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

    /**
     * Add a new staff
     * @param staff
     */
    public static void addStaff(Staff staff) {
        try {
            BufferedWriter f_writer = new BufferedWriter(new FileWriter(temFileName));

            File myObj = new File(originalFileName);
            Scanner myReader = new Scanner(myObj);
            
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                f_writer.write(data);
                f_writer.newLine();
            }

            f_writer.write(staff.getID() + "," + staff.getName() + "," + staff.getRole().toString() + "," + staff.getGenderString() + "," + staff.getAge() + "," + staff.getPassword().getPassword());
            myReader.close();
            f_writer.close();

            Path source = Paths.get(temFileName);
            Path toDir = Paths.get(originalFileName);
            Files.move(source, toDir, StandardCopyOption.REPLACE_EXISTING);

            GlobalData.getInstance().userInstances.add(staff);
        }
        catch (Exception e) {
            System.out.println("An error occurred");
        }
    }

    /**
     * Update a staff
     * @param staff
     */
    public static void updateStaff(Staff staff) {
        try {
            BufferedWriter f_writer = new BufferedWriter(new FileWriter(temFileName));

            File myObj = new File(originalFileName);
            Scanner myReader = new Scanner(myObj);
            f_writer.write(myReader.nextLine());
            f_writer.newLine();

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                if (dataList[0].equals(staff.getID())) {
                    f_writer.write(staff.getID() + "," + staff.getName() + "," + staff.getRole().toString() + "," + staff.getGenderString() + "," + staff.getAge() + "," + staff.getPassword().getPassword());
                } else {
                    f_writer.write(data);
                }
                f_writer.newLine();
            }
            myReader.close();
            f_writer.close();

            Path source = Paths.get(temFileName);
            Path toDir = Paths.get(originalFileName);
            Files.move(source, toDir, StandardCopyOption.REPLACE_EXISTING);

            ((Staff)GlobalData.getInstance().userInstances.getInstance(staff.getID())).update(staff.getID(), staff.getName(), staff.getRole(), staff.getGender(), staff.getAge(), staff.getPassword());
        }
        catch (Exception e) {
            System.out.println("An error occurred");
        }
    }

    /**
     * Remove Staff by ID
     * @param ID
     */
    public static void removeStaffByID(String ID) {
        try {
            BufferedWriter f_writer = new BufferedWriter(new FileWriter(temFileName));

            File myObj = new File(originalFileName);
            Scanner myReader = new Scanner(myObj);
            f_writer.write(myReader.nextLine());
            f_writer.newLine();

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                if (!dataList[0].equals(ID)) {
                    f_writer.write(data);
                    f_writer.newLine();
                }
            }
            myReader.close();
            f_writer.close();

            Path source = Paths.get(temFileName);
            Path toDir = Paths.get(originalFileName);
            Files.move(source, toDir, StandardCopyOption.REPLACE_EXISTING);

            GlobalData.getInstance().userInstances.remove(ID);
        }
        catch (Exception e) {
            System.out.println("An error occurred");
        }
    }

    /**
     * Search for Staff Member by ID and Print Staff Member Info
     *
     * @param ID ID of Staff Member
     * @return true iff Staff Member Found
     */
    public static boolean printStaffByID(String ID){
        Staff staff = getStaffByID(ID);
        if(staff == null) return false;
        System.out.println();
        System.out.println("Staff ID: " + staff.getID());
        System.out.println("Role: " + staff.getRole().toString());
        System.out.println("Name: " + staff.getName());
        System.out.println("Gender: " + staff.getGenderString());
        System.out.println("Age: " + staff.getAge());
        return true;
    }

    /**
     * Search for Staff Member by Name and Print Staff Member Info
     *
     * @param name Name of Staff Member
     * @return true iff Staff Member Found
     */
    public static boolean printStaffByName(String name){
        for (Staff staff : getAllStaffData()){
            if(staff.getName().equalsIgnoreCase(name)) {
                printStaffByID(staff.getID());
                return true;
            }
        }
        return false;
    }
}
