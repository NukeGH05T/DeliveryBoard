package me.nukeghost.external.plugins;

import com.ssomar.score.api.executableitems.ExecutableItemsAPI;
import com.ssomar.score.api.executableitems.config.ExecutableItemsManagerInterface;
import me.nukeghost.external.ItemPlugin;
import org.bukkit.inventory.ItemStack;

import static com.ssomar.score.api.ExecutableItemsAPI.getExecutableItem;

public class ExtExecutableItems extends ItemPlugin {
    @Override
    public String getName() {
        return "ExecutableItems";
    }

    @Override
    public ItemStack generateItem(String itemID, String itemType, Integer amount) {
        ItemStack item = getExecutableItem(itemID);
        int finalAmount = Math.max(1, amount);
        finalAmount = Math.min(amount, item.getType().getMaxStackSize());
        item.setAmount(amount);
        return item;
    }

    @Override
    public String generateItemString(ItemStack itemStack) {
        ExecutableItemsManagerInterface manager = ExecutableItemsAPI.getExecutableItemsManager();

        for (String exItemID : manager.getExecutableItemIdsList()) {
            ItemStack itemToCompare = getExecutableItem(exItemID);
            if (itemToCompare.equals(itemStack)) {

                String itemString = "exi@" + exItemID + "@" + itemStack.getAmount();
                return itemString;
            }
        }
        return null;
    }

    @Override
    public boolean isMatching(ItemStack generatedItem, ItemStack submittedItem) {
        if (generatedItem.equals(submittedItem)) {
            return true;
        }
        return false;
    }
}
