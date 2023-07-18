package me.nukeghost.commands;

import me.nukeghost.DeliveryBoard;
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
            completions.add("add");
            completions.add("board");
            completions.add("edit");
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

        if ((args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("edit")) &&
                args.length == 2) {
            List<String> completions = DeliveryBoard.plugin.getConfig().getStringList("active-deliveries");

            for (String s : completions) {
                if (s.toLowerCase().startsWith(args[1].toLowerCase())) {
                    commands.add(s);
                }
            }

            return commands;
        }

        // AddRequiredItem command's <plugin> section
        if (args[0].equalsIgnoreCase("add") &&
                 args.length == 3) {
            List<String> completions = new ArrayList<>();
            completions.add("ItemsAdder");
            completions.add("MMOItems");
            completions.add("EcoItems");
            completions.add("ExecutableItems");
            completions.add("Vanilla");
            completions.add("Serialize");

            for (String s : completions) {
                if (s.toLowerCase().startsWith(args[2].toLowerCase())) {
                    commands.add(s);
                }
            }

            return commands;
        }
        return null;
    }
}
