package me.nukeghost.external;

import org.bukkit.inventory.ItemStack;

public abstract class ItemPlugin {
    public abstract String getName();
    public abstract ItemStack generateItem(String itemID, String itemType);
    public abstract String generateItemString(ItemStack itemStack);
    public abstract boolean isMatching(ItemStack generatedItem, ItemStack submittedItem);

}
