package me.nukeghost.menusystem;

import me.nukeghost.language.Message;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static me.nukeghost.handlers.GenerationHandler.generateStaticItem;

public abstract class PaginatedMenu extends Menu{
    protected int page = 0;

    //28 empty slots per page
    protected int maxItemsPerPage = 28;

    protected int index = 0;


    protected ItemStack PREVIOUS_ARROW = generateStaticItem("previousPageMaterial");
    protected ItemStack NEXT_ARROW = generateStaticItem("nextPageMaterial");
    protected ItemStack CLOSE_BTN = generateStaticItem("closePageMaterial");
    protected ItemStack INFO_ITEM = generateStaticItem("infoPageMaterial");

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


        ItemStack previousArrow = PREVIOUS_ARROW;
        ItemMeta prevMeta = previousArrow.getItemMeta();
        prevMeta.setDisplayName(Message.PGN_PREVIOUS_PAGE);
        previousArrow.setItemMeta(prevMeta);
        inventory.setItem(45, previousArrow);

        ItemStack nextArrow = NEXT_ARROW;
        ItemMeta nextMeta = nextArrow.getItemMeta();
        nextMeta.setDisplayName(Message.PGN_NEXT_PAGE);
        nextArrow.setItemMeta(nextMeta);
        inventory.setItem(53, nextArrow);

        ItemStack close = CLOSE_BTN;
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName(Message.PGN_CLOSE_PAGE);
        close.setItemMeta(closeMeta);
        inventory.setItem(49, close);

        ItemStack info = INFO_ITEM;
        ItemMeta infoMeta = info.getItemMeta();
        infoMeta.setDisplayName(Message.PGN_INFO_ITEM);
        infoMeta.setLore(Message.PGN_INFO_DETAILS);
        info.setItemMeta(infoMeta);
        inventory.setItem(4, info);
    }
}
