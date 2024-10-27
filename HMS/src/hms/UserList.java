package hms;

import hms.users.Patient;
import hms.users.Doctor;
import hms.users.Pharmacist;
import hms.users.Administrator;

import java.util.ArrayList;

public class UserList {
    /**
     * List of Doctors in the system
     */
    private ArrayList<Doctor> doctors = new ArrayList<Doctor>();

    /**
     * List of Patients in the system
     */
    private ArrayList<Patient> patients = new ArrayList<Patient>();

    /**
     * List of Pharmacist in the system
     */
    private ArrayList<Pharmacist> pharmacists = new ArrayList<Pharmacist>();

    /**
     * List of Administrator in the system
     */
    private ArrayList<Administrator> administrators = new ArrayList<Administrator>();

    /**
     * Constructor
     */
    public UserList() {
    	
    }
    
    /**
     * Accessor of Doctors
     * @return
     */
    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }   

    /**
     * Setter of Doctors
     * @param doctors
     */
    public void setDoctors(ArrayList<Doctor> doctors) {
        this.doctors = doctors;
    }

    /**
     * Add Doctor
     * @param doctor
     */
    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    /**
     * Accessor of Patients
     * @return list of Patients
     */
    public ArrayList<Patient> getPatients() {
        return patients;
    }

    /**
     * Setter of Patients
     * @param patients
     */
    public void setPatients(ArrayList<Patient> patients) {
        this.patients = patients;
    }

    /**
     * Add Patient
     * @param patient
     */
    public void addPatient(Patient patient) {
        patients.add(patient);
    }


    /**
     * Accessor of Pharmacist
     * @return list of Pharamacists
     */
    public ArrayList<Pharmacist> getPharmacist() {
        return pharmacists;
    }

    /**
     * Setter of Administrators
     * @param pharmacists
     */
    public void setPharmacist(ArrayList<Pharmacist> pharmacists) {
        this.pharmacists = pharmacists;
    }
    
    /**
     * Add Pharmacist
     * @param pharmacist
     */
    public void addPharmacist(Pharmacist pharmacist) {
        pharmacists.add(pharmacist);
    }

    /**
     * Accesspr of Administrators
     * @return list of administrators
     */
    public ArrayList<Administrator> getAdministrator() {
        return administrators;
    }

    /**
     * Setter of Administrators
     * @param administrators
     */
    public void setAdministrator(ArrayList<Administrator> administrators) {
        this.administrators = administrators;
    }

    /**
     * Add Administrators
     * @param administrator
     */
    public void addAdministrator(Administrator administrator) {
        administrators.add(administrator);
    }

    /**
     * Get all users
     */
    public ArrayList<User> getUsers() {
        ArrayList<User> userArray = new ArrayList<User>();
        userArray.addAll(doctors);
        userArray.addAll(patients);
        userArray.addAll(pharmacists);
        userArray.addAll(administrators);
        return userArray;
    }
}
