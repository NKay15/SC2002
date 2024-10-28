package hms.appointments;

import java.util.*;

import hms.users.*;
import hms.utils.Date;

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
    public boolean scheduleAppointment(Appointment appointment) {
        if (isSlotAvailable(appointment)) {
            System.out.println("Successfully scheduled. Waiting for the doctor.");
            pendingAppointments.add(appointment);
            return true;
        } else {
            System.out.println("Current slot is not available.");
            return false;
        }
    }

    /**
     * I'm not sure whether this works, will test later
     *
     * @param appointmentId
     * @return
     */
    public boolean acceptAppointment(int appointmentId) {
        Appointment appointment = pendingAppointments.remove(appointmentId);
        appointment.confirm();
        appointments.add(appointment);
        System.out.println("Appointment accepted.");
        return true;
    }

    /**
     * the doctor declined appointment
     *
     * @param appointmentId
     * @return
     */
    public boolean declineAppointment(int appointmentId) {
        Appointment appointment = pendingAppointments.remove(appointmentId);
        appointment.cancel();
        System.out.println("Appointment rejected.");
        return true;
    }

    /**
     * Reschedule an existing appointment to a new slot.
     *
     * @param existingAppointment The existing appointment to be rescheduled
     * @param newAppointment      The new appointment with updated time slot
     * @return true if rescheduled successfully, false otherwise
     */
    public boolean rescheduleAppointment(Appointment existingAppointment, Appointment newAppointment) {
        if (isSlotAvailable(newAppointment)) {
            Appointment tempAppointment = cancelAppointment(existingAppointment);
            if (tempAppointment != null) {
                scheduleAppointment(newAppointment);
                System.out.println("Successfully rescheduled.");
                tempAppointment.reschedule();
                appointments.add(tempAppointment);
                pendingAppointments.add(tempAppointment);
                return true;
            }
            System.out.println("No exisitng appointment found. Make sure it's not in the pending state.");
            return false;
        }
        System.out.println("New time slot is not available.");
        return false;
    }

    /**
     * Cancel an existing appointment.
     *
     * @param appointment The appointment to be canceled
     * @return true if canceled successfully, false otherwise
     */

    public Appointment cancelAppointment(Appointment appointment) {
        List<Appointment> targetList = appointment.getStatus() == 1 ? pendingAppointments : appointments;
        int appointmentNum = findAppointment(appointment, targetList);
        if (appointmentNum != -1) {
            System.out.println("Successfully cancelled.");
            return targetList.remove(appointmentNum);
        } else {
            System.out.println("Can't find slot.");
            return null;
        }
    }

    /**
     * Check if a slot is available for a certain appointment.
     *
     * @param appointment The appointment to check availability for
     * @return true if the slot is available, false otherwise
     */
    private boolean isSlotAvailable(Appointment appointment) {
        return isSlotAvailable(appointment.getDoctorID(), appointment.getTimeSlot(), appointment.getDate());
    }

    /**
     * Check if a slot is available for a specific doctor at a specific time.
     *
     * @param doctorID The ID of the doctor
     * @param time     The date and time of the appointment
     * @return true if the slot is available, false otherwise
     */

    private boolean isSlotAvailable(String doctorID, int time, Date date) {
        for (Appointment appointment : appointments) {
            if (appointment.getDoctorID().equals(doctorID) && appointment.getTimeSlot() == time && appointment.getDate().equals(date)) {
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
    private int findAppointment(Appointment appointment, List<Appointment> appointments) {
        return findAppointment(appointment.getPatientID(), appointment.getTimeSlot(), appointments);
    }

    /**
     * Find the index of an appointment in the list by patient ID and date.
     *
     * @param patientID The ID of the patient
     * @param time      The date and time of the appointment
     * @return The index of the appointment if found, or -1 if not found
     */
    private int findAppointment(String patientID, int time, List<Appointment> appointments) {
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            if (appointment.getPatientID().equals(patientID) && appointment.getTimeSlot() == time) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @param date
     * @param doctor
     */
    public void printAvailableSlot(Date date, Doctor doctor) {
        // Single doctor version calls the array version
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

                if (isSlotAvailable(doctor.getDoctorID(), time, date)) {
                    String slotTime = String.format("%02d:%02d", time / 100, time % 100);
                    System.out.println(slotTime);
                }
            }
            System.out.println("-----------------------------------");
        }
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

    public List<Appointment> getAppointments(Doctor doctor) {
        List<Appointment> appointmentsForDoctor = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getDoctorID().equals(doctor.getDoctorID())) {
                appointmentsForDoctor.add(appointment);
            }
        }
        return appointmentsForDoctor;
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

    public List<Appointment> getPendingAppointments(Doctor doctor) {
        List<Appointment> pendingAppointmentsForDoctor = new ArrayList<>();
        for (Appointment appointment : pendingAppointments) {
            if (appointment.getDoctorID().equals(doctor.getDoctorID())) {
                pendingAppointmentsForDoctor.add(appointment);
            }
        }
        return pendingAppointmentsForDoctor;
    }

}
