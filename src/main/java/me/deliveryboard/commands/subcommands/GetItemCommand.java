package me.deliveryboard.commands.subcommands;

import dev.lone.itemsadder.api.CustomStack;
import me.deliveryboard.commands.SubCommand;
import me.deliveryboard.external.plugins.ExtEcoItems;
import me.deliveryboard.external.plugins.ExtItemsAdder;
import me.deliveryboard.external.plugins.ExtMMOItems;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static me.deliveryboard.DeliveryBoard.plugin;

public class GetItemCommand extends SubCommand {
    @Override
    public String getName() {
        return "get";
    }

    @Override
    public String getDescription() {
        return "Gets item";
    }

    @Override
    public String getSyntax() {
        return "/db get";
    }

    @Override
    public String getPermissionNode() {
        return null;
    }

    @Override
    public void perform(Player p, String[] args) {
        if (args[1].equalsIgnoreCase("raw")) {
            p.getInventory().addItem(plugin.getConfig().getItemStack("item-save"));
            p.sendMessage("Got item from config!");
        } else if (args[1].equalsIgnoreCase("verifym")) {

            MMOItem mmoitem = MMOItems.plugin.getMMOItem(MMOItems.plugin.getTypes().get("SWORD"), "CUTLASS");
            ItemStack item = mmoitem.newBuilder().build();
            ExtMMOItems extMMOItems = new ExtMMOItems();
            boolean matches = extMMOItems.isMatching(item, p.getInventory().getItemInMainHand());
            if (matches) { p.sendMessage("MMOItem Matched"); }
            else { p.sendMessage("MMOItem Not Matched"); }

        } else if (args[1].equalsIgnoreCase("verifye")) {
            ExtEcoItems extEcoItems = new ExtEcoItems();
            ItemStack genItem = extEcoItems.generateItem("grappling_hook", "");
            System.out.println("Gen Item Type: " + genItem.getType());

            if (extEcoItems.isMatching(genItem, p.getInventory().getItemInMainHand())) {
                p.sendMessage("Matched EcoItem");
            } else {
                p.sendMessage("EcoItems not matched");
            }


        } else if (args[1].equalsIgnoreCase("verifyi")) {
//            ExtItemsAdder extItemsAdder = new ExtItemsAdder();
//            ItemStack genItem = extItemsAdder.generateItem("iageneric:coin", "");
            ExtItemsAdder itemsAdder = new ExtItemsAdder();
            ItemStack genItem = itemsAdder.generateItem(args[2], "");
            if (CustomStack.isInRegistry("iageneric:coin")) {
                p.sendMessage("Exists");
            }

            if (itemsAdder.isMatching(genItem, p.getInventory().getItemInMainHand())) {
                p.sendMessage("Matched ItemsAdder Item");
            } else {
                p.sendMessage("Mismatched ItemsAdder Item");
            }
        }
    }
}
