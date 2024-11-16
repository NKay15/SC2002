package hms.appointments;

import java.util.List;
import java.util.UUID;

/**
 * Manages appointments for administrators within the Health Management System.
 */
public class AdministratorAppointmentManager {
    private AppointmentScheduler scheduler = AppointmentScheduler.getInstance();

    /**
     * Retrieves all appointments managed by the scheduler.
     *
     * @return a list of all appointments
     */
    public List<Appointment> getAppointments() {
        return scheduler.getAppointments();
    }

    /**
     * Retrieves all pending appointments.
     *
     * @return a list of pending appointments
     */
    public List<Appointment> getPendingAppointments() {
        return scheduler.getPendingAppointments();
    }

    /**
     * Finds an appointment by its unique identifier (UUID).
     *
     * @param uuid the unique identifier of the appointment
     * @return the appointment associated with the given UUID, or null if not found
     */
    public Appointment findAppointment(UUID uuid) {
        return scheduler.findAppointment(uuid);
    }

    /**
     * Finds an appointment by its unique identifier (UUID) in a specified list of appointments.
     *
     * @param uuid the unique identifier of the appointment
     * @param appointments the list in which to search for the appointment
     * @return the appointment associated with the given UUID, or null if not found
     */
    public Appointment findAppointment(UUID uuid, List<Appointment> appointments) {
        return scheduler.findAppointment(uuid, appointments);
    }
}
