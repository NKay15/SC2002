import hms.GlobalData;
import hms.UserList;
import hms.pharmacy.Inventory;
import hms.services.*;
import hms.users.Administrator;
import hms.users.Doctor;
import hms.users.Patient;
import hms.users.Pharmacist;
import hms.users.Staff;
import hms.users.User;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
    	/* Load data into userList */
    	UserList userList = new UserList();
    	userList.setPatients(PatientFileService.getPatientData());
    	ArrayList<Staff> temUser = new ArrayList<Staff>();
    	temUser = StaffFileService.getStaffData();
    	for (Staff user : temUser) {
    		if (user.getRole() == 2) {
    			Doctor temDoc = new Doctor(user.getID(), user.getName(), user.getGender(), user.getAge(), user.getPassword());
    			userList.addDoctor(temDoc);
    		} else if (user.getRole() == 3) {
    			Pharmacist temPhar = new Pharmacist(user.getID(), user.getName(), user.getGender(), user.getAge(), user.getPassword());
    			userList.addPharmacist(temPhar);
    		} else if (user.getRole() == 4) {
    			Administrator temAdmin = new Administrator(user.getID(), user.getName(), user.getGender(), user.getAge(), user.getPassword());
    			userList.addAdministrator(temAdmin);
    		}
    	}
    	
		/*Load Medical History*/
		MedicalRecordFileService.loadMedicalHistory(userList);

		/* Load data into inventory */
		Inventory inventory = InventoryFileService.getInventory();

		/* Set Global Data */
		GlobalData gd = GlobalData.getInstance();
		gd.userList = userList;
		gd.inventory = inventory;
    	
    	Scanner sc = gd.sc;
    	
    	/* Login */
		while(true) {
			User currentUser = null;
			int accessLevel = -1;
			do {
				System.out.println("Hospital Management System (HMS)");
				System.out.println("==============================");
				System.out.println("Please login");
				System.out.print("Enter your ID (0 to exit): ");
				String ID = sc.next();
				sc.nextLine();

				if(ID.equals("0")) {
					accessLevel = -1;
					break;
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
					System.out.println("This user does not exist. Please check your ID again.\n");
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

		/*Write Inventory */
		InventoryFileService.writeInventory(gd.inventory);

		/*Wrtie Medical History */
		MedicalRecordFileService.writeMedicalHistory(gd.userList.getPatients());
    }
}
