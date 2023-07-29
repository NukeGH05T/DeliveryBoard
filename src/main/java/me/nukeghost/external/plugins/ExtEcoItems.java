package me.nukeghost.external.plugins;

import com.willfp.ecoitems.items.EcoItem;
import com.willfp.ecoitems.items.EcoItems;
import me.nukeghost.external.ItemPlugin;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

public class ExtEcoItems extends ItemPlugin {
    @Override
    public String getName() {
        return "EcoItems";
    }

    /**
     * @param itemID Item ID aka the filename of that specific ecoitem
     * @param itemType NOT USED
     * @return ItemStack/null
     */
    @Override
    public ItemStack generateItem(String itemID, String itemType, Integer amount) {
        for (EcoItem ecoItem : EcoItems.INSTANCE.values()) {
            if (ecoItem.getId().getKey().equalsIgnoreCase(itemID)) {
                ItemStack item = ecoItem.getItemStack();
                int finalAmount = Math.max(1, amount);
                finalAmount = Math.min(amount, item.getType().getMaxStackSize());
                item.setAmount(amount);
                return item;
            }
        }

        Bukkit.getLogger().severe("Invalid item ID: " + itemID);
        return null;
    }

    @Override
    public String generateItemString(ItemStack itemStack) {
        for (EcoItem ecoItem : EcoItems.INSTANCE.values()) {
            if (ecoItem.getItemStack().equals(itemStack)) {
                String itemID = String.valueOf(ecoItem.getId());
                String id = ecoItem.getID();

                String itemString = "eco@" + id + "@" + itemStack.getAmount();
                return itemString;
            }
        }

        return null;
    }

    /**
     * @param generatedItem Used first to hardmatch with all ecoitems
     * @param submittedItem Hard matched to the generated item
     * @return [UNSAFE] Uses hardmatch
     */
    @Override
    public boolean isMatching(ItemStack generatedItem, ItemStack submittedItem) {
        if (generatedItem.equals(submittedItem)) {
            return true;
        }
        return false;
    }
}
