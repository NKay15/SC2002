package hms.utils;

/**
 * Enum for blood type
 */
public enum BloodType {
    UNKNOWN("Unknown"),
    A_PLUS("A+"),
    A_MINUS("A-"),
    B_PLUS("B+"),
    B_MINUS("B-"),
    AB_PLUS("AB+"),
    AB_MINUS("AB-"),
    O_PLUS("O+"),
    O_MINUS("O-");
    
    private String value;

    private BloodType(String value)
    {
        this.value = value;
    }

    public String toString()
    {
        return this.value;
    }
}
