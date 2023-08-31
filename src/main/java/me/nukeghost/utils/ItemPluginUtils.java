package me.nukeghost.utils;

import me.nukeghost.DeliveryBoard;
import me.nukeghost.external.ItemPlugin;
import me.nukeghost.external.plugins.ExtEcoItems;
import me.nukeghost.external.plugins.ExtExecutableItems;
import me.nukeghost.external.plugins.ExtItemsAdder;
import me.nukeghost.external.plugins.ExtMMOItems;
import org.bukkit.inventory.ItemStack;

public class ItemPluginUtils {
    public static ItemPlugin getItemPlugin(ItemStack itemStack) {

        String finalString;
        if (DeliveryBoard.enabledItemPlugins.contains("ItemsAdder") &&
                new ExtItemsAdder().generateItemString(itemStack) != null) {
            return new ExtItemsAdder();
        } else if (DeliveryBoard.enabledItemPlugins.contains("MMOItems") &&
                new ExtMMOItems().generateItemString(itemStack) != null) {
            return new ExtMMOItems();
        } else if (DeliveryBoard.enabledItemPlugins.contains("EcoItems") &&
                new ExtEcoItems().generateItemString(itemStack) != null) {
            return new ExtEcoItems();
        } else if (DeliveryBoard.enabledItemPlugins.contains("ExecutableItems") &&
                new ExtExecutableItems().generateItemString(itemStack) != null) {
            return new ExtExecutableItems();
        }
        return null;
    }
}
