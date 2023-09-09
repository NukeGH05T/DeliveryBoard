package me.nukeghost.handlers;

import me.nukeghost.DeliveryBoard;
import me.nukeghost.database.TokenDatabase;
import me.nukeghost.language.Message;
import me.nukeghost.menusystem.PlayerMenuUtility;
import me.nukeghost.template.Delivery;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

import static me.nukeghost.DeliveryBoard.plugin;

public class SkipHandler {
    private final Economy economy;

    public SkipHandler() {
        this.economy = DeliveryBoard.getEconomy();
    }

    public boolean hasDeductedSkipCost(PlayerMenuUtility pmu, Delivery delivery) {
        Player p = pmu.getOwner();

        if (!p.hasPermission("deliveryboard.skip." + pmu.getDeliveryID())) return false;
        if (plugin.getConfig().getBoolean("paid-delivery-skip")){ //CONFIG

            //Economy
            if (plugin.getConfig().getBoolean("economy.enabled")) { //CONFIG
                double cost = plugin.getConfig().getInt("economy.amount"); //CONFIG

                if (reducePlayerBalance(p, cost)) {
                    p.sendMessage(Message.SKIP_SUCCESSFUL);
                } else {
                    p.sendMessage(Message.INSUFFICIENT_TOKENS);
                    return false;
                }

                //Item
            } else if (plugin.getConfig().getBoolean("vanilla.enabled")){
                String itemString = plugin.getConfig().getString("vanilla.item", "DIAMOND");
                Material itemType = Material.matchMaterial(itemString);
                int quantity = plugin.getConfig().getInt("vanilla.amount");

                if (itemType == null) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Invalid Item provided - " + itemString);
                    return false;
                }

                if (deductItem(p, itemType, quantity)) {
                    p.sendMessage(Message.SKIP_SUCCESSFUL);
                    // Perform further actions related to the purchase
                } else {
                    p.sendMessage(Message.INSUFFICIENT_TOKENS);
                    return false;
                }

                //Token
            } else if (plugin.getConfig().getBoolean("delivery-tokens.enabled")) {
                if (Integer.parseInt(TokenDatabase.getCurrencyAmount(p.getUniqueId().toString())) >= delivery.getSkipCost()) {
                    UUID uuid = p.getUniqueId();

                    int takeAmount = delivery.getSkipCost();
                    int prevAmount = Integer.parseInt(TokenDatabase.getCurrencyAmount(uuid.toString()));

                    TokenDatabase.updateCurrency(prevAmount - takeAmount, uuid.toString());
                    p.sendMessage(Message.SKIP_SUCCESSFUL);
                } else {
                    p.sendMessage(Message.INSUFFICIENT_TOKENS);
                    return false;
                }
            }
        }
        return true;
    }

    private double getPlayerBalance(Player player) {
        if (economy == null) {
            plugin.getLogger().warning("Economy instance is null. Cannot check player balance.");
            return 0.0;
        }

        return economy.getBalance(player);
    }

    private boolean reducePlayerBalance(Player player, double amount) {
        if (economy == null) {
            plugin.getLogger().warning("Economy instance is null. Cannot reduce player balance.");
            return false;
        }

        if (economy.getBalance(player) < amount) {
            return false; // Player doesn't have enough money
        }

        economy.withdrawPlayer(player, amount);
        return true;
    }

    private boolean deductItem(Player player, Material itemType, int quantity) {
        ItemStack itemStack = new ItemStack(itemType, quantity);
        int count = 0;

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == itemType) {
                if (item.getAmount() >= quantity) {
                    item.setAmount(item.getAmount() - quantity);
                    player.updateInventory();
                    return true;
                } else {
                    count += item.getAmount();
                }
            }
        }

        if (count >= quantity) {
            for (ItemStack item : player.getInventory().getContents()) {
                if (item != null && item.getType() == itemType) {
                    int currentAmount = item.getAmount();
                    if (currentAmount >= quantity) {
                        item.setAmount(currentAmount - quantity);
                        player.updateInventory();
                        return true;
                    } else {
                        player.getInventory().remove(item);
                        quantity -= currentAmount;
                    }
                }
            }
        }

        return false;
    }

}
