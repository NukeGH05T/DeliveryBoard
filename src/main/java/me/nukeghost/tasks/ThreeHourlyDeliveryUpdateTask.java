package me.nukeghost.tasks;

import me.nukeghost.DeliveryBoard;
import me.nukeghost.handlers.GenerationHandler;
import org.bukkit.scheduler.BukkitRunnable;

import static me.nukeghost.DeliveryBoard.cooldown;

public class ThreeHourlyDeliveryUpdateTask extends BukkitRunnable {

    DeliveryBoard plugin;
    long startTime = System.currentTimeMillis();

    public ThreeHourlyDeliveryUpdateTask(DeliveryBoard plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        long startTime = cooldown.get("three-hourly");
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;
        if (elapsedTime >= (3600000 * 3)) { // 1 hour = 3600000 milliseconds
            cooldown.put("six-hourly", System.currentTimeMillis() + (3600000 * 3));
            DeliveryBoard.setThreeHourlyItem(GenerationHandler.generateDeliveryItem("three-hourly"));
            DeliveryBoard.sixHourlyCompletedPlayerList.clear();
        }
    }
}
