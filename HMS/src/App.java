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
import hms.utils.Role;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
    	/* Load data into userList */
    	UserList userList = new UserList();
    	userList.setPatients(PatientFileService.getAllPatientData());
    	ArrayList<Staff> temUser = new ArrayList<Staff>();
    	userList.setDoctors(DoctorFileService.getAllDoctorData());
		userList.setPharmacist(PharmacistFileService.getAllPharmacistData());
		userList.setAdministrator(AdministratorFileService.getAllAdministratorsData());

    	
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
			Role accessLevel = null;
			do {
				System.out.println("Hospital Management System (HMS)");
				System.out.println("==============================");
				System.out.println("Please login");
				System.out.print("Enter your ID (0 to exit): ");
				String ID = sc.next();
				sc.nextLine();

				if(ID.equals("0")) {
					accessLevel = null;
					break;
				}
				
				boolean found = false;
				for (User user : PatientFileService.getAllPatientData()) {
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
				if(accessLevel != null) {
					System.out.println("Logged in successfully.\n");
					break;
				} 
				System.out.println("Password is incorrect!");
			} while (accessLevel == null);
			
			if(accessLevel == null) break;

			/* Menu */
			switch(accessLevel) {
			case Role.PATIENT: // Patient
				Patient currentPatient = null;
				for (Patient patient : PatientFileService.getAllPatientData()) {
					if (patient.getID().equals(currentUser.getID())) {
						currentPatient = patient;
						break;
					}
				}
				currentPatient.menu();
				break;

			case Role.DOCTOR: // Doctor
				Doctor currentDoctor = null;
				for (Doctor doctor : StaffFileService.getAllDoctorData()) {
					if (doctor.getID().equals(currentUser.getID())) {
						currentDoctor = doctor;
						break;
					}
				}
				currentDoctor.menu();
				break;

			case Role.PHARMACIST: // Pharmacist
				Pharmacist currentPharmacist = null;
				for (Pharmacist pharmacist : StaffFileService.getAllPharmacistData()) {
					if (pharmacist.getID().equals(currentUser.getID())) {
						currentPharmacist = pharmacist;
						break;
					}
				}
				currentPharmacist.menu();
				break;

			case Role.ADMINISTRATOR: // Administrator
				Administrator currentAdministrator = null;
				for (Administrator administrator : StaffFileService.getAllAdministratorsData()) {
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
