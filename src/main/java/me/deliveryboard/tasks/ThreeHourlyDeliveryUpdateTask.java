package me.deliveryboard.tasks;

import me.deliveryboard.DeliveryBoard;
import me.deliveryboard.handlers.GenerationHandler;
import org.bukkit.scheduler.BukkitRunnable;

public class ThreeHourlyDeliveryUpdateTask extends BukkitRunnable {

    DeliveryBoard plugin;
    long startTime = System.currentTimeMillis();

    public ThreeHourlyDeliveryUpdateTask(DeliveryBoard plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {

        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;

        if (elapsedTime >= 3600000 * 3) { // 1 hour = 3600000 milliseconds
            DeliveryBoard.setThreeHourlyItem(GenerationHandler.generateDeliveryItem("three-hourly"));
            DeliveryBoard.threeHourlyCompletedPlayerList.clear();
        } else {
        }
    }
}
