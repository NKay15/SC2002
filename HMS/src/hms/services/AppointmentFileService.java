package hms.services;

import hms.appointments.Appointment;
import hms.utils.InputValidation;
import hms.appointments.AppointmentScheduler;

import java.io.*;
import java.util.*;


public class AppointmentFileService extends InputValidation {
    private static final String fileName = "HMS/src/data/Appointment_List.txt";
    private static List<Appointment> appointments;
    private static List<Appointment> pendingAppointments;

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
        appointmentScheduler.setLists(appointments,pendingAppointments);
    }

    private static void formatAppointment(String[] dataList) {
        Appointment appointment = new Appointment(
                UUID.fromString(dataList[0]),
                PatientFileService.getPatientByID(dataList[1]),
                DoctorFileService.getDoctorByID(dataList[2]),
                Integer.parseInt(dataList[3]),
                Integer.parseInt(dataList[4]),
                Integer.parseInt(dataList[5]),
                UUID.fromString(dataList[2]));
        if (appointment.getStatus() == 1) {
            pendingAppointments.add(appointment);
        } else {
            appointments.add(appointment);
        }
    }


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

    private static String formatAppointmentData(Appointment appointment) {
        return String.format("%s, %s, %s, %d, %d, %d, %s\n",
                appointment.getUuid().toString(),
                appointment.getPatient().getID(),
                appointment.getDoctor().getID(),
                appointment.getDate().getIntDate(),
                appointment.getTimeSlot().getIntTime(),
                appointment.getStatus(),
                (appointment.getRescheduled() == null ? "null" : appointment.getRescheduled().toString()));
    }
}
