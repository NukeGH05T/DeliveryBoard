package me.nukeghost.commands.subcommands;

import me.nukeghost.commands.SubCommand;
import org.bukkit.entity.Player;

import static me.nukeghost.DeliveryBoard.plugin;

public class SaveItemCommand extends SubCommand {
    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "Saves item";
    }

    @Override
    public String getSyntax() {
        return "/db save";
    }

    @Override
    public String getPermissionNode() {
        return null;
    }

    @Override
    public void perform(Player p, String[] args) {
        plugin.getConfig().set("item-save", p.getInventory().getItemInMainHand());
        plugin.saveConfig();

        p.sendMessage("Item saved!");
    }
}
