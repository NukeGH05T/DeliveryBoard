package me.nukeghost.utils;

import me.nukeghost.DeliveryBoard;
import me.nukeghost.language.Message;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static me.nukeghost.handlers.GenerationHandler.generateIconItem;
import static me.nukeghost.utils.ColorUtils.translateColorCodes;

public class MenuUtils {
    public static void updateLoreTime(Inventory inventory, Player owner) {
        //Generate delivery icon if player has specific permission

        //Hourly Setup
        if (owner.hasPermission("deliveryboard.delivery.hourly")) {
            long nextHourlyStartTime = DeliveryBoard.cooldown.get("hourly");

            //ItemStack hourlyItem = generateIconItem(DeliveryBoard.plugin.getConfig().getString("hourlyIcon"));
            ItemStack hourlyItem = generateIconItem("hourlyIcon");
            ItemMeta hourlyMeta = hourlyItem.getItemMeta();
            hourlyMeta.setDisplayName(ColorUtils.translateHexColorCodes( "<#", ">",
                    ChatColor.translateAlternateColorCodes('&',
                            DeliveryBoard.plugin.getConfig().getString("gui.titles.hourlyTitle"))));


            String timeHourly = TimeUtils.formatEpochTime(nextHourlyStartTime - System.currentTimeMillis());

            List<String> hourlyLore = PlaceholderUtils.parsePlaceholders(Message.DB_HOURLY_ITEM_LORE, owner, timeHourly);


            if (DeliveryBoard.hourlyCompletedPlayerList.contains(owner)) {
                //hourlyLore.add(ChatColor.GREEN + "COMPLETED");//take from complete list & use special parser, paass timeHourly
                //hourlyLore.add(ChatColor.GRAY + "Refreshes In: " + ChatColor.GREEN + timeHourly);//
                for (String footer : Message.DB_HOURLY_ITEM_LORE_FOOTER_COMPLETE) {
                    hourlyLore.add(PlaceholderUtils.parsePlaceholders(footer, owner, timeHourly));
                }
            } else {
                //hourlyLore.add(ChatColor.GRAY + "Time Left: " + ChatColor.YELLOW + timeHourly);//from incomplete list
                for (String footer : Message.DB_HOURLY_ITEM_LORE_FOOTER_INCOMPLETE) {
                    hourlyLore.add(PlaceholderUtils.parsePlaceholders(footer, owner, timeHourly));
                }
            }

            hourlyMeta.setLore(hourlyLore);
            hourlyItem.setItemMeta(hourlyMeta);
            inventory.setItem(11, hourlyItem);
        }
        //Hourly Setup

        //3 hourly Setup
        if (owner.hasPermission("deliveryboard.delivery.threehourly")) {
            long nextThreeHourlyStartTime = DeliveryBoard.cooldown.get("three-hourly");

            ItemStack threeHourlyItem = generateIconItem("threeHourlyIcon");
            ItemMeta threeHourlyMeta = threeHourlyItem.getItemMeta();
            threeHourlyMeta.setDisplayName(ColorUtils.translateHexColorCodes( "<#", ">",
                    ChatColor.translateAlternateColorCodes('&',
                            DeliveryBoard.plugin.getConfig().getString("gui.titles.threeHourlyTitle"))));

            String timeThreeHourly = TimeUtils.formatEpochTime(nextThreeHourlyStartTime - System.currentTimeMillis());

            List<String> threeHourlyLore = PlaceholderUtils.parsePlaceholders(Message.DB_THREE_HOURLY_ITEM_LORE, owner, timeThreeHourly);

            if (DeliveryBoard.threeHourlyCompletedPlayerList.contains(owner)) {
                //threeHourlyLore.add(ChatColor.GREEN + "COMPLETED");//
                //threeHourlyLore.add(ChatColor.GRAY + "Refreshes In: " + ChatColor.GREEN + timeThreeHourly);//
                for (String footer : Message.DB_THREE_HOURLY_ITEM_LORE_FOOTER_COMPLETE) {
                    threeHourlyLore.add(PlaceholderUtils.parsePlaceholders(footer, owner, timeThreeHourly));
                }
            } else {
                //threeHourlyLore.add(ChatColor.GRAY + "Time Left: " + ChatColor.YELLOW + timeThreeHourly);//
                for (String footer : Message.DB_THREE_HOURLY_ITEM_LORE_FOOTER_INCOMPLETE) {
                    threeHourlyLore.add(PlaceholderUtils.parsePlaceholders(footer, owner, timeThreeHourly));
                }
            }
            threeHourlyMeta.setLore(threeHourlyLore);
            threeHourlyItem.setItemMeta(threeHourlyMeta);
            inventory.setItem(13, threeHourlyItem);
        }
        //3 hourly Setup

        //6 hourly Setup
        if (owner.hasPermission("deliveryboard.delivery.sixhourly")) {
            long nextSixHourlyStartTime = DeliveryBoard.cooldown.get("six-hourly");

            ItemStack sixHourlyItem = generateIconItem("sixHourlyIcon");
            ItemMeta sixHourlyMeta = sixHourlyItem.getItemMeta();
            sixHourlyMeta.setDisplayName(ColorUtils.translateHexColorCodes( "<#", ">",
                    ChatColor.translateAlternateColorCodes('&',
                            DeliveryBoard.plugin.getConfig().getString("gui.titles.sixHourlyTitle"))));

            String timeSix = TimeUtils.formatEpochTime(nextSixHourlyStartTime - System.currentTimeMillis());

            List<String> sixHourlyLore = PlaceholderUtils.parsePlaceholders(Message.DB_SIX_HOURLY_ITEM_LORE, owner, timeSix);


            if (DeliveryBoard.sixHourlyCompletedPlayerList.contains(owner)) {
                //sixHourlyLore.add(ChatColor.GREEN + "COMPLETED");//
                //sixHourlyLore.add(ChatColor.GRAY + "Refreshes In: " + ChatColor.GREEN + timeSix);//
                for (String footer : Message.DB_SIX_HOURLY_ITEM_LORE_FOOTER_COMPLETE) {
                    sixHourlyLore.add(PlaceholderUtils.parsePlaceholders(footer, owner, timeSix));
                }
            } else {
                //sixHourlyLore.add(ChatColor.GRAY + "Time Left: " + ChatColor.YELLOW + timeSix);//
                for (String footer : Message.DB_SIX_HOURLY_ITEM_LORE_FOOTER_INCOMPLETE) {
                    sixHourlyLore.add(PlaceholderUtils.parsePlaceholders(footer, owner, timeSix));
                }
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
     * @param deliveryIconTitle Supports traditional Bukkit coloring and Hex (<#2b3aef6>)
     * @apiNote Not LOCALIZED! Localize before usage!
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

    /**
    *@apinote Not LOCALIZED!
     */
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
