import hms.GlobalData;
import hms.appointments.AppointmentScheduler;
import hms.pharmacy.Inventory;
import hms.services.*;
import hms.users.Administrator;
import hms.users.Doctor;
import hms.users.Patient;
import hms.users.Pharmacist;
import hms.users.Staff;
import hms.users.User;
import hms.utils.Role;

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

        /*Load Medical History*/
        //MedicalRecordFileService.loadMedicalHistory(userList);

        /* Load data into inventory */
        Inventory inventory = InventoryFileService.getInventory();

        /* Load Doctor Availability, not sure where to add. */

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
                System.out.print("Enter your ID (0 to exit): ");
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
                    System.out.println("Logged in successfully.\n");
                    break;
                }
                System.out.println("Password is incorrect!");
            } while (accessLevel == null);

            if (accessLevel == null) break;

            /* Menu */
            switch (accessLevel) {
                case PATIENT: // Patient
                    Patient currentPatient = PatientFileService.getPatientByID(currentUser.getID());
                    if (currentPatient == null) break;
                    currentPatient.menu();
                    break;

                case DOCTOR: // Doctor
                    Doctor currentDoctor = DoctorFileService.getDoctorByID(currentUser.getID());
                    if (currentDoctor == null) break;
                    currentDoctor.menu();
                    break;

                case PHARMACIST: // Pharmacist
                    Pharmacist currentPharmacist = PharmacistFileService.getPharmacistByID(currentUser.getID());
                    if (currentPharmacist == null) break;
                    currentPharmacist.menu();
                    break;

                case ADMINISTRATOR: // Administrator
                    Administrator currentAdministrator = AdministratorFileService.getAdministratorByID(currentUser.getID());
                    if (currentAdministrator == null) break;
                    currentAdministrator.menu();
                    break;

                default:

                    break;
            }
        }

        /* Write Appointments */
        AppointmentFileService.writeAppointments(scheduler);

        /*Write Inventory */
        InventoryFileService.writeInventory(gd.inventory);

        /*Wrtie Medical History */
        MedicalRecordFileService.writeMedicalHistory(PatientFileService.getAllPatientData());

        /* Write Doctor Availability, not sure where to put */
        DoctorAvailabilityFileService.writeSchedulesToFile(DoctorFileService.getAllDoctorData());
    }
}
