package me.nukeghost.listeners;

import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static me.nukeghost.DeliveryBoard.deliveries;
import static me.nukeghost.DeliveryBoard.plugin;

public class FirstItemsAdderLoadListener implements Listener {
    @EventHandler
    public void onItemsAdderItemLoad(ItemsAdderLoadDataEvent e) {
        //Initializes all deliveries for the first time after ItemsAdder loads it's items if IA is present
        if (deliveries.isEmpty()) {
            plugin.startTasks();
        }
    }
}
