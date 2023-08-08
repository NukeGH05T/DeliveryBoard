package me.nukeghost.menusystem.menu.deliverymenu;

import me.nukeghost.DeliveryBoard;
import me.nukeghost.handlers.RewardHandler;
import me.nukeghost.handlers.SkipHandler;
import me.nukeghost.handlers.VerificationHandler;
import me.nukeghost.language.Message;
import me.nukeghost.menusystem.Menu;
import me.nukeghost.menusystem.PlayerMenuUtility;
import me.nukeghost.menusystem.menu.ShowDeliveryBoardMenu;
import me.nukeghost.template.Delivery;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static me.nukeghost.DeliveryBoard.deliveries;
import static me.nukeghost.utils.PlaceholderUtils.parsePlaceholders;

public class DeliveryMenu extends Menu {
    public DeliveryMenu(PlayerMenuUtility playerMenuUtility, int index) {
        super(playerMenuUtility);
        deliveryIndexInList = index;
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
    int deliveryIndexInList = 0;

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.isShiftClick()) {
            e.setCancelled(true);
        }
        if (e.getSlot() != inputSlotIndex && e.getInventory() == e.getClickedInventory()) {
            e.setCancelled(true);
        }

        if (e.getCurrentItem().getType() == super.SKIP.getType() && e.getSlot() == 13) {
            //Deduct cost if they have skip perm
            if (!(new SkipHandler().hasDeductedSkipCost(playerMenuUtility, deliveries.get(deliveryIndexInList)))) return;

            //Max submission
            Delivery delivery = deliveries.get(deliveryIndexInList);
            if (!p.hasPermission("deliveryboard.delivery.forcesubmit") && delivery.isHasReachedMaxSubmission()) return;

            //Give proper reward
            RewardHandler rewardHandler = new RewardHandler(DeliveryBoard.plugin);
            rewardHandler.giveRewards(p, playerMenuUtility.getDeliveryID());

            //Put the player in an hourly tracking hashmap so, player can't redo the same delivery!
            DeliveryBoard.deliveryCompletedPlayerList.get(deliveryIndexInList).add(p);

            p.closeInventory();

            if (DeliveryBoard.deliveryCompletedPlayerList.get(deliveryIndexInList).size() >= delivery.getMaxSubmission()) {
                deliveries.get(deliveryIndexInList).setHasReachedMaxSubmission(true);
                System.out.println("Max submissions reached!");
            }
        }

        if (e.getCurrentItem().getType() == super.ACCEPT.getType() && e.getSlot() == 26) {
            if (e.getInventory().getItem(inputSlotIndex) == null) {
                p.sendMessage(Message.EMPTY_SUBMISSION);//
                return;
            }

            VerificationHandler verificationHandler = new VerificationHandler(deliveries.get(deliveryIndexInList).getDeliveryItem());

            if (!verificationHandler.checkItem(e.getInventory().getItem(inputSlotIndex))) {
                p.sendMessage(Message.WRONG_SUBMISSION);//
            } else if (verificationHandler.checkItem(e.getInventory().getItem(inputSlotIndex))) {
                Delivery delivery = deliveries.get(deliveryIndexInList);
                if (!p.hasPermission("deliveryboard.delivery.forcesubmit") && delivery.isHasReachedMaxSubmission()) return;
                //Give proper reward
                RewardHandler rewardHandler = new RewardHandler(DeliveryBoard.plugin);
                rewardHandler.giveRewards(p, playerMenuUtility.getDeliveryID());

                //Put the player in an hourly tracking hashmap so, player can't redo the same delivery!
                DeliveryBoard.deliveryCompletedPlayerList.get(deliveryIndexInList).add(p);

                p.sendMessage(Message.SUCCESSFUL_SUBMISSION);//
                inventory.setItem(22, null);
                p.closeInventory();

                if (DeliveryBoard.deliveryCompletedPlayerList.get(deliveryIndexInList).size() >= delivery.getMaxSubmission()) {
                    deliveries.get(deliveryIndexInList).setHasReachedMaxSubmission(true);
                    System.out.println("Max submissions reached!");
                }
            }
        } else if (e.getCurrentItem().getType() == super.CANCEL.getType() && e.getSlot() == 18) {
            new ShowDeliveryBoardMenu(playerMenuUtility).open();
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack detailsItem = super.INFO;
        ItemMeta detailsMeta = detailsItem.getItemMeta();
        List<String> detailsLore = Message.ICON_ITEM_LORE;
        detailsMeta.setLore(detailsLore);
        detailsItem.setItemMeta(detailsMeta);
        inventory.setItem(11, detailsItem);

        //Dupe fix
        ItemStack iconItem = deliveries.get(deliveryIndexInList).getDeliveryItem().clone();
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
        confirmDeliveryMeta.setDisplayName(Message.SUBMIT_ITEM_DISPLAY);
        List<String> confirmDeliveryLore = Message.SUBMIT_ITEM_LORE;
        confirmDeliveryMeta.setLore(confirmDeliveryLore);
        confirmDeliveryItem.setItemMeta(confirmDeliveryMeta);

        //Cancel Button
        ItemStack backToDeliveryBoardMenu = super.CANCEL;
        ItemMeta backToDeliveryBoardMenuMeta = backToDeliveryBoardMenu.getItemMeta();
        backToDeliveryBoardMenuMeta.setDisplayName(Message.BACK_ITEM_DISPLAY);//
        backToDeliveryBoardMenu.setItemMeta(backToDeliveryBoardMenuMeta);

        //Skip Button
        ItemStack skipItem = super.SKIP;
        ItemMeta skipMeta = skipItem.getItemMeta();
        skipMeta.setDisplayName(Message.SKIP_ITEM_DISPLAY);
        skipMeta.setLore(parsePlaceholders(Message.SKIP_ITEM_LORE, playerMenuUtility.getOwner(), null, deliveryIndexInList));
        skipItem.setItemMeta(skipMeta);

        inventory.setItem(13, skipItem);
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
