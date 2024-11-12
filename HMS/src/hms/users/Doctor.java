package hms.users;

import hms.GlobalData;
import hms.appointments.*;
import hms.services.StaffFileService;
import hms.services.DoctorAvailabilityFileService;
import hms.utils.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

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
        doctorSchedules = DoctorAvailabilityFileService.loadSchedulesFromFile(this);
        doctorScheduler = new DoctorScheduleManager(this);
        patientList = new ArrayList<>();
    }

    public DoctorSchedules getDoctorSchedules() {
        return doctorSchedules;
    }

    public void menu() {
        doctorScheduler.updateDoctorData();
        Scanner sc = GlobalData.getInstance().sc;
        int choice = 1;

        while (true) {

            System.out.println("-----Doctor Menu-----");
            System.out.println("1. View Patient Medical Records ");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule ");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept or Decline Appointment Requests");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. Record Appointment Outcome ");
            super.menu(8);
            System.out.println("-----End of Menu-----");

            System.out.print("Enter menu number: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    viewPatientMedicalRecords();
                    break;

                case 2:
                    if (patientList == null) {
                        System.out.println("You have no patient.");
                        break;
                    }
                    System.out.println("Select patient (0 : exit): ");
                    for (int i = 0; i < patientList.size(); i++) {
                        System.out.println((i + 1) + " : " + patientList.get(i).getName());
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
                        System.out.println("You have no upcoming appointment.");
                        break;
                    }
                    viewPersonalSchedule();
                    break;

                case 4:
                    setAvailabilityforAppointments();
                    break;

                case 5:
                    if (doctorScheduler == null) {
                        System.out.println("You have no upcoming appointment request.");
                        break;
                    }
                    acceptOrDeclineAppointmentRequests();
                    break;

                case 6:
                    if (doctorScheduler == null) {
                        System.out.println("You have no upcoming appointment.");
                        break;
                    }
                    viewUpcomingAppointments();
                    break;

                case 7:
                    if (doctorScheduler == null) {
                        System.out.println("You have no appointment to view.");
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
                    if (!super.useroptions(choice - 7)) {
                        return;
                    }
            }
        }
    }

    public void viewPatientMedicalRecords() {
        if (patientList.size() == 0) {
            System.out.println("Hi, Doc. " + this.getName() + ". You have no patient.");
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
        System.out.println("Add a new medical record:");
        String message = StaffFileService.nextLine();
        patient.addMedicalRecord(message);
    }

    public void viewPersonalSchedule() {
        doctorScheduler.printUpcomingSlots(this);
    }

    /**
     * Set Availability for Appointments
     */
    public void setAvailabilityforAppointments() {
        Scanner scanner = GlobalData.getInstance().sc;
        int ddmmyyyy;
        System.out.print("Enter the date (ddmmyyyy): ");
        ddmmyyyy = scanner.nextInt();
        Date date = new Date(ddmmyyyy);
        doctorSchedules.setDoctorSchedule(date);
    }

    private boolean isInPatientList(Patient keyPatient) {
        for (Patient patient : patientList) {
            if (Objects.equals(patient.getID(), keyPatient.getID())) return true;
        }
        return false;
    }

    private void updatePatientList(Patient patient, int op) {
        if ((op == 1) && (isInPatientList(patient) == false)) {
            patientList.add(patient);
        } else if ((op == -1) && (isInPatientList(patient) == true)) {
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

        if (appointmentList.size() == 0) {
            System.out.println("No pending request!");
        }

        for (Appointment appointment : appointmentList) {
            if (appointment.getStatus() != 1) continue;
            Patient patient = appointment.getPatient();
            Date date = appointment.getDate();
            Time Time = appointment.getTimeSlot();

            ++id;
            System.out.println("-----Request " + id + "-----");
            if (appointment.checkRscheduled()) System.out.println("***Resccheduled Appointment***");
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
                System.out.println("This request is still in your pending list. Please don't forget to reply later");
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
}