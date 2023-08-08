package me.nukeghost.listeners;

import me.nukeghost.DeliveryBoard;
import me.nukeghost.commands.subcommands.admin.EditRequiredItemCommand;
import me.nukeghost.language.Message;
import me.nukeghost.menusystem.PaginatedMenu;
import me.nukeghost.menusystem.PlayerMenuUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

import static me.nukeghost.DeliveryBoard.plugin;

public class AdminCommandAddListener implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onCommandAdded(AsyncPlayerChatEvent e) {
        if (DeliveryBoard.adminAddCommandRewardHashMap.isEmpty()) return;
        if (!DeliveryBoard.adminAddCommandRewardHashMap.containsKey(e.getPlayer())) return;
        if (!e.getPlayer().hasPermission(new EditRequiredItemCommand().getPermissionNode())) return;
        if (!e.getMessage().startsWith("comm:")) return;

        ItemStack handItem = new ItemStack(Material.PAPER);

        String finalString = "comm@" + e.getMessage().replace("comm:", "").trim();

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

        List<String> reqList = plugin.getConfig().getStringList("delivery." + pmu.getDeliveryID() + ".reward-setup.mixed.confirmed-rewards");
        reqList.add(finalString);
        plugin.getConfig().set("delivery." + pmu.getDeliveryID() + ".reward-setup.mixed.confirmed-rewards", reqList);
        plugin.saveConfig();

        e.getPlayer().sendMessage(ChatColor.DARK_AQUA + "[DB] Item added successfully.");
        Bukkit.getScheduler().runTask(plugin, () -> {
            menu.open();
            DeliveryBoard.adminAddCommandRewardHashMap.remove(e.getPlayer());
        });
    }
}
