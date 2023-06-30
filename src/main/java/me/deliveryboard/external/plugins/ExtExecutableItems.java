package me.deliveryboard.external.plugins;

import me.deliveryboard.external.ItemPlugin;
import org.bukkit.inventory.ItemStack;

import static com.ssomar.score.api.ExecutableItemsAPI.getExecutableItem;

public class ExtExecutableItems extends ItemPlugin {
    @Override
    public String getName() {
        return "ExecutableItems";
    }

    @Override
    public ItemStack generateItem(String itemID, String itemType) {
        ItemStack item = getExecutableItem(itemID);
        return item;
    }

    @Override
    public boolean isMatching(ItemStack generatedItem, ItemStack submittedItem) {
        if (generatedItem.equals(submittedItem)) {
            return true;
        }
        return false;
    }
}
