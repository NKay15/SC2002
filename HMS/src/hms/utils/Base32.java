package hms.utils;

import java.lang.StringBuilder;

public class Base32 {
    public static int base32Toint(char c) {
        if((int)c >= 65 && (int)c <= 90) return (int)c - 65;
        else if((int)c >= 50 && (int)c <= 55) return (int)c - 24;
        else if(c == '=') return 32;
        else return -1;
    }

    /**
     * Converts an int to a base32 char
     * @param i
     * @return
     */
    public static char intToBase32(int i) {
        if(i >= 0 && i <= 25) return (char)(i+65);
        else if(i >= 26 && i <=31) return (char)(i+24);
        else if(i == 32) return '=';
        else return '\0';
    }

    public static String encode(String s) {
        StringBuilder ret = new StringBuilder();
        int buffer = 0;
        int length = 0;

        for(byte b : s.getBytes()) {
            buffer <<= 8;
            buffer |= b & 0xFF;
            length += 8;

            while(length >= 5) {
                int i = (buffer >> (length - 5) & 0x1F);
                ret.append(intToBase32(i));
                length -= 5;
            }
        }

        while(ret.length()%8 != 0) ret.append('=');

        return ret.toString();
    }

    public static String decode(String s) {
        s = s.replaceAll("=", "");
        StringBuilder ret = new StringBuilder();
        int buffer = 0;
        int length = 0;

        for(int i = 0; i < s.length(); i++) {
            buffer <<= 5;
            buffer |= base32Toint(s.charAt(i));
            length += 5;

            while(length >= 8) {
                byte b = (byte)(buffer >> (length-8));
                ret.append((char) b);
                length -= 8;
            }
        }

        return ret.toString();
    }
}
