package hms.utils;

import java.security.SecureRandom;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class TOTP {
    private String key;
    private long max = 9223372036854775807L, min = 8223372036854775806L;
    private int timeInverval = 30, totpLength = 6;

    public TOTP() {
        try {
            long value = SecureRandom.getInstance("SHA1PRNG").nextLong(min, max);
            this.key = Base32.encode(value+"").substring(0,16);
        }
        catch (Exception e) {
            System.out.println("Error at TOTP constructor");
        
        }
    }

    public TOTP (String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

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

    public boolean validateTOTP(int input) {
        long time = System.currentTimeMillis() / 1000L / (long)timeInverval;

        return (input == generateTOTP(time) || input == generateTOTP(time+1) || input == generateTOTP(time-1));
    }
}

