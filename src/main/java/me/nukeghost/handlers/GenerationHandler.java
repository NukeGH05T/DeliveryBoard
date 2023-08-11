package me.nukeghost.handlers;

import com.ssomar.score.api.executableitems.config.ExecutableItemsManagerInterface;
import dev.lone.itemsadder.api.CustomStack;
import me.nukeghost.DeliveryBoard;
import me.nukeghost.external.plugins.ExtEcoItems;
import me.nukeghost.external.plugins.ExtExecutableItems;
import me.nukeghost.external.plugins.ExtItemsAdder;
import me.nukeghost.external.plugins.ExtMMOItems;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import static com.ssomar.score.api.executableitems.ExecutableItemsAPI.getExecutableItemsManager;
import static me.nukeghost.DeliveryBoard.enabledItemPlugins;
import static me.nukeghost.DeliveryBoard.plugin;

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
        } else if (allowedItemString[0].equalsIgnoreCase("ser")){

            String encodedString = allowedItemString[1];
            byte[] serializedObject = Base64.getDecoder().decode(encodedString);

            try {
                ByteArrayInputStream in = new ByteArrayInputStream(serializedObject);
                BukkitObjectInputStream is = new BukkitObjectInputStream(in);
                ItemStack deliveryItem = (ItemStack) is.readObject();

                return deliveryItem;

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }

        } else {
            String itemID = allowedItemString[1];
            String itemType = allowedItemString.length > 2 ? allowedItemString[2] : "";

            if (allowedItemString[0].equalsIgnoreCase("mmo")) {
                //MMOItems - 'mmo@CUTLASS@SWORD'
                if (enabledItemPlugins.contains("MMOItems")) {
                    ExtMMOItems mmoItems = new ExtMMOItems();
                    DeliveryBoard.usedItemPluginsHashMap.put(deliveryID, new ExtMMOItems());
                    System.out.println(Arrays.toString(allowedItemString));
                    return mmoItems.generateItem(itemID, itemType, Integer.valueOf(allowedItemString[3])); //index out of bounds 3
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
                        return itemsAdder.generateItem(itemID, itemType, Integer.valueOf(allowedItemString[2]));
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
                    return ecoItems.generateItem(itemID, itemType, Integer.valueOf(allowedItemString[2]));
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
                        return executableItems.generateItem(itemID, itemType, Integer.valueOf(allowedItemString[2]));
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

    public static ItemStack generateIconItem(String deliveryID) { //iconID is from config.yml
        String[] allowedItemString = parseMaterialAndAmount(plugin.getConfig().getString("delivery." + deliveryID + ".icon-item"));

        if (allowedItemString[0].equalsIgnoreCase("van")){

            ItemStack deliveryItem = new ItemStack(Material.getMaterial(allowedItemString[1]), Integer.parseInt(allowedItemString[2]));
            return deliveryItem;
        } else if (allowedItemString[0].equalsIgnoreCase("ser")){

            String encodedString = allowedItemString[1];
            byte[] serializedObject = Base64.getDecoder().decode(encodedString);

            try {
                ByteArrayInputStream in = new ByteArrayInputStream(serializedObject);
                BukkitObjectInputStream is = new BukkitObjectInputStream(in);
                ItemStack deliveryItem = (ItemStack) is.readObject();

                return deliveryItem;

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }

        } else {
            String itemID = allowedItemString[1];
            String itemType = allowedItemString.length > 2 ? allowedItemString[2] : "";

            if (allowedItemString[0].equalsIgnoreCase("mmo")) {
                //MMOItems - 'mmo@CUTLASS@SWORD'
                if (enabledItemPlugins.contains("MMOItems")) {
                    return new ExtMMOItems().generateItem(itemID, itemType, Integer.valueOf(allowedItemString[3]));
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + allowedItemString[0] + " is not enabled but, present in " + ChatColor.YELLOW + deliveryID);
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Please enable the plugin or remove any related items");
                }

            } else if (allowedItemString[0].equalsIgnoreCase("iad")) {
                //ItemsAdder - 'iad@iageneric:coin@1'
                if (enabledItemPlugins.contains("ItemsAdder")) {
                    if (CustomStack.isInRegistry(itemID)) {
                        return new ExtItemsAdder().generateItem(itemID, itemType, Integer.valueOf(allowedItemString[2]));
                    } else {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Invalid item ID provided: " + itemID + " for " + deliveryID);
                    }
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + allowedItemString[0] + " is not enabled but, present in " + ChatColor.YELLOW + deliveryID);
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Please enable the plugin or remove any related items");
                }

            } else if (allowedItemString[0].equalsIgnoreCase("eco")) {
                //EcoItems - 'eco@grappling_hook'
                //Might malfunction at any time
                if (enabledItemPlugins.contains("EcoItems")) {
                    return new ExtEcoItems().generateItem(itemID, itemType, Integer.valueOf(allowedItemString[2]));
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + allowedItemString[0] + " is not enabled but, present in " + ChatColor.YELLOW + deliveryID);
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Please enable the plugin or remove any related items");
                }
            } else if (allowedItemString[0].equalsIgnoreCase("exi")) {
                //ExecutableItem - 'exi@iageneric:coin@1'
                if (enabledItemPlugins.contains("ExecutableItems")) {
                    ExecutableItemsManagerInterface executableItemsManager = getExecutableItemsManager();
                    if (executableItemsManager.isValidID(itemID)) {
                        return new ExtExecutableItems().generateItem(itemID, itemType, Integer.valueOf(allowedItemString[2]));
                    } else {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Invalid item ID provided: " + itemID + " for " + deliveryID);
                    }
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + allowedItemString[0] + " is not enabled but, present in " + ChatColor.YELLOW + deliveryID);
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Please enable the plugin or remove any related items");
                }

            }
        }

        return null;
    }

    public static ItemStack generateItemFromString(String itemString) { //iconID is from config.yml
        String[] allowedItemString = parseMaterialAndAmount(itemString);

        if (allowedItemString[0].equalsIgnoreCase("van")){

            ItemStack deliveryItem = new ItemStack(Material.getMaterial(allowedItemString[1]), Integer.parseInt(allowedItemString[2]));
            return deliveryItem;
        } else if (allowedItemString[0].equalsIgnoreCase("ser")){

            String encodedString = allowedItemString[1];
            byte[] serializedObject = Base64.getDecoder().decode(encodedString);

            try {
                ByteArrayInputStream in = new ByteArrayInputStream(serializedObject);
                BukkitObjectInputStream is = new BukkitObjectInputStream(in);
                ItemStack deliveryItem = (ItemStack) is.readObject();

                return deliveryItem;

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }

        } else {
            String itemID = allowedItemString[1];
            String itemType = allowedItemString.length > 2 ? allowedItemString[2] : "";

            if (allowedItemString[0].equalsIgnoreCase("mmo")) {
                //MMOItems - 'mmo@CUTLASS@SWORD@1'
                if (enabledItemPlugins.contains("MMOItems")) {
                    return new ExtMMOItems().generateItem(itemID, itemType, Integer.valueOf(allowedItemString[3]));
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + allowedItemString[0] + " is not enabled but, present!");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Please enable the plugin or remove any related items");
                }

            } else if (allowedItemString[0].equalsIgnoreCase("iad")) {
                //ItemsAdder - 'iad@iageneric:coin@1'
                if (enabledItemPlugins.contains("ItemsAdder")) {
                    if (CustomStack.isInRegistry(itemID)) {
                        return new ExtItemsAdder().generateItem(itemID, itemType, Integer.valueOf(allowedItemString[2]));
                    } else {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Invalid item ID provided: " + itemID);
                    }
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + allowedItemString[0] + " is not enabled but, present!");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Please enable the plugin or remove any related items");
                }

            } else if (allowedItemString[0].equalsIgnoreCase("eco")) {
                //EcoItems - 'eco@grappling_hook'
                //Might malfunction at any time
                if (enabledItemPlugins.contains("EcoItems")) {
                    return new ExtEcoItems().generateItem(itemID, itemType, Integer.valueOf(allowedItemString[2]));
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + allowedItemString[0] + " is not enabled but, present!");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Please enable the plugin or remove any related items");
                }
            } else if (allowedItemString[0].equalsIgnoreCase("exi")) {
                //ExecutableItem - 'exi@iageneric:coin@1'
                if (enabledItemPlugins.contains("ExecutableItems")) {
                    ExecutableItemsManagerInterface executableItemsManager = getExecutableItemsManager();
                    if (executableItemsManager.isValidID(itemID)) {
                        return new ExtExecutableItems().generateItem(itemID, itemType, Integer.valueOf(allowedItemString[2]));
                    } else {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Invalid item ID provided: " + itemID);
                    }
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + allowedItemString[0] + " is not enabled but, present!");
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
                    return new ExtMMOItems().generateItem(itemID, itemType, Integer.valueOf(allowedItemString[3]));
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + allowedItemString[0] + " is not enabled but, present in " + ChatColor.YELLOW + staticID);
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Please enable the plugin or remove any related items");
                }

            } else if (allowedItemString[0].equalsIgnoreCase("iad")) {
                //ItemsAdder - 'iad@iageneric:coin@1'
                if (enabledItemPlugins.contains("ItemsAdder")) {
                    if (CustomStack.isInRegistry(itemID)) {
                        return new ExtItemsAdder().generateItem(itemID, itemType, Integer.valueOf(allowedItemString[2]));
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
                    return new ExtEcoItems().generateItem(itemID, itemType, Integer.valueOf(allowedItemString[2]));
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + allowedItemString[0] + " is not enabled but, present in " + ChatColor.YELLOW + staticID);
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "Please enable the plugin or remove any related items");
                }
            } else if (allowedItemString[0].equalsIgnoreCase("exi")) {
                //ExecutableItem - 'exi@iageneric:coin@1'
                if (enabledItemPlugins.contains("ExecutableItems")) {
                    ExecutableItemsManagerInterface executableItemsManager = getExecutableItemsManager();
                    if (executableItemsManager.isValidID(itemID)) {
                        return new ExtExecutableItems().generateItem(itemID, itemType, Integer.valueOf(allowedItemString[2]));
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

            if (parts.length == 3) {
                return new String[]{itemPlugin, itemID, itemType};
            } else {
                return new String[]{itemPlugin, itemID, itemType, parts[3]};
            }
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
