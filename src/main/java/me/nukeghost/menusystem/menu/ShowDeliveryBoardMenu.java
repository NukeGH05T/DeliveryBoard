package me.nukeghost.menusystem.menu;

import me.nukeghost.DeliveryBoard;
import me.nukeghost.menusystem.Menu;
import me.nukeghost.menusystem.PlayerMenuUtility;
import me.nukeghost.menusystem.menu.deliverymenu.DeliveryMenu;
import me.nukeghost.template.Delivery;
import me.nukeghost.utils.ColorUtils;
import me.nukeghost.utils.MenuUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

import static me.nukeghost.DeliveryBoard.deliveries;
import static me.nukeghost.DeliveryBoard.plugin;

public class ShowDeliveryBoardMenu extends Menu {
    private int taskId; // Variable to store the task ID for cancellation

    public ShowDeliveryBoardMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ColorUtils.translateHexColorCodes( "<#", ">", ChatColor.translateAlternateColorCodes('&', DeliveryBoard.plugin.getConfig().getString("gui.titles.boardTitle")));
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        e.setCancelled(true);
        if (e.getCurrentItem() == super.FILLER_GLASS) return;
        if (e.getCurrentItem() == null) return;

        List<String> activeDeliveries = plugin.getConfig().getStringList("active-deliveries");


        int deliveryIndex = -1;
        for (int i = 0; i < deliveries.size(); i++) {
            if (deliveries.get(i).getPositionSlot() == e.getSlot()) {
                deliveryIndex = i;
            }
        }

        playerMenuUtility.setDeliveryTitle(e.getCurrentItem().getItemMeta().getDisplayName());
        playerMenuUtility.setDeliveryID(deliveries.get(deliveryIndex).getDeliveryID());

        //Do nothing if they have already completed the delivery
        if (DeliveryBoard.deliveryCompletedPlayerList.get(deliveryIndex).contains(e.getWhoClicked())) {
            return;
        }
        DeliveryMenu deliveryMenu = new DeliveryMenu(playerMenuUtility, deliveryIndex);
        deliveryMenu.open();
    }

    @Override
    public void setMenuItems() {
        // Update the time in the lore every second
        taskId = new BukkitRunnable() {
            @Override
            public void run() {
                MenuUtils.updateLoreTime(inventory, playerMenuUtility.getOwner());
            }
        }.runTaskTimer(DeliveryBoard.plugin, 0, 20).getTaskId();

        MenuUtils.updateLoreTime(inventory, playerMenuUtility.getOwner());
    }


    @Override
    public void onClose(InventoryCloseEvent e) {
        // Cancel the task when the menu is closed
        Bukkit.getScheduler().cancelTask(taskId);
    }
}
