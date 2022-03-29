package at.technikum.tourplanner.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
public class ToolsImpl implements Tools{

    /**
     * HASH STRING
     ***********************************************/
    protected static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    protected static String toHexString(byte[] hash) {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }

    @Override
    public String hashString(String text) {

        try {
            return toHexString(getSHA(text));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown for incorrect algorithm: " + e);
        }

        return text;
    }
    /**************************************************************/


    /**
     * DATE
     ******************************************************/
    // DATUM + UHRZEIT
    protected String formatDateTime(int format) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter df = null;

        if (format < 1 || format > 4) {
            format = 2;
        }

        switch (format) {
            case 1:
                df = DateTimeFormatter.BASIC_ISO_DATE;
                break;    // 20160131
            case 2:
                df = DateTimeFormatter.ISO_LOCAL_DATE;
                break;   // 2016-18-31
            case 3:
                df = DateTimeFormatter.ISO_DATE_TIME;
                break;   // 2016-01-31T20:07:07.095
            case 4:
                df = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm");
                break;     // 31.01.2016 20:07
        }

        return df.toString();
    }

    // DATUM
    public String formatDate(int format) {
        LocalDate date = LocalDate.now();
        DateTimeFormatter df = null;

        switch (format) {
            case 2:
                df = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
                break;      // Sonntag, 31. Januar 2016
            case 3:
                df = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
                break;     // 31. Januar 2016
            case 4:
                df = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
                break;    // 31.01.2016
            case 5:
                df = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
                break;   // 31.01.16
            default:
                return date.toString();   // 2016-01-31
        }

        return df.format(date);
    }
    /*************************************************************/




    /**
     * CONVERTING
     ***********************************************/
    // CONVERT TO NUMBER - Double
    public Double convertToDouble(String text) {
        return Double.parseDouble(text);
    }

    // CONVERT TO NUMBER - Integer
    public Integer convertToInt(String text) {
        return Integer.parseInt(text);
    }

    // CONVERT TO BOOLEAN
    public boolean convertToBoolean(String text) {
        return Boolean.parseBoolean(text);
    }

    /*************************************************************/

}