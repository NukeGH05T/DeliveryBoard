package me.nukeghost.commands.subcommands.tokens;

import me.nukeghost.DeliveryBoard;
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

public class ResetTokenCommand extends SubCommand {
    FileConfiguration lang = LanguageConfig.getLangConfig();
    @Override
    public String getName() {
        return "reset";
    }

    @Override
    public String getDescription() {
        return Message.TOKEN_RESET_DESCRIPTION;
    }

    @Override
    public String getSyntax() {
        return "/db reset <player>";
    }

    @Override
    public String getPermissionNode() {
        return "deliveryboard.token.manage";
    }

    @Override
    public void perform(Player p, String[] args) {
        if (args.length < 2) return;
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

        if (target == null) {
            p.sendMessage(ChatColor.RED + "[DB] Player " + ChatColor.YELLOW + args[1] + ChatColor.RED + " not found!");
            return;
        }

        UUID uuid = target.getUniqueId();

        TokenDatabase.updateCurrency(DeliveryBoard.defaultTokenAmount, uuid.toString());
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[DB] &9Reset tokens for player &e" + args[1]));
    }
}
