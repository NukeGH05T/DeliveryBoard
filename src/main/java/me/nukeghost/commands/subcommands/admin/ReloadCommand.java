package me.nukeghost.commands.subcommands.admin;

import me.nukeghost.DeliveryBoard;
import me.nukeghost.commands.SubCommand;
import me.nukeghost.handlers.AccumulatedRewardHandler;
import me.nukeghost.language.LanguageConfig;
import me.nukeghost.language.Message;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static me.nukeghost.DeliveryBoard.startTasks;

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

                LanguageConfig.setupLang();
                Message.loadMessages();

                //Loading accumulated reward data
                new AccumulatedRewardHandler().reloadAccumulatedRewardData();

                //Loading active deliveries from config
                startTasks();
            } catch (Exception e) {
                p.sendMessage(Message.RELOAD_FAILED);
                e.printStackTrace();
            }

            p.sendMessage(ChatColor.DARK_AQUA + "[DB]" + Message.RELOAD_SUCCESSFULL);//
        }else{
            p.sendMessage(Message.RELOAD_NO_PERM);//
        }

    }

}
