package hms;

public class Medicine {

    /**
     * Name of the medicine
     */
    private String name;

    /**
     * Amount of medicine
     */
    private int amount;

    /**
     * Constructor of Medicine
     * @param name name of medicine
     * @param amount amount of medicine
     */
    public Medicine(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    /**
     * Accessor of name
     * @return name of medicine
     */
    public String name(){
        return this.name;
    }

    /**
     * Accessor of amount
     * @return amount of medicine
     */
    public int amount(){
        return this.amount;
    }

    /**
     * To be used by phramacist to give prescription
     * @param given amount of medince
     * @return true if successful otherwise false and amount remain unchange
     */
    public boolean prescribe(int given) {
        if(amount >= given){
            amount -= given;
            return true;
        }
        else return false;
    }

    public void restock(int quantity) {
        amount += quantity;
    }

    /**
     * Used by inventory to request restock
     * @param no number of medicine
     * @return medicine to be added to request
     */
    public Medicine copy(int no) {
        return new Medicine(name, no);
    }

    /**
     * Print medicine and amount
     */
    public void print() {
        System.out.println(name + " : " + amount);
    }
}
