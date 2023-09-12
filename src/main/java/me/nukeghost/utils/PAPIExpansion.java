package me.nukeghost.utils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static me.nukeghost.DeliveryBoard.rankerNameList;

public class PAPIExpansion extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "DeliveryBoard";
    }

    @Override
    public @NotNull String getAuthor() {
        return "NukeGH05T";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player p,String params) {
        if (p == null) {
            return "";
        }

        // uuid, points, rank
        //Name Placeholder
        if (params.equals("name_1")) { // %deliveryboard_name_1%
            return getName(0);
        }
        if (params.equals("name_2")) { // %deliveryboard_name_2%
            return getName(1);
        }
        if (params.equals("name_3")) { // %deliveryboard_name_3%
            return getName(2);
        }
        if (params.equals("name_4")) { // %deliveryboard_name_3%
            return getName(3);
        }
        if (params.equals("name_5")) { // %deliveryboard_name_3%
            return getName(40);
        }
        if (params.equals("name_6")) { // %deliveryboard_name_3%
            return getName(5);
        }
        if (params.equals("name_7")) { // %deliveryboard_name_3%
            return getName(6);
        }
        if (params.equals("name_8")) { // %deliveryboard_name_3%
            return getName(7);
        }
        if (params.equals("name_9")) { // %deliveryboard_name_3%
            return getName(8);
        }
        if (params.equals("name_10")) { // %deliveryboard_name_3%
            return getName(9);
        }

        //Points Placeholder
        if (params.equals("points_1")) { // %deliveryboard_points_1%
            return getPoints(0);
        }
        if (params.equals("points_2")) { // %deliveryboard_points_2%
            return getPoints(1);
        }
        if (params.equals("points_3")) { // %deliveryboard_points_3%
            return getPoints(2);
        }
        if (params.equals("points_4")) { // %deliveryboard_points_3%
            return getPoints(3);
        }
        if (params.equals("points_5")) { // %deliveryboard_points_3%
            return getPoints(4);
        }
        if (params.equals("points_6")) { // %deliveryboard_points_3%
            return getPoints(5);
        }
        if (params.equals("points_7")) { // %deliveryboard_points_3%
            return getPoints(6);
        }
        if (params.equals("points_8")) { // %deliveryboard_points_3%
            return getPoints(7);
        }
        if (params.equals("points_9")) { // %deliveryboard_points_3%
            return getPoints(8);
        }
        if (params.equals("points_10")) { // %deliveryboard_points_3%
            return getPoints(9);
        }

        return ChatColor.RED + "[invalid_placeholder]";
    }

    /**
     * @param id Starts from 0
     * @return
     */
    private String getName(int id) {
        try {
            return Bukkit.getOfflinePlayer(UUID.fromString(rankerNameList.get(id)[0])).getName();
        } catch (IndexOutOfBoundsException ex) {
            return "???";
        }
    }

    /**
     * @param id Starts from 0
     * @return
     */
    private String getPoints(int id) {
        try {
            return rankerNameList.get(id)[1];
        } catch (IndexOutOfBoundsException ex) {
            return "???";
        }
    }
}
