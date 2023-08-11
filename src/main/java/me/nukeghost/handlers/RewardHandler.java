package me.nukeghost.handlers;

import me.nukeghost.DeliveryBoard;
import me.nukeghost.utils.PlaceholderUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RewardHandler {
    DeliveryBoard plugin;

    public RewardHandler(DeliveryBoard plugin) {
        this.plugin = plugin;
    }

    public void giveRewards(Player p, String deliveryID) {
        int maxRewardAmount = plugin.getConfig().getInt("delivery." + deliveryID +".reward-setup.mixed.max-reward");
        List<String> confirmedRewardsList = plugin.getConfig().getStringList("delivery." + deliveryID +".reward-setup.mixed.confirmed-rewards");
        List<String> randomRewardsList = new ArrayList<>();

        for (String rewardStringInList : plugin.getConfig().getStringList("delivery." + deliveryID +".reward-setup.mixed.random-rewards-pool")) {
            int weight = processRewardWeight(rewardStringInList);
            for (int i = 0; i < weight; i++) {
                randomRewardsList.add(rewardStringInList);
            }
        }

        //Give each confirmed reward
        for (String confirmedRewardString : confirmedRewardsList) {
            if (confirmedRewardString.startsWith("item")) {
                giveItemRewardFromString(confirmedRewardString, p);
            } else if (confirmedRewardString.startsWith("comm")) {
                giveCommandRewardFromString(confirmedRewardString, p);
            }
        }

        //Pick random rewards and execute until it reaches max amount
        giveRandomRewards(randomRewardsList, maxRewardAmount, p);
    }

    /**
     * @param rewardString Put the reward string in here. Ex: item@DIAMOND@32
     * @return Index 1 = Material Name |
     *         Index 2 = Item Amount
     */
    private void giveItemRewardFromString(String rewardString, Player p) {
        String[] parts = rewardString.split("@");

        String itemName = parts[0];   // "item"
        String itemMaterial = parts[1];   // "DIAMOND"
        int itemQuantity = Integer.parseInt(parts[2]);   // "32"

        if (Material.matchMaterial(itemMaterial) == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Failed to parse material: " + ChatColor.YELLOW + itemMaterial);
            return;
        }

        if (itemQuantity > Material.getMaterial(itemMaterial).getMaxStackSize()) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Amount exceeds maximum stack size for " + ChatColor.YELLOW + itemMaterial);
            return;
        }

        //Give the player the items if they have space in inventory, otherwise drop the items on player's location
        if (p.getInventory().firstEmpty() != -1) {
            // Player has empty space in their inventory, give the item directly
            p.getInventory().addItem(new ItemStack(Material.getMaterial(itemMaterial), itemQuantity));
        } else {
            // Player's inventory is full, drop the items on the player's location
            p.getWorld().dropItem(p.getLocation(), new ItemStack(Material.getMaterial(itemMaterial), itemQuantity));
        }
    }

    /**
     * @param rewardString Put the reward string in here. Ex: comm@eco give {PLAYER_NAME} 500
     * @return Index 1 = Command
     */
    private String giveCommandRewardFromString(String rewardString, Player p) {
        String[] parts = rewardString.split("@");

        // Run the command
        boolean success = runCommand(PlaceholderUtils.parsePlaceholders(parts[1], p));

        if (!success) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Failed to run reward command: " + ChatColor.YELLOW + parts[1] + ChatColor.RED + " for " + ChatColor.YELLOW + p.getDisplayName());
        }


        return parts[1]; // "eco give {PLAYER_NAME} 500"
    }

    private void giveRandomRewards(List<String> rewardsList, int maxRewardAmount, Player p) {
        if (rewardsList.isEmpty() || maxRewardAmount <= 0) {
            return;
        }

        Random random = new Random();

        for (int i = 0; i < maxRewardAmount; i++) {
            int randomIndex = random.nextInt(rewardsList.size());
            String randomReward = rewardsList.get(randomIndex);

            if (randomReward.startsWith("item")) {
                giveItemRewardFromString(randomReward, p);
            } else if (randomReward.startsWith("comm")) {
                giveCommandRewardFromString(randomReward, p);
            }

            rewardsList.remove(randomIndex);

            if (rewardsList.isEmpty()) {
                break;
            }
        }
    }

    private boolean runCommand(String command) {
        CommandSender console = Bukkit.getServer().getConsoleSender();
        return Bukkit.getServer().dispatchCommand(console, command);
    }

    private int processRewardWeight(String rewardString) {
        String parts[] = rewardString.split("@");
        int weight = Integer.parseInt(parts[parts.length - 1]);

        return weight;
    }

}
