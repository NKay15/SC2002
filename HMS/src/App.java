import java.io.File;  
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator; 
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import hms.users.User;
import hms.UserList;
import hms.users.Administrator;
import hms.users.Doctor;
import hms.users.Patient;
import hms.users.Pharmacist;
import hms.users.Staff;
import hms.utils.Date;
import hms.Inventory; 
import hms.GlobalData; 

public class App {
    public static void main(String[] args) throws Exception {
    	/* Load data into userList */
    	UserList userList = new UserList();
    	userList.setPatients(getPatientData());
    	ArrayList<Staff> temUser = new ArrayList<Staff>();
    	temUser = getStaffData();
    	for (Staff user : temUser) {
    		if (user.getRole() == 2) {
    			Doctor temDoc = new Doctor(user.getID(), user.getName(), user.getGender(), user.getAge());
    			userList.addDoctor(temDoc);
    		} else if (user.getRole() == 3) {
    			Pharmacist temPhar = new Pharmacist(user.getID(), user.getName(), user.getGender(), user.getAge());
    			userList.addPharmacist(temPhar);
    		} else if (user.getRole() == 4) {
    			Administrator temAdmin = new Administrator(user.getID(), user.getName(), user.getGender(), user.getAge());
    			userList.addAdministrator(temAdmin);
    		}
    	}
    	
		/* Load data into inventory */
		Inventory inventory = getInventory();

		/* Set Global Data */
		GlobalData gd = GlobalData.getInstance();
		gd.userList = userList;
		gd.inventory = inventory;
    	
    	Scanner sc = gd.sc;
    	
    	/* Login */
    	User currentUser = null;
    	int accessLevel = -1;
		while(true) {
			do {
				System.out.println("Hospital Management System (HMS)");
				System.out.println("==============================");
				System.out.println("Please login");
				System.out.print("Enter your ID (0 to exit): ");
				String ID = sc.next();

				if(ID.equals("0")) {
					accessLevel = -1;
				}
				
				boolean found = false;
				for (User user : userList.getUsersRoleSorted()) {
					if (user.getID().equals(ID)) {
						found = true;
						currentUser = user;
						break;
					}
				}
				if (found == false) {
					System.out.println("This user does not exist. Please check your ID again.");
					continue;
				}
				
				accessLevel = currentUser.login();
				if(accessLevel != -1) {
					System.out.println("Logged in successfully.\n");
					break;
				} 
				System.out.println("Password is incorrect!");
			} while (accessLevel == -1);
			
			if(accessLevel == -1) break;

			/* Menu */
			switch(accessLevel) {
			case 1: // Patient
				Patient currentPatient = null;
				for (Patient patient : userList.getPatients()) {
					if (patient.getID().equals(currentUser.getID())) {
						currentPatient = patient;
						break;
					}
				}
				currentPatient.menu();
				break;

			case 2: // Doctor
				Doctor currentDoctor = null;
				for (Doctor doctor : userList.getDoctors()) {
					if (doctor.getID().equals(currentUser.getID())) {
						currentDoctor = doctor;
						break;
					}
				}
				currentDoctor.menu();
				break;

			case 3: // Pharmacist
				Pharmacist currentPharmacist = null;
				for (Pharmacist pharmacist : userList.getPharmacists()) {
					if (pharmacist.getID().equals(currentUser.getID())) {
						currentPharmacist = pharmacist;
						break;
					}
				}
				currentPharmacist.menu();
				break;

			case 4: // Administrator
				Administrator currentAdministrator = null;
				for (Administrator administrator : userList.getAdministrators()) {
					if (administrator.getID().equals(currentUser.getID())) {
						currentAdministrator = administrator;
						break;
					}
				}
				currentAdministrator.menu();
				break;
				
			default:
				break;
			}
		}
    }
    
    /**
     * Get Patient Data
     */
    public static ArrayList<Patient> getPatientData() {
    	try
        {  
    		ArrayList<Patient> patientArray = new ArrayList<Patient>();
    		
            File file = new File("HMS/src/data/Patient_List.xlsx");
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fis);   
            XSSFSheet sheet = wb.getSheetAt(0);
            Iterator<Row> itr = sheet.iterator();
            itr.next(); // Skip header
            
            while (itr.hasNext())
            {  
                Row row = itr.next();  
                Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column  
                
                Cell id = cellIterator.next();  
                
                Cell name = cellIterator.next();
                
                Cell dob = cellIterator.next();
                Date dateFormat = new Date(Integer.valueOf(dob.toString().substring(0, 4)), Integer.valueOf(dob.toString().substring(5, 7)), Integer.valueOf(dob.toString().substring(8,10)));
                
                Cell gender = cellIterator.next();
                int genderNo = 0;
                if (gender.toString() == "Male") {
                	genderNo = 1;
                } else if (gender.toString() == "Female") {
                	genderNo = 2;
                }
                
                Cell bloodType = cellIterator.next();
                int bloodTypeNo = 0;
                
                if (bloodType.toString() == "A+") {
                	bloodTypeNo = 1;
                }
                if (bloodType.toString() == "A-") {
                	bloodTypeNo = 2;
                }
                if (bloodType.toString() == "B+") {
                	bloodTypeNo = 3;
                }
                if (bloodType.toString() == "B-") {
                	bloodTypeNo = 4;
                } 
                if (bloodType.toString() == "AB+") {
                	bloodTypeNo = 5;
                }
                if (bloodType.toString() == "AB-") {
                	bloodTypeNo = 6;
                } 
                if (bloodType.toString() == "O+") {
                	bloodTypeNo = 7;
                } 
                if (bloodType.toString() == "O-") {
                	bloodTypeNo = 8;
                }
                
                Cell email = cellIterator.next();
                
                Patient newPatient = new Patient(id.toString(), name.toString(), genderNo, dateFormat, 0, email.toString(), bloodTypeNo);
                patientArray.add(newPatient);
            }
            
            return patientArray;
        }  
        catch(Exception e)  
        {  
            e.printStackTrace();
            return null;
        }  
    }
    
    /**
     * Get Staff Data
     */
    public static ArrayList<Staff> getStaffData() {
    	try
        {  
    		ArrayList<Staff> staffArray = new ArrayList<Staff>();
    		
            File file = new File("HMS/src/data/Staff_List.xlsx");
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fis);   
            XSSFSheet sheet = wb.getSheetAt(0);
            Iterator<Row> itr = sheet.iterator();
            itr.next(); // Skip header
            
            while (itr.hasNext())
            {  
                Row row = itr.next();  
                Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column  
                
                Cell id = cellIterator.next();  
                
                Cell name = cellIterator.next();
                
                Cell role = cellIterator.next();
                int roleNo = 0;
                if (role.toString().equals("Doctor")) {
                	roleNo = 2;
                }
                if (role.toString().equals("Pharmacist")) {
                	roleNo = 3;
                }
                if (role.toString().equals("Administrator")) {
                	roleNo = 4;
                }
                
                Cell gender = cellIterator.next();
                int genderNo = 0;
                if (gender.toString() == "Male") {
                	genderNo = 1;
                } else if (gender.toString() == "Female") {
                	genderNo = 2;
                }
                
                Cell age = cellIterator.next();
                
                Staff newUser = new Staff(id.toString(), name.toString(), roleNo, genderNo, (int) Math.floor(age.getNumericCellValue()));
                staffArray.add(newUser);
            }
            
            return staffArray;
        }  
        catch(Exception e)  
        {  
            e.printStackTrace();
            return null;
        }  
    }

	/**
	 * Read medicine from file and generate initial inventory
	 * @return initial inventory
	 */
	public static Inventory getInventory() {
		try {
			Inventory setup = new Inventory();

			File file = new File("HMS/src/data/Medicine_List.xlsx");
			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook wb = new XSSFWorkbook(fis);   
			XSSFSheet sheet = wb.getSheetAt(0);
			Iterator<Row> itr = sheet.iterator();
			itr.next(); // Skip header

			while(itr.hasNext()) {
				Row row = itr.next();  
				Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column  
					
				Cell name = cellIterator.next();  
					
				Cell amount = cellIterator.next();
					
				Cell low = cellIterator.next();

				setup.addNewMedicine(name.toString(), (int) amount.getNumericCellValue(), (int) Math.floor(low.getNumericCellValue()));
			}

			return setup;
		}
		catch(Exception e)  
        {  
            e.printStackTrace();
            return new Inventory();
        }
	}
}
