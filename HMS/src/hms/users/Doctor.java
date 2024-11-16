package hms.users;

import hms.GlobalData;
import hms.appointments.*;
import hms.services.StaffFileService;
import hms.services.DoctorAvailabilityFileService;
import hms.utils.*;
import hms.utils.Date;

import java.nio.file.FileSystemNotFoundException;
import java.util.*;

public class Doctor extends Staff {

    private DoctorSchedules doctorSchedules;

    /**
     * doctor scheduler
     */
    private DoctorScheduleManager doctorScheduler;

    /**
     * patient list of doctor
     */
    private List<Patient> patientList;

    public Doctor(String doctorID, String name, int gender, int age, Password password) {
        super(doctorID, name, Role.DOCTOR, gender, age, password);
        doctorScheduler = new DoctorScheduleManager(this);
        doctorSchedules = new DoctorSchedules(this);
        patientList = new ArrayList<>();
    }

    public DoctorSchedules getDoctorSchedules() {
        return doctorSchedules;
    }

    public void menu() {
        doctorScheduler.updateDoctorData();
        Scanner sc = GlobalData.getInstance().sc;
        int choice;
        boolean inputError = false;

        while (true) {
            try {
                if (!inputError) {
                    System.out.println("\n-----Doctor Menu-----");
                    System.out.println("1. View Patient Medical Records ");
                    System.out.println("2. Update Patient Medical Records");
                    System.out.println("3. View Personal Schedule ");
                    System.out.println("4. Set Availability for Appointments");
                    System.out.println("5. Accept or Decline Appointment Requests");
                    System.out.println("6. View Upcoming Appointments");
                    System.out.println("7. Record Appointment Outcome ");
                    super.menu(8);
                    System.out.println("-----End of Menu-----");
                    System.out.print("Enter your choice: ");
                }
                else inputError = false;
                choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        viewPatientMedicalRecords();
                        break;

                    case 2:
                        if (patientList == null || patientList.size() == 0) {
                            System.out.println("You have No Patients!");
                            break;
                        }
                        System.out.println("Select Patient (0 to Exit): ");
                        for (int i = 0; i < patientList.size(); i++) {
                            System.out.println((i + 1) + ". " + patientList.get(i).getName());
                        }
                        choice = sc.nextInt();
                        sc.nextLine();
                        if (choice < 1 || choice > patientList.size()) {
                            choice = 2;
                            break;
                        }
                        updatePatientMedicalRecords(patientList.get(choice - 1));
                        choice = 2;
                        break;

                    case 3:
                        if (doctorScheduler == null) {
                            System.out.println("You have No Upcoming Appointments!");
                            break;
                        }
                        viewPersonalSchedule();
                        break;

                    case 4:
                        setAvailabilityForAppointments();
                        break;

                    case 5:
                        if (doctorScheduler == null) {
                            System.out.println("You have No Pending Appointment Requests!");
                            break;
                        }
                        acceptOrDeclineAppointmentRequests();
                        break;

                    case 6:
                        if (doctorScheduler == null) {
                            System.out.println("You have No Upcoming Appointments!");
                            break;
                        }
                        viewUpcomingAppointments();
                        break;

                    case 7:
                        if (doctorScheduler == null) {
                            System.out.println("You have No Appointments to View!");
                            break;
                        }
                        viewUpcomingAppointments();
                        System.out.print("Select Appointment (0 to Cancel): ");
                        choice = sc.nextInt();
                        Appointment choiceAppointment = doctorScheduler.getUpcomingAppointment(choice - 1);
                        if (choiceAppointment == null) {
                            choice = 7;
                            break;
                        }
                        recordAppointmentOutcome(choiceAppointment);
                        choice = 7;
                        break;

                    default:
                        if (!super.userOptions(choice - 7)) {
                            return;
                        }
                        else if (choice < 1 || choice > 10) inputError = true;
                        break;
                }
            }
            catch (InputMismatchException e) {
                inputError = true;
                sc.nextLine();
                System.out.print("Invalid choice! Try again: ");
            }
        }
    }

    public void viewPatientMedicalRecords() {
        if (patientList.size() == 0) {
            System.out.println("Hi, Doc. " + this.getName() + ". You have no patients.");
            return;
        }
        System.out.println("Hi, Doc. " + this.getName() + ". Here is your patient list:");
        int id = 0;
        for (Patient patient : patientList) {
            ++id;
            System.out.println(id + ". " + patient.getName());
        }
        System.out.println("Choose one you want to view.");
        Scanner scan = GlobalData.getInstance().sc;
        int ch = scan.nextInt();
        patientList.get(ch - 1).viewMedicalRecord();
    }

    /**
     * Update Patient Medical Records
     *
     * @param patient Patient that the doctor want to add
     */
    public void updatePatientMedicalRecords(Patient patient) {
        System.out.println("Add a New Medical Record:");
        String message = StaffFileService.nextLine();
        patient.addMedicalRecord(message);
    }

    public void viewPersonalSchedule() {
        doctorScheduler.printUpcomingSlots(this);
    }

    /**
     * Set Availability for Appointments
     */
    public void setAvailabilityForAppointments() {
        Scanner scanner = GlobalData.getInstance().sc;
        int ddmmyyyy;
        System.out.print("Enter the Date (in DDMMYYYY): ");
        ddmmyyyy = scanner.nextInt();
        Date date = new Date(ddmmyyyy);
        doctorSchedules.setDoctorSchedule(date);
        DoctorAvailabilityFileService.writeSchedulesToFile(doctorSchedules);
    }

    private boolean isInPatientList(Patient keyPatient) {
        for (Patient patient : patientList) {
            if (Objects.equals(patient.getID(), keyPatient.getID())) return true;
        }
        return false;
    }

    private void updatePatientList(Patient patient, int op) {
        if ((op == 1) && (!isInPatientList(patient))) {
            patientList.add(patient);
        } else if ((op == -1) && (isInPatientList(patient))) {
            patientList.remove(patient);
        }
    }

    /**
     * Accept Or Decline Appointment Requests
     */
    public void acceptOrDeclineAppointmentRequests() {
        doctorScheduler.updateDoctorData();
        List<Appointment> appointmentList = doctorScheduler.getPendingAppointmentsDoctor();
        int id = 0;
        Scanner scan = GlobalData.getInstance().sc;

        if (appointmentList.isEmpty()) {
            System.out.println("No Pending Appointment Requests!");
        }

        for (Appointment appointment : appointmentList) {
            if (appointment.getStatus() != 1) continue;
            Patient patient = appointment.getPatient();
            Date date = appointment.getDate();
            Time Time = appointment.getTimeSlot();

            ++id;
            System.out.println("-----Request " + id + "-----");
            if (appointment.checkRescheduled()) System.out.println("***Rescheduled Appointment***");
            System.out.println("Name of Patient: " + patient.getName());
            System.out.println("Patient ID: " + patient.getID());
            System.out.print("Date: ");
            date.print();
            System.out.print("Status： ");
            appointment.printStatus();
            String hour = Integer.toString(Time.getHour());
            String minute = Integer.toString(Time.getMinute());
            if (Time.getHour() < 10) {
                hour = "0" + hour;
            }
            if (Time.getMinute() < 10) {
                minute = "0" + minute;
            }
            System.out.println("Time: " + hour + ":" + minute);
            // System.out.println("\nTime: " + Time.getHour() + ":" + Time.getMinute());
            System.out.println("-----End of Request " + id + "-----");
            System.out.println("1. Accept");
            System.out.println("2. Decline");
            System.out.println("3. Later");
            int ch = scan.nextInt();
			while(ch < 1 || ch > 3){
				System.out.println("Invalid Input!");
				System.out.println("1. Accept");
				System.out.println("2. Decline");
				System.out.println("3. Later");
				ch = scan.nextInt();
			}
            if (ch == 1) {
                doctorScheduler.acceptAppointments(appointment);
                updatePatientList(patient, 1);
            } else if (ch == 2) {
                doctorScheduler.declineAppointments(appointment);
            } else {
                System.out.println("This request is still in your pending list. Please don't forget to reply later!");
            }
        }
    }

    public void viewUpcomingAppointments() {
        doctorScheduler.printUpcomingSlots(this);
    }

    public void recordAppointmentOutcome(Appointment appointment) {
        doctorScheduler.updateAppointmentOutcomeRecord(appointment);
    }

    public void printRole() {
        System.out.print("Doctor");
    }

    public DoctorScheduleManager getDoctorScheduler() {
        return doctorScheduler;
    }

    public void setDoctorSchedules(DoctorSchedules doctorSchedules) {
        this.doctorSchedules = doctorSchedules;
    }

    public List<Patient> getPatientList() {
        return patientList;
    }

    public void addPatient(Patient patient){
        patientList.add(patient);
    }

}