package hms.pharmacy;

import hms.GlobalData;
import hms.medicalRecords.AppointmentOutcomeRecord;
import hms.services.InventoryFileService;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class of the medicine inventory that manage the inventory
 */
public class Inventory {

    /**
     * List of medicine in storage
     */
    private ArrayList<Medicine> catalog;

    /**
     * Low level stock level
     */

    private ArrayList<Medicine> lowlevel;

    /**
     * List of medicine that have been requested to be restocked
     */
    private ArrayList<Medicine> requests;

    /**
     * Return the index of a medicine
     * @param check medicine to find
     * @return index of medicine in catalog -1 if not present
     */
    private int findIndex(Medicine check) {
        for(int i = 0; i < catalog.size(); i++) {
            if(catalog.get(i).name().equals(check.name())) return i;
        }

        return -1;
    }

    /**
     * Return the index of a medicine
     * @param check medicine name to find
     * @return index of medicine in catalog -1 if not present
     */
    private int findIndex(String check) {
        for(int i = 0; i < catalog.size(); i++) {
            if(catalog.get(i).name().equals(check)) return i;
        }

        return -1;
    }

    /**
     * Return whether requests is empty (for admin)
     * @return true if empty, false otherwise
     */
    public boolean isRequestsEmpty() { return requests.isEmpty(); }

    /**
     * Constructor for Inventory
     */
    public Inventory() {
        catalog = new ArrayList<Medicine>();
        requests = new ArrayList<Medicine>();
        lowlevel = new ArrayList<Medicine>();
    }

    /**
     * Adding new medicine to the inventory
     */
    public void addNewMedicine() {
        Scanner sc = GlobalData.getInstance().sc;
        boolean alreadyExists = true;
        System.out.print("Enter Name of New Medicine: ");
        String name = InventoryFileService.nextLine();

        while(alreadyExists) {
            alreadyExists = false;
            for (int i = 0; i < catalog.size(); i++) {
                if (name.equals(catalog.get(i).name())) {
                    System.out.println("Medicine Already Exists in the Catalog!");
                    System.out.println("Enter Name of New Medicine: ");
                    name = sc.nextLine();
                    alreadyExists = true;
                }
            }
        }

        System.out.println("Continue to Add New Medicine \"" + name + "\"?");
        System.out.println("Enter 1 to Continue; or Enter anything else to Cancel.");
        System.out.print("Enter your choice: ");
        String continueToAdd = sc.next(); sc.nextLine();
        if (!continueToAdd.equals("1")) {
            System.out.println("Operation Cancelled.");
            return;
        }

        System.out.print("Enter Initial Quantity: ");
        int amount = sc.nextInt(); sc.nextLine();
        System.out.print("Enter Low Level Alert: ");
        int level = sc.nextInt(); sc.nextLine();

        String confirmAdding = "1";
        while (true) {
            if (confirmAdding.equals("1") || confirmAdding.equals("2") || confirmAdding.equals("3") || confirmAdding.equals("4")) {
                System.out.println("\nPlease ensure that all fields below are correct before confirming:");
                System.out.println("Name: " + name);
                System.out.println("Initial Quantity: " + amount);
                System.out.println("Low Level Alert: " + level);
                System.out.println("\nConfirm to Add New Medicine?");
                System.out.println("Enter 1 to Confirm; 2 to Cancel; " +
                        "3 to Edit Initial Quantity; or 4 to Edit Low Level.");
                System.out.print("Enter your choice: ");
            }
            confirmAdding = sc.next(); sc.nextLine();

            switch (confirmAdding) {
                case "1":
                    catalog.add(new Medicine(name, amount));
                    lowlevel.add(new Medicine(name, level));
                    System.out.println("New Medicine \"" + name + "\" Successfully Added to Inventory!");
                    return;

                case "2":
                    System.out.println("Operation Cancelled.");
                    return;

                case "3":
                    System.out.print("Enter Initial Quantity: ");
                    amount = sc.nextInt(); sc.nextLine();
                    System.out.println("Initial Quantity has been Modified.");
                    break;

                case "4":
                    System.out.print("Enter Low Level Alert: ");
                    level = sc.nextInt(); sc.nextLine();
                    System.out.println("Low Level Alert has been Modified.");
                    break;

                default:
                    System.out.print("Invalid choice! Try again: ");
                    break;
            }
        }
    }

    /**
     * Adding new medicine to the inventory when data is available to pass in
     * @param name name of medicine
     * @param amount amount of medicine
     * @param level low level alert
     * @return true if successfully added. false when it is already present in the inventory and not added in
     */
    public boolean addNewMedicine(String name, int amount, int level) {
        for(int i = 0; i < catalog.size(); i++) {
            if(name.equals(catalog.get(i).name())) {
                return false;
            }
        }

        catalog.add(new Medicine(name, amount));
        lowlevel.add(new Medicine(name, level));
        return true;
    }

    /**
     * Used by administrator to add quantity of medication
     */
    public void updateStockLevelMenu() {
        Scanner sc = GlobalData.getInstance().sc;
        printCurrentInventory();
        System.out.print("Enter Index of Medicine (0 to Cancel): ");
        int med = sc.nextInt(); sc.nextLine();
        while(med < 1 || med > catalog.size()) {
            if (med == 0) {
                System.out.println("Operation Cancelled.");
                return;
            }
            System.out.print("Invalid input! Try again: ");
            med = sc.nextInt(); sc.nextLine();
        }
        System.out.print("Enter Quantity of Restock (0 to Cancel): ");
        int quantity = sc.nextInt(); sc.nextLine();
        while(quantity < 1) {
            if (quantity == 0) {
                System.out.println("Operation Cancelled.");
                return;
            }
            System.out.print("Invalid input! Try again: ");
            quantity = sc.nextInt(); sc.nextLine();
        }
        System.out.println("\nConfirm to Update Stock Level?");
        System.out.println("Enter 1 to Confirm; or 2 to Cancel.");
        System.out.print("Enter your choice: ");
        String confirmAdding;

        while (true) {
            confirmAdding = sc.next(); sc.nextLine();
            switch (confirmAdding) {
                case "1":
                    catalog.get(med-1).restock(quantity);
                    System.out.println("Stock Level Successfully Restocked!");
                    break;

                case "2":
                    System.out.println("Operation Cancelled.");
                    break;

                default:
                    System.out.print("Invalid choice! Try again: ");
                    continue;
            }
            return;
        }
    }

    /**
     * Used by pharmacist to dispense medication
     * @param aop AppointmentOutcomeRecord of the appointment
     * @return true if successful false otherwise
     */
    public boolean dispense(AppointmentOutcomeRecord aop) {
        if(aop.isDispensed()) return false;

        for(Medicine n : aop.getPrescription()) {
            int idx = findIndex(n);
            if(catalog.get(idx).amount() < n.amount()){
                return false;
            }
        }

        for(Medicine n : aop.getPrescription()) {
            int idx = findIndex(n);
            catalog.get(idx).prescribe(n.amount());

            if(catalog.get(idx).amount() <= lowlevel.get(idx).amount())
                System.out.println(catalog.get(idx).name() + "is at Low amount of "
                        + catalog.get(idx).amount() + ". Please Restock!");
        }

        aop.dispense();
        return true;
    }

    /**
     * Used by pharmacist to request a restock
     */
    public void createRequest() {
        Scanner sc = GlobalData.getInstance().sc;
        System.out.println("Current Inventory:");
        GlobalData.getInstance().inventory.printCurrentInventory();
        System.out.print("Enter Index of Medicine (0 to Cancel): ");
        int med = sc.nextInt(); sc.nextLine();
        while(med < 1 || med > catalog.size()) {
            if (med == 0) {
                System.out.println("Operation Cancelled.");
                return;
            }
            System.out.print("Invalid input! Try again: ");
            med = sc.nextInt(); sc.nextLine();
        }
        System.out.print("Enter Quantity of Medicine (0 to Cancel): ");
        int quantity = sc.nextInt(); sc.nextLine();
        while(quantity < 1) {
            if (quantity == 0) {
                System.out.println("Operation Cancelled.");
                return;
            }
            System.out.print("Invalid input! Try again: ");
            quantity = sc.nextInt(); sc.nextLine();
        }

        System.out.println("Confirm to Submit Restock Request?");
        System.out.println("Enter 1 to Confirm; or 2 to Cancel.");
        System.out.print("Enter your choice: ");
        String confirmAdding;

        while (true) {
            confirmAdding = sc.next(); sc.nextLine();
            switch (confirmAdding) {
                case "1":
                    requests.add(catalog.get(med-1).copy(quantity));
                    System.out.println("Restock Request Successfully Created!");
                    break;

                case "2":
                    System.out.println("Operation Cancelled.");
                    break;

                default:
                    System.out.print("Invalid choice! Try again: ");
                    continue;
            }
            return;
        }
    }

    /**
     * add request
     * @param name name of medicine
     * @param amount amount of medicine
     * @return true if added successfully and false otherwise
     */
    public boolean addRequest(String name, int amount){
        int i = findIndex(name);
        if(i == -1) return false;
        else {
            requests.add(new Medicine(name, amount));
            return true;
        }
    }

    /**
     * Use by doctor to generate prescription
     * @return Medicine to be prescribe
     */
    public Medicine[] generatePrescription(){
        System.out.println("\nCurrent Inventory, for your reference:");
        printCurrentInventory();
        ArrayList<Medicine> ret = new ArrayList<Medicine>();
        int med = 1;
        int quantity;
        
        while(med > 0 && med <= catalog.size()) {
            System.out.print("Enter Index of Medicine to Prescribe (0 to Finish): ");
            Scanner sc = GlobalData.getInstance().sc;
            med = sc.nextInt(); sc.nextLine();
            if(med == 0) break;
            while(med < 0 || med > catalog.size()) {
                System.out.print("Invalid input! Try again: ");
                med = sc.nextInt(); sc.nextLine();
            }

            int i;
            for(i = 0; i < ret.size(); i++) {
                if(ret.get(i).name().equals(catalog.get(med-1).name())) break;
            }

            if(i == ret.size()) {
                System.out.print("Enter Amount to Prescribe: ");
                quantity = sc.nextInt();
                ret.add(new Medicine(catalog.get(med-1).name(), quantity));
            }
            else {
                System.out.print("Enter New Amount to Prescribe: ");
                quantity = sc.nextInt();
                ret.get(i).setAmount(quantity);
            }
        }

        Medicine[] arr = new Medicine[ret.size()];
        for(int i = 0; i < ret.size(); i++) {
            arr[i] = ret.get(i);
        }

        return arr;
    }

    /**
     * Change the low level amount of a medicine
     */
    public void setNewLowLevel() {
        Scanner sc = GlobalData.getInstance().sc;
        printCurrentInventory();
        System.out.print("Enter Index of Medicine (0 to Cancel): ");
        int med = sc.nextInt(); sc.nextLine();
        if (med == 0) {
            System.out.println("Operation Cancelled.");
            return;
        }
        while(med < 0 || med > catalog.size()) {
            System.out.print("Invalid input! Try again: ");
            med = sc.nextInt(); sc.nextLine();
        }
        System.out.print("Enter New Low Level (0 to Cancel): ");
        int quantity = sc.nextInt(); sc.nextLine();
        if (quantity == 0) {
            System.out.println("Operation Cancelled.");
            return;
        }
        while(quantity < 0) {
            System.out.print("Invalid input! Try again: ");
            quantity = sc.nextInt(); sc.nextLine();
        }
        lowlevel.get(med-1).prescribe(lowlevel.get(med-1).amount());
        lowlevel.get(med-1).restock(quantity);
        System.out.println("Low Level Alert Successfully Updated!");
    }

    /**
     * Used by ... to manage restock
     */
    public void manageRestockRequests() {
        if (requests.isEmpty()) {
            return;
        }
        System.out.println("Pending Restock Requests:");
        printRestockRequest();
        System.out.print("Enter Index of Request: ");
        Scanner sc = GlobalData.getInstance().sc;
        int req = sc.nextInt(); sc.nextLine();
        while(req < 1 || req > requests.size()) {
            System.out.print("Invalid input! Try again: ");
            req = sc.nextInt(); sc.nextLine();
        }
        int idx = findIndex(requests.get(req-1));
        System.out.print("Enter 1 to Exit; 2 to Approve; 3 to Reject.\nEnter your choice: ");
        String choice;
        while (true) {
            choice = sc.next(); sc.nextLine();
            switch (choice) {
                case "1":
                    System.out.println();
                    return;
                case "2":
                    catalog.get(idx).restock(requests.get(req - 1).amount());
                    requests.remove(req - 1);
                    System.out.println("Request Successfully Approved!");
                    return;
                case "3":
                    requests.remove(req - 1);
                    System.out.println("Request Successfully Rejected!");
                    return;
                default:
                    System.out.print("Invalid choice! Try again: ");
                    break;
            }
        }
    }

    /**
     * Print current inventory
     */
    public void printCurrentInventory() {
        System.out.println("---------------------");
        for(int i = 0; i < catalog.size(); i++) {
            System.out.print((i+1) + ". " + catalog.get(i).name() + ": " + catalog.get(i).amount());
            if(catalog.get(i).amount() <= lowlevel.get(i).amount())
                System.out.println(" **LOW, PLEASE RESTOCK!**");
            else System.out.println(" (LL: " + lowlevel.get(i).amount() + ")");
        }
        System.out.println("---------------------");
    }

    /**
     * Print restock request
     */
    public void printRestockRequest() {
        System.out.println("---------------------");
        for(int i = 0; i < requests.size(); i++) {
            System.out.println((i+1) + ". " + requests.get(i).name() + ": " + requests.get(i).amount());
        }
        System.out.println("---------------------");
    }

    /**
     * accessor of the size of catalog 
     * @return  size of catalog
     */
    public int getSize() {
        return catalog.size();
    }

    /**
     * accessor of the name of medicine
     * @param i index of medicine
     * @return name of medicine
     */
    public String getName(int i) {
        if(i < 0 || i >= catalog.size()) return "\0";
        return catalog.get(i).name();
    }

    /**
     * accessor of the quantity of medicine
     * @param i index of medicine
     * @return quantity of medicine
     */
    public int getAmount(int i) {
        if(i < 0 || i >= catalog.size()) return -1;
        return catalog.get(i).amount();
    }

    /**
     * accessor of the lowlevel of medicine
     * @param i index of medicine
     * @return lowlevel of medicine
     */
    public int getLowLevel(int i) {
        if(i < 0 || i >= catalog.size()) return -1;
        return lowlevel.get(i).amount();
    }

    /**
     * accessor of the size of request 
     * @return  size of request
     */
    public int getRequestSize() {
        return requests.size();
    }

    /**
     * accessor of the name of medicine in request
     * @param i index of medicine
     * @return name of medicine
     */
    public String getRequestName(int i) {
        if(i < 0 || i >= requests.size()) return "\0";
        return requests.get(i).name();
    }

    /**
     * accessor of the request quantity of medicine
     * @param i index of medicine
     * @return quantity of medicine
     */
    public int getRequestAmount(int i) {
        if(i < 0 || i >= requests.size()) return -1;
        return requests.get(i).amount();
    }
}
