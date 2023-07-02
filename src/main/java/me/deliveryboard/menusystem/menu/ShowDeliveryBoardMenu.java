package me.deliveryboard.menusystem.menu;

import me.deliveryboard.DeliveryBoard;
import me.deliveryboard.menusystem.Menu;
import me.deliveryboard.menusystem.PlayerMenuUtility;
import me.deliveryboard.menusystem.menu.deliverymenu.HourlyDeliveryMenu;
import me.deliveryboard.menusystem.menu.deliverymenu.SixHourlyDeliveryMenu;
import me.deliveryboard.menusystem.menu.deliverymenu.ThreeHourlyDeliveryMenu;
import me.deliveryboard.utils.ColorUtils;
import me.deliveryboard.utils.MenuUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.scheduler.BukkitRunnable;

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
        if (e.getSlot() == 11) { //11
            // Open hourly delivery menu
            if (DeliveryBoard.hourlyCompletedPlayerList.contains(e.getWhoClicked())) {
                e.getWhoClicked().sendMessage(ChatColor.RED + "You have already completed the delivery!");
            } else {
                playerMenuUtility.setDeliveryTitle(e.getCurrentItem().getItemMeta().getDisplayName());
                HourlyDeliveryMenu hourlyDeliveryMenu = new HourlyDeliveryMenu(playerMenuUtility);
                hourlyDeliveryMenu.open();
            }


        } else if (e.getSlot() == 13) { //13
            if (DeliveryBoard.threeHourlyCompletedPlayerList.contains(e.getWhoClicked())) {
                e.getWhoClicked().sendMessage(ChatColor.RED + "You have already completed the delivery!");
            } else {
                playerMenuUtility.setDeliveryTitle(e.getCurrentItem().getItemMeta().getDisplayName());
                ThreeHourlyDeliveryMenu deliveryMenu = new ThreeHourlyDeliveryMenu(playerMenuUtility);
                deliveryMenu.open();
            }


        } else if (e.getSlot() == 15) { //15
            if (DeliveryBoard.sixHourlyCompletedPlayerList.contains(e.getWhoClicked())) {
                e.getWhoClicked().sendMessage(ChatColor.RED + "You have already completed the delivery!");
            } else {
                playerMenuUtility.setDeliveryTitle(e.getCurrentItem().getItemMeta().getDisplayName());
                SixHourlyDeliveryMenu deliveryMenu = new SixHourlyDeliveryMenu(playerMenuUtility);
                deliveryMenu.open();
            }


        }
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
