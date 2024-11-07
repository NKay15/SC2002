package hms;

import hms.users.*;

import java.util.*;

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
     * Menu to Remove Pharmacist by ID (for Admins)
     * @param ID
     */
    public boolean removeDoctorByIDMenu(String ID, Scanner sc) {
        for (Doctor doctor : doctors) {
            if (doctor.getID().equals(ID)) {
                System.out.println("Confirm to Remove Doctor?");
                System.out.print("Enter 1 to Confirm; or 2 to Cancel.\nEnter your choice: ");
                int choice;
                while (true) {
                    choice = sc.nextInt();
                    sc.nextLine();
                    switch (choice) {
                        case 1:
                            doctors.remove(doctor);
                            System.out.println("Pharmacist Successfully Removed! Returning to Menu...\n");
                            break;
                        case 2:
                            System.out.println("Operation Cancelled. Returning to Menu...\n");
                            break;
                        default:
                            System.out.print("Invalid choice! Enter your choice: ");
                            continue;
                    }
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Update Doctor by ID
     * @param ID
     * @param sc Scanner
     */
    public boolean updateDoctorByIDMenu(String ID, Scanner sc) {
        Doctor curDoctor = null;
        for (Doctor doctor : doctors) {
            if (doctor.getID().equals(ID)) {
                curDoctor = doctor;
                break;
            }
        }

        /* If there are no doctor found */
        if (curDoctor == null) {
            return false;
        }

        /* Menu */
        System.out.println("-----Doctor Update Menu-----");
        System.out.println("Doctor ID: " + curDoctor.getID());
    	System.out.println("1. Update Name");
    	System.out.println("2. Update Gender");
        System.out.println("3. Update Age");
        System.out.println("4. Cancel");
        System.out.println("----------------------------");
        System.out.print("Enter your choice: ");
        int choice;

        while (true) {
            choice = sc.nextInt(); sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Enter New Name: ");
                    String name = sc.nextLine();
                    curDoctor.setName(name);
                    break;

                case 2:
                    System.out.print("Enter New Gender: ");
                    int gender = sc.nextInt();
                    sc.nextLine();
                    curDoctor.setGender(gender);
                    break;

                case 3:
                    System.out.print("Enter New Age: ");
                    int age = sc.nextInt();
                    sc.nextLine();
                    curDoctor.setAge(age);
                    break;

                case 4:
                    System.out.println("Operation Cancelled. Returning to Menu...\n");
                    return true;

                default:
                    System.out.println("Invalid choice! Enter your choice: ");
                    continue;
            }
            break;
        }

        System.out.println("\nPlease ensure that all fields below are correct before confirming:\n");
        System.out.println("Doctor ID: " + curDoctor.getID());
        System.out.println("Name: " + curDoctor.getName());
        System.out.println("Gender: " + curDoctor.getGender());
        System.out.println("Age: " + curDoctor.getAge());
        System.out.println("\nConfirm to Update Details? Enter 1 to Confirm; or 2 to Cancel.");
        System.out.print("Enter your choice: ");

        while (true) {
            choice = sc.nextInt(); sc.nextLine();
            switch (choice) {
                case 1:
                    removeDoctorByID(ID);
                    addDoctor(curDoctor);
                    System.out.println("Doctor Successfully Updated! Returning to Menu...\n");
                    break;

                case 2:
                    System.out.println("Operation Cancelled. Returning to Menu...\n");
                    break;

                case 3:
                    System.out.print("Invalid Choice! Enter your choice: ");
                    continue;
            }
            return true;
        }
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
                doctors.remove(pharmacist);
                break;
            }
        }
    }

    /**
     * Menu to Remove Pharmacist by ID (for Admins)
     * @param ID
     * @param sc Scanner
     */
    public boolean removePharmacistByIDMenu(String ID, Scanner sc) {
        for (Pharmacist pharmacist : pharmacists) {
            if (pharmacist.getID().equals(ID)) {
                System.out.println("Confirm to Remove Pharmacist?");
                System.out.print("Enter 1 to Confirm; or 2 to Cancel.\nEnter your choice: ");
                int choice;
                while (true) {
                    choice = sc.nextInt();
                    sc.nextLine();
                    switch (choice) {
                        case 1:
                            pharmacists.remove(pharmacist);
                            System.out.println("Pharmacist Successfully Removed! Returning to Menu...\n");
                            break;
                        case 2:
                            System.out.println("Operation Cancelled. Returning to Menu...\n");
                            break;
                        default:
                            System.out.print("Invalid choice! Enter your choice: ");
                            continue;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Update Pharmacist by ID
     * @param ID
     * @param sc Scanner
     */
    public boolean updatePharmacistByIDMenu(String ID, Scanner sc) {
        Pharmacist curPharmacist = null;
        for (Pharmacist pharmacist : pharmacists) {
            if (pharmacist.getID().equals(ID)) {
                curPharmacist = pharmacist;
                break;
            }
        }

        /* If there are no doctor found */
        if (curPharmacist == null) {
            return false;
        }

        /* Menu */
        System.out.println("-----Pharmacist Update Menu-----");
        System.out.println("Pharmacist ID: " + curPharmacist.getID());
    	System.out.println("1.Update Name");
    	System.out.println("2.Update Gender");
        System.out.println("3.Update Age");
        System.out.println("4. Cancel");
        System.out.println("----------------------------");
        System.out.print("Enter your choice: ");
        int choice;

        while (true) {
            choice = sc.nextInt(); sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Enter New Name: ");
                    String name = sc.nextLine();
                    curPharmacist.setName(name);
                    break;

                case 2:
                    System.out.print("Enter New Gender: ");
                    int gender = sc.nextInt();
                    sc.nextLine();
                    curPharmacist.setGender(gender);
                    break;

                case 3:
                    System.out.print("Enter New Age: ");
                    int age = sc.nextInt();
                    sc.nextLine();
                    curPharmacist.setAge(age);
                    break;

                case 4:
                    System.out.println("Operation Cancelled. Returning to Menu...\n");
                    return true;

                default:
                    System.out.println("Invalid choice! Enter your choice: ");
                    continue;
            }
            break;
        }

        System.out.println("\nPlease ensure that all fields below are correct before confirming:\n");
        System.out.println("Pharmacist ID: " + curPharmacist.getID());
        System.out.println("Name: " + curPharmacist.getName());
        System.out.println("Gender: " + curPharmacist.getGender());
        System.out.println("Age: " + curPharmacist.getAge());
        System.out.println("\nConfirm to Update Details? Enter 1 to Confirm; or 2 to Cancel.");
        System.out.print("Enter your choice: ");

        while (true) {
            choice = sc.nextInt(); sc.nextLine();
            switch (choice) {
                case 1:
                    removePharmacistByID(ID);
                    addPharmacist(curPharmacist);
                    System.out.println("Pharmacist Successfully Updated! Returning to Menu...\n");
                    break;

                case 2:
                    System.out.println("Operation Cancelled. Returning to Menu...\n");
                    break;

                case 3:
                    System.out.print("Invalid Choice! Enter your choice: ");
                    continue;
            }
            return true;
        }
    }

    /**
     * Accesspr of Administrators
     * @return list of administrators
     */
    public ArrayList<Administrator> getAdministrators() {
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
     * Remove Administrator by ID
     * @param ID
     */
    public void removeAdministratorByID(String ID) {
        for (Administrator administrator : administrators) {
            if (administrator.getID().equals(ID)) {
                doctors.remove(administrator);
                break;
            }
        }
    }

    /**
     * Menu to Remove Pharmacist by ID (for Admins)
     * @param ID
     * @param sc Scanner
     */
    public boolean removeAdministratorByIDMenu(String ID, Scanner sc) {
        for (Administrator administrator : administrators) {
            if (administrator.getID().equals(ID)) {
                System.out.println("Confirm to Remove Administrator?");
                System.out.print("Enter 1 to Confirm; or 2 to Cancel.\nEnter your choice: ");
                int choice;
                while (true) {
                    choice = sc.nextInt(); sc.nextLine();
                    switch (choice) {
                        case 1:
                            administrators.remove(administrator);
                            System.out.println("Administrator Successfully Removed! Returning to Menu...\n");
                            break;
                        case 2:
                            System.out.println("Operation Cancelled. Returning to Menu...\n");
                            break;
                        default:
                            System.out.print("Invalid choice! Enter your choice: ");
                            continue;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Update Administrator by ID
     * @param ID
     * @param sc Scanner
     */
    public boolean updateAdministratorByIDMenu(String ID, Scanner sc) {
        Administrator curAdministrator = null;
        for (Administrator administrator : administrators) {
            if (administrator.getID().equals(ID)) {
                curAdministrator = administrator;
                break;
            }
        }

        /* If there are no doctor found */
        if (curAdministrator == null) {
            return false;
        }

        /* Menu */
        System.out.println("-----Administrator Update Menu-----");
        System.out.println("Administrator ID: " + curAdministrator.getID());
    	System.out.println("1.Update Name");
    	System.out.println("2.Update Gender");
        System.out.println("3.Update Age");
        System.out.println("4. Cancel");
        System.out.println("----------------------------");
        System.out.print("Enter your choice: ");
        int choice;

        while (true) {
            choice = sc.nextInt(); sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Enter New Name: ");
                    String name = sc.nextLine();
                    curAdministrator.setName(name);
                    break;

                case 2:
                    System.out.print("Enter New Gender: ");
                    int gender = sc.nextInt();
                    sc.nextLine();
                    curAdministrator.setGender(gender);
                    break;

                case 3:
                    System.out.print("Enter New Age: ");
                    int age = sc.nextInt();
                    sc.nextLine();
                    curAdministrator.setAge(age);
                    break;

                case 4:
                    System.out.println("Operation Cancelled. Returning to Menu...\n");
                    return true;

                default:
                    System.out.println("Invalid Choice! Enter your choice: ");
                    continue;
            }
            break;
        }

        System.out.println("\nPlease ensure that all fields below are correct before confirming:\n");
        System.out.println("Administrator ID: " + curAdministrator.getID());
        System.out.println("Name: " + curAdministrator.getName());
        System.out.println("Gender: " + curAdministrator.getGender());
        System.out.println("Age: " + curAdministrator.getAge());
        System.out.println("\nConfirm to Update Details? Enter 1 to Confirm; or 2 to Cancel.");
        System.out.print("Enter your choice: ");

        while (true) {
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    removeAdministratorByID(ID);
                    addAdministrator(curAdministrator);
                    System.out.println("Pharmacist Successfully Updated! Returning to Menu...\n");
                    break;

                case 2:
                    System.out.println("Operation Cancelled. Returning to Menu...\n");
                    break;

                case 3:
                    System.out.print("Invalid choice! Enter your choice: ");
                    continue;
            }
            return true;
        }
    }

    /**
     * Get all users (By selection)
     * @param sc Scanner
     * @return list of users
     */
    public ArrayList<User> getUsersSorted(Scanner sc) {
        System.out.println("-----Sort Users By-----");
    	System.out.println("1.Role");
    	System.out.println("2.Gender");
        System.out.println("3.Name");
        System.out.println("4.ID");

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
