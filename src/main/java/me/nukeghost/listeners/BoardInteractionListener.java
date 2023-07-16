package me.nukeghost.listeners;

import me.nukeghost.DeliveryBoard;
import me.nukeghost.commands.subcommands.BoardCommand;
import me.nukeghost.menusystem.PlayerMenuUtility;
import me.nukeghost.menusystem.menu.ShowDeliveryBoardMenu;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class BoardInteractionListener implements Listener {

    DeliveryBoard plugin;

    public BoardInteractionListener(DeliveryBoard plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (!e.getPlayer().hasPermission(new BoardCommand().getPermissionNode())) return;
        if (Objects.equals(plugin.getConfig().getString("board-location"), "")) return;

        String locationString = plugin.getConfig().getString("board-location");
        String[] parts = locationString.split("/");

        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        int z = Integer.parseInt(parts[2]);

        if (e.getClickedBlock().getLocation().equals(new Location(e.getPlayer().getWorld(), x, y, z))) {
            new ShowDeliveryBoardMenu(new PlayerMenuUtility(e.getPlayer())).open();
        }
    }
}
