package me.nukeghost.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtils {

    static public final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";
    public static final char COLOR_CHAR = '\u00A7';

    /**
     * @param text The string of text to apply color/effects to
     * @return Returns a string of text with color/effects applied
     */
    public static String translateColorCodes(String text) {

        if (text == null) Bukkit.getLogger().warning("[DB] " + "Missing text input for: " + ChatColor.YELLOW + text);
        String[] texts = text.split(String.format(WITH_DELIMITER, "&"));

        StringBuilder finalText = new StringBuilder();

        for (int i = 0; i < texts.length; i++){
            if (texts[i].equalsIgnoreCase("&")){
                //get the next string
                i++;
                if (texts[i].charAt(0) == '#'){
                    finalText.append(net.md_5.bungee.api.ChatColor.valueOf(texts[i].substring(0, 7)) + texts[i].substring(7));
                }else{
                    finalText.append(ChatColor.translateAlternateColorCodes('&', "&" + texts[i]));
                }
            }else{
                finalText.append(texts[i]);
            }
        }

        return finalText.toString();
    }

    /**
     * @param textList The string of text to apply color/effects to
     * @return Returns a string of text with color/effects applied
     */
    public static List<String> translateColorCodes(List<String> textList) {

        List<String> outputList = new ArrayList<>();
        for (String text : textList) {
            String[] texts = text.split(String.format(WITH_DELIMITER, "&"));

            StringBuilder finalText = new StringBuilder();

            for (int i = 0; i < texts.length; i++) {
                if (texts[i].equalsIgnoreCase("&")) {
                    //get the next string
                    i++;
                    if (texts[i].charAt(0) == '#') {
                        finalText.append(net.md_5.bungee.api.ChatColor.valueOf(texts[i].substring(0, 7)) + texts[i].substring(7));
                    } else {
                        finalText.append(ChatColor.translateAlternateColorCodes('&', "&" + texts[i]));
                    }
                } else {
                    finalText.append(texts[i]);
                }
            }

            outputList.add(finalText.toString());
        }
        return outputList;
    }

    /*
    Credit: Elementeral
    https://www.spigotmc.org/members/elementeral.717560/
     */
    public static String translateHexColorCodes(String startTag, String endTag, String message) {
        final Pattern hexPattern = Pattern.compile(startTag + "([A-Fa-f0-9]{6})" + endTag);
        Matcher matcher = hexPattern.matcher(message);
        StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);
        while (matcher.find())
        {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, COLOR_CHAR + "x"
                    + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
            );
        }
        return matcher.appendTail(buffer).toString();
    }

    public static List<String> translateHexColorCodes(String startTag, String endTag, List<String> messages) {
        List<String> stringList = new ArrayList<>();
        for (String message : messages) {
            final Pattern hexPattern = Pattern.compile(startTag + "([A-Fa-f0-9]{6})" + endTag);
            Matcher matcher = hexPattern.matcher(message);
            StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);
            while (matcher.find()) {
                String group = matcher.group(1);
                matcher.appendReplacement(buffer, COLOR_CHAR + "x"
                        + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                        + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                        + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
                );
            }
            stringList.add(matcher.appendTail(buffer).toString());
        }
        return stringList;
    }

}

