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
        appointments = new ArrayList<>();
        pendingAppointments = new ArrayList<>();
    }

    public void setLists(List<Appointment> appointments, List<Appointment> pendingAppointments) {
        this.appointments = appointments;
        this.pendingAppointments = pendingAppointments;
    }

    /**
     * Schedules a new appointment if the slot is available.
     *
     * @param appointment The appointment to be scheduled
     */
    protected void scheduleAppointment(Appointment appointment) {
        if (isSlotAvailable(appointment)) {
            System.out.println("Successfully scheduled. Waiting for the doctor.");
            pendingAppointments.add(appointment);
        } else {
            System.out.println("Current slot is not available.");
        }
    }

    /**
     * Accepts a pending appointment, moving it to the confirmed list.
     *
     * @param pendingAppointment The pending appointment to accept
     */
    protected void acceptAppointment(Appointment pendingAppointment) {
        if (!isSlotAvailable(pendingAppointment)) {
            System.out.println("Slot not available.");
            pendingAppointment.cancel();
            pendingAppointments.remove(pendingAppointment);
            appointments.add(pendingAppointment);
            return;
        }
        Appointment appointment = cancelAppointment(pendingAppointment);
        if (appointment == null) {
            System.out.println("No such appointment");
            return;
        }
        if (appointment.getRescheduled() != null && findAppointment(appointment.getRescheduled(), appointments) == null) {
            System.out.println("Can't find related events need to be rescheduled. Discarding rescheduled data.");
            appointment.clearRescheduled();
        }

        System.out.println("Successfully Accepted!");
        appointment.confirm();
        pendingAppointments.remove(appointment);
        appointments.add(appointment);
    }

    /**
     * Declines a pending appointment.
     *
     * @param pendingAppointment The pending appointment to decline
     */
    protected void declineAppointment(Appointment pendingAppointment) {
        Appointment appointment = findAppointment(pendingAppointment.getUuid(), pendingAppointments);
        if (appointment == null) {
            System.out.println("No such appointment");
            return;
        }
        if (appointment.getStatus() != 1) {
            System.out.println("Appointment not pending.");
            return;
        }
        if (appointment.getRescheduled() != null && findAppointment(appointment.getRescheduled(), appointments) == null) {
            appointment.clearRescheduled();
            System.out.println("Can't find related events need to be rescheduled. Discarding rescheduled date.");
        }
        appointment.cancel();
        pendingAppointments.remove(appointment);
        appointments.add(appointment);
        System.out.println("Successfully Declined!");
    }

    /**
     * Reschedules an existing appointment to a new slot.
     *
     * @param existingAppointment The existing appointment to be rescheduled
     * @param newAppointment      The new appointment with updated time slot
     */
    protected void rescheduleAppointment(Appointment existingAppointment, Appointment newAppointment) {
        if (isSlotAvailable(newAppointment)) {
            List<Appointment> tempList = findWhichList(existingAppointment);
            if (tempList == null) {
                System.out.println("No existing appointment found.");
                return;
            }
            if (tempList.equals(pendingAppointments)) {
                scheduleAppointment(newAppointment);
                existingAppointment.cancel();
                pendingAppointments.remove(existingAppointment);
                return;
            }
            existingAppointment.cancel();
            newAppointment.setRescheduled(existingAppointment);
            scheduleAppointment(newAppointment);
            return;
        }
        System.out.println("New time slot is not available.");
    }


    /**
     * Cancels an appointment in a specific list.
     *
     * @param appointment The appointment to cancel
     * @return The canceled appointment if successful; otherwise null
     */
    protected Appointment cancelAppointment(Appointment appointment) {
        if (findAppointment(appointment)) {
            if (appointment.getStatus() == 1) {
                pendingAppointments.remove(appointment);
            }
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
    protected boolean isSlotAvailable(Appointment appointment) {
        return isSlotAvailable(appointment.getDoctor(), appointment.getTimeSlot(), appointment.getDate());
    }

    /**
     * Checks if a slot is available for a specific doctor at a specific time.
     *
     * @param doctor The ID of the doctor
     * @param time   The date and time of the appointment
     * @return true if the slot is available; otherwise false
     */
    protected boolean isSlotAvailable(Doctor doctor, Time time, Date date) {
        return doctor.getDoctorSchedules().isDoctorWorking(date, time) && !isSlotOccupied(doctor, time, date);
    }

    /**
     * Checks if a specific time slot is occupied for a given doctor on a specific date.
     *
     * @param doctor the doctor for whom the slot is being checked
     * @param time   the time slot to check
     * @param date   the date to check
     * @return true if the slot is occupied, false otherwise
     */
    protected boolean isSlotOccupied(Doctor doctor, Time time, Date date) {
        boolean occupied = false;
        List<Appointment> appointmentList = getAppointments(doctor);
        for (Appointment appointment : appointmentList) {
            if (appointment.getDate().equals(date) && appointment.getTimeSlot().equals(time) && appointment.getStatus() == 2) {
                occupied = true;
                break;
            }
        }
        return occupied;
    }

    /**
     * Checks if an appointment exists for a specific time slot in the provided list of appointments.
     *
     * @param time         the time slot to find
     * @param appointments the list of appointments to search through
     * @return true if the time slot is found, false otherwise
     */
    protected boolean findAppointmentSlot(Time time, List<Appointment> appointments) {
        for (Appointment appointment : appointments) {
            if (appointment.getTimeSlot().equals(time)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds an appointment in the list.
     *
     * @param appointment The appointment to search for
     * @return The found appointment if successful; otherwise null
     */
    protected boolean findAppointment(Appointment appointment) {
        return findAppointment(appointment.getUuid()) != null;
    }

    /**
     * Finds an appointment in a specific list by its unique identifier.
     *
     * @param appointment  The appointment to search for
     * @param appointments The list of appointments to search in
     * @return The found appointment if successful; otherwise null
     */
    protected boolean findAppointment(Appointment appointment, List<Appointment> appointments) {
        return findAppointment(appointment.getUuid(), appointments) != null;
    }

    /**
     * Searches for an appointment using its UUID in a specified list.
     *
     * @param uuid The unique identifier of the appointment
     * @return The found appointment if successful; otherwise null
     */
    protected Appointment findAppointment(UUID uuid) {
        for (Appointment appointment : appointments) {
            if (appointment.getUuid().equals(uuid)) {
                return appointment;
            }
        }
        for (Appointment appointment : pendingAppointments) {
            if (appointment.getUuid().equals(uuid)) {
                return appointment;
            }
        }
        return null;
    }

    /**
     * Searches for an appointment using its UUID in a specified list.
     *
     * @param uuid The unique identifier of the appointment
     * @return The found appointment if successful; otherwise null
     */
    public Appointment findAppointmentToWrite(String uuid) {
        for (Appointment appointment : appointments) {
            if (appointment.getUuid().toString().equals(uuid)) {
                return appointment;
            }
        }
        for (Appointment appointment : pendingAppointments) {
            if (appointment.getUuid().toString().equals(uuid)) {
                return appointment;
            }
        }
        return null;
    }

    protected Appointment findAppointment(UUID uuid, List<Appointment> appointments) {
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

    protected List<Appointment> findWhichList(Appointment appointment) {
        if (findAppointment(appointment, appointments)) return appointments;
        if (findAppointment(appointment, pendingAppointments)) return pendingAppointments;
        System.out.println("Not in lists.");
        return null;
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
    protected List<Appointment> getAppointments(Patient patient) {
        return getAppointments(patient, appointments);
    }

    protected List<Appointment> getAppointments(Patient patient, List<Appointment> appointments) {
        List<Appointment> appointmentsForPatient = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getPatientID().equals(patient.getID()) && appointment.getStatus() != 3) {
                appointmentsForPatient.add(appointment);
            }
        }
        return appointmentsForPatient;
    }

    /**
     * Retrieves a list of pending appointments for the specified patient.
     *
     * @param patient the Patient whose pending appointments are to be retrieved
     * @return a List of Appointment objects that are pending for the given patient
     */
    protected List<Appointment> getPendingAppointments(Patient patient) {
        return getAppointments(patient, pendingAppointments);
    }

    /**
     * Retrieves the list of appointments for a specific doctor.
     *
     * @param doctor The doctor for whom to retrieve appointments
     * @return The list of appointments for the given doctor
     */
    protected List<Appointment> getAppointments(Doctor doctor) {
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
    protected List<Appointment> getPendingAppointments(Doctor doctor) {

        List<Appointment> pendingAppointmentsForDoctor = new ArrayList<>();
        for (Appointment appointment : pendingAppointments) {
            if (appointment.getDoctorID().equals(doctor.getID())) {
                pendingAppointmentsForDoctor.add(appointment);
            }
        }
        return pendingAppointmentsForDoctor;
    }
}
