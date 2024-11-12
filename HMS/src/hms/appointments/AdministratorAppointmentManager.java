package hms.appointments;

import java.util.List;
import java.util.UUID;

public class AdministratorAppointmentManager {
    private AppointmentScheduler scheduler = AppointmentScheduler.getInstance();
    public List<Appointment> getAppointments() {
        return scheduler.getAppointments();
    }

    public List<Appointment> getPendingAppointments() {
        return scheduler.getPendingAppointments();
    }

    public Appointment findAppointment(UUID uuid) {
        return scheduler.findAppointment(uuid);
    }
    public Appointment findAppointment(UUID uuid, List<Appointment> appointments) {
        return scheduler.findAppointment(uuid, appointments);
    }
}
