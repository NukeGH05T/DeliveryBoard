package me.deliveryboard.tasks;

import me.deliveryboard.DeliveryBoard;
import me.deliveryboard.handlers.GenerationHandler;
import org.bukkit.scheduler.BukkitRunnable;

public class HourlyDeliveryUpdateTask extends BukkitRunnable {

    DeliveryBoard plugin;
    long startTime = System.currentTimeMillis();

    public HourlyDeliveryUpdateTask(DeliveryBoard plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;

        if (elapsedTime >= 3600000) { // 1 hour = 3600000 milliseconds
            DeliveryBoard.setHourlyItem(GenerationHandler.generateDeliveryItem("hourly"));
            DeliveryBoard.hourlyCompletedPlayerList.clear();
        } else {
        }
    }
}
