package me.deliveryboard.menusystem.menu.deliverymenu;

import me.deliveryboard.DeliveryBoard;
import me.deliveryboard.handlers.RewardHandler;
import me.deliveryboard.handlers.VerificationHandler;
import me.deliveryboard.language.Message;
import me.deliveryboard.menusystem.Menu;
import me.deliveryboard.menusystem.PlayerMenuUtility;
import me.deliveryboard.menusystem.menu.ShowDeliveryBoardMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ThreeHourlyDeliveryMenu extends Menu {
    public ThreeHourlyDeliveryMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return playerMenuUtility.getDeliveryTitle();
    }

    @Override
    public int getSlots() {
        return 27;
    }

    int inputSlotIndex = 22;

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.isShiftClick()) {
            e.setCancelled(true);
        }
        if (e.getSlot() != inputSlotIndex && e.getInventory() == e.getClickedInventory()) {
            e.setCancelled(true);
        }

        if (e.getCurrentItem().getType() == super.ACCEPT.getType() && e.getSlot() == 26) {
            if (e.getInventory().getItem(inputSlotIndex) == null) {
                p.sendMessage(Message.THD_EMPTY_SUBMISSION);//
                return;
            }

            VerificationHandler verificationHandler = new VerificationHandler(DeliveryBoard.getThreeHourlyItem());

            if (!verificationHandler.checkItem(e.getInventory().getItem(inputSlotIndex))) {
                p.sendMessage(Message.THD_WRONG_SUBMISSION);//
            } else if (verificationHandler.checkItem(e.getInventory().getItem(inputSlotIndex))) {

                //Give proper reward
                RewardHandler rewardHandler = new RewardHandler(DeliveryBoard.plugin);
                rewardHandler.giveRewards(p, "three-hourly");

                //Preventing player from redoing the same delivery before it's restarted
                DeliveryBoard.threeHourlyCompletedPlayerList.add(p);

                p.sendMessage(Message.THD_SUCCESSFUL_SUBMISSION);//
                inventory.setItem(22, null);
                p.closeInventory();
            }
        } else if (e.getCurrentItem().getType() == super.CANCEL.getType() && e.getSlot() == 18) {
            new ShowDeliveryBoardMenu(playerMenuUtility).open();
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack detailsItem = super.INFO;
        ItemMeta detailsMeta = detailsItem.getItemMeta();

        //List<String> detailsLore = new ArrayList<>();//
        List<String> detailsLore = Message.THD_ICON_ITEM_LORE;
        detailsMeta.setLore(detailsLore);
        detailsItem.setItemMeta(detailsMeta);

        inventory.setItem(11, detailsItem);

        //Dupe fix
        ItemStack iconItem = DeliveryBoard.getThreeHourlyItem().clone();
        ItemMeta meta = iconItem.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (meta.hasLore()) lore = meta.getLore();
        lore.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "ID: " + System.currentTimeMillis());
        meta.setLore(lore);
        iconItem.setItemMeta(meta);
        inventory.setItem(15, iconItem);

        //Item delivery slot 22
        //Decorating around it
        for (int i = 0; i < 27; i++) {
            if (inventory.getItem(i) == null && i != 22) {
                inventory.setItem(i, super.FILLER_GLASS);
            }
        }

        ItemStack confirmDeliveryItem = super.ACCEPT;
        ItemMeta confirmDeliveryMeta = confirmDeliveryItem.getItemMeta();
        confirmDeliveryMeta.setDisplayName(Message.THD_SUBMIT_ITEM_DISPLAY);//
        //List<String> confirmDeliveryLore = new ArrayList<>();//
        List<String> confirmDeliveryLore = Message.THD_SUBMIT_ITEM_LORE;
        confirmDeliveryMeta.setLore(confirmDeliveryLore);
        confirmDeliveryItem.setItemMeta(confirmDeliveryMeta);

        ItemStack backToDeliveryBoardMenu = super.CANCEL;
        ItemMeta backToDeliveryBoardMenuMeta = backToDeliveryBoardMenu.getItemMeta();
        backToDeliveryBoardMenuMeta.setDisplayName(Message.THD_BACK_ITEM_DISPLAY);//
        backToDeliveryBoardMenu.setItemMeta(backToDeliveryBoardMenuMeta);

        inventory.setItem(26, confirmDeliveryItem);
        inventory.setItem(18, backToDeliveryBoardMenu);
    }

    @Override
    public void onClose(InventoryCloseEvent e) {
        Player p = playerMenuUtility.getOwner();
        if (inventory.getItem(22) == null) {
            return;
        } else {
            ItemStack dropItem = inventory.getItem(22);
            p.getWorld().dropItem(p.getLocation(), dropItem);
        }
    }
}
