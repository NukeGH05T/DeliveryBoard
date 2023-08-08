package me.nukeghost.commands.subcommands.user;

import me.nukeghost.commands.CommandManager;
import me.nukeghost.commands.SubCommand;
import me.nukeghost.language.Message;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpCommand extends SubCommand {
    public String getName(){
        return "help";
    }

    @Override
    public String getDescription(){
        return Message.HELP_COMMAND_DESCRIPTION;
        //return "Show all of the commands for DeliveryBoard";
    }

    @Override
    public String getSyntax(){
        return "/db help";
    }

    @Override
    public String getPermissionNode() {
        return null;
    }

    @Override
    public void perform(Player p, String args[]){

        CommandManager commandManager = new CommandManager();
        p.sendMessage(ChatColor.YELLOW + "=================="+ChatColor.AQUA + ChatColor.BOLD +"Delivery" +
                ChatColor.DARK_AQUA + "Board" + ChatColor.YELLOW + "=================");
        for (int i = 0; i < commandManager.getSubCommands().size(); i++){
            if (commandManager.getSubCommands().get(i).getPermissionNode() == null || p.hasPermission(commandManager.getSubCommands().get(i).getPermissionNode())) {
                p.sendMessage(ChatColor.GREEN + commandManager.getSubCommands().get(i).getSyntax() + ChatColor.GRAY + " - " + ChatColor.WHITE + ChatColor.ITALIC + commandManager.getSubCommands().get(i).getDescription());
            }
        }
        p.sendMessage(ChatColor.YELLOW + "================================================");

    }

}
