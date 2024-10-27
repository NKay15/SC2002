package hms;

import hms.users.Patient;
import java.util.ArrayList;

public class UserList {
    /**
     * List of Doctors in the system
     */
    private ArrayList<Doctor> doctors;

    /**
     * List of Patients in the system
     */
    private ArrayList<Patient> patients;

    /**
     * List of Pharmacist in the system
     */
    private ArrayList<Pharmacist> pharmacists;

    /**
     * List of Administrator in the system
     */
    private ArrayList<Administrator> adminstrators;

    /**
     * Get/Set/Add Doctors
     */
    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }   

    public void setDoctors(Doctor doctors) {
        this.doctors = doctors;
    }

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    /**
     * Get/Set/Add Patients
     */
    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Patient patients) {
        this.patients = patients;
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }


    /**
     * Get/Set/Add Pharmacist
     */
    public ArrayList<Pharmacist> getPharmacist() {
        return pharmacists;
    }

    public void setPharmacist(Pharmacist pharmacists) {
        this.pharmacists = pharmacists;
    }

    public void addPharmacist(Pharmacist pharmacist) {
        pharmacists.add(pharmacist);
    }

    /**
     * Get/Set/Add Administrators
     */

    public ArrayList<Administrator> getAdministrator() {
        return adminstrators;
    }

    public void setAdministrator(Administrator administrators) {
        this.administrators = administrators;
    }

    public void addAdministrator(Administrato administrator) {
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
