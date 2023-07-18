package me.nukeghost.handlers;

import me.nukeghost.DeliveryBoard;
import me.nukeghost.external.ItemPlugin;
import me.nukeghost.utils.ItemPluginUtils;
import org.bukkit.inventory.ItemStack;

public class VerificationHandler {
    private final ItemPlugin itemPlugin;
    private final ItemStack generatedItemStack;

    public VerificationHandler(ItemPlugin itemPlugin, ItemStack generatedItemStack) {
        this.itemPlugin = itemPlugin;
        this.generatedItemStack = generatedItemStack;
    }

    /**
     * Fetches the ItemPlugin from provided ItemStack
     * ItemPlugin is null for Vanilla items
     */
    public VerificationHandler(ItemStack generatedItemStack) {
        this.itemPlugin = ItemPluginUtils.getItemPlugin(generatedItemStack);
        this.generatedItemStack = generatedItemStack;
    }

    public boolean checkItem(ItemStack submittedItem) {
        if (!DeliveryBoard.enabledItemPlugins.isEmpty()) {
            for (String plugin : DeliveryBoard.enabledItemPlugins) {
                if (itemPlugin != null && itemPlugin.getName().equalsIgnoreCase(plugin)) {
                    return itemPlugin.isMatching(generatedItemStack, submittedItem);
                } else {
                    //Vanilla item
                    return generatedItemStack.equals(submittedItem);
                }
            }
        } else {
            return generatedItemStack.equals(submittedItem);
        }
        return false;
    }
}
