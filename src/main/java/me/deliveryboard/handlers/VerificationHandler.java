package me.deliveryboard.handlers;

import me.deliveryboard.DeliveryBoard;
import me.deliveryboard.external.ItemPlugin;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class VerificationHandler {
    private final ItemPlugin itemPlugin;
    private final ItemStack generatedItemStack;

    public VerificationHandler(ItemPlugin itemPlugin, ItemStack generatedItemStack) {
        this.itemPlugin = itemPlugin;
        this.generatedItemStack = generatedItemStack;
    }

    public boolean checkItem(ItemStack submittedItem) {
        if (!DeliveryBoard.usedItemPluginsHashMap.isEmpty()) {
            for (String plugin : DeliveryBoard.enabledItemPlugins) {
                if (itemPlugin.getName().equalsIgnoreCase(plugin)) {
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
