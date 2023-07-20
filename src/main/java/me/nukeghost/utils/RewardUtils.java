package me.nukeghost.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

import static me.nukeghost.DeliveryBoard.plugin;

public class RewardUtils {
    public static ItemStack getRewardFromString(String rewardString) {
        String[] parsed = rewardString.split("@");

        if (rewardString.contains("item")) {
            //item@EMERALD@1
            ItemStack itemToGive = new ItemStack(Material.getMaterial(parsed[1]), Integer.parseInt(parsed[2]));
            ItemMeta meta = itemToGive.getItemMeta();
            meta.setLore(Collections.singletonList(ChatColor.DARK_GRAY + "Item"));
            itemToGive.setItemMeta(meta);

            return itemToGive;

        } else if (rewardString.contains("comm")) {
            //comm@effect give {PLAYER_NAME} minecraft:absorption 10 3
            String command = rewardString.replace("comm@", "");

            ItemStack commandNamedItem = new ItemStack(Material.PAPER);
            ItemMeta commandMeta = commandNamedItem.getItemMeta();
            commandMeta.setDisplayName(ChatColor.WHITE + command);
            commandMeta.setLore(Collections.singletonList(ChatColor.DARK_GRAY + "Command"));
            commandNamedItem.setItemMeta(commandMeta);

            return commandNamedItem;
        }

        Bukkit.getLogger().severe("Something went wrong while showing \"" + rewardString + "\" in rewards");
        return null;
    }

    public static int getListIndex(String rewardString, String deliveryID) {
        List<String> rewardsList = plugin.getConfig().getStringList("delivery." + deliveryID + ".reward-setup.mixed.confirmed-rewards");

        for (int i = 0; i < rewardsList.size(); i++) {
            if (rewardString.equalsIgnoreCase(rewardsList.get(i))) {
                return i;
            }
        }
        return -1;
    }
}
