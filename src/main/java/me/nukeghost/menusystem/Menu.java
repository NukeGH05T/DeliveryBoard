package me.nukeghost.menusystem;

import me.nukeghost.utils.MenuUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import static me.nukeghost.handlers.GenerationHandler.generateStaticItem;

public abstract class Menu implements InventoryHolder {
    protected Inventory inventory;

    //Use custom item generator XD who wants only vanilla items lol
    protected ItemStack FILLER_GLASS = generateStaticItem("fillerMaterial");
    protected ItemStack ACCEPT = generateStaticItem("acceptMaterial");
    protected ItemStack SKIP = generateStaticItem("skipMaterial");
    protected ItemStack CANCEL = generateStaticItem("cancelMaterial");
    protected ItemStack INFO = generateStaticItem("infoMaterial");

    protected int ACCEPT_SLOT = MenuUtils.loadDeliveryMenuSlots("accept");
    protected int CANCEL_SLOT = MenuUtils.loadDeliveryMenuSlots("cancel");
    protected int INFO_SLOT = MenuUtils.loadDeliveryMenuSlots("info");
    protected int SKIP_SLOT = MenuUtils.loadDeliveryMenuSlots("skip");
    protected int ICON_SLOT = MenuUtils.loadDeliveryMenuSlots("icon");
    protected int INPUT_SLOT = MenuUtils.loadDeliveryMenuSlots("input");

    protected PlayerMenuUtility playerMenuUtility;

    public Menu(PlayerMenuUtility playerMenuUtility) {
        this.playerMenuUtility = playerMenuUtility;
    }

    public abstract String getMenuName();
    public abstract int getSlots();
    public abstract void handleMenu(InventoryClickEvent e);
    public abstract void setMenuItems();
    public void open() {
        inventory = Bukkit.createInventory(this, getSlots(), getMenuName());
        this.setMenuItems();
        playerMenuUtility.getOwner().openInventory(inventory);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public abstract void onClose(InventoryCloseEvent e);
}
