package hms;

import hms.users.*;

import java.util.ArrayList;
import java.util.Scanner;

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
     * Remove Doctor by ID
     * @param ID
     */
    public void removeDoctorByID(String ID) {
        for (Doctor doctor : doctors) {
            if (doctor.getID() == ID) {
                doctors.remove(doctor);
                break;
            }
        }
    }

    /**
     * Update Doctor by ID
     * @Param ID
     */
    public void updateDoctorByID(String ID) {
        Doctor curDoctor = null;
        for (Doctor doctor : doctors) {
            if (doctor.getID() == ID) {
                curDoctor = doctor;
                break;
            }
        }

        /* If there are no doctor found */
        if (curDoctor == null) {
            return;
        }

        /* Menu */
        System.out.println("-----Doctor Update Menu-----");
    	System.out.println("1.Update Name");
    	System.out.println("2.Update Gender");
        System.out.println("3.Update Age");

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();

        switch(choice) {
            case 1:
                System.out.println("Enter new name");
                String name = sc.next();
                curDoctor.setName(name);
                break;
            case 2:
                System.out.println("Enter new gender");
                int gender = sc.nextInt();
                curDoctor.setGender(gender);
                break;
            case 3:
                System.out.println("Enter new age");
                int age = sc.nextInt();
                curDoctor.setAge(age);
                break;
        }

        removeDoctorByID(ID);
        addDoctor(curDoctor);
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
     * Remove Patient by ID
     * @param ID
     */
    public void removePatientByID(String ID) {
        for (Patient patient : patients) {
            if (patient.getID() == ID) {
                patients.remove(patient);
                break;
            }
        }
    }

    /**
     * Update Patient by ID
     * @Param ID
     */
    public void updatePatientByID(String ID) {
        Patient curPatient = null;
        for (Patient patient : patients) {
            if (patient.getID() == ID) {
                curPatient = patient;
                break;
            }
        }

        /* If there are no doctor found */
        if (curPatient == null) {
            return;
        }

        curPatient = curPatient.updatePersonalInformation();

        removePatientByID(ID);
        addPatient(curPatient);
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
     * Remove Pharmacist by ID
     * @param ID
     */
    public void removePharmacistByID(String ID) {
        for (Pharmacist pharmacist : pharmacists) {
            if (pharmacist.getID() == ID) {
                pharmacists.remove(pharmacist);
                break;
            }
        }
    }

    /**
     * Update Pharmacist by ID
     * @Param ID
     */
    public void updatePharmacistByID(String ID) {
        Pharmacist curPharmacist = null;
        for (Pharmacist pharmacist : pharmacists) {
            if (pharmacist.getID() == ID) {
                curPharmacist = pharmacist;
                break;
            }
        }

        /* If there are no doctor found */
        if (curPharmacist == null) {
            return;
        }

        /* Menu */
        System.out.println("-----Pharmacist Update Menu-----");
    	System.out.println("1.Update Name");
    	System.out.println("2.Update Gender");
        System.out.println("3.Update Age");

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();

        switch(choice) {
            case 1:
                System.out.println("Enter new name");
                String name = sc.next();
                curPharmacist.setName(name);
                break;
            case 2:
                System.out.println("Enter new gender");
                int gender = sc.nextInt();
                curPharmacist.setGender(gender);
                break;
            case 3:
                System.out.println("Enter new age");
                int age = sc.nextInt();
                curPharmacist.setAge(age);
                break;
        }

        removePharmacistByID(ID);
        addPharmacist(curPharmacist);
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
     * Remove Administrators by ID
     * @param ID
     */
    public void removeAdministratorByID(String ID) {
        for (Administrator administrator : administrators) {
            if (administrator.getID() == ID) {
                administrators.remove(administrator);
                break;
            }
        }
    }

    /**
     * Update Administrator by ID
     * @Param ID
     */
    public void updateAdministratorByID(String ID) {
        Administrator curAdministrator = null;
        for (Administrator administrator : administrators) {
            if (administrator.getID() == ID) {
                curAdministrator = administrator;
                break;
            }
        }

        /* If there are no doctor found */
        if (curAdministrator == null) {
            return;
        }

        /* Menu */
        System.out.println("-----Administrator Update Menu-----");
    	System.out.println("1.Update Name");
    	System.out.println("2.Update Gender");
        System.out.println("3.Update Age");

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();

        switch(choice) {
            case 1:
                System.out.println("Enter new name");
                String name = sc.next();
                curAdministrator.setName(name);
                break;
            case 2:
                System.out.println("Enter new gender");
                int gender = sc.nextInt();
                curAdministrator.setGender(gender);
                break;
            case 3:
                System.out.println("Enter new age");
                int age = sc.nextInt();
                curAdministrator.setAge(age);
                break;
        }

        removeAdministratorByID(ID);
        addAdministrator(curAdministrator);
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

    /**
     * Find user by ID
     * @param user
     */
    public User getUserByID(String ID) {
        ArrayList<User> userlist = getUsers();
        for (User user : userlist) {
            if (user.getID() == ID) {
                return user;
            }
        }
        return null;
    }

    /**
     * Remove from userlist
     * @param User
     */
    public void removeUserByID(String ID) {
        User curUser = getUserByID(ID);
        switch (curUser.getRole()) {
            case 1:
                removePatientByID(ID);
                break;
            case 2:
                removeDoctorByID(ID);
                break;
            case 3:
                removePharmacistByID(ID);
                break;
            case 4:
                removeAdministratorByID(ID);
                break;
        }
    }
}
