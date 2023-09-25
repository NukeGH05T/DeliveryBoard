package me.nukeghost.commands.subcommands.user;

import me.clip.placeholderapi.PlaceholderAPI;
import me.nukeghost.commands.SubCommand;
import me.nukeghost.language.Message;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DeliveryTopCommand extends SubCommand {
    @Override
    public String getName() {
        return "deliverytop";
    }

    @Override
    public String getDescription() {
        return Message.DELIVER_TOP_COMMAND_DESCREPTION;
    }

    @Override
    public String getSyntax() {
        return "/db deliverytop";
    }

    @Override
    public String getPermissionNode() {
        return "deliveryboard.delivery.top";
    }

    @Override
    public void perform(Player p, String[] args) {
        p.sendMessage(Message.DELIVER_TOP_COMMAND_TITLE);
        p.sendMessage(PlaceholderAPI.setPlaceholders(p, ChatColor.translateAlternateColorCodes('&', "&9 1. &6%deliveryboard_name_1% &7- &a%deliveryboard_points_1%")));
        p.sendMessage(PlaceholderAPI.setPlaceholders(p, ChatColor.translateAlternateColorCodes('&', "&9 2. &6%deliveryboard_name_2% &7- &a%deliveryboard_points_3%")));
        p.sendMessage(PlaceholderAPI.setPlaceholders(p, ChatColor.translateAlternateColorCodes('&', "&9 3. &6%deliveryboard_name_3% &7- &a%deliveryboard_points_3%")));
        p.sendMessage(PlaceholderAPI.setPlaceholders(p, ChatColor.translateAlternateColorCodes('&', "&9 4. &6%deliveryboard_name_4% &7- &a%deliveryboard_points_4%")));
        p.sendMessage(PlaceholderAPI.setPlaceholders(p, ChatColor.translateAlternateColorCodes('&', "&9 5. &6%deliveryboard_name_5% &7- &a%deliveryboard_points_5%")));
        p.sendMessage(PlaceholderAPI.setPlaceholders(p, ChatColor.translateAlternateColorCodes('&', "&9 6. &6%deliveryboard_name_6% &7- &a%deliveryboard_points_6%")));
        p.sendMessage(PlaceholderAPI.setPlaceholders(p, ChatColor.translateAlternateColorCodes('&', "&9 7. &6%deliveryboard_name_7% &7- &a%deliveryboard_points_7%")));
        p.sendMessage(PlaceholderAPI.setPlaceholders(p, ChatColor.translateAlternateColorCodes('&', "&9 8. &6%deliveryboard_name_8% &7- &a%deliveryboard_points_8%")));
        p.sendMessage(PlaceholderAPI.setPlaceholders(p, ChatColor.translateAlternateColorCodes('&', "&9 9. &6%deliveryboard_name_9% &7- &a%deliveryboard_points_9%")));
        p.sendMessage(PlaceholderAPI.setPlaceholders(p, ChatColor.translateAlternateColorCodes('&', "&9 10. &6%deliveryboard_name_10% &7- &a%deliveryboard_points_10%")));
    }
}
