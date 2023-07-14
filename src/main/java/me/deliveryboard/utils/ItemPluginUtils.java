package me.deliveryboard.utils;

import me.deliveryboard.external.ItemPlugin;
import me.deliveryboard.external.plugins.ExtEcoItems;
import me.deliveryboard.external.plugins.ExtExecutableItems;
import me.deliveryboard.external.plugins.ExtItemsAdder;
import me.deliveryboard.external.plugins.ExtMMOItems;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class ItemPluginUtils {
    public static ItemPlugin getItemPlugin(ItemStack itemStack) {
        String iaString = new ExtItemsAdder().generateItemString(itemStack);
        String mmoString = new ExtMMOItems().generateItemString(itemStack);
        String ecoString = new ExtEcoItems().generateItemString(itemStack);
        String eiString = new ExtExecutableItems().generateItemString(itemStack);

        String finalString;
        if (iaString != null) {
            return new ExtItemsAdder();
        } else if (mmoString != null) {
            return new ExtMMOItems();
        } else if (ecoString != null) {
            return new ExtEcoItems();
        } else if (eiString != null) {
            return new ExtExecutableItems();
        }
        return null;
    }
}
