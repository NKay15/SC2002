package hms.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import hms.appointments.Appointment;
import hms.appointments.AppointmentScheduler;
import hms.medicalRecords.AppointmentOutcomeRecord;
import hms.pharmacy.Medicine;
import hms.utils.InputValidation;
import hms.utils.Date;

public class AOPFileService extends InputValidation {
    public static void loadAOP() {
        try {
            File myObj = new File("HMS/src/data/AOP_List.txt");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // Remove header line

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                Appointment appointment = AppointmentScheduler.getInstance().findAppointmentToWrite(dataList[0]);
                ArrayList<Medicine> prescriptionList = new ArrayList<Medicine>();

                String[] prescription = dataList[3].split("\\%");
                for(int i = 0; i < prescription.length-1; i += 2) {
                    prescriptionList.add(new Medicine(prescription[i], Integer.valueOf(prescription[i+1])));
                }
                String[] date = dataList[1].split("\\/");

                appointment.setAOP(new AppointmentOutcomeRecord(new Date(Integer.valueOf(date[0]), Integer.valueOf(date[1]), Integer.valueOf(date[2])), dataList[2], prescriptionList.toArray(new Medicine[prescriptionList.size()]), Integer.valueOf(dataList[4]), dataList[5]));
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred. Location AOPFileService");
        }
    }

    public static void writeAOP() {
        try {
            FileWriter fw = new FileWriter("HMS/src/data/AOP_List.txt");
            fw.write("UUID,Date,Service,prescription,Status,Notes\n");

            for(Appointment appointment : AppointmentScheduler.getInstance().getAppointments()) {
                if(appointment.getAop() == null) continue;
                fw.write(appointment.getUuid().toString() + "," + appointment.getAop().getDate().day() + "/" + appointment.getAop().getDate().month() + "/"  + appointment.getAop().getDate().year()+ "," + appointment.getAop().getService() + ",");
                for(int i = 0; i < appointment.getAop().getPrescription().length; i++) {
                    Medicine med = appointment.getAop().getPrescription()[i];
                    fw.write(med.name() + "%" + med.amount());
                    if(i < appointment.getAop().getPrescription().length - 1) fw.write("%");
                }
                fw.write("," + appointment.getAop().getStatus() + "," + appointment.getAop().getNotes());
            }

            fw.close();
        }
        catch (Exception e) {
            System.out.println("An error occurred. Location AOPFileService");
        }
    }
}
