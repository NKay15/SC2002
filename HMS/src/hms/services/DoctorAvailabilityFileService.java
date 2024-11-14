package hms.services;

import hms.appointments.DoctorSchedules;
import hms.appointments.DoctorSchedule;
import hms.users.Doctor;
import hms.utils.Date;
import hms.utils.InputValidation;
import hms.utils.Time;

import java.io.*;
import java.util.*;


public class DoctorAvailabilityFileService extends InputValidation {
    private static final String fileName = "HMS/src/data/Doctor_Availability_List.txt";
    private static DoctorSchedules schedules;

    public static void loadSchedulesFromFile(Doctor doctor) {
        schedules = new DoctorSchedules(doctor);
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // Remove header line
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");
                formatDoctorSchedule(dataList);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. Trace for Doctor Availability File not found.");
        }
        doctor.setDoctorSchedules(schedules);
    }

    private static void formatDoctorSchedule(String[] dataList) {
        List<Time[]> breaks = new ArrayList<>();
        if (!dataList[4].equals("0")){
            String[] breakParts = dataList[5].split(";");
            for (String part : breakParts) {
                String[] times = part.split("-");
                breaks.add(new Time[]{new Time(Integer.parseInt(times[0])), new Time(Integer.parseInt(times[1]))});
            }
        }
        DoctorSchedule schedule = new DoctorSchedule(
                DoctorFileService.getDoctorByID(dataList[0]),
                new Date(Integer.parseInt(dataList[1])),
                new Time(Integer.parseInt(dataList[2])),
                new Time(Integer.parseInt(dataList[3])),
                Integer.parseInt(dataList[4]), breaks);

        schedules.addSchedule(schedule);
    }


    public static void writeSchedulesToFile(DoctorSchedules schedules) {
        try {
            FileWriter fw = new FileWriter(fileName);
            fw.write("doctor, date, startTime, endTime, breakCount, breaks\n");
            for (DoctorSchedule schedule : schedules.getDoctorSchedules()) {
                fw.write(schedule.getDoctor().getID() + ",");
                fw.write(schedule.getDate().getIntDate() + ",");
                fw.write(schedule.getStartTime().getIntTime() + ",");
                fw.write(schedule.getEndTime().getIntTime() + ",");
                fw.write(schedule.getBreakCount() + ",");
                for (int i = 0; i < schedule.getBreakCount(); i++) {
                    if (i > 0) fw.write(";");
                    fw.write(schedule.getBreaks().get(i)[0].getIntTime() + "-" +
                            schedule.getBreaks().get(i)[1].getIntTime());
                }
                fw.write("\n");
            }
            fw.close();
        } catch (Exception e) {
            System.out.println("An error occurred. Cannot write doctor availability list.");
        }
    }

    public static void writeSchedulesToFile(List<Doctor> doctors) {
        for (Doctor doctor : doctors) {
            writeSchedulesToFile(doctor.getDoctorSchedules());
        }
    }
}
