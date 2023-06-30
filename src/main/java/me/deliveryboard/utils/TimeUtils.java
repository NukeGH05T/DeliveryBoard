package me.deliveryboard.utils;

import org.bukkit.Material;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtils {
    public static String formatEpochTime(long epochTime) {

        Date date = new Date(epochTime);

        String dateFormat = "HH:mm:ss"; // Customize the format as needed

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public static long convertToSeconds(String input) {
        //Splits time provided as 1d-2h-3m-4s
        String[] parts = input.split("-");

        long totalSeconds = 0;

        for (String part : parts) {
            char unit = part.charAt(part.length() - 1);
            int value = Integer.parseInt(part.substring(0, part.length() - 1));

            switch (unit) {
                case 's':
                    totalSeconds += value;
                    break;
                case 'm':
                    totalSeconds += value * 60;
                    break;
                case 'h':
                    totalSeconds += value * 60 * 60;
                    break;
                case 'd':
                    totalSeconds += value * 24 * 60 * 60;
                    break;
                default:
                    System.out.println("Invalid unit: " + unit);
            }
        }

        return totalSeconds;
    }
}

