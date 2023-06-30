package me.deliveryboard.utils;

import me.deliveryboard.DeliveryBoard;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static me.deliveryboard.utils.ColorUtils.translateColorCodes;

public class MenuUtils {
    public static void updateLoreTime(Inventory inventory, Player owner) {
        //Generate delivery icon if player has specific permission

        //Hourly Setup
        if (owner.hasPermission("deliveryboard.delivery.hourly")) {
            long nextHourlyStartTime = DeliveryBoard.cooldown.get("hourly");

            ItemStack hourlyItem = new ItemStack(Material.REDSTONE);
            ItemMeta hourlyMeta = hourlyItem.getItemMeta();
            hourlyMeta.setDisplayName(ChatColor.DARK_AQUA + "Hourly Delivery");

            List<String> hourlyLore = new ArrayList<>();
            hourlyLore.add(ChatColor.DARK_GRAY + "---------------------------");
            hourlyLore.add(ChatColor.GRAY + "The town needs new deliveries");
            hourlyLore.add(ChatColor.GRAY + "all the time. Provide what the");
            hourlyLore.add(ChatColor.GRAY + "townsfolk needs and get rewarded.");
            hourlyLore.add(ChatColor.DARK_GRAY + "---------------------------");
            if (DeliveryBoard.hourlyCompletedPlayerList.contains(owner)) {
                hourlyLore.add(ChatColor.GREEN + "COMPLETED");
                hourlyLore.add(ChatColor.GRAY + "Refreshes In: " + ChatColor.GREEN + TimeUtils.formatEpochTime(nextHourlyStartTime - System.currentTimeMillis()));
            } else {
                hourlyLore.add(ChatColor.GRAY + "Time Left: " + ChatColor.YELLOW + TimeUtils.formatEpochTime(nextHourlyStartTime - System.currentTimeMillis()));
            }

            hourlyMeta.setLore(hourlyLore);
            hourlyItem.setItemMeta(hourlyMeta);
            inventory.setItem(11, hourlyItem);
        }
        //Hourly Setup

        //3 hourly Setup
        if (owner.hasPermission("deliveryboard.delivery.threehourly")) {
            long nextThreeHourlyStartTime = DeliveryBoard.cooldown.get("three-hourly");

            ItemStack threeHourlyItem = new ItemStack(Material.QUARTZ);
            ItemMeta threeHourlyMeta = threeHourlyItem.getItemMeta();
            threeHourlyMeta.setDisplayName(ChatColor.DARK_AQUA + "Three Hourly Delivery");

            List<String> threeHourlyLore = new ArrayList<>();
            threeHourlyLore.add(ChatColor.DARK_GRAY + "---------------------------");
            threeHourlyLore.add(ChatColor.GRAY + "The town needs new deliveries");
            threeHourlyLore.add(ChatColor.GRAY + "all the time. Provide what the");
            threeHourlyLore.add(ChatColor.GRAY + "townsfolk needs and get rewarded.");
            threeHourlyLore.add(ChatColor.DARK_GRAY + "---------------------------");
            if (DeliveryBoard.threeHourlyCompletedPlayerList.contains(owner)) {
                threeHourlyLore.add(ChatColor.GREEN + "COMPLETED");
                threeHourlyLore.add(ChatColor.GRAY + "Refreshes In: " + ChatColor.GREEN + TimeUtils.formatEpochTime(nextThreeHourlyStartTime - System.currentTimeMillis()));
            } else {
                threeHourlyLore.add(ChatColor.GRAY + "Time Left: " + ChatColor.YELLOW + TimeUtils.formatEpochTime(nextThreeHourlyStartTime - System.currentTimeMillis()));
            }
            threeHourlyMeta.setLore(threeHourlyLore);
            threeHourlyItem.setItemMeta(threeHourlyMeta);
            inventory.setItem(13, threeHourlyItem);
        }
        //3 hourly Setup

        //6 hourly Setup
        if (owner.hasPermission("deliveryboard.delivery.sixhourly")) {
            long nextSixHourlyStartTime = DeliveryBoard.cooldown.get("six-hourly");

            ItemStack sixHourlyItem = new ItemStack(Material.EMERALD);
            ItemMeta sixHourlyMeta = sixHourlyItem.getItemMeta();
            sixHourlyMeta.setDisplayName(ChatColor.DARK_AQUA + "Six Hourly Delivery");

            List<String> sixHourlyLore = new ArrayList<>();
            sixHourlyLore.add(ChatColor.DARK_GRAY + "---------------------------");
            sixHourlyLore.add(ChatColor.GRAY + "The town needs new deliveries");
            sixHourlyLore.add(ChatColor.GRAY + "all the time. Provide what the");
            sixHourlyLore.add(ChatColor.GRAY + "townsfolk needs and get rewarded.");
            sixHourlyLore.add(ChatColor.DARK_GRAY + "---------------------------");
            if (DeliveryBoard.sixHourlyCompletedPlayerList.contains(owner)) {
                sixHourlyLore.add(ChatColor.GREEN + "COMPLETED");
                sixHourlyLore.add(ChatColor.GRAY + "Refreshes In: " + ChatColor.GREEN + TimeUtils.formatEpochTime(nextSixHourlyStartTime - System.currentTimeMillis()));
            } else {
                sixHourlyLore.add(ChatColor.GRAY + "Time Left: " + ChatColor.YELLOW + TimeUtils.formatEpochTime(nextSixHourlyStartTime - System.currentTimeMillis()));
            }
            sixHourlyMeta.setLore(sixHourlyLore);
            sixHourlyItem.setItemMeta(sixHourlyMeta);
            inventory.setItem(15, sixHourlyItem);
        }
        //6 hourly Setup

    }

    /**
     * @param cooldownKey Cooldown timer key from Main class
     * @param iconMaterial  Material used for the representative item
     * @param deliveryIconTitle Supports traditional Bukkit coloring and Hex (&#2b3aef6)
     * @return
     */
    public ItemStack createDeliveryIconItem(String cooldownKey, Material iconMaterial, String deliveryIconTitle) {
        long nextStartTime = DeliveryBoard.cooldown.get(cooldownKey); //"six-hourly"

        ItemStack deliveryItem = new ItemStack(iconMaterial);
        ItemMeta deliveryMeta = deliveryItem.getItemMeta();
        deliveryMeta.setDisplayName(translateColorCodes(deliveryIconTitle));

        List<String> deliveryLore = new ArrayList<>();
        deliveryLore.add(ChatColor.DARK_GRAY + "---------------------------");
        deliveryLore.add(ChatColor.GRAY + "The town needs new deliveries");
        deliveryLore.add(ChatColor.GRAY + "all the time. Provide what the");
        deliveryLore.add(ChatColor.GRAY + "townsfolk needs and get rewarded.");
        deliveryLore.add(ChatColor.DARK_GRAY + "---------------------------");
        deliveryLore.add(ChatColor.GRAY + "Time Left: " + ChatColor.YELLOW + TimeUtils.formatEpochTime(nextStartTime - System.currentTimeMillis()));

        deliveryMeta.setLore(deliveryLore);
        deliveryItem.setItemMeta(deliveryMeta);

        return deliveryItem;
    }

    public ItemStack createDeliveryIconItem(String cooldownKey) {
        long nextStartTime = DeliveryBoard.cooldown.get(cooldownKey); //"six-hourly"

        Material iconMaterial = Material.CHEST_MINECART;
        String deliveryIconTitle = ChatColor.DARK_AQUA + TimeUtils.formatEpochTime(nextStartTime - System.currentTimeMillis()) + " Timed Delivery";

        ItemStack deliveryItem = new ItemStack(iconMaterial);
        ItemMeta deliveryMeta = deliveryItem.getItemMeta();
        deliveryMeta.setDisplayName(translateColorCodes(deliveryIconTitle));

        List<String> deliveryLore = new ArrayList<>();
        deliveryLore.add(ChatColor.DARK_GRAY + "---------------------------");
        deliveryLore.add(ChatColor.GRAY + "The town needs new deliveries");
        deliveryLore.add(ChatColor.GRAY + "all the time. Provide what the");
        deliveryLore.add(ChatColor.GRAY + "townsfolk needs and get rewarded.");
        deliveryLore.add(ChatColor.DARK_GRAY + "---------------------------");
        deliveryLore.add(ChatColor.GRAY + "Time Left: " + ChatColor.YELLOW + TimeUtils.formatEpochTime(nextStartTime - System.currentTimeMillis()));

        deliveryMeta.setLore(deliveryLore);
        deliveryItem.setItemMeta(deliveryMeta);

        return deliveryItem;
    }

}
