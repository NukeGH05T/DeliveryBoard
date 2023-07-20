package me.nukeghost.menusystem.menu.boardconfig;

import me.nukeghost.DeliveryBoard;
import me.nukeghost.external.plugins.ExtEcoItems;
import me.nukeghost.external.plugins.ExtExecutableItems;
import me.nukeghost.external.plugins.ExtItemsAdder;
import me.nukeghost.external.plugins.ExtMMOItems;
import me.nukeghost.handlers.GenerationHandler;
import me.nukeghost.handlers.VerificationHandler;
import me.nukeghost.language.Message;
import me.nukeghost.menusystem.PaginatedMenu;
import me.nukeghost.menusystem.PlayerMenuUtility;
import me.nukeghost.utils.ColorUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import static me.nukeghost.DeliveryBoard.plugin;

public class EditRequiredItemsMenu extends PaginatedMenu {
    public EditRequiredItemsMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        String editBoardTitle = plugin.getConfig().getString("gui.titles.editBoardTitle");
        return ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(editBoardTitle));
    }

    @Override
    public int getSlots() {
        return 54;
    }

    List<String> requiredItemsList;


    @Override
    public void handleMenu(InventoryClickEvent e) {
        e.setCancelled(true);
        requiredItemsList = plugin.getConfig().getStringList("delivery." + playerMenuUtility.getDeliveryID() + ".allowed-materials");

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
                if (!((index + 1) >= requiredItemsList.size())) {
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

        } else if (!e.getCurrentItem().equals(super.FILLER_GLASS) && e.getClickedInventory().getType() != InventoryType.PLAYER) {
            List<String> configItemlList = plugin.getConfig().getStringList("delivery." + playerMenuUtility.getDeliveryID() + ".allowed-materials");

            //Check against all items
            for (String itemString : configItemlList) {
                ItemStack reqItem = GenerationHandler.generateItemFromString(itemString);
                //This item is the clicked item
                if (new VerificationHandler(reqItem).checkItem(e.getCurrentItem())) {

                    if (e.getClick() == ClickType.RIGHT && e.getClickedInventory() != null) {
                        //Remove the item
                        configItemlList.remove(itemString);

                        //Saving in the config
                        plugin.getConfig().set("delivery." + playerMenuUtility.getDeliveryID() + ".allowed-materials", configItemlList);
                        plugin.saveConfig();

                        //Refreshing the GUI
                        inventory.clear();
                        setMenuItems();
                        break;
                    }
                }
            }
        } else if (e.getClickedInventory().getType() == InventoryType.PLAYER) {
            //Add the item
            ItemStack handItem = e.getCurrentItem();

            String finalString = null;
            //Serialize the item and store it as string
            try {
                ByteArrayOutputStream io = new ByteArrayOutputStream();
                BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);

                os.writeObject(handItem);
                os.flush();

                byte[] serializedObject = io.toByteArray();
                String serString = Base64.getEncoder().encodeToString(serializedObject);

                finalString = "ser@" + serString + "@1";

            } catch (IOException ex) {
                p.sendMessage(ChatColor.RED + "[DB] An error occured while saving the item. Check console for more info.");
                ex.printStackTrace();
            }

            if (finalString == null) {
                finalString = Message.ADD_INVALID_ITEM;
                return;
            }

            List<String> reqList = plugin.getConfig().getStringList("delivery." + playerMenuUtility.getDeliveryID() + ".allowed-materials");
            reqList.add(finalString);
            plugin.getConfig().set("delivery." + playerMenuUtility.getDeliveryID() + ".allowed-materials", reqList);
            plugin.saveConfig();

            p.sendMessage(ChatColor.DARK_AQUA + "[DB] Item saved successfully.");
            p.sendMessage(ChatColor.GRAY + "[DB] Dynamic items(most MMOItems items) will not work properly if added using this GUI. Please use " +
                    ChatColor.GREEN + "/db add <delivery> <plugin> " + ChatColor.GRAY + "to add dynamic items.");

            //Refreshing the GUI
            inventory.clear();
            setMenuItems();
        }
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();
        List<String> itemsList = plugin.getConfig().getStringList("delivery." + playerMenuUtility.getDeliveryID() + ".allowed-materials");

        if (itemsList != null && !itemsList.isEmpty()) {
            for (int i = 0; i < super.maxItemsPerPage; i++) {
                index = super.maxItemsPerPage * page + i;
                if (index >= itemsList.size()) break;
                if (itemsList.get(index) != null){

                    ItemStack reqItem = GenerationHandler.generateItemFromString(itemsList.get(index));
//                    ItemMeta reqMeta = reqItem.getItemMeta();
//
//                    List<String> lore = new ArrayList<>();
//                    lore.add(ChatColor.RED + "[Right Click]" + ChatColor.YELLOW + " to remove item from list");
//                    if (reqMeta.hasLore()) {
//                        for (String line : reqMeta.getLore()) {
//                            lore.add(line);
//                        }
//                    }
//
//                    reqMeta.setLore(lore);
//                    reqItem.setItemMeta(reqMeta);

                    inventory.addItem(reqItem);

                }
            }
        }

    }

    @Override
    public void onClose(InventoryCloseEvent e) {

    }
}
