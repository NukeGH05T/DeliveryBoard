package me.nukeghost.utils;

import me.nukeghost.DeliveryBoard;
import me.nukeghost.language.Message;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static me.nukeghost.DeliveryBoard.deliveries;
import static me.nukeghost.DeliveryBoard.plugin;
import static me.nukeghost.handlers.GenerationHandler.generateIconItem;

public class MenuUtils {
    public static void updateLoreTime(Inventory inventory, Player owner) {

        //Generate delivery icon if player has specific permission
        List<String> activeDeliveries = plugin.getConfig().getStringList("active-deliveries");

        for (int i = 0; i < activeDeliveries.size(); i++) {
            String deliveryID = activeDeliveries.get(i);
            if (owner.hasPermission("deliveryboard.delivery." + deliveryID)) {
                ItemStack hourlyItem = generateIconItem(deliveryID);
                ItemMeta hourlyMeta = hourlyItem.getItemMeta();
                hourlyMeta.setDisplayName(ColorUtils.translateHexColorCodes( "<#", ">",
                        ChatColor.translateAlternateColorCodes('&',
                                plugin.getConfig().getString("delivery." + deliveryID + ".title"))));


                long nextStartTime = deliveries.get(i).getCooldownStart() + (deliveries.get(i).getCooldownTime() * 1000);
                String time = TimeUtils.formatEpochTime(nextStartTime - System.currentTimeMillis());

                List<String> lore = PlaceholderUtils.parsePlaceholders(Message.DB_ITEM_LORE, owner, time, i);

                if (DeliveryBoard.deliveryCompletedPlayerList.get(i).contains(owner)) {
                    //Already completed delivery
                    for (String footer : Message.DB_ITEM_LORE_FOOTER_COMPLETE) {
                        lore.add(PlaceholderUtils.parsePlaceholders(footer, owner, time, i));
                    }
                } else {
                    //Hasn't completed delivery
                    if (deliveries.get(i).getMaxSubmission() == -1 || //No max submission set
                            DeliveryBoard.deliveryCompletedPlayerList.get(i).size() < deliveries.get(i).getMaxSubmission()) { //Not maxed yet
                        for (String footer : Message.DB_ITEM_LORE_FOOTER_INCOMPLETE) {
                            lore.add(PlaceholderUtils.parsePlaceholders(footer, owner, time, i));
                        }
                    } else if (DeliveryBoard.deliveryCompletedPlayerList.get(i).size() >= deliveries.get(i).getMaxSubmission()) {
                        for (String footer : Message.DB_ITEM_LORE_FOOTER_INCOMPLETE) {
                            //Submission maxed. Don't let them submit
                            lore.add(PlaceholderUtils.parsePlaceholders(footer, owner, time, i));
                        }
                    }
                }

                hourlyMeta.setLore(lore);
                hourlyItem.setItemMeta(hourlyMeta);
                inventory.setItem(deliveries.get(i).getPositionSlot(), hourlyItem);

            }
        }
    }

}
