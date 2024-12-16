package me.nukeghost.listeners;

import me.nukeghost.DeliveryBoard;
import me.nukeghost.database.TokenDatabase;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

import static me.nukeghost.DeliveryBoard.plugin;

public class PlayerCurrencyInitializeOnJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (plugin.getConfig().getString("database").equalsIgnoreCase("off")) return;
        UUID uuid = e.getPlayer().getUniqueId();
        if (!TokenDatabase.doesPlayerEntryExist(uuid.toString())) {
            TokenDatabase.saveCurrency(DeliveryBoard.defaultTokenAmount, uuid.toString());
        }
    }
}
