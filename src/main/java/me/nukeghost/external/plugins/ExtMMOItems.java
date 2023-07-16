package me.nukeghost.external.plugins;

import io.lumine.mythic.lib.api.item.NBTItem;
import me.nukeghost.external.ItemPlugin;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

public class ExtMMOItems extends ItemPlugin {
    @Override
    public String getName() {
        return "MMOItems";
    }

    /**
     * @param itemID Item ID used when creating the mmoitem
     * @param itemType Type of item (Ex: SWORD)
     * @return
     */
    @Override
    public ItemStack generateItem(String itemID, String itemType) {
        if (itemID.equalsIgnoreCase("") || itemType.equalsIgnoreCase("") ||
        itemID == null || itemType == null) {
            Bukkit.getLogger().severe("ItemID & ItemType both are required for item generation!");
            return null;
        }
        if (MMOItems.plugin.getTypes().has(itemType)) {
            try {
                MMOItem mmoitem = MMOItems.plugin.getMMOItem(MMOItems.plugin.getTypes().get(itemType), itemID);
                ItemStack item = mmoitem.newBuilder().build();
                return item;
            } catch (Exception e) {
                Bukkit.getLogger().severe("Invalid item ID: " + itemID);
            }
        } else {
            Bukkit.getLogger().severe("Invalid item type: " + itemType);
            return null;
        }
        return null;
    }

    @Override
    public String generateItemString(ItemStack itemStack) {
        if (NBTItem.get(itemStack).hasType()) {
            //MMOItems item
            NBTItem nbtItem = NBTItem.get(itemStack);
            String itemType = nbtItem.getType();
            String itemID = nbtItem.getString("MMOITEMS_ITEM_ID");

            String itemString = "mmo@" + itemID + "@" + itemType;
            return itemString;
        }
        return null;
    }

    /**
     * @param generatedItem
     * @param submittedItem
     * @return [SAFE] Uses MMOItems preferred method
     */
    @Override
    public boolean isMatching(ItemStack generatedItem, ItemStack submittedItem) {
        NBTItem nitem = NBTItem.get(generatedItem);

        ItemStack itemToVerify = submittedItem;
        NBTItem nbtVItem = NBTItem.get(itemToVerify);
        if (nbtVItem.hasType()) {
            //MMOItems Item
            boolean matchesID = nbtVItem.getString("MMOITEMS_ITEM_ID").equalsIgnoreCase(nitem.getString("MMOITEMS_ITEM_ID"));

            if (matchesID) {
                if (nbtVItem.getType().equalsIgnoreCase(nitem.getType())) {
                    return true;
                }
            }
        }

        return false;
    }
}
