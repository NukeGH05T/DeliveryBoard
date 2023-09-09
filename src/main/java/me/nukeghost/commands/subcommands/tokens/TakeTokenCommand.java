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

public class TakeTokenCommand extends SubCommand {
    FileConfiguration lang = LanguageConfig.getLangConfig();
    @Override
    public String getName() {
        return "take";
    }

    @Override
    public String getDescription() {
        return Message.TOKEN_TAKE_DESCRIPTION;
    }

    @Override
    public String getSyntax() {
        return "/db take <player> <amount>";
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

        int giveAmount = Integer.parseInt(args[2]);
        int prevAmount = Integer.parseInt(TokenDatabase.getCurrencyAmount(uuid.toString()));

        TokenDatabase.updateCurrency(prevAmount - giveAmount, uuid.toString());
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[DB] &9Took&a " + giveAmount + " &9tokens from &e" + args[1] + "&9. The player now has &a" + Integer.parseInt(TokenDatabase.getCurrencyAmount(uuid.toString())) + " &9tokens"));
    }
}
