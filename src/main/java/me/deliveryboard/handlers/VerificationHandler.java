package me.deliveryboard.handlers;

import me.deliveryboard.DeliveryBoard;
import me.deliveryboard.external.ItemPlugin;
import org.bukkit.inventory.ItemStack;

public class VerificationHandler {
    static ItemPlugin itemPlugin;
    static ItemStack generatedItemStack;

    public VerificationHandler(ItemPlugin itemPlugin, ItemStack generatedItemStack) {
        this.itemPlugin = itemPlugin;
        this.generatedItemStack = generatedItemStack;
    }

    public boolean checkItem(ItemStack submittedItem) {
        for (String plugin : DeliveryBoard.enabledItemPlugins) {
            if (itemPlugin != null && itemPlugin.getName().equalsIgnoreCase(plugin)) {
                return itemPlugin.isMatching(generatedItemStack, submittedItem);
            } else {
                //Vanilla item
                return generatedItemStack.equals(submittedItem);
            }
        }
        return false;
    }
}
