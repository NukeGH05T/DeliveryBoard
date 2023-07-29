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

import static me.nukeghost.DeliveryBoard.deliveries;
import static me.nukeghost.DeliveryBoard.plugin;
import static me.nukeghost.handlers.GenerationHandler.generateIconItem;
import static me.nukeghost.utils.ColorUtils.translateColorCodes;

public class MenuUtils {
    public static void updateLoreTime(Inventory inventory, Player owner) {

        //Generate delivery icon if player has specific permission
        List<String> activeDeliveries = plugin.getConfig().getStringList("active-deliveries");

        for (int i = 0; i < activeDeliveries.size(); i++) {
            String deliveryID = activeDeliveries.get(i);
            if (owner.hasPermission("deliveryboard.delivery." + deliveryID)) {
                System.out.println("hasperm " + deliveryID);
                long nextStartTime = deliveries.get(i).getCooldownStart() + deliveries.get(i).getCooldownTime();//DeliveryBoard.cooldown.get(deliveryID);

                //ItemStack hourlyItem = generateIconItem(DeliveryBoard.plugin.getConfig().getString("hourlyIcon"));
                ItemStack hourlyItem = generateIconItem(deliveryID);
                ItemMeta hourlyMeta = hourlyItem.getItemMeta();
                hourlyMeta.setDisplayName(ColorUtils.translateHexColorCodes( "<#", ">",
                        ChatColor.translateAlternateColorCodes('&',
                                plugin.getConfig().getString("delivery." + deliveryID + ".title"))));


                String time = TimeUtils.formatEpochTime(nextStartTime - System.currentTimeMillis());

                List<String> lore = PlaceholderUtils.parsePlaceholders(Message.DB_ITEM_LORE, owner, time);

                if (DeliveryBoard.deliveryCompletedPlayerList.get(i).contains(owner)) {
                    for (String footer : Message.DB_ITEM_LORE_FOOTER_COMPLETE) {
                        lore.add(PlaceholderUtils.parsePlaceholders(footer, owner, time));
                    }
                } else {
                    for (String footer : Message.DB_ITEM_LORE_FOOTER_INCOMPLETE) {
                        lore.add(PlaceholderUtils.parsePlaceholders(footer, owner, time));
                    }
                }

                hourlyMeta.setLore(lore);
                hourlyItem.setItemMeta(hourlyMeta);
                inventory.setItem(i, hourlyItem);

            }
        }
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
