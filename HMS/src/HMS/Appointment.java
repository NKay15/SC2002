package hms;

public class Appointment {
    /**
     * ID of patient
     */
    private String patientID;

    /**
     * ID of doctor
     */
    private String doctorID;

    /**
     * Integer to store the status of appointment 1 - pending 2 - confirmed 3 - canceled 4 - completed
     */
    private int status;

    /**
     * Date of appointment
     */
    private Date date;

    /**
     * Time slot of appointment
     */
    private int timeSlot;

    /**
     * Appointmet Outcome Record to be created after appointment is completed
     */
    private AppointmentOutcomeRecord aop;

    /**
     * Constructor of appointment
     * @param patient ID of patient
     * @param doctor ID of doctor
     * @param date Date of appointment
     * @param time Time Slot of appointment
     */
    public Appointment(String patient, String doctor, Date date, int time) {
        patientID = patient;
        doctorID = doctor;
        this.date = date;
        timeSlot = time;
        status = 1;
        aop = null;
    }

    /**
     * Accessor of patientID
     * @return patientID
     */
    public String getpatientID() {
        return patientID;
    }

    /**
     * Accessor of doctorID
     * @return doctorID
     */
    public String getdoctorID() {
        return doctorID;
    }

    /**
     * Accessor of date
     * @return date
     */
    public Date getdate() {
        return date;
    }

    public int gettimeSlot() {
        return timeSlot;
    }

    /**
     * Change the date and time of appointment
     * @param date
     * @param time
     */
    public void changeDate(Date date, int time) {
        this.date = date;
        timeSlot = time;
    }

    /**
     * For doctor to accept the appointmemt
     */
    public void confirm() {
        status = 2;
    }

    /**
     * For canceling the appointment
     */
    public void cancel() {
        status = 3;
    }

    /**
     * Change the appointment to complete and generate the appointment outcome record
     */
    public void complete() {
        status = 4;
        aop = new AppointmentOutcomeRecord();
    }

    /**
     * Print the status of the appointment
     */
    public void printStatus() {
        switch (status) {
            case 1 : System.out.println("pending");
            break;
            case 2 : System.out.println("confirmed");
            break;
            case 3 : System.out.println("canceled");
            break;
            case 4 : System.out.println("completed");
            break;
        }
    }

    /**
     * Print the Appointment Outcome Record if appointment is completed
     */
    public void printAOP() {
        if(status == 4) aop.print();
        else System.out.println("Appointment is not completed");
    }
}
