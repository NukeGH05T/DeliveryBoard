package me.nukeghost.external.plugins;

import io.th0rgal.oraxen.api.OraxenItems;
import io.th0rgal.oraxen.items.ItemBuilder;
import me.nukeghost.external.ItemPlugin;
import org.bukkit.inventory.ItemStack;

public class ExtOraxen extends ItemPlugin {
    @Override
    public String getName() {
        return "Oraxen";
    }

    @Override
    public ItemStack generateItem(String itemID, String itemType, Integer amount) {
        if (OraxenItems.exists(itemID)) {
            ItemBuilder itemBuilder = OraxenItems.getItemById(itemID);
            return itemBuilder.build();
        }
        return null;
    }

    @Override
    public String generateItemString(ItemStack itemStack) {
        if (itemStack != null) {
            String oraxenID = OraxenItems.getIdByItem(itemStack);
            return "orx@" + oraxenID + "@" + itemStack.getAmount();
        }
        return null;
    }

    @Override
    public boolean isMatching(ItemStack generatedItem, ItemStack submittedItem) {
        if (OraxenItems.exists(OraxenItems.getIdByItem(submittedItem))) {
            String subID = OraxenItems.getIdByItem(submittedItem);
            String genID = OraxenItems.getIdByItem(generatedItem);

            if (subID.equalsIgnoreCase(genID)) return true;
        }

        return false;
    }
}
