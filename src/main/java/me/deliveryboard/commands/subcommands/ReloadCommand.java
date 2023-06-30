package me.deliveryboard.commands.subcommands;

import me.deliveryboard.DeliveryBoard;
import me.deliveryboard.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ReloadCommand extends SubCommand {
    @Override
    public String getName(){
        return "reload";
    }

    @Override
    public String getDescription(){
        return "Reload the configuration of the plugin";
    }

    @Override
    public String getSyntax(){
        return "/db reload";
    }

    @Override
    public String getPermissionNode() {
        return "deliveryboard.reload";
    }

    @Override
    public void perform(Player p, String args[]){

        if (p.hasPermission(getPermissionNode())){
            DeliveryBoard.plugin.reloadConfig();
            DeliveryBoard.plugin.saveConfig();

            p.sendMessage(ChatColor.DARK_AQUA+"Config Reloaded!");
        }else{
            p.sendMessage(ChatColor.RED + "You do not have permission to do that!");
        }

    }

}
