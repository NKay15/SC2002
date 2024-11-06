package hms;

import hms.users.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

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
    private ArrayList<Administrator> administrators;

    /**
     * Constructor
     */
    public UserList() {
    	patients = new ArrayList<Patient>();
        pharmacists = new ArrayList<Pharmacist>();
        administrators = new ArrayList<Administrator>();
        doctors = new ArrayList<Doctor>();
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
            if (doctor.getID().equals(ID)) {
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
            if (doctor.getID().equals(ID)) {
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
            if (patient.getID().equals(ID)) {
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
            if (patient.getID().equals(ID)) {
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
    public ArrayList<Pharmacist> getPharmacists() {
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
            if (pharmacist.getID().equals(ID)) {
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
            if (pharmacist.getID().equals(ID)) {
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
            if (administrator.getID().equals(ID)) {
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
            if (administrator.getID().equals(ID)) {
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
     * Get all users (By selection)
     * @return list of users
     */
    public ArrayList<User> getUsersSorted() {
        System.out.println("-----Sort Users By-----");
    	System.out.println("1.Role");
    	System.out.println("2.Gender");
        System.out.println("3.Name");
        System.out.println("4.ID");

        Scanner sc = new Scanner(System.in);
        int choice = 0;

        do {
            choice = sc.nextInt();
            switch(choice) {
                case 1:
                    return getUsersRoleSorted();
                case 2:
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Input is invalid");
                    break;
            }
        } while (choice<1 || choice>3);

        return null;
    }

    /**
     * Get all users by role
     * @return list of users
     */
    public ArrayList<User> getUsersRoleSorted() {
        ArrayList<User> userArray = new ArrayList<User>();
        userArray.addAll(patients);
        userArray.addAll(doctors);
        userArray.addAll(pharmacists);
        userArray.addAll(administrators);
        return userArray;
    }

    /**
     * Get all users by gender
     * @return list of users
     */
    public ArrayList<User> getUsersGenderSorted() {
        ArrayList<User> userArray = new ArrayList<User>();
        ArrayList<User> temAr1 = new ArrayList<User>();
        ArrayList<User> temAr2 = new ArrayList<User>();
        ArrayList<User> temAr3 = new ArrayList<User>();

        for (Patient patient : patients) {
            if (patient.getGender() == 1) {
                temAr1.add(patient);
            } else if (patient.getGender() == 2) {
                temAr2.add(patient);
            } else {
                temAr3.add(patient);
            }
        }

        for (Doctor doctor : doctors) {
            if (doctor.getGender() == 1) {
                temAr1.add(doctor);
            } else if (doctor.getGender() == 2) {
                temAr2.add(doctor);
            } else {
                temAr3.add(doctor);
            }
        }

        for (Pharmacist pharmacist : pharmacists) {
            if (pharmacist.getGender() == 1) {
                temAr1.add(pharmacist);
            } else if (pharmacist.getGender() == 2) {
                temAr2.add(pharmacist);
            } else {
                temAr3.add(pharmacist);
            }
        }

        for (Administrator administrator : administrators) {
            if (administrator.getGender() == 1) {
                temAr1.add(administrator);
            } else if (administrator.getGender() == 2) {
                temAr2.add(administrator);
            } else {
                temAr3.add(administrator);
            }
        }

        userArray.addAll(temAr1);
        userArray.addAll(temAr2);
        userArray.addAll(temAr3);

        return userArray;
    }

    /**
     * Get all users by Name
     * @return list of users
     */
    public ArrayList<User> getUsersNameSorted() {
        ArrayList<User> userArray = new ArrayList<User>();
        userArray.addAll(patients);
        userArray.addAll(doctors);
        userArray.addAll(pharmacists);
        userArray.addAll(administrators);

        // Sort by name in ascending order
        Collections.sort(userArray, new Comparator<User>() {
            @Override
            public int compare(User p1, User p2) {
                return p1.getName().compareTo(p2.getName());
            }
        });

        return userArray;
    }

    /**
     * Get all users by ID
     * @return list of users
     */
    public ArrayList<User> getUsersIDSorted() {
        ArrayList<User> userArray = new ArrayList<User>();
        userArray.addAll(patients);
        userArray.addAll(doctors);
        userArray.addAll(pharmacists);
        userArray.addAll(administrators);

        // Sort by name in ascending order
        Collections.sort(userArray, new Comparator<User>() {
            @Override
            public int compare(User p1, User p2) {
                return p1.getID().compareTo(p2.getID());
            }
        });

        return userArray;
    }

    /**
     * Find user by ID
     * @param ID
     */
    public User getUserByID(String ID) {
        ArrayList<User> userlist = getUsersRoleSorted();
        for (User user : userlist) {
            if (user.getID() == ID) {
                return user;
            }
        }
        return null;
    }

    /**
     * Remove from userlist
     * @param ID
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
