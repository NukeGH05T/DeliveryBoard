package me.nukeghost.menusystem.menu.boardconfig;

import me.nukeghost.DeliveryBoard;
import me.nukeghost.language.Message;
import me.nukeghost.menusystem.PaginatedMenu;
import me.nukeghost.menusystem.PlayerMenuUtility;
import me.nukeghost.utils.RewardUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static me.nukeghost.DeliveryBoard.plugin;

public class EditRandomRewardsMenu extends PaginatedMenu {
    public EditRandomRewardsMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Edit Random Rewards";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        e.setCancelled(true);
        List<String> rewardList = plugin.getConfig().getStringList("delivery." + playerMenuUtility.getDeliveryID() + ".reward-setup.mixed.random-rewards-pool");

        Player p = (Player) e.getWhoClicked();


        //Loading the config before making changes
        try {
            DeliveryBoard.plugin.reloadConfig();
            DeliveryBoard.plugin.saveConfig();
        } catch (Exception ex) {
            p.sendMessage(Message.RELOAD_FAILED);
            ex.printStackTrace();
        }

        if (e.getSlot() == 45 || e.getSlot() == 53) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(Message.PGN_PREVIOUS_PAGE)) {
                if (page == 0) {
                    p.sendMessage(Message.ON_FIRST_PAGE);
                } else {
                    page = page - 1;
                    super.open();
                }
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(Message.PGN_NEXT_PAGE)) {
                if (!((index + 1) >= rewardList.size())) {
                    page = page + 1;
                    super.open();
                } else {
                    p.sendMessage(Message.ON_LAST_PAGE);
                }
            }
        } else if (e.getSlot() == 49) {
            //Closing inventory
            p.closeInventory();
            new EditBoardMainMenu(playerMenuUtility).open();
        } else if (e.getSlot() == 51 || e.getSlot() == 47) {
            //Add Reward Item inventory
            p.closeInventory();

            HashMap<PaginatedMenu, PlayerMenuUtility> menuUtilityHashMap = new HashMap<>();
            menuUtilityHashMap.put(this, playerMenuUtility);
            DeliveryBoard.adminAddCommandRewardHashMap.put(p, menuUtilityHashMap);

            if (e.getSlot() == 51) {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "comm:" + ChatColor.WHITE + " give {PLAYER_NAME} minecraft:diamond 10"));
            } else {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "Insert a whole number"));
            }

        }  else if (e.getClickedInventory().getType() == InventoryType.PLAYER && e.getClick() == ClickType.LEFT) {
            //Add the item
            ItemStack handItem = e.getCurrentItem();

            String finalString = "item@" + handItem.getType() + "@" + handItem.getAmount();

            List<String> reqList = plugin.getConfig().getStringList("delivery." + playerMenuUtility.getDeliveryID() + ".reward-setup.mixed.random-rewards-pool");
            reqList.add(finalString);
            plugin.getConfig().set("delivery." + playerMenuUtility.getDeliveryID() + ".reward-setup.mixed.random-rewards-pool", reqList);
            plugin.saveConfig();

            p.sendMessage(ChatColor.DARK_AQUA + "[DB] Item added successfully.");

            //Refreshing the GUI
            inventory.clear();
            setMenuItems();
        } else if (!e.getCurrentItem().equals(super.FILLER_GLASS) && e.getClickedInventory().getType() != InventoryType.PLAYER) {
            List<String> configItemlList = plugin.getConfig().getStringList("delivery." + playerMenuUtility.getDeliveryID() + ".reward-setup.mixed.random-rewards-pool");

            //Check against all items
            for (String itemString : configItemlList) {
                ItemStack reqItem = RewardUtils.getRewardFromString(itemString);
                if (reqItem.equals(e.getCurrentItem())) {
                    if (e.getClick() == ClickType.RIGHT && e.getClickedInventory() != null) {
                        //Remove the item
                        configItemlList.remove(itemString);

                        //Saving in the config
                        plugin.getConfig().set("delivery." + playerMenuUtility.getDeliveryID() + ".reward-setup.mixed.random-rewards-pool", configItemlList);
                        plugin.saveConfig();

                        //Refreshing the GUI
                        inventory.clear();
                        setMenuItems();
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();
        List<String> rewardList = plugin.getConfig().getStringList("delivery." + playerMenuUtility.getDeliveryID() + ".reward-setup.mixed.random-rewards-pool");

        if (rewardList != null && !rewardList.isEmpty()) {
            for (int i = 0; i < super.maxItemsPerPage; i++) {
                index = super.maxItemsPerPage * page + i;
                if (index >= rewardList.size()) break;
                if (rewardList.get(index) != null) {

                    inventory.addItem(RewardUtils.getRewardFromString(rewardList.get(index)));

                }
            }
        }

        //AddRewardItem button
        ItemStack addRewardItem = new ItemStack(Material.COMMAND_BLOCK);
        ItemMeta addRewardItemItemMeta = addRewardItem.getItemMeta();
        addRewardItemItemMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Add Command Reward");
        addRewardItemItemMeta.setLore(Collections.singletonList(ChatColor.DARK_GRAY + "Add a command to random reward pool"));
        addRewardItem.setItemMeta(addRewardItemItemMeta);

        inventory.setItem(51, addRewardItem);

        //AddRewardItem button
        ItemStack setMaxAmount = new ItemStack(Material.COMPARATOR);
        ItemMeta setMaxAmountItemMeta = setMaxAmount.getItemMeta();
        setMaxAmountItemMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Set Max Reward Amount");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Current max amount: " + ChatColor.WHITE + plugin.getConfig().getInt("delivery." + playerMenuUtility.getDeliveryID() + ".reward-setup.mixed.max-reward"));
        lore.add(ChatColor.DARK_GRAY + "Set the amount of rewards to give from");
        lore.add(ChatColor.DARK_GRAY + "random reward pool");
        setMaxAmountItemMeta.setLore(lore);
        setMaxAmount.setItemMeta(setMaxAmountItemMeta);

        inventory.setItem(47, setMaxAmount);
    }

    @Override
    public void onClose(InventoryCloseEvent e) {

    }
}
