package me.nukeghost.commands.subcommands.tokens;

import me.nukeghost.commands.SubCommand;
import me.nukeghost.database.TokenDatabase;
import me.nukeghost.language.LanguageConfig;
import me.nukeghost.language.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TokenBalanceCommand extends SubCommand {
    FileConfiguration lang = LanguageConfig.getLangConfig();
    @Override
    public String getName() {
        return "balance";
    }

    @Override
    public String getDescription() {
        return Message.TOKEN_BALANCE_DESCRIPTION;
    }

    @Override
    public String getSyntax() {
        return "/db balance [player]";
    }

    @Override
    public String getPermissionNode() {
        return "deliveryboard.token.viewself";
    }

    @Override
    public void perform(Player p, String[] args) {
        if (args.length == 2) {
            if (!p.hasPermission("deliveryboard.token.viewothers")) {
                p.sendMessage(Message.TOKEN_BALANCE_NO_PERM);
                return;
            }
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

            if (target == null) {
                p.sendMessage(ChatColor.RED + "[DB] Player " + ChatColor.YELLOW + args[1] + ChatColor.RED + " not found!");
                return;
            }

            UUID uuid = target.getUniqueId();
            int otherBalance = Integer.parseInt(TokenDatabase.getCurrencyAmount(uuid.toString()));

            p.sendMessage(Message.TOKEN_BALANCE + otherBalance);
        } else {
            UUID uuid = p.getUniqueId();
            int otherBalance = Integer.parseInt(TokenDatabase.getCurrencyAmount(uuid.toString()));

            p.sendMessage(Message.TOKEN_BALANCE + otherBalance);
        }


    }
}
