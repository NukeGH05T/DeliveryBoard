package me.nukeghost.commands.subcommands;

import me.nukeghost.commands.SubCommand;
import me.nukeghost.language.Message;
import me.nukeghost.menusystem.PlayerMenuUtility;
import me.nukeghost.menusystem.menu.ShowDeliveryBoardMenu;
import org.bukkit.entity.Player;

public class BoardCommand extends SubCommand {
    @Override
    public String getName() {
        return "board";
    }

    @Override
    public String getDescription() {
        return Message.BOARD_COMMAND_DESCRIPTION;
        //return "View the delivery board and submit required items.";
    }

    @Override
    public String getSyntax() {
        return "/db board";
    }

    @Override
    public String getPermissionNode() {
        return "deliveryboard.board";
    }

    @Override
    public void perform(Player p, String[] args) {
        ShowDeliveryBoardMenu menu = new ShowDeliveryBoardMenu(new PlayerMenuUtility(p));
        menu.open();
    }
}
