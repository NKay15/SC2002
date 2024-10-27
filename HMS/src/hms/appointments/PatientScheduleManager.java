package hms.appointments;

import java.util.List;

public class PatientScheduleManager {
    /**
     * Prints all appointments for the patient with a given patient ID.
     *
     * @param patientID the ID of the patient whose appointments need to be printed
     */
    public void printPatientAppointment(String patientID, AppointmentScheduler Scheduler){
        for (Appointment appointment : Scheduler.getAppointments()) {
            if (appointment.getPatientID().equals(patientID)) {
                System.out.println("Doctor ID: " + appointment.getDoctorID());
                System.out.println("Date: " + appointment.getDate());
                System.out.println("Time Slot: " + appointment.getTimeSlot());
                System.out.print("Status: ");
                appointment.printStatus();
                System.out.println("-------------");
            }
        }
    }
}
