package hms.appointments;

import hms.medicalRecords.AppointmentOutcomeRecord;
import hms.users.*;
import hms.utils.*;

import java.util.ArrayList;
import java.util.List;

public class DoctorScheduleManager {
    private Doctor doctor;
    private AppointmentScheduler scheduler = AppointmentScheduler.getInstance();
    private List<Appointment> appointmentList = new ArrayList<>();
    private List<Appointment> pendingList = new ArrayList<>();


    public DoctorScheduleManager(Doctor doctor) {
        this.doctor = doctor;
        appointmentList = scheduler.getAppointments(doctor);
        pendingList = scheduler.getPendingAppointments(doctor);
    }
    public void acceptAppointments(int appointmentId) {
        if (appointmentId >= 0 && appointmentId < pendingList.size())
            scheduler.acceptAppointment(appointmentId);
        else System.out.println("Slot not found.");
    }

    public void declineAppointments(int appointmentId) {
        if (appointmentId >= 0 && appointmentId < pendingList.size())
            scheduler.declineAppointment(appointmentId);
        else System.out.println("Slot not found.");
    }

    public void completeAppointments(Appointment appointment){
        appointment.complete();
        updateAppointmentOutcomeRecord(appointment);
    }

    public void updateAppointmentOutcomeRecord(Appointment appointment) {
        appointment.setAop();
    }

    public void printAvailableSlots(Date date, Doctor doctor) {
        scheduler.printAvailableSlot(date, doctor);
    }

    public void printPendingSlots(Doctor doctor) {
        for (Appointment appointment : pendingList) {
            System.out.println("Doctor ID: " + doctor.getDoctorID() + "'s pending slots are:");
            if (appointment.getDoctorID().equals(doctor.getDoctorID())) {
                int time = appointment.getTimeSlot();
                String slotTime = String.format("%02d:%02d", time / 100, time % 100);
                System.out.println(appointment.getDate().get() + slotTime);
            }
            System.out.println("-----------------------------------");
        }
    }

    public void printUpcomingSlots(Doctor doctor) {
        for (Appointment appointment : appointmentList) {
            if (appointment.getStatus() == 2) {
                System.out.println("Doctor ID: " + doctor.getDoctorID() + "'s upcoming slots are:");
                int time = appointment.getTimeSlot();
                String slotTime = String.format("%02d:%02d", time / 100, time % 100);
                System.out.println(appointment.getDate().get() + slotTime);
            }
        }
    }



}
