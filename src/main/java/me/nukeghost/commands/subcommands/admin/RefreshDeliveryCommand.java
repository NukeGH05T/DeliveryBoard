package me.nukeghost.commands.subcommands.admin;

import me.nukeghost.commands.SubCommand;
import me.nukeghost.language.Message;
import me.nukeghost.template.Delivery;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static me.nukeghost.DeliveryBoard.deliveries;
import static me.nukeghost.DeliveryBoard.deliveryCompletedPlayerList;

public class RefreshDeliveryCommand extends SubCommand {
    @Override
    public String getName() {
        return "refresh";
    }

    @Override
    public String getDescription() {
        return Message.REFRESH_COMMAND_DESCRIPTION;
    }

    @Override
    public String getSyntax() {
        return "/db refresh <delivery>";
    }

    @Override
    public String getPermissionNode() {
        return "deliveryboard.refresh";
    }

    @Override
    public void perform(Player p, String[] args) {
        for (Delivery delivery :  deliveries) {
            if (delivery.getDeliveryID().equalsIgnoreCase(args[1])) {
                delivery.updateDeliveryItem();
                deliveryCompletedPlayerList.get(deliveries.indexOf(delivery)).clear();
                p.sendMessage(ChatColor.DARK_AQUA + "[DB] Refreshed delivery.");
            }
        }
    }
}
