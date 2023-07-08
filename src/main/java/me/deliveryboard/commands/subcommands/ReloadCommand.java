package me.deliveryboard.commands.subcommands;

import me.deliveryboard.DeliveryBoard;
import me.deliveryboard.commands.SubCommand;
import me.deliveryboard.language.Message;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ReloadCommand extends SubCommand {
    @Override
    public String getName(){
        return "reload";
    }

    @Override
    public String getDescription(){
        return Message.RELOAD_COMMAND_DESCRIPTION;
        //return "Reload the configuration of the plugin";
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
            try {
                DeliveryBoard.plugin.reloadConfig();
                DeliveryBoard.plugin.saveConfig();
            } catch (Exception e) {
                p.sendMessage(Message.RELOAD_FAILED);
                e.printStackTrace();
            }

            p.sendMessage(Message.RELOAD_SUCCESSFULL);//
        }else{
            p.sendMessage(Message.RELOAD_NO_PERM);//
        }

    }

}
