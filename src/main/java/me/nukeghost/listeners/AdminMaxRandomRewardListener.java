package me.nukeghost.listeners;

import me.nukeghost.DeliveryBoard;
import me.nukeghost.commands.subcommands.admin.EditRequiredItemCommand;
import me.nukeghost.language.Message;
import me.nukeghost.menusystem.PaginatedMenu;
import me.nukeghost.menusystem.PlayerMenuUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.List;

import static me.nukeghost.DeliveryBoard.plugin;

public class AdminMaxRandomRewardListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommandAdded(AsyncPlayerChatEvent e) {
        if (DeliveryBoard.adminAddCommandRewardHashMap.isEmpty()) return;
        if (!DeliveryBoard.adminAddCommandRewardHashMap.containsKey(e.getPlayer())) return;
        if (!e.getPlayer().hasPermission(new EditRequiredItemCommand().getPermissionNode())) return;
        if (!e.getMessage().trim().matches("^\\d+$")) return;

        int maxAmount = Integer.parseInt(e.getMessage());

        HashMap<PaginatedMenu, PlayerMenuUtility> menuUtilHash = DeliveryBoard.adminAddCommandRewardHashMap.get(e.getPlayer());

        PaginatedMenu menu = (PaginatedMenu) menuUtilHash.keySet().toArray()[0];
        PlayerMenuUtility pmu = menuUtilHash.get(menu);

        //Loading the config before making changes
        try {
            DeliveryBoard.plugin.reloadConfig();
            DeliveryBoard.plugin.saveConfig();
        } catch (Exception ex) {
            e.getPlayer().sendMessage(Message.RELOAD_FAILED);
            ex.printStackTrace();
        }

        List<String> reqList = plugin.getConfig().getStringList("delivery." + pmu.getDeliveryID() + ".reward-setup.mixed.random-rewards-pool");
        if (reqList.size() < maxAmount) {
            e.getPlayer().sendMessage(ChatColor.RED + "[DB] Max reward exceeds the amount of rewards in random reward pool!");
            return;
        }
        plugin.getConfig().set("delivery." + pmu.getDeliveryID() + ".reward-setup.mixed.max-reward", maxAmount);
        plugin.saveConfig();

        e.getPlayer().sendMessage(ChatColor.DARK_AQUA + "[DB] Max reward set to " + maxAmount);
        Bukkit.getScheduler().runTask(plugin, () -> {
            menu.open();
            DeliveryBoard.adminAddCommandRewardHashMap.remove(e.getPlayer());
        });
    }
}
