package me.nukeghost.tasks;

import me.nukeghost.DeliveryBoard;
import me.nukeghost.handlers.GenerationHandler;
import org.bukkit.scheduler.BukkitRunnable;

import static me.nukeghost.DeliveryBoard.cooldown;

public class SixHourlyDeliveryUpdateTask extends BukkitRunnable {

    DeliveryBoard plugin;

    public SixHourlyDeliveryUpdateTask(DeliveryBoard plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        long startTime = cooldown.get("six-hourly");
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;
        if (elapsedTime >= (3600000 * 6)) { // 1 hour = 3600000 milliseconds
            cooldown.put("six-hourly", System.currentTimeMillis() + (3600000 * 6));
            DeliveryBoard.setThreeHourlyItem(GenerationHandler.generateDeliveryItem("six-hourly"));
            DeliveryBoard.sixHourlyCompletedPlayerList.clear();
        }
    }
}
