package hms.appointments;

import java.util.ArrayList;
import java.util.List;

import hms.users.*;
import hms.utils.*;

public class PatientScheduleManager {

    private Patient patient;
    private AppointmentScheduler scheduler = AppointmentScheduler.getInstance();
    private List<Appointment> appointmentList = new ArrayList<>();
    private List<Appointment> pendingAppointmentList = new ArrayList<>();

    /**
     * Constructs a PatientScheduleManager for the given patient and retrieves their appointments.
     *
     * @param patient the patient whose appointments are managed
     */
    public PatientScheduleManager(Patient patient) {
        this.patient = patient;
        appointmentList = scheduler.getAppointments(patient);
        pendingAppointmentList = scheduler.getPendingAppointments(patient);
    }

    /**
     * Schedules a new appointment for the patient if it doesn't already exist in their appointment list.
     *
     * @param appointment the appointment to schedule
     */
    public void schedulePatientAppointment(Appointment appointment) {
        updatePatientData();
        if (scheduler.findAppointmentSlot(appointment.getTimeSlot(), pendingAppointmentList)) {
            System.out.println("You have already scheduled this slot.");
            return;
        }
        scheduler.scheduleAppointment(appointment);
    }

    /**
     * Reschedules an existing appointment for the patient with a new appointment.
     *
     * @param existingAppointment the appointment to be rescheduled
     * @param newAppointment      the new appointment details
     */
    public void reschedulePatientAppointment(Appointment existingAppointment, Appointment newAppointment) {
        updatePatientData();
        if (scheduler.findWhichList(existingAppointment) != null)
            scheduler.rescheduleAppointment(existingAppointment, newAppointment);
    }

    /**
     * Cancels an existing appointment for the patient if it is found in the appointment lists.
     *
     * @param appointment the appointment to cancel
     */
    public void cancelPatientAppointment(Appointment appointment) {
        updatePatientData();
        if (scheduler.findAppointment(appointment)) {
            scheduler.cancelAppointment(appointment);
        } else {
            System.out.println("Slot not found");
        }
    }

    /**
     * Prints all appointments for the patient in a formatted manner.
     */
    public void printPatientAppointment() {
        updatePatientData();
        int i = 1;
        for (Appointment appointment : appointmentList) {
            System.out.println((i) + " :");
            System.out.println("Doctor ID: " + appointment.getDoctorID());
            System.out.print("Date: ");
            appointment.getDate().print();
            System.out.print("Time Slot: ");
            appointment.getTimeSlot().print();
            System.out.print("Status: ");
            appointment.printStatus();
            System.out.println("-------------");
            i++;
        }
        for (Appointment appointment : pendingAppointmentList) {
            System.out.println((i) + " :");
            System.out.println("Doctor ID: " + appointment.getDoctorID());
            System.out.print("Date: ");
            appointment.getDate().print();
            System.out.print("Time Slot: ");
            appointment.getTimeSlot().print();
            System.out.println("Status: ");
            appointment.printStatus();
            System.out.println("-------------");
            i++;
        }
    }

    /**
     * Prints available time slots for a doctor on a given date.
     *
     * @param date   the date for which available slots are printed
     * @param doctor the specific doctor to check availability
     */
    public void printAvailableSlots(Date date, Doctor doctor) {
        doctor.getDoctorSchedules().printAvailableSlot(date);
    }

    /**
     * Generate an appointment if the doctor is available.
     *
     * @param patient patient of the appointment
     * @param doctor  doctor of the appointment
     * @param date    date of the appointment
     * @param time    time of the appointment
     * @return appointment if doctor is free otherwise null will be return
     */
    public Appointment generateAppointment(Patient patient, Doctor doctor, Date date, Time time) {
        if (doctor.getDoctorScheduler().isSlotAvailable(time, date)) {
            return new Appointment(patient, doctor, date, time);
        } else return null;
    }

    /**
     * Prints outcome records for appointments with status indicating completion.
     */
    public void printAppointmentOutcomeRecord() {
        updatePatientData();
        for (Appointment appointment : appointmentList) {
            if (appointment.getStatus() == 4) {
                System.out.println("Appointment Outcome Record for patient: " + appointment.getPatientID());
                appointment.printAOP();
                System.out.println("-------------");
            }
        }
    }

    /**
     * Updates the list of appointments for the patient from the scheduler.
     */
    public void updatePatientData() {
        appointmentList = scheduler.getAppointments(patient);
        pendingAppointmentList = scheduler.getPendingAppointments(patient);
    }

    /**
     * Accessor of upcoming appointment
     *
     * @param i index of appointment
     * @return null if index does not exist
     */
    public Appointment getUpcomingAppointment(int i) {
        updatePatientData();

        if (appointmentList == null && pendingAppointmentList == null) return null;
        int totalSize = (appointmentList != null ? appointmentList.size() : 0)
                + (pendingAppointmentList != null ? pendingAppointmentList.size() : 0);
        if (i < 0 || i >= totalSize) return null;
        if (appointmentList != null && i < appointmentList.size()) {
            return appointmentList.get(i);
        } else {
            int adjustedIndex = appointmentList != null ? i - appointmentList.size() : i;
            if (adjustedIndex < pendingAppointmentList.size()) {
                return pendingAppointmentList.get(adjustedIndex);
            }
        }
        return null;
    }

    /**
     * Retrieves the list of appointments for this patient.
     */
    public List<Appointment> getAppointments() {
        return scheduler.getAppointments(patient);
    }

    /**
     * Retrieves the list of pending appointments for this patient.
     */
    public List<Appointment> getPendingAppointments() {
        return scheduler.getPendingAppointments(patient);
    }
}
