package me.nukeghost.external.plugins;

import dev.lone.itemsadder.api.CustomStack;
import me.nukeghost.external.ItemPlugin;
import org.bukkit.inventory.ItemStack;

public class ExtItemsAdder extends ItemPlugin {
    @Override
    public String getName() {
        return "ItemsAdder";
    }

    @Override
    public ItemStack generateItem(String itemID, String itemType, Integer amount) {
        CustomStack stack = CustomStack.getInstance(itemID);
        if(stack != null)
        {
            ItemStack item = stack.getItemStack();
            int finalAmount = Math.max(1, amount);
            finalAmount = Math.min(amount, item.getType().getMaxStackSize());
            item.setAmount(amount);
            return item;
        }
        return null;
    }

    @Override
    public String generateItemString(ItemStack itemStack) {
        CustomStack stack = CustomStack.byItemStack(itemStack);

        if(stack != null) {
            //IA Custom Item
            String itemString = "iad@" + stack.getNamespacedID() + "@" + itemStack.getAmount();
            return itemString;
        }
        return null;
    }

    @Override
    public boolean isMatching(ItemStack generatedItem, ItemStack submittedItem) {

        CustomStack subStack = CustomStack.byItemStack(submittedItem);
        CustomStack genStack = CustomStack.byItemStack(generatedItem);

        try {
            if (CustomStack.isInRegistry(subStack.getNamespacedID())) {
                if (genStack.matchNamespacedID(subStack)) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
