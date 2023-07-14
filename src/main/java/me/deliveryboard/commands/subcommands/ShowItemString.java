package me.deliveryboard.commands.subcommands;

import me.deliveryboard.commands.SubCommand;
import me.deliveryboard.external.plugins.ExtEcoItems;
import me.deliveryboard.external.plugins.ExtExecutableItems;
import me.deliveryboard.external.plugins.ExtItemsAdder;
import me.deliveryboard.external.plugins.ExtMMOItems;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ShowItemString extends SubCommand {
    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "Get the item string of currently held item";
    }

    @Override
    public String getSyntax() {
        return "/db show <h|th|sh>";
    }

    @Override
    public String getPermissionNode() {
        return "deliveryboard.add";
    }

    @Override
    public void perform(Player p, String[] args) {
        String iaString = new ExtItemsAdder().generateItemString(p.getInventory().getItemInMainHand());
        String mmoString = new ExtMMOItems().generateItemString(p.getInventory().getItemInMainHand());
        String ecoString = new ExtEcoItems().generateItemString(p.getInventory().getItemInMainHand());
        String eiString = new ExtExecutableItems().generateItemString(p.getInventory().getItemInMainHand());

        String finalString;
        if (iaString != null) {
            //p.sendMessage(ChatColor.DARK_AQUA + iaString);
            finalString = iaString;
        } else if (mmoString != null) {
            //p.sendMessage(ChatColor.YELLOW + mmoString);
            finalString = mmoString;
        } else if (ecoString != null) {
            //p.sendMessage(ChatColor.LIGHT_PURPLE + ecoString);
            finalString = ecoString;
        } else if (eiString != null) {
            //p.sendMessage(ChatColor.GREEN + eiString);
            finalString = eiString;
        } else {
            //Vanilla Item
            String vanString = "van@" + p.getInventory().getItemInMainHand().getType() + "@" +
                    p.getInventory().getItemInMainHand().getAmount();
            p.sendMessage(ChatColor.DARK_PURPLE + vanString);
            finalString = vanString;
        }

        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(new TextComponent("Click!")).create());
        ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, finalString);
        TextComponent message = new TextComponent(ChatColor.DARK_AQUA + "[DB] ItemString: " + finalString);
        message.setHoverEvent(hoverEvent);
        message.setClickEvent(clickEvent);
        p.spigot().sendMessage(message);
    }
}
