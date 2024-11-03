package hms.appointments;

import java.util.*;

import hms.users.*;
import hms.utils.Date;
import hms.utils.*;

/**
 * Manages the scheduling of appointments in the healthcare management system.
 * This singleton class provides methods to schedule, accept, decline, reschedule, and cancel appointments.
 */
public class AppointmentScheduler {
    private static AppointmentScheduler instance;
    private List<Appointment> appointments;
    private List<Appointment> pendingAppointments;

    private AppointmentScheduler() {
        this.appointments = new ArrayList<>();
        this.pendingAppointments = new ArrayList<>();
    }

    /**
     * Schedules a new appointment if the slot is available.
     *
     * @param appointment The appointment to be scheduled
     * @return The scheduled appointment if successful; otherwise null
     */
    public Appointment scheduleAppointment(Appointment appointment) {
        if (isSlotAvailable(appointment)) {
            System.out.println("Successfully scheduled. Waiting for the doctor.");
            pendingAppointments.add(appointment);
            return appointment;
        } else {
            System.out.println("Current slot is not available.");
            return null;
        }
    }

    /**
     * Accepts a pending appointment, moving it to the confirmed list.
     *
     * @param pendingAppointment The pending appointment to accept
     */
    public void acceptAppointment(Appointment pendingAppointment) {
        Appointment appointment = cancelAppointment(pendingAppointment, pendingAppointments);
        if (appointment == null) {
            System.out.println("No such appointment");
            return;
        }
        if (appointment.getStatus() == 5) {
            Appointment rescheduledAppointment = findAppointment(appointment.getRescheduled(), appointments);
            if (rescheduledAppointment == null){
                System.out.println("Can't find related events need to be rescheduled.");
                return;
            }
            cancelAppointment(rescheduledAppointment, appointments);
        }
        appointment.confirm();
        pendingAppointments.remove(appointment);
        appointments.add(appointment);
    }

    /**
     * Declines a pending appointment.
     *
     * @param pendingAppointment The pending appointment to decline
     */
    public void declineAppointment(Appointment pendingAppointment) {
        Appointment appointment = findAppointment(pendingAppointment.getUuid(), pendingAppointments);
        if (appointment == null) return;
        if (appointment.getStatus() == 1) {
            appointment.cancel();
            System.out.println("Appointment rejected.");
        } else if (appointment.getStatus() == 5) {
            findAppointment(appointment.getRescheduled().getUuid(), appointments).confirm();
            cancelAppointment(appointment, pendingAppointments);
        } else {
            System.out.println("Appointment not pending or rescheduled");
        }
    }

    /**
     * Reschedules an existing appointment to a new slot.
     *
     * @param existingAppointment The existing appointment to be rescheduled
     * @param newAppointment      The new appointment with updated time slot
     * @return The newly scheduled appointment if successful; otherwise null
     */
    public Appointment rescheduleAppointment(Appointment existingAppointment, Appointment newAppointment) {
        if (isSlotAvailable(newAppointment)) {
            List<Appointment> tempList = findWhichList(existingAppointment);
            if (tempList == null) {
                System.out.println("No existing appointment found.");
                return null;
            }
            if (tempList.equals(pendingAppointments)) {
                scheduleAppointment(newAppointment);
                cancelAppointment(existingAppointment, pendingAppointments);
                return newAppointment;
            }
            existingAppointment.reschedule();
            newAppointment.reschedule();
            newAppointment.setRescheduled(existingAppointment);
            scheduleAppointment(newAppointment);
            return newAppointment;
        }
        System.out.println("New time slot is not available.");
        return null;
    }

    /**
     * Cancels an existing appointment.
     *
     * @param appointment The appointment to be canceled
     */
    public void cancelAppointment(Appointment appointment) {
        cancelAppointment(appointment, findWhichList(appointment));
    }

    /**
     * Cancels an appointment in a specific list.
     *
     * @param appointment     The appointment to cancel
     * @param appointmentList The list from which to cancel the appointment
     * @return The canceled appointment if successful; otherwise null
     */
    public Appointment cancelAppointment(Appointment appointment, List<Appointment> appointmentList) {
        if (findAppointment(appointment) != null) {
            appointment.cancel();
            return appointment;
        }
        System.out.println("Can't find slot.");
        return null;
    }

    /**
     * Checks if a slot is available for a certain appointment.
     *
     * @param appointment The appointment to check availability for
     * @return true if the slot is available; otherwise false
     */
    private boolean isSlotAvailable(Appointment appointment) {
        return isSlotAvailable(appointment.getDoctor(), appointment.getTimeSlot(), appointment.getDate());
    }

    /**
     * Checks if a slot is available for a specific doctor at a specific time.
     *
     * @param doctor The ID of the doctor
     * @param time   The date and time of the appointment
     * @return true if the slot is available; otherwise false
     */
    private boolean isSlotAvailable(Doctor doctor, Time time, Date date) {
        return doctor.getDoctorSchedules().isDoctorAvailable(date, time);
    }

    /**
     * Finds an appointment in the list.
     *
     * @param appointment The appointment to search for
     * @return The found appointment if successful; otherwise null
     */
    public Appointment findAppointment(Appointment appointment) {
        List<Appointment> tempList = findWhichList(appointment);
        if (tempList != null) {
            return findAppointment(appointment, tempList);
        }
        return null;
    }

    /**
     * Prints the availability of a doctor for a specific date.
     *
     * @param doctor The doctor whose availability is to be printed
     * @param date   The date to check availability for
     */
    public void printAppointment(Doctor doctor, Date date) {
        doctor.viewAvailability(date);
    }

    /**
     * Finds an appointment in a specific list by its unique identifier.
     *
     * @param appointment  The appointment to search for
     * @param appointments The list of appointments to search in
     * @return The found appointment if successful; otherwise null
     */
    public Appointment findAppointment(Appointment appointment, List<Appointment> appointments) {
        return findAppointment(appointment.getUuid(), appointments);
    }

    /**
     * Searches for an appointment using its UUID in a specified list.
     *
     * @param uuid         The unique identifier of the appointment
     * @param appointments The list of appointments to search in
     * @return The found appointment if successful; otherwise null
     */
    private Appointment findAppointment(UUID uuid, List<Appointment> appointments) {
        for (Appointment appointment : appointments) {
            if (appointment.getUuid().equals(uuid)) {
                return appointment;
            }
        }
        return null;
    }

    /**
     * Determines the list which contains the specified appointment.
     *
     * @param appointment The appointment to search for
     * @return The list containing the appointment; null if not found
     */
    public List<Appointment> findWhichList(Appointment appointment) {
        if (findAppointment(appointment, appointments) != null) return appointments;
        if (findAppointment(appointment, pendingAppointments) != null) return pendingAppointments;
        System.out.println("Not in lists.");
        return null;
    }

    /**
     * Returns the total number of confirmed appointments.
     *
     * @return The number of confirmed appointments
     */
    public int getAppointmentsCount() {
        return appointments.size();
    }

    /**
     * Returns the total number of pending appointments.
     *
     * @return The number of pending appointments
     */
    public int getPendingAppointmentCount() {
        return pendingAppointments.size();
    }

    /**
     * Retrieves the singleton instance of the appointment scheduler.
     *
     * @return The instance of the appointment scheduler
     */
    public static AppointmentScheduler getInstance() {
        if (instance == null) {
            instance = new AppointmentScheduler();
        }
        return instance;
    }

    /**
     * Retrieves the list of confirmed appointments.
     *
     * @return The list of confirmed appointments
     */
    public List<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * Retrieves the list of pending appointments.
     *
     * @return The list of pending appointments
     */
    public List<Appointment> getPendingAppointments() {
        return pendingAppointments;
    }

    /**
     * Retrieves the list of appointments for a specific patient.
     *
     * @param patient The patient for whom to retrieve appointments
     * @return The list of appointments for the given patient
     */
    public List<Appointment> getAppointments(Patient patient) {
        List<Appointment> appointmentsForPatient = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getPatientID().equals(patient.getID())) {
                appointmentsForPatient.add(appointment);
            }
        }
        return appointmentsForPatient;
    }

    public List<Appointment> getPendingAppointments(Patient patient) {
        List<Appointment> pendingAppointmentsForPatient = new ArrayList<>();
        for (Appointment appointment : pendingAppointments) {
            if (appointment.getPatientID().equals(patient.getID())) {
                pendingAppointmentsForPatient.add(appointment);
            }
        }
        return pendingAppointmentsForPatient;
    }

     /**
     * Retrieves the list of appointments for a specific doctor.
     *
     * @param doctor The doctor for whom to retrieve appointments
     * @return The list of appointments for the given doctor
     */
    public List<Appointment> getAppointments(Doctor doctor) {
        List<Appointment> appointmentsForDoctor = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getDoctorID().equals(doctor.getID())) {
                appointmentsForDoctor.add(appointment);
            }
        }
        return appointmentsForDoctor;
    }
  
    /**
     * Retrieves the list of pending appointments for a specific doctor.
     *
     * @param doctor The doctor for whom to retrieve pending appointments
     * @return The list of pending appointments for the given doctor
     */
    public List<Appointment> getPendingAppointments(Doctor doctor) {
        List<Appointment> pendingAppointmentsForDoctor = new ArrayList<>();
        for (Appointment appointment : pendingAppointments) {
            if (appointment.getDoctorID().equals(doctor.getID())) {
                pendingAppointmentsForDoctor.add(appointment);
            }
        }
        return pendingAppointmentsForDoctor;
    }

}
