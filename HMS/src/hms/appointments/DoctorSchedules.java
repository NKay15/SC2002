package hms.appointments;

import hms.utils.Date;
import hms.utils.Time;
import hms.users.*;

import java.util.*;

public class DoctorSchedules {

    private static DoctorSchedules instance;
    private List<DoctorSchedule> doctorSchedules;
    private Doctor doctor;

    public DoctorSchedules(Doctor doctor) {
        this.doctor = doctor;
        doctorSchedules = new ArrayList<DoctorSchedule>();
    }

    public void setDoctorSchedule() {
        DoctorSchedule doctorSchedule = new DoctorSchedule(doctor);
        doctorSchedules.add(doctorSchedule);
    }

    public List<DoctorSchedule> getDoctorSchedules() {
        return doctorSchedules;
    }

    public DoctorSchedule findDateSchedule(Date date) {
        for (DoctorSchedule doctorSchedule : doctorSchedules) {
            if (doctorSchedule.getDate().equals(date)) return doctorSchedule;
        }
        return null;
    }

    public boolean isDoctorAvailable(Date date, int time) {
        Time tempTime = new Time(time);
        return(isDoctorAvailable(date, tempTime));
    }

    public boolean isDoctorAvailable(Date date, Time time) {
        DoctorSchedule schedule = findDateSchedule(date);
        if (schedule == null) {
            return false;
        }
        return schedule.isDoctorAvailable(time);
    }


    public void printAvailableSlot(Date date) {
        System.out.println("Doctor ID: " + doctor.getDoctorID() + "'s avaialble slots are:");
        DoctorSchedule schedule = findDateSchedule(date);
        Time startTime = schedule.getStartTime();
        Time endTime = schedule.getEndTime();
        for (int time = startTime.getIntTime(); time < endTime.getIntTime(); time += 30) {
            if (time % 100 == 60) time += 40;
            if (isDoctorAvailable(date,time)) {
                String slotTime = String.format("%02d:%02d", time / 100, time % 100);
                System.out.println(slotTime);
            }
        }
        System.out.println("-----------------------------------");
    }

}
