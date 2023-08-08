package me.nukeghost.commands.subcommands.admin;

import me.nukeghost.commands.SubCommand;
import me.nukeghost.language.Message;
import me.nukeghost.menusystem.PlayerMenuUtility;
import me.nukeghost.menusystem.menu.boardconfig.EditBoardMainMenu;
import me.nukeghost.menusystem.menu.boardconfig.EditRequiredItemsMenu;
import org.bukkit.entity.Player;

public class EditRequiredItemCommand extends SubCommand {
    @Override
    public String getName() {
        return "edit";
    }

    @Override
    public String getDescription() {
        return Message.EDIT_COMMAND_DESCRIPTION;
    }

    @Override
    public String getSyntax() {
        return "/db edit <delivery>";
    }

    @Override
    public String getPermissionNode() {
        return "deliveryboard.edit";
    }

    @Override
    public void perform(Player p, String[] args) {
        if (args.length < 2) return;
        PlayerMenuUtility pmu = new PlayerMenuUtility(p);
        pmu.setDeliveryID(args[1]);
        EditBoardMainMenu menu = new EditBoardMainMenu(pmu);
        menu.open();
    }
}
