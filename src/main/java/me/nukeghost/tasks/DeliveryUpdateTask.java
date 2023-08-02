package me.nukeghost.tasks;

import me.nukeghost.DeliveryBoard;
import org.bukkit.scheduler.BukkitRunnable;

import static me.nukeghost.DeliveryBoard.deliveries;

public class DeliveryUpdateTask extends BukkitRunnable {
    DeliveryBoard plugin;

    public DeliveryUpdateTask(DeliveryBoard plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (int i = 0;i < deliveries.size(); i++) {
            long remainingCooldown = System.currentTimeMillis() - deliveries.get(i).getCooldownStart();
            if (remainingCooldown >= deliveries.get(i).getCooldownTime()) {
                deliveries.get(i).updateDeliveryItem();
                System.out.println("Refreshed delivery " + deliveries.get(i).getDeliveryID());
            }
        }

    }


}
