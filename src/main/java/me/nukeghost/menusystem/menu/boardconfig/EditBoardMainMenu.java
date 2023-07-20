package me.nukeghost.menusystem.menu.boardconfig;

import me.nukeghost.language.Message;
import me.nukeghost.menusystem.Menu;
import me.nukeghost.menusystem.PlayerMenuUtility;
import me.nukeghost.utils.ColorUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static me.nukeghost.DeliveryBoard.plugin;

public class EditBoardMainMenu extends Menu {
    public EditBoardMainMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        String editBoardTitle = plugin.getConfig().getString("gui.titles.editBoardTitle") + playerMenuUtility.getDeliveryID();
        return ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(editBoardTitle));
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        e.setCancelled(true);
        if (e.getCurrentItem() == null) return;
        Player p = (Player) e.getWhoClicked();

        if (e.getSlot() == 10 && e.getCurrentItem().getType().equals(Material.CHEST_MINECART)) {
            p.closeInventory();
            new EditRequiredItemsMenu(playerMenuUtility).open();
        } else if (e.getSlot() == 13 && e.getCurrentItem().getType().equals(Material.BEACON)) {
            p.closeInventory();
            new EditConfirmedRewardsMenu(playerMenuUtility).open();
        } else if (e.getSlot() == 16 && e.getCurrentItem().getType().equals(Material.ENDER_CHEST)) {
            p.closeInventory();
            new EditRandomRewardsMenu(playerMenuUtility).open();
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack requiredItemMenu = new ItemStack(Material.CHEST_MINECART);
        ItemMeta requiredItemMeta = requiredItemMenu.getItemMeta();
        requiredItemMeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Required Items");
        List<String> requiredLore = new ArrayList<>();
        requiredLore.add(ChatColor.GRAY + "Add/Remove all the required");
        requiredLore.add(ChatColor.GRAY + "items for this delivery.");
        requiredItemMeta.setLore(requiredLore);
        requiredItemMenu.setItemMeta(requiredItemMeta);
        inventory.setItem(10, requiredItemMenu);

        ItemStack confirmedRewardMenu = new ItemStack(Material.BEACON);
        ItemMeta confirmedRewardMeta = confirmedRewardMenu.getItemMeta();
        confirmedRewardMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Confirmed Rewards");
        List<String> confirmedRewardLore = new ArrayList<>();
        confirmedRewardLore.add(ChatColor.GRAY + "Add/Remove all the confirmed");
        confirmedRewardLore.add(ChatColor.GRAY + "rewards for this delivery.");
        confirmedRewardMeta.setLore(confirmedRewardLore);
        confirmedRewardMenu.setItemMeta(confirmedRewardMeta);
        inventory.setItem(13, confirmedRewardMenu);

        ItemStack randomRewardMenu = new ItemStack(Material.ENDER_CHEST);
        ItemMeta randomRewardMeta = randomRewardMenu.getItemMeta();
        randomRewardMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Random Rewards");
        List<String> randomRewardLore = new ArrayList<>();
        randomRewardLore.add(ChatColor.GRAY + "Add/Remove all the random");
        randomRewardLore.add(ChatColor.GRAY + "rewards for this delivery.");
        randomRewardMeta.setLore(randomRewardLore);
        randomRewardMenu.setItemMeta(randomRewardMeta);
        inventory.setItem(16, randomRewardMenu);

        //Decorating around it
        for (int i = 0; i < 27; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, super.FILLER_GLASS);
            }
        }
    }

    @Override
    public void onClose(InventoryCloseEvent e) {

    }
}
