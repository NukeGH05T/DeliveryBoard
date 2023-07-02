package me.deliveryboard.handlers;

import com.ssomar.score.api.executableitems.config.ExecutableItemsManagerInterface;
import dev.lone.itemsadder.api.CustomStack;
import me.deliveryboard.DeliveryBoard;
import me.deliveryboard.external.plugins.ExtEcoItems;
import me.deliveryboard.external.plugins.ExtExecutableItems;
import me.deliveryboard.external.plugins.ExtItemsAdder;
import me.deliveryboard.external.plugins.ExtMMOItems;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

import static com.ssomar.score.api.executableitems.ExecutableItemsAPI.getExecutableItemsManager;
import static me.deliveryboard.DeliveryBoard.enabledItemPlugins;
import static me.deliveryboard.DeliveryBoard.plugin;

public class GenerationHandler {
    public static ItemStack generateDeliveryItem(String deliveryID) {
        List<String> allowedItemsList = plugin.getConfig().getStringList("delivery." + deliveryID + ".allowed-materials");

        if (allowedItemsList.isEmpty()) return null;
        int randomItemIndex = getRandomEntry(allowedItemsList);

        if (randomItemIndex == -1) {
            Bukkit.getLogger().severe("An error occured while indexing " + deliveryID + " deliveries. Please make sure the allowed materials aren't empty!");
            return null;
        }

        String[] allowedItemString = parseMaterialAndAmount(allowedItemsList.get(randomItemIndex));

        if (allowedItemString[0].equalsIgnoreCase("van")){

            ItemStack deliveryItem = new ItemStack(Material.getMaterial(allowedItemString[1]), Integer.parseInt(allowedItemString[2]));
            return deliveryItem;
        } else {
            String itemID = allowedItemString[1];
            String itemType = allowedItemString.length > 2 ? allowedItemString[2] : "";

            if (allowedItemString[0].equalsIgnoreCase("mmo")) {
                //MMOItems - 'mmo@CUTLASS@SWORD'
                if (enabledItemPlugins.contains("MMOItems")) {
                    ExtMMOItems mmoItems = new ExtMMOItems();
                    DeliveryBoard.usedItemPluginsHashMap.put(deliveryID, new ExtMMOItems());
                    return mmoItems.generateItem(itemID, itemType);
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + allowedItemString[0] + " is not enabled but, present in allowed material for " + ChatColor.YELLOW + deliveryID);
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Please enable the plugin or remove any related items");
                }

            } else if (allowedItemString[0].equalsIgnoreCase("iad")) {
                //ItemsAdder - 'iad@iageneric:coin@1'
                if (enabledItemPlugins.contains("ItemsAdder")) {
                    if (CustomStack.isInRegistry(itemID)) {
                        ExtItemsAdder itemsAdder = new ExtItemsAdder();
                        DeliveryBoard.usedItemPluginsHashMap.put(deliveryID, new ExtItemsAdder());
                        return itemsAdder.generateItem(itemID, itemType);
                    } else {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Invalid item ID provided: " + itemID + " for " + deliveryID);
                    }
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + allowedItemString[0] + " is not enabled but, present in allowed material for " + ChatColor.YELLOW + deliveryID);
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Please enable the plugin or remove any related items");
                }

            } else if (allowedItemString[0].equalsIgnoreCase("eco")) {
                //EcoItems - 'eco@grappling_hook'
                //Might malfunction at any time
                if (enabledItemPlugins.contains("EcoItems")) {
                    ExtEcoItems ecoItems = new ExtEcoItems();
                    DeliveryBoard.usedItemPluginsHashMap.put(deliveryID, new ExtEcoItems());
                    return ecoItems.generateItem(itemID, itemType);
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + allowedItemString[0] + " is not enabled but, present in allowed material for " + ChatColor.YELLOW + deliveryID);
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Please enable the plugin or remove any related items");
                }
            } else if (allowedItemString[0].equalsIgnoreCase("exi")) {
                //ExecutableItem - 'exi@iageneric:coin@1'
                if (enabledItemPlugins.contains("ExecutableItems")) {
                    ExecutableItemsManagerInterface executableItemsManager = getExecutableItemsManager();
                    if (executableItemsManager.isValidID(itemID)) {
                        ExtExecutableItems executableItems = new ExtExecutableItems();
                        DeliveryBoard.usedItemPluginsHashMap.put(deliveryID, new ExtExecutableItems());
                        return executableItems.generateItem(itemID, itemType);
                    } else {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Invalid item ID provided: " + itemID + " for " + deliveryID);
                    }
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + allowedItemString[0] + " is not enabled but, present in allowed material for " + ChatColor.YELLOW + deliveryID);
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Please enable the plugin or remove any related items");
                }

            }
        }

        return null;
    }

    public static ItemStack generateIconItem(String iconID) { //iconID is from config.yml
        String[] allowedItemString = parseMaterialAndAmount(plugin.getConfig().getString("gui.icons." + iconID));

        if (allowedItemString[0].equalsIgnoreCase("van")){

            ItemStack deliveryItem = new ItemStack(Material.getMaterial(allowedItemString[1]), Integer.parseInt(allowedItemString[2]));
            return deliveryItem;
        } else {
            String itemID = allowedItemString[1];
            String itemType = allowedItemString.length > 2 ? allowedItemString[2] : "";

            if (allowedItemString[0].equalsIgnoreCase("mmo")) {
                //MMOItems - 'mmo@CUTLASS@SWORD'
                if (enabledItemPlugins.contains("MMOItems")) {
                    return new ExtMMOItems().generateItem(itemID, itemType);
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + allowedItemString[0] + " is not enabled but, present in " + ChatColor.YELLOW + iconID);
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Please enable the plugin or remove any related items");
                }

            } else if (allowedItemString[0].equalsIgnoreCase("iad")) {
                //ItemsAdder - 'iad@iageneric:coin@1'
                if (enabledItemPlugins.contains("ItemsAdder")) {
                    if (CustomStack.isInRegistry(itemID)) {
                        return new ExtItemsAdder().generateItem(itemID, itemType);
                    } else {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Invalid item ID provided: " + itemID + " for " + iconID);
                    }
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + allowedItemString[0] + " is not enabled but, present in " + ChatColor.YELLOW + iconID);
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Please enable the plugin or remove any related items");
                }

            } else if (allowedItemString[0].equalsIgnoreCase("eco")) {
                //EcoItems - 'eco@grappling_hook'
                //Might malfunction at any time
                if (enabledItemPlugins.contains("EcoItems")) {
                    return new ExtEcoItems().generateItem(itemID, itemType);
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + allowedItemString[0] + " is not enabled but, present in " + ChatColor.YELLOW + iconID);
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Please enable the plugin or remove any related items");
                }
            } else if (allowedItemString[0].equalsIgnoreCase("exi")) {
                //ExecutableItem - 'exi@iageneric:coin@1'
                if (enabledItemPlugins.contains("ExecutableItems")) {
                    ExecutableItemsManagerInterface executableItemsManager = getExecutableItemsManager();
                    if (executableItemsManager.isValidID(itemID)) {
                        return new ExtExecutableItems().generateItem(itemID, itemType);
                    } else {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Invalid item ID provided: " + itemID + " for " + iconID);
                    }
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + allowedItemString[0] + " is not enabled but, present in " + ChatColor.YELLOW + iconID);
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Please enable the plugin or remove any related items");
                }

            }
        }

        return null;
    }

    public static ItemStack generateStaticItem(String staticID) { //iconID is from config.yml
        String[] allowedItemString = parseMaterialAndAmount(plugin.getConfig().getString("gui.static." + staticID));

        if (allowedItemString[0].equalsIgnoreCase("van")){

            ItemStack deliveryItem = new ItemStack(Material.getMaterial(allowedItemString[1]), Integer.parseInt(allowedItemString[2]));
            return deliveryItem;
        } else {
            String itemID = allowedItemString[1];
            String itemType = allowedItemString.length > 2 ? allowedItemString[2] : "";

            if (allowedItemString[0].equalsIgnoreCase("mmo")) {
                //MMOItems - 'mmo@CUTLASS@SWORD'
                if (enabledItemPlugins.contains("MMOItems")) {
                    return new ExtMMOItems().generateItem(itemID, itemType);
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + allowedItemString[0] + " is not enabled but, present in " + ChatColor.YELLOW + staticID);
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Please enable the plugin or remove any related items");
                }

            } else if (allowedItemString[0].equalsIgnoreCase("iad")) {
                //ItemsAdder - 'iad@iageneric:coin@1'
                if (enabledItemPlugins.contains("ItemsAdder")) {
                    if (CustomStack.isInRegistry(itemID)) {
                        return new ExtItemsAdder().generateItem(itemID, itemType);
                    } else {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Invalid item ID provided: " + itemID + " for " + staticID);
                    }
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + allowedItemString[0] + " is not enabled but, present in " + ChatColor.YELLOW + staticID);
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Please enable the plugin or remove any related items");
                }

            } else if (allowedItemString[0].equalsIgnoreCase("eco")) {
                //EcoItems - 'eco@grappling_hook'
                //Might malfunction at any time
                if (enabledItemPlugins.contains("EcoItems")) {
                    return new ExtEcoItems().generateItem(itemID, itemType);
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + allowedItemString[0] + " is not enabled but, present in " + ChatColor.YELLOW + staticID);
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Please enable the plugin or remove any related items");
                }
            } else if (allowedItemString[0].equalsIgnoreCase("exi")) {
                //ExecutableItem - 'exi@iageneric:coin@1'
                if (enabledItemPlugins.contains("ExecutableItems")) {
                    ExecutableItemsManagerInterface executableItemsManager = getExecutableItemsManager();
                    if (executableItemsManager.isValidID(itemID)) {
                        return new ExtExecutableItems().generateItem(itemID, itemType);
                    } else {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Invalid item ID provided: " + itemID + " for " + staticID);
                    }
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + allowedItemString[0] + " is not enabled but, present in " + ChatColor.YELLOW + staticID);
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Please enable the plugin or remove any related items");
                }

            }
        }

        return null;
    }

    public static String[] parseMaterialAndAmount(String unparsedString) {
        String[] parts = unparsedString.split("@");

        String itemPlugin = parts[0]; // van, mmo, iad, eco

        if (itemPlugin.equalsIgnoreCase("van")) {
            //VANILLA ITEM HANDLING - LEGACY
            String itemMaterial = parts[1];   // "DIAMOND"
            int itemQuantity = Integer.parseInt(parts[2]);   // "32"

            if (Material.matchMaterial(itemMaterial) == null) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Failed to parse material: " + ChatColor.YELLOW + itemMaterial);
                return null;
            }

            if (Material.getMaterial(itemMaterial).getMaxStackSize() < itemQuantity) {
                itemQuantity = Material.getMaterial(itemMaterial).getMaxStackSize();
            }

            return new String[]{itemPlugin, itemMaterial, String.valueOf(itemQuantity)};
        } else {
            String itemID = parts[1];
            String itemType = parts[2];

            return new String[]{itemPlugin, itemID, itemType};
        }


    }

    public static int getRandomEntry(List<String> list) {
        if (list.isEmpty()) {
            return -1;
        }

        Random random = new Random();
        int index = random.nextInt(list.size());
        return index;
    }
}
