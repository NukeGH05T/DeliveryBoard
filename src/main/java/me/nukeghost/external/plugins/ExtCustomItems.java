package me.nukeghost.external.plugins;

import me.nukeghost.external.ItemPlugin;
import org.bukkit.inventory.ItemStack;

import static com.jojodmo.customitems.api.CustomItemsAPI.getCustomItem;
import static com.jojodmo.customitems.api.CustomItemsAPI.getCustomItemID;

public class ExtCustomItems extends ItemPlugin {
    @Override
    public String getName() {
        return "CustomItems";
    }

    @Override
    public ItemStack generateItem(String itemID, String itemType, Integer amount) {
        ItemStack is = getCustomItem(itemID);
        if(is != null) {
            return is;
        }

        return getCustomItem(itemID);
    }

    @Override
    public String generateItemString(ItemStack itemStack) {
        if(itemStack == null) {
            return null;
        }

        String customItemID = getCustomItemID(itemStack);
        if(itemStack != null) {
            return "cui:" + customItemID;
        }

        return "minecraft:" + itemStack.getType().name();
    }

    @Override
    public boolean isMatching(ItemStack generatedItem, ItemStack submittedItem) {
        return generateItemString(submittedItem).equalsIgnoreCase(generateItemString(generatedItem));
    }
}
