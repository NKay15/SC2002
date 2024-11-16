package hms.utils;

import java.security.SecureRandom;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * A class that handles TOTP for 2FA
 */
public class TOTP {
    /**
     * TOTP key
     */
    private String key;

    /**
     * Constants used in the class
     */
    private static long max = 9223372036854775807L, min = 8223372036854775806L;
    
    /**
     * Settings of TOTP
     */
    private int timeInverval = 30, totpLength = 6;

    /**
     * Constructor that will generate a random key
     */
    public TOTP() {
        try {
            long value = SecureRandom.getInstance("SHA1PRNG").nextLong(min, max);
            this.key = Base32.encode(value+"").substring(0,16);
        }
        catch (Exception e) {
            System.out.println("Error at TOTP constructor");
        
        }
    }

    /**
     * Constructor used if a key is known
     * @param key key of TOTP
     */
    public TOTP (String key) {
        this.key = key;
    }

    /**
     * Accessor of key
     * @return key in base32
     */
    public String getKey() {
        return key;
    }

    /**
     * Creates a one time password
     * @param time current time
     * @return one time password
     */
    public int generateTOTP(long time) {
        byte[] keyB = Base32.decode(key).getBytes();
        byte[] timeB = new byte[8];
        
        for(int i = 7; i >= 0; i--) {
            timeB[i] = (byte) (time & 0xFF);
            time >>= 8;
        }

        try {
            Mac hmac = Mac.getInstance("HmacSHA1");
            hmac.init(new SecretKeySpec(keyB, "HmacSHA1"));

            byte[] hash = hmac.doFinal(timeB);

            int offset = hash[hash.length - 1] & 0xF;

            long msb1 = (hash[offset] & 0x7F) << 24;
            long msb2 = (hash[offset+1] & 0xFF) << 16;
            long msb3 = (hash[offset+2] & 0xFF) << 8;
            long msb4 = hash[offset+3] & 0xFF;

            long bCode = msb1 | msb2 | msb3 | msb4;

            int totp = (int) (bCode % Math.pow(10, totpLength));
            return totp;
        }

        catch(Exception e) {
            System.out.println("No hmac algorithm exists");
            return -1;
        }
    }

    /**
     * Check is a one time password is correct
     * @param input one time password given
     * @return true if it is correct
     */
    public boolean validateTOTP(int input) {
        long time = System.currentTimeMillis() / 1000L / (long)timeInverval;

        return (input == generateTOTP(time) || input == generateTOTP(time+1) || input == generateTOTP(time-1));
    }
}

