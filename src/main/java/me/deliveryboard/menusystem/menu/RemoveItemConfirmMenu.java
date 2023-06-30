package me.deliveryboard.menusystem.menu;

import me.deliveryboard.menusystem.Menu;
import me.deliveryboard.menusystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RemoveItemConfirmMenu extends Menu {
    public RemoveItemConfirmMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return null;
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        switch (e.getCurrentItem().getType()) {
            case LIGHT_BLUE_STAINED_GLASS_PANE:
                e.getWhoClicked().closeInventory();
                e.getWhoClicked().sendMessage("Confirmed");
                break;
            case RED_STAINED_GLASS_PANE:
                e.getWhoClicked().closeInventory();
                e.getWhoClicked().sendMessage("Cancelled");
                break;
            default:
                break;
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack yes = super.ACCEPT;//11th Slot
        ItemMeta yesMeta = yes.getItemMeta();
        yesMeta.setDisplayName(ChatColor.AQUA + "Yes");
        yes.setItemMeta(yesMeta);

        ItemStack no = super.CANCEL;//15th Slot
        ItemMeta noMeta = no.getItemMeta();
        noMeta.setDisplayName(ChatColor.RED + "No");
        no.setItemMeta(noMeta);

        inventory.setItem(11, yes);
        inventory.setItem(15, no);

        for (int i = 0;i < 27; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, super.FILLER_GLASS);
            }
        }

    }

    @Override
    public void onClose(InventoryCloseEvent e) {

    }
}
