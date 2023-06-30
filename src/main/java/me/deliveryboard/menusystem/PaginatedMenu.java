package me.deliveryboard.menusystem;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class PaginatedMenu extends Menu{
    protected int page = 0;

    //28 empty slots per page
    protected int maxItemsPerPage = 28;

    protected int index = 0;

    public PaginatedMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    public void addMenuBorder() {
        for (int i = 0; i < 9; ++i) {
            inventory.setItem(i, super.FILLER_GLASS);
        }

        for (int i = 45; i < 54; ++i) {
            if (i != 45 && i != 53) {
                inventory.setItem(i, super.FILLER_GLASS);
            }
        }

        //Side Glasses
        inventory.setItem(9, super.FILLER_GLASS);
        inventory.setItem(17, super.FILLER_GLASS);
        inventory.setItem(18, super.FILLER_GLASS);
        inventory.setItem(26, super.FILLER_GLASS);
        inventory.setItem(27, super.FILLER_GLASS);
        inventory.setItem(35, super.FILLER_GLASS);
        inventory.setItem(36, super.FILLER_GLASS);
        inventory.setItem(44, super.FILLER_GLASS);


        ItemStack arrow = new ItemStack(Material.SPECTRAL_ARROW);
        ItemMeta arrMeta = arrow.getItemMeta();
        arrMeta.setDisplayName(ChatColor.DARK_AQUA + "< Previous Page");
        arrow.setItemMeta(arrMeta);
        inventory.setItem(45, arrow);

        arrMeta.setDisplayName(ChatColor.DARK_AQUA + "Next Page >");
        arrow.setItemMeta(arrMeta);
        inventory.setItem(53, arrow);

        ItemStack close = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName(ChatColor.DARK_RED + "Close");
        close.setItemMeta(closeMeta);
        inventory.setItem(49, close);
    }
}
