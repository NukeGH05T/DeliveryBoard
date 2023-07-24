package me.nukeghost.commands.subcommands;

import me.nukeghost.DeliveryBoard;
import me.nukeghost.commands.SubCommand;
import me.nukeghost.external.plugins.ExtEcoItems;
import me.nukeghost.external.plugins.ExtExecutableItems;
import me.nukeghost.external.plugins.ExtItemsAdder;
import me.nukeghost.external.plugins.ExtMMOItems;
import me.nukeghost.language.Message;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import static me.nukeghost.DeliveryBoard.plugin;

public class AddRequiredItemCommand extends SubCommand {
    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return Message.ADD_COMMAND_DESCRIPTION;
    }

    @Override
    public String getSyntax() {
        return "/db add <delivery> <plugin>";
    }

    @Override
    public String getPermissionNode() {
        return "deliveryboard.add";
    }

    @Override
    public void perform(Player p, String[] args) {
        ItemStack handItem = p.getInventory().getItemInMainHand();
        if (handItem == null || handItem.getType().equals(Material.AIR)) {
            p.sendMessage(Message.ADD_INVALID_ITEM);
        }

        //A plugin(prefix) is provided
        if (args.length == 3) {
            String finalString = null;
            if (DeliveryBoard.enabledItemPlugins.contains("ItemsAdder") && args[2].equalsIgnoreCase("ItemsAdder")) {
                finalString = new ExtItemsAdder().generateItemString(handItem);
            } else if (DeliveryBoard.enabledItemPlugins.contains("MMOItems") && args[2].equalsIgnoreCase("MMOItems")) {
                finalString = new ExtMMOItems().generateItemString(handItem);
            } else if (DeliveryBoard.enabledItemPlugins.contains("EcoItems") && args[2].equalsIgnoreCase("EcoItems")) {
                finalString = new ExtEcoItems().generateItemString(handItem);
            } else if (DeliveryBoard.enabledItemPlugins.contains("ExecutableItems") && args[2].equalsIgnoreCase("ExecutableItems")) {
                finalString = new ExtExecutableItems().generateItemString(handItem);
            } else if (args[2].equalsIgnoreCase("Vanilla")){
                //Vanilla Item
                String vanString = "van@" + handItem.getType() + "@" +
                        p.getInventory().getItemInMainHand().getAmount();
                finalString = vanString;
            }else if (args[2].equalsIgnoreCase("Serialize")){
                //Serialize the item and store it as string
                try {
                    ByteArrayOutputStream io = new ByteArrayOutputStream();
                    BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);

                    os.writeObject(handItem);
                    os.flush();

                    byte[] serializedObject = io.toByteArray();
                    String serString = Base64.getEncoder().encodeToString(serializedObject);

                    finalString = "ser@" + serString + "@1";

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                //finalString = vanString;
            }

            if (finalString == null) {
                finalString = Message.ADD_INVALID_ITEM;
                return;
            }

            List<String> reqList = plugin.getConfig().getStringList("delivery." + args[1] + ".allowed-materials");
            reqList.add(finalString);
            plugin.getConfig().set("delivery." + args[1] + ".allowed-materials", reqList);
            plugin.saveConfig();

            p.sendMessage(ChatColor.DARK_AQUA + "[DB] Item saved successfully.");

        }
    }
}
