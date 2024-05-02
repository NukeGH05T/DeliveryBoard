package me.nukeghost.menusystem.menu.deliverymenu;

import me.clip.placeholderapi.PlaceholderAPI;
import me.nukeghost.DeliveryBoard;
import me.nukeghost.handlers.RewardHandler;
import me.nukeghost.handlers.SkipHandler;
import me.nukeghost.handlers.VerificationHandler;
import me.nukeghost.language.Message;
import me.nukeghost.menusystem.Menu;
import me.nukeghost.menusystem.PlayerMenuUtility;
import me.nukeghost.menusystem.menu.ShowDeliveryBoardMenu;
import me.nukeghost.template.Delivery;
import me.nukeghost.utils.PlaceholderUtils;
import org.bukkit.Bukkit;
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
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            return PlaceholderAPI.setPlaceholders(playerMenuUtility.getOwner(), playerMenuUtility.getDeliveryGUITitle());
        }

        return playerMenuUtility.getDeliveryTitle();
    }

    @Override
    public int getSlots() {
        return 27;
    }

    int inputSlotIndex = super.INPUT_SLOT;
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

        if (e.getCurrentItem().getType() == super.SKIP.getType() && e.getSlot() == super.SKIP_SLOT) {
            //Deduct cost if they have skip perm
            if (!(new SkipHandler().hasDeductedSkipCost(playerMenuUtility, deliveries.get(deliveryIndexInList)))) return;

            //Max submission
            Delivery delivery = deliveries.get(deliveryIndexInList);
            if (!p.hasPermission("deliveryboard.delivery.forcesubmit") && delivery.isHasReachedMaxSubmission()) return;

            //Give proper reward
            RewardHandler rewardHandler = new RewardHandler(DeliveryBoard.plugin);
            rewardHandler.giveRewards(p, playerMenuUtility.getDeliveryID());

            //Add accumulation
            if (deliveries.get(deliveryIndexInList).isAccummulate()) deliveries.get(deliveryIndexInList).addAccumulation(playerMenuUtility.getOwner());

            //Put the player in an hourly tracking hashmap so, player can't redo the same delivery!
            DeliveryBoard.deliveryCompletedPlayerList.get(deliveryIndexInList).add(p.getUniqueId());

            p.closeInventory();

            if (DeliveryBoard.deliveryCompletedPlayerList.get(deliveryIndexInList).size() >= delivery.getMaxSubmission()) {
                deliveries.get(deliveryIndexInList).setHasReachedMaxSubmission(true);
                //System.out.println("Max submissions reached!");
            }
        }

        if (e.getCurrentItem().getType() == super.ACCEPT.getType() && e.getSlot() == super.ACCEPT_SLOT) {
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

                //Add accumulation
                if (deliveries.get(deliveryIndexInList).isAccummulate()) deliveries.get(deliveryIndexInList).addAccumulation(playerMenuUtility.getOwner());

                //Put the player in an hourly tracking hashmap so, player can't redo the same delivery!
                DeliveryBoard.deliveryCompletedPlayerList.get(deliveryIndexInList).add(p.getUniqueId());

                p.sendMessage(Message.SUCCESSFUL_SUBMISSION);//
                //TODO: Use it to drop the extra items once the specific type of item is no longer required.
                int amountNeeded = inventory.getItem(super.ICON_SLOT).getAmount();

                ItemStack inputtedItems = inventory.getItem(super.INPUT_SLOT);
                int amountSubmitted = inputtedItems.getAmount();

                if (amountNeeded == amountSubmitted) {
                    inventory.setItem(super.INPUT_SLOT, null);
                } else if (amountNeeded < amountSubmitted) {
                    inputtedItems.setAmount(inputtedItems.getAmount() - amountNeeded);
                    inventory.setItem(super.INPUT_SLOT, null);

                    //Drop the extra items
                    playerMenuUtility.getOwner().getWorld().dropItem(playerMenuUtility.getOwner().getLocation(), inputtedItems);
                }
                p.closeInventory();

                if (DeliveryBoard.deliveryCompletedPlayerList.get(deliveryIndexInList).size() >= delivery.getMaxSubmission()) {
                    deliveries.get(deliveryIndexInList).setHasReachedMaxSubmission(true);
                    System.out.println("Max submissions reached!");
                }
            }
        } else if (e.getCurrentItem().getType() == super.CANCEL.getType() && e.getSlot() == super.CANCEL_SLOT) {
            new ShowDeliveryBoardMenu(playerMenuUtility).open();
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack detailsItem = super.INFO;
        ItemMeta detailsMeta = detailsItem.getItemMeta();
        detailsMeta.setDisplayName(deliveries.get(deliveryIndexInList).getDeliveryInfoTitle());
        List<String> detailsLore = deliveries.get(deliveryIndexInList).getDeliveryInfoLore();
        detailsMeta.setLore(PlaceholderUtils.parsePlaceholders(detailsLore, playerMenuUtility.getOwner(), deliveries.get(deliveryIndexInList).getDeliveryName(), deliveryIndexInList));
        detailsItem.setItemMeta(detailsMeta);
        inventory.setItem(super.INFO_SLOT, detailsItem);

        //Dupe fix
        ItemStack iconItem = deliveries.get(deliveryIndexInList).getDeliveryItem().clone();
        ItemMeta meta = iconItem.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (meta.hasLore()) lore = meta.getLore();
        lore.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "ID: " + System.currentTimeMillis());
        meta.setLore(lore);
        iconItem.setItemMeta(meta);
        inventory.setItem(super.ICON_SLOT, iconItem);

        //Item delivery slot 22
        //Decorating around it
        for (int i = 0; i < 27; i++) {
            if (inventory.getItem(i) == null && i != inputSlotIndex) {
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
        if (deliveries.get(deliveryIndexInList).getSkipCost() < 0) { //Don't create skip if skipping is disabled
            ItemStack skipItem = super.SKIP;
            ItemMeta skipMeta = skipItem.getItemMeta();
            skipMeta.setDisplayName(Message.SKIP_ITEM_DISPLAY);
            skipMeta.setLore(parsePlaceholders(Message.SKIP_ITEM_LORE, playerMenuUtility.getOwner(), null, deliveryIndexInList));
            skipItem.setItemMeta(skipMeta);
            inventory.setItem(super.SKIP_SLOT, skipItem);
        }

        inventory.setItem(super.ACCEPT_SLOT, confirmDeliveryItem);
        inventory.setItem(super.CANCEL_SLOT, backToDeliveryBoardMenu);
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
