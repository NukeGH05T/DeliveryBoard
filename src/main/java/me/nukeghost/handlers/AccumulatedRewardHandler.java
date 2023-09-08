package me.nukeghost.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.HashSet;
import java.util.Set;

import static me.nukeghost.DeliveryBoard.plugin;

public class AccumulatedRewardHandler {

    public static Set<String> sections = new HashSet<>();
    public void loadAccumulatedRewardData() {
        sections = plugin.getConfig().getConfigurationSection("accumulated-rewards").getKeys(false);
        for (String section : sections) { //10, 15, ...
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[DB] Loaded accumulated rewards for " + section + ".");
        }
    }

    public void reloadAccumulatedRewardData() {
        sections.clear();
        loadAccumulatedRewardData();
    }
}
