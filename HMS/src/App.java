import hms.GlobalData;
import hms.appointments.AppointmentScheduler;
import hms.pharmacy.Inventory;
import hms.services.*;
import hms.users.*;
import hms.utils.Role;

import javax.print.Doc;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        /* Load data into userList */
		/*
    	UserList userList = new UserList();
    	userList.setPatients(PatientFileService.getAllPatientData());
    	ArrayList<Staff> temUser = new ArrayList<Staff>();
    	userList.setDoctors(DoctorFileService.getAllDoctorData());
		userList.setPharmacist(PharmacistFileService.getAllPharmacistData());
		userList.setAdministrator(AdministratorFileService.getAllAdministratorsData());
		*/

        /* Load Appointments */
        AppointmentScheduler scheduler = AppointmentScheduler.getInstance();
        AppointmentFileService.loadAppointments(scheduler);

        for (Doctor doctor:DoctorFileService.getAllDoctorData()){
            DoctorAvailabilityFileService.loadSchedulesFromFile(doctor);
        }
        AOPFileService.loadAOP();

        /*Load Medical History*/
        DoctorPatientFileService.loadPatientList();
        MedicalRecordFileService.loadMedicalHistory();

        /*Load 2FA */
        SecureFileService.load2FA();

        /* Load data into inventory */
        Inventory inventory = InventoryFileService.getInventory();

        /* Set Global Data */
        GlobalData gd = GlobalData.getInstance();
        gd.inventory = inventory;

        Scanner sc = gd.sc;

        /* Login */
        User currentUser = null;
        while (true) {
            Role accessLevel = null;
            do {
                System.out.println("Hospital Management System (HMS)");
                System.out.println("==============================");
                System.out.println("Please login");
                System.out.print("Enter your ID (0 to Exit): ");
                String ID = sc.next();
                sc.nextLine();

                if (ID.equals("0")) {
                    accessLevel = null;
                    break;
                }

                boolean found = false;


                for (User user : UserFileService.getUsersRoleSorted()) {
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
                if (accessLevel != null) {
                    System.out.println("Logged in successfully.");
                    break;
                }
                System.out.println("Password or Token is incorrect!");
            } while (accessLevel == null);

            if (accessLevel == null) break;

            /* Menu */
            else currentUser.menu();
        }

        /* Write Appointments */
        AppointmentFileService.writeAppointments(scheduler);
        AOPFileService.writeAOP();

        /*Write Inventory */
        InventoryFileService.writeInventory(gd.inventory);

        /*Write Medical History */
        MedicalRecordFileService.writeMedicalHistory(PatientFileService.getAllPatientData());
        DoctorPatientFileService.writePatientList();

        /*Write 2FA */
        SecureFileService.write2FA();

    }
}
