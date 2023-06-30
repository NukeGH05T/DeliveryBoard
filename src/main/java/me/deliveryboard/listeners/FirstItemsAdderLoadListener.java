package me.deliveryboard.listeners;

import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import me.deliveryboard.DeliveryBoard;
import me.deliveryboard.handlers.GenerationHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FirstItemsAdderLoadListener implements Listener {
    @EventHandler
    public void onItemsAdderItemLoad(ItemsAdderLoadDataEvent e) {
        //Initializes all deliveries for the first time after ItemsAdder loads it's items if IA is present
        if (DeliveryBoard.getHourlyItem() == null && DeliveryBoard.getThreeHourlyItem() == null && DeliveryBoard.getSixHourlyItem() == null) {
            DeliveryBoard.setHourlyItem(GenerationHandler.generateDeliveryItem("hourly"));
            DeliveryBoard.setThreeHourlyItem(GenerationHandler.generateDeliveryItem("three-hourly"));
            DeliveryBoard.setSixHourlyItem(GenerationHandler.generateDeliveryItem("six-hourly"));
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "Initialized Deliveries");
        }
    }
}
