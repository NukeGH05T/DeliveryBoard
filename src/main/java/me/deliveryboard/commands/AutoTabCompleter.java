package me.deliveryboard.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class AutoTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> commands = new ArrayList<>();
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            //completions.add("show");
            completions.add("board");
            completions.add("reload");
            completions.add("setboard");
            completions.add("help");

            for (String s : completions) {
                if (s.toLowerCase().startsWith(args[0].toLowerCase())) {
                    commands.add(s);
                }
            }

            return commands;
        }
        return null;
    }
}
