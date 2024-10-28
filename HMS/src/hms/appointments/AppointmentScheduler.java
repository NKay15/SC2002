package hms.appointments;

import java.util.*;

import hms.users.*;
import hms.utils.Date;
import hms.utils.*;

public class AppointmentScheduler {
    private static AppointmentScheduler instance;
    private List<Appointment> appointments;
    private List<Appointment> pendingAppointments;

    private AppointmentScheduler() {
        this.appointments = new ArrayList<>();
        this.pendingAppointments = new ArrayList<>();
    }

    /**
     * Schedule a new appointment if the slot is available.
     *
     * @param appointment The appointment to be scheduled
     * @return true if scheduled successfully, false otherwise
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
     * @param pendingAppointment
     */
    public void acceptAppointment(Appointment pendingAppointment) {
        Appointment appointment = cancelAppointment(pendingAppointment, pendingAppointments);
        if (appointment == null) {
            System.out.println("No such appointmnet");
            return;
        }
        if (appointment.getStatus() == 5) {
            Appointment rescheduledAppointment = findAppointment(appointment.getRescheduled(), appointments);
            cancelAppointment(rescheduledAppointment, appointments);
        }
        appointment.confirm();
        appointments.add(appointment);
    }

    /**
     * @param pendingAppointment
     */
    public void declineAppointment(Appointment pendingAppointment) {
        Appointment appointment = findAppointment(pendingAppointment.getUuid(), pendingAppointments);
        if (appointment == null) return;
        if (appointment.getStatus() == 1) {
            appointment.cancel();
            System.out.println("Appointment rejected.");
            return;
        }
        findAppointment(appointment.getRescheduled().getUuid(), appointments).confirm();
        cancelAppointment(appointment, pendingAppointments);
    }

    /**
     * Reschedule an existing appointment to a new slot.
     *
     * @param existingAppointment The existing appointment to be rescheduled
     * @param newAppointment      The new appointment with updated time slot
     * @return true if rescheduled successfully, false otherwise
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
     * Cancel an existing appointment.
     *
     * @param appointment The appointment to be canceled
     * @return true if canceled successfully, false otherwise
     */

    public void cancelAppointment(Appointment appointment) {
        cancelAppointment(appointment, findWhichList(appointment));
    }

    public Appointment cancelAppointment(Appointment appointment, List<Appointment> appointmentList) {
        if (findAppointment(appointment.getUuid(), appointmentList) != null) {
            appointment.cancel();
            return appointment;
        }
        System.out.println("Can't find slot.");
        return null;
    }

    /**
     * Check if a slot is available for a certain appointment.
     *
     * @param appointment The appointment to check availability for
     * @return true if the slot is available, false otherwise
     */
    private boolean isSlotAvailable(Appointment appointment) {
        return isSlotAvailable(appointment.getDoctor(), appointment.getTimeSlot(), appointment.getDate());
    }

    /**
     * Check if a slot is available for a specific doctor at a specific time.
     *
     * @param doctor The ID of the doctor
     * @param time   The date and time of the appointment
     * @return true if the slot is available, false otherwise
     */

    private boolean isSlotAvailable(Doctor doctor, int time, Date date) {
        if (!Time.checkTime(time)) return false;
        for (Appointment appointment : appointments) {
            if (appointment.getDoctorID().equals(doctor.getDoctorID()) && appointment.getTimeSlot() == time && appointment.getDate().equals(date) && appointment.getStatus() == 2) {
                return false;
            }
        }
        return true;
    }

    /**
     * Find the index of an appointment in the list.
     *
     * @param appointment The appointment to search for
     * @return The index of the appointment if found, or -1 if not found
     */
    public Appointment findAppointment(Appointment appointment, List<Appointment> appointments) {
        return findAppointment(appointment.getUuid(), appointments);
    }


    private Appointment findAppointment(UUID uuid, List<Appointment> appointments) {
        for (Appointment appointment : appointments) {
            if (appointment.getUuid().equals(uuid)) {
                return appointment;
            }
        }
        return null;
    }


    public List<Appointment> findWhichList(Appointment appointment) {
        for (Appointment tempAppointment : appointments) {
            if (findAppointment(appointment, appointments) != null) return appointments;
        }
        for (Appointment tempApointment : pendingAppointments) {
            if (findAppointment(appointment, pendingAppointments) != null) return pendingAppointments;
        }
        System.out.println("Not in lists.");
        return null;
    }

    /**
     * Returns the number of appointments in the list.
     *
     * @return the number of appointments
     */
    public int getAppointmentsCount() {
        return appointments.size();
    }

    /**
     * Returns the number of pending appointments in the list.
     *
     * @return
     */
    public int getPendingAppointmentCount() {
        return pendingAppointments.size();
    }

    /**
     * @return instance of appointment scheduler
     */
    public static AppointmentScheduler getInstance() {
        if (instance == null) {
            instance = new AppointmentScheduler();
        }
        return instance;
    }

    /**
     * @return the list of appointments
     */
    public List<Appointment> getAppointments() {
        return appointments;
    }

    public List<Appointment> getPendingAppointments() {
        return pendingAppointments;
    }

    public List<Appointment> getAppointments(Patient patient) {
        List<Appointment> appointmentsForPatient = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getPatientID().equals(patient.getPatientID())) {
                appointmentsForPatient.add(appointment);
            }
        }
        return appointmentsForPatient;
    }

    public List<Appointment> getAppointments(Doctor doctor) {
        List<Appointment> appointmentsForDoctor = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getDoctorID().equals(doctor.getDoctorID())) {
                appointmentsForDoctor.add(appointment);
            }
        }
        return appointmentsForDoctor;
    }


    public List<Appointment> getPendingAppointments(Doctor doctor) {
        List<Appointment> pendingAppointmentsForDoctor = new ArrayList<>();
        for (Appointment appointment : pendingAppointments) {
            if (appointment.getDoctorID().equals(doctor.getDoctorID())) {
                pendingAppointmentsForDoctor.add(appointment);
            }
        }
        return pendingAppointmentsForDoctor;
    }

    /**
     * @param date
     * @param doctor
     */
    public void printAvailableSlot(Date date, Doctor doctor) {
        printAvailableSlot(date, new Doctor[]{doctor});
    }

    /**
     * print available slots, assume each slot is 30 min and schedule as shown.
     *
     * @param date
     * @param doctors
     */
    public void printAvailableSlot(Date date, Doctor[] doctors) {
        int startTime = 800;
        int endTime = 1800;
        int breakStart = 1200;
        int breakEnd = 1330;

        for (Doctor doctor : doctors) {
            System.out.println("Doctor ID: " + doctor.getDoctorID() + "'s avaialble slots are:");

            for (int time = startTime; time < endTime; time += 30) {
                if (time >= breakStart && time < breakEnd) {
                    if (time == breakStart) {
                        time = breakEnd - 30;
                    }
                    continue;
                }

                if (time % 100 == 60) time += 40;

                if (isSlotAvailable(doctor, time, date)) {
                    String slotTime = String.format("%02d:%02d", time / 100, time % 100);
                    System.out.println(slotTime);
                }
            }
            System.out.println("-----------------------------------");
        }
    }

}
