package hms.utils;

/**
 * Enum for roles
 */
public enum Role {
    UNKNOWN("Unknown"),
    PATIENT("Patient"),
    DOCTOR("Doctor"),
    PHARMACIST("Pharmacist"),
    ADMINISTRATOR("Administrator");
    
    private String value;

    private Role(String value)
    {
        this.value = value;
    }

    public String toString()
    {
        return this.value;
    }
}
