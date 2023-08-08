package me.nukeghost.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static me.nukeghost.DeliveryBoard.*;

public class PlaceholderUtils {

    public static List<String> parsePlaceholders(List<String> inputs, Player p) {
        List<String> outputList = new ArrayList<>();
        for (String input : inputs) {
            outputList.add(parsePlaceholders(input, p));
        }
        return outputList;
    }

    public static List<String> parsePlaceholders(List<String> inputs, Player p, String timeString, int index) {
        List<String> outputList = new ArrayList<>();
        for (String input : inputs) {
            outputList.add(parsePlaceholders(input, p, timeString, index));
        }
        return outputList;
    }

    public static String parsePlaceholders(String input, Player p) {
        Pattern pattern = Pattern.compile("\\{([A-Z_]+)\\}");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String placeholder = matcher.group(0); // e.g., {PLAYER_NAME}, {PLAYER_X}, etc.
            String placeholderKey = matcher.group(1); // e.g., PLAYER_NAME, PLAYER_X, etc.

            // Perform actions based on the placeholder key
            switch (placeholderKey) {
                case "PLAYER_NAME":
                    // Get the player's display name
                    String playerName = ChatColor.stripColor(p.getName()); // Replace with your logic to retrieve the player's name
                    input = input.replace(placeholder, playerName);
                    break;
                case "PLAYER_X":
                    // Get the player's X position
                    double playerX = p.getLocation().getX(); // Replace with your logic to retrieve the player's X position
                    input = input.replace(placeholder, String.valueOf(playerX));
                    break;
                case "PLAYER_Y":
                    // Get the player's Y position
                    double playerY = p.getLocation().getY(); // Replace with your logic to retrieve the player's Y position
                    input = input.replace(placeholder, String.valueOf(playerY));
                    break;
                case "PLAYER_Z":
                    // Get the player's Z position
                    double playerZ = p.getLocation().getZ(); // Replace with your logic to retrieve the player's Z position
                    input = input.replace(placeholder, String.valueOf(playerZ));
                    break;
                case "BOARD_LOC":
                    String configBoardLocation = plugin.getConfig().getString("board-location");
                    input = input.replace(placeholder, String.valueOf(configBoardLocation));
                    break;
            }
        }
        return input;
    }

    /**
     * @param input The string to parse
     * @param p Player who completed the delivery
     * @param timeString Applicable for duration calculation in footer OR to get delivery name
     * @param deliveryIndex Applicable for current/max submission in footer
     * @return
     */
    public static String parsePlaceholders(String input, Player p, String timeString, int deliveryIndex) {
        Pattern pattern = Pattern.compile("\\{([A-Z_]+)\\}");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String placeholder = matcher.group(0); // e.g., {PLAYER_NAME}, {PLAYER_X}, etc.
            String placeholderKey = matcher.group(1); // e.g., PLAYER_NAME, PLAYER_X, etc.

            // Perform actions based on the placeholder key
            switch (placeholderKey) {
                case "PLAYER_NAME":
                    // Get the player's display name
                    String playerName = p.getDisplayName(); // Replace with your logic to retrieve the player's name
                    input = input.replace(placeholder, playerName);
                    break;
                case "PLAYER_X":
                    // Get the player's X position
                    double playerX = p.getLocation().getX(); // Replace with your logic to retrieve the player's X position
                    input = input.replace(placeholder, String.valueOf(playerX));
                    break;
                case "PLAYER_Y":
                    // Get the player's Y position
                    double playerY = p.getLocation().getY(); // Replace with your logic to retrieve the player's Y position
                    input = input.replace(placeholder, String.valueOf(playerY));
                    break;
                case "PLAYER_Z":
                    // Get the player's Z position
                    double playerZ = p.getLocation().getZ(); // Replace with your logic to retrieve the player's Z position
                    input = input.replace(placeholder, String.valueOf(playerZ));
                    break;
                case "BOARD_LOC":
                    String configBoardLocation = plugin.getConfig().getString("board-location");
                    input = input.replace(placeholder, String.valueOf(configBoardLocation));
                    break;
                case "DURATION":
                    input = input.replace(placeholder, timeString);
                    break;
                case "CURRENT_SUBMISSIONS":
                    input = input.replace(placeholder, String.valueOf(deliveryCompletedPlayerList.get(deliveryIndex).size()));
                    break;
                case "MAX_SUBMISSIONS":
                    input = input.replace(placeholder, String.valueOf(deliveries.get(deliveryIndex).getMaxSubmission()));
                    break;
                case "DELIVERY":
                    input = input.replace(placeholder, ChatColor.stripColor(timeString));
                    break;
                case "SKIP_COST":
                    input = input.replace(placeholder, String.valueOf(deliveries.get(deliveryIndex).getSkipCost()));
                    break;
            }
        }
        return input;
    }
}
