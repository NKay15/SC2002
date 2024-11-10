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
     * @param adminUsing Administrator Using Remove Operation
     */
    public boolean removeDoctorByIDMenu(String ID, Administrator adminUsing) {
        Scanner sc = GlobalData.getInstance().sc;
        for (Doctor doctor : doctors) {
            if (doctor.getID().equals(ID)) {
                System.out.println("\nPlease ensure that all fields below are correct before confirming:");
                System.out.println("Doctor ID: " + doctor.getID());
                System.out.println("Name: " + doctor.getName());
                System.out.println("Gender: " + doctor.getGenderString());
                System.out.println("Age: " + doctor.getAge());
                System.out.print("\nEnter your Password to Confirm (0 to Cancel): ");
                String password = sc.nextLine();
                while (!adminUsing.checkPassword(password)) {
                    if (password.equals("0")) {
                        System.out.println("Operation Cancelled. Returning to Menu...");
                        return true;
                    }
                    else {
                        System.out.print("Wrong Password! Try again: ");
                        password = sc.nextLine();
                    }
                }
                doctors.remove(doctor);
                System.out.println("Doctor Successfully Removed! Returning to Menu...");
                return true;
            }
        }
        return false;
    }

    /**
     * Update Doctor by ID
     * @param ID
     * @param adminUsing Administrator Using Update Operation
     */
    public boolean updateDoctorByIDMenu(String ID, Administrator adminUsing) {
        Scanner sc = GlobalData.getInstance().sc;
        Doctor doctor = null;
        for (Doctor curDoctor : doctors) {
            if (curDoctor.getID().equals(ID)) {
                doctor = curDoctor;
                break;
            }
        }
        /* If there is no doctor found */
        if (doctor == null) return false;

        String newDoctorID = doctor.getID();
        String newDoctorName = doctor.getName();
        int newDoctorGender = doctor.getGender();
        String newDoctorGenderString = doctor.getGenderString();
        int newDoctorAge = doctor.getAge();
        int changeWhat;

        /* Menu */
        System.out.println("\n-----Doctor Update Menu-----");
        System.out.println("Doctor ID: " + doctor.getID());
        System.out.println("Name: " + doctor.getName());
        System.out.println("Gender: " + doctor.getGenderString());
        System.out.println("Age: " + doctor.getAge());
    	System.out.println("\n1. Update Name");
    	System.out.println("2. Update Gender");
        System.out.println("3. Update Age");
        System.out.println("4. Cancel");
        System.out.println("----------------------------");
        System.out.print("Enter your choice: ");
        String choice;

        while (true) {
            choice = sc.next(); sc.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Current Name: " + doctor.getName());
                    System.out.print("Enter New Name: ");
                    newDoctorName = sc.nextLine();
                    changeWhat = 1;
                    break;

                case "2":
                    System.out.println("Current Gender: " + doctor.getGenderString());
                    System.out.print("Enter New Gender (0: Unknown; 1: Male; 2: Female): ");
                    newDoctorGenderString = sc.next();
                    sc.nextLine();
                    while (true) {
                        switch (newDoctorGenderString) {
                            case "0":
                            case "Unknown":
                            case "unknown":
                                newDoctorGender = 0;
                                newDoctorGenderString = "Unknown";
                                break;
                            case "1":
                            case "Male":
                            case "male":
                                newDoctorGender = 1;
                                newDoctorGenderString = "Male";
                                break;
                            case "2":
                            case "Female":
                            case "female":
                                newDoctorGender = 2;
                                newDoctorGenderString = "Female";
                                break;
                            default:
                                System.out.print("Invalid choice! Try again: ");
                                newDoctorGenderString = sc.next();
                                sc.nextLine();
                                continue;
                        }
                        break;
                    }
                    changeWhat = 2;
                    break;

                case "3":
                    System.out.println("Current Age: " + doctor.getAge());
                    System.out.print("Enter New Age: ");
                    newDoctorAge = sc.nextInt();
                    sc.nextLine();
                    changeWhat = 3;
                    break;

                case "4":
                    System.out.println("Operation Cancelled. Returning to Menu...");
                    return true;

                default:
                    System.out.print("Invalid choice! Try again: ");
                    continue;
            }
            break;
        }

        System.out.println("\nPlease ensure that all fields below are correct before confirming:");
        System.out.println("Doctor ID: " + newDoctorID);
        System.out.println("Name: " + newDoctorName);
        System.out.println("Gender: " + newDoctorGenderString);
        System.out.println("Age: " + newDoctorAge);
        System.out.print("\nEnter your Password to Confirm (0 to Cancel): ");
        String password = sc.nextLine();
        while (!adminUsing.checkPassword(password)) {
            if (password.equals("0")) {
                System.out.println("Operation Cancelled. Returning to Menu...");
                return true;
            }
            else {
                System.out.print("Wrong Password! Try again: ");
                password = sc.nextLine();
            }
        }
        switch (changeWhat) {
            case 1:
                doctor.setName(newDoctorName);
                break;
            case 2:
                doctor.setGender(newDoctorGender);
                break;
            case 3:
                doctor.setAge(newDoctorAge);
                break;
        }
        System.out.println("Doctor Successfully Updated! Returning to Menu...");
        return true;
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
     * @param adminUsing Administrator Using Remove Operation
     */
    public boolean removePharmacistByIDMenu(String ID, Administrator adminUsing) {
        Scanner sc = GlobalData.getInstance().sc;
        for (Pharmacist pharmacist : pharmacists) {
            if (pharmacist.getID().equals(ID)) {
                System.out.println("\nPlease ensure that all fields below are correct before confirming:");
                System.out.println("Pharmacist ID: " + pharmacist.getID());
                System.out.println("Name: " + pharmacist.getName());
                System.out.println("Gender: " + pharmacist.getGenderString());
                System.out.println("Age: " + pharmacist.getAge());
                System.out.print("\nEnter your Password to Confirm (0 to Cancel): ");
                String password = sc.nextLine();
                while (!adminUsing.checkPassword(password)) {
                    if (password.equals("0")) {
                        System.out.println("Operation Cancelled. Returning to Menu...");
                        return true;
                    }
                    else {
                        System.out.print("Wrong Password! Try again: ");
                        password = sc.nextLine();
                    }
                }
                pharmacists.remove(pharmacist);
                System.out.println("Pharmacist Successfully Removed! Returning to Menu...");
                return true;
            }
        }
        return false;
    }

    /**
     * Update Pharmacist by ID
     * @param ID
     * @param adminUsing Administrator Using Update Operation
     */
    public boolean updatePharmacistByIDMenu(String ID, Administrator adminUsing) {
        Scanner sc = GlobalData.getInstance().sc;
        Pharmacist pharmacist = null;
        for (Pharmacist curPharmacist : pharmacists) {
            if (curPharmacist.getID().equals(ID)) {
                pharmacist = curPharmacist;
                break;
            }
        }
        /* If there are is doctor found */
        if (pharmacist == null) return false;

        String newPharmacistID = pharmacist.getID();
        String newPharmacistName = pharmacist.getName();
        int newPharmacistGender = pharmacist.getGender();
        String newPharmacistGenderString = pharmacist.getGenderString();
        int newPharmacistAge = pharmacist.getAge();
        int changeWhat;

        /* Menu */
        System.out.println("\n-----Pharmacist Update Menu-----");
        System.out.println("Pharmacist ID: " + pharmacist.getID());
        System.out.println("Name: " + pharmacist.getName());
        System.out.println("Gender: " + pharmacist.getGender());
        System.out.println("Age: " + pharmacist.getAge());
    	System.out.println("\n1. Update Name");
    	System.out.println("2. Update Gender");
        System.out.println("3. Update Age");
        System.out.println("4. Cancel");
        System.out.println("----------------------------");
        System.out.print("Enter your choice: ");
        String choice;

        while (true) {
            choice = sc.next(); sc.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Current Name: " + pharmacist.getName());
                    System.out.print("Enter New Name: ");
                    newPharmacistName = sc.nextLine();
                    changeWhat = 1;
                    break;

                case "2":
                    System.out.println("Current Gender: " + pharmacist.getGender());
                    System.out.print("Enter New Gender (0: Unknown; 1: Male; 2: Female): ");
                    newPharmacistGenderString = sc.next();
                    sc.nextLine();
                    while (true){
                        switch (newPharmacistGenderString) {
                            case "0":
                            case "Unknown":
                            case "unknown":
                                newPharmacistGender = 0;
                                newPharmacistGenderString = "Unknown";
                                break;
                            case "1":
                            case "Male":
                            case "male":
                                newPharmacistGender = 1;
                                newPharmacistGenderString = "Male";
                                break;
                            case "2":
                            case "Female":
                            case "female":
                                newPharmacistGender = 2;
                                newPharmacistGenderString = "Female";
                                break;
                            default:
                                System.out.print("Invalid choice! Try again: ");
                                newPharmacistGenderString = sc.next();
                                sc.nextLine();
                                continue;
                        }
                        break;
                    }
                    changeWhat = 2;
                    break;

                case "3":
                    System.out.println("Current Age: " + pharmacist.getAge());
                    System.out.print("Enter New Age: ");
                    newPharmacistAge = sc.nextInt();
                    sc.nextLine();
                    changeWhat = 3;
                    break;

                case "4":
                    System.out.println("Operation Cancelled. Returning to Menu...");
                    return true;

                default:
                    System.out.print("Invalid choice! Enter your choice: ");
                    continue;
            }
            break;
        }

        System.out.println("\nPlease ensure that all fields below are correct before confirming:");
        System.out.println("Pharmacist ID: " + newPharmacistID);
        System.out.println("Name: " + newPharmacistName);
        System.out.println("Gender: " + newPharmacistGenderString);
        System.out.println("Age: " + newPharmacistAge);
        System.out.print("\nEnter your Password to Confirm (0 to Cancel): ");
        String password = sc.nextLine();
        while (!adminUsing.checkPassword(password)) {
            if (password.equals("0")) {
                System.out.println("Operation Cancelled. Returning to Menu...");
                return true;
            }
            else {
                System.out.print("Wrong Password! Try again: ");
                password = sc.nextLine();
            }
        }
        switch (changeWhat) {
            case 1:
                pharmacist.setName(newPharmacistName);
            case 2:
                pharmacist.setGender(newPharmacistGender);
            case 3:
                pharmacist.setAge(newPharmacistAge);
        }
        System.out.println("Pharmacist Successfully Updated! Returning to Menu...");
        return true;
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
     * @param adminUsing Administrator Using Remove Operation
     */
    public boolean removeAdministratorByIDMenu(String ID, Administrator adminUsing) {
        if (adminUsing.getID().equals(ID)) return false;
        Scanner sc = GlobalData.getInstance().sc;
        for (Administrator administrator : administrators) {
            if (administrator.getID().equals(ID)) {
                System.out.println("\nPlease ensure that all fields below are correct before confirming:");
                System.out.println("Administrator ID: " + administrator.getID());
                System.out.println("Name: " + administrator.getName());
                System.out.println("Gender: " + administrator.getGenderString());
                System.out.println("Age: " + administrator.getAge());
                System.out.print("\nEnter your Password to Confirm (0 to Cancel): ");
                String password = sc.nextLine();
                while (!adminUsing.checkPassword(password)) {
                    if (password.equals("0")) {
                        System.out.println("Operation Cancelled. Returning to Menu...");
                        return true;
                    }
                    else {
                        System.out.print("Wrong Password! Try again: ");
                        password = sc.nextLine();
                    }
                }
                administrators.remove(administrator);
                System.out.println("Administrator Successfully Removed! Returning to Menu...");
                return true;
            }
        }
        return false;
    }

    /**
     * Update Administrator by ID
     * @param ID
     * @param adminUsing Administrator Using Update Operation
     */
    public boolean updateAdministratorByIDMenu(String ID, Administrator adminUsing) {
        Scanner sc = GlobalData.getInstance().sc;
        Administrator administrator = null;
        for (Administrator curAdministrator : administrators) {
            if (curAdministrator.getID().equals(ID)) {
                administrator = curAdministrator;
                break;
            }
        }
        /* If there is no administrator found */
        if (administrator == null) return false;

        String newAdministratorID = administrator.getID();
        String newAdministratorName = administrator.getName();
        int newAdministratorGender = administrator.getGender();
        String newAdministratorGenderString = administrator.getGenderString();
        int newAdministratorAge = administrator.getAge();
        int changeWhat;

        /* Menu */
        System.out.println("\n-----Administrator Update Menu-----");
        System.out.println("Administrator ID: " + administrator.getID());
        System.out.println("Name: " + administrator.getName());
        System.out.println("Gender: " + administrator.getGender());
        System.out.println("Age: " + administrator.getAge());
    	System.out.println("\n1. Update Name");
    	System.out.println("2. Update Gender");
        System.out.println("3. Update Age");
        System.out.println("4. Cancel");
        System.out.println("----------------------------");
        System.out.print("Enter your choice: ");
        String choice;

        while (true) {
            choice = sc.next(); sc.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Current Name: " + administrator.getName());
                    System.out.print("Enter New Name: ");
                    newAdministratorName = sc.nextLine();
                    changeWhat = 1;
                    break;

                case "2":
                    System.out.println("Current Gender: " + administrator.getGender());
                    System.out.print("Enter New Gender (0: Unknown; 1: Male; 2: Female): ");
                    newAdministratorGenderString = sc.next();
                    sc.nextLine();
                    while (true){
                        switch (newAdministratorGenderString) {
                            case "0":
                            case "Unknown":
                            case "unknown":
                                newAdministratorGender = 0;
                                newAdministratorGenderString = "Unknown";
                                break;
                            case "1":
                            case "Male":
                            case "male":
                                newAdministratorGender = 1;
                                newAdministratorGenderString = "Male";
                                break;
                            case "2":
                            case "Female":
                            case "female":
                                newAdministratorGender = 2;
                                newAdministratorGenderString = "Female";
                                break;
                            default:
                                System.out.print("Invalid choice! Try again: ");
                                newAdministratorGenderString = sc.next();
                                sc.nextLine();
                                continue;
                        }
                        break;
                    }
                    changeWhat = 2;
                    break;

                case "3":
                    System.out.println("Current Age: " + administrator.getAge());
                    System.out.print("Enter New Age: ");
                    newAdministratorAge = sc.nextInt();
                    sc.nextLine();
                    changeWhat = 3;
                    break;

                case "4":
                    System.out.println("Operation Cancelled. Returning to Menu...");
                    return true;

                default:
                    System.out.print("Invalid Choice! Enter your choice: ");
                    continue;
            }
            break;
        }

        System.out.println("\nPlease ensure that all fields below are correct before confirming:");
        System.out.println("Administrator ID: " + newAdministratorID);
        System.out.println("Name: " + newAdministratorName);
        System.out.println("Gender: " + newAdministratorGenderString);
        System.out.println("Age: " + newAdministratorAge);
        System.out.print("\nEnter your Password to Confirm (0 to Cancel): ");
        String password = sc.nextLine();
        while (!adminUsing.checkPassword(password)) {
            if (password.equals("0")) {
                System.out.println("Operation Cancelled. Returning to Menu...");
                return true;
            }
            else {
                System.out.print("Wrong Password! Try again: ");
                password = sc.nextLine();
            }
        }
        switch (changeWhat){
            case 1:
                administrator.setName(newAdministratorName);
                break;
            case 2:
                administrator.setGender(newAdministratorGender);
                break;
            case 3:
                administrator.setAge(newAdministratorAge);
                break;
        }
        System.out.println("Administrator Successfully Updated! Returning to Menu...");
        return true;
    }

    /**
     * Get all users (By selection)
     * @param choice Choice of sorting
     * @return list of users
     */
    public ArrayList<User> getStaffSorted(int choice) {
        switch (choice) {
            case 1:
                return getStaffRoleSorted();
            case 2:
                return getStaffIDSorted();
            case 3:
                return getStaffNameSorted();
            case 4:
                return getStaffGenderSorted();
            case 5:
                return getStaffAgeSorted();
            default:
                return null;
        }
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
     * Get all staff by role
     * @return list of staff
     */
    public ArrayList<User> getStaffRoleSorted() {
        ArrayList<User> userArray = new ArrayList<User>();
        userArray.addAll(doctors);
        userArray.addAll(pharmacists);
        userArray.addAll(administrators);
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
     * Get all staff by Name
     * @return list of users
     */
    public ArrayList<User> getStaffNameSorted() {
        ArrayList<User> userArray = new ArrayList<User>();
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
     * Get all staff by ID
     * @return list of users
     */
    public ArrayList<User> getStaffIDSorted() {
        ArrayList<User> userArray = new ArrayList<User>();
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
     * Get all staff by gender
     * @return list of users
     */
    public ArrayList<User> getStaffGenderSorted() {
        ArrayList<User> userArray = new ArrayList<User>();
        userArray.addAll(doctors);
        userArray.addAll(pharmacists);
        userArray.addAll(administrators);

        // Sort by name in ascending order
        Collections.sort(userArray, new Comparator<User>() {
            @Override
            public int compare(User p1, User p2) {
                if (p1 instanceof Staff && p2 instanceof Staff) {
                    return Integer.compare(p1.getGender(), p2.getGender());
                }
                else return 0;
            }
        });

        return userArray;
    }

    /**
     * Get all staff by age
     * @return list of users
     */
    public ArrayList<User> getStaffAgeSorted() {
        ArrayList<User> userArray = new ArrayList<User>();
        userArray.addAll(doctors);
        userArray.addAll(pharmacists);
        userArray.addAll(administrators);

        // Sort by name in ascending order
        Collections.sort(userArray, new Comparator<User>() {
            @Override
            public int compare(User p1, User p2) {
                if (p1 instanceof Staff && p2 instanceof Staff) {
                    return Integer.compare(p1.getAge(), p2.getAge());
                }
                else return 0;
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
