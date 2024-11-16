package hms.services;

import hms.appointments.Appointment;
import hms.utils.InputValidation;
import hms.appointments.AppointmentScheduler;

import java.io.*;
import java.util.*;

/**
 * Service class for managing appointment data, including loading from and writing to a file.
 * It extends InputValidation to provide input validation functionalities.
 */
public class AppointmentFileService extends InputValidation {
    private static final String fileName = "HMS/src/data/Appointment_List.txt";
    private static List<Appointment> appointments;
    private static List<Appointment> pendingAppointments;

    /**
     * Loads appointments from a file and populates the provided AppointmentScheduler.
     *
     * @param appointmentScheduler the AppointmentScheduler to update with loaded appointments.
     */
    public static void loadAppointments(AppointmentScheduler appointmentScheduler) {
        appointments = new ArrayList<>();
        pendingAppointments = new ArrayList<>();
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // Remove header line
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");
                formatAppointment(dataList);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. Trace for Appointment File not found.");
        }
        appointmentScheduler.setLists(appointments, pendingAppointments);
    }

    /**
     * Formats appointment data from the given string array and adds it to the appropriate list.
     *
     * @param dataList array of strings containing appointment data.
     */
    private static void formatAppointment(String[] dataList) {
        UUID uuid = (dataList[6].equals("null")) ? null : UUID.fromString(dataList[6]);
        Appointment appointment = new Appointment(
                UUID.fromString(dataList[0]),
                PatientFileService.getPatientByID(dataList[1]),
                DoctorFileService.getDoctorByID(dataList[2]),
                Integer.parseInt(dataList[3]),
                Integer.parseInt(dataList[4]),
                Integer.parseInt(dataList[5]),
                uuid
        );
        if (appointment.getStatus() == 1) {
            pendingAppointments.add(appointment);
        } else {
            appointments.add(appointment);
        }
    }

    /**
     * Writes the current list of appointments and pending appointments to a file.
     *
     * @param appointmentScheduler the AppointmentScheduler containing the appointments to write.
     */
    public static void writeAppointments(AppointmentScheduler appointmentScheduler) {
        appointments = appointmentScheduler.getAppointments();
        pendingAppointments = appointmentScheduler.getPendingAppointments();
        try {
            FileWriter fw = new FileWriter(fileName);
            fw.write("uuid, patient, doctor, date, time, status, rescheduled\n");
            for (Appointment appointment : appointments) {
                fw.write(formatAppointmentData(appointment));
            }
            for (Appointment appointment : pendingAppointments) {
                fw.write(formatAppointmentData(appointment));
            }
            fw.close();
        } catch (Exception e) {
            System.out.println("An error occurred. Cannot write appointments.");
        }
    }

    /**
     * Formats appointment data into a comma-separated string for file writing.
     *
     * @param appointment the Appointment to format.
     * @return a string representation of the appointment data.
     */
    private static String formatAppointmentData(Appointment appointment) {
        return String.format("%s,%s,%s,%d,%d,%d,%s\n",
                appointment.getUuid().toString(),
                appointment.getPatient().getID(),
                appointment.getDoctor().getID(),
                appointment.getDate().getIntDate(),
                appointment.getTimeSlot().getIntTime(),
                appointment.getStatus(),
                (appointment.getRescheduled() == null ? "null" : appointment.getRescheduled().toString()));
    }
}
