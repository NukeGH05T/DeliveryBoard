package me.nukeghost.template;

import me.nukeghost.data.PlayerData;
import me.nukeghost.handlers.GenerationHandler;
import me.nukeghost.handlers.RewardHandler;
import me.nukeghost.language.Message;
import me.nukeghost.utils.ColorUtils;
import me.nukeghost.utils.PlaceholderUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

import static me.nukeghost.DeliveryBoard.*;
import static me.nukeghost.handlers.AccumulatedRewardHandler.sections;

public class Delivery {
    private String deliveryID;
    private String deliveryName;
    private long cooldownStart;
    private long cooldownTime;

    private ItemStack deliveryItem;
    private int amount;
    private int slotAmount;

    private int positionSlot; //The slot in which to place the delivery icon

    private ItemStack iconItem;

    private int maxSubmission;

    private int skipCost;
    private boolean hasReachedMaxSubmission = false;
    private boolean shouldSendAlert = false;
    private List<String> deliveryInfoLore = new ArrayList<>();

    private List<Player> deliveryCompletedPlayersList = new ArrayList<>();

    private boolean accummulate = false;

    public Delivery(String deliveryID, String deliveryName, long cooldown) {
        this.deliveryID = deliveryID;
        this.deliveryName = deliveryName;
        this.cooldownTime = cooldown;

        this.accummulate = plugin.getConfig().getBoolean("delivery." + deliveryID + ".add-accumulation");
        this.positionSlot = plugin.getConfig().getInt("delivery." + deliveryID + ".position-slot");
        this.maxSubmission = plugin.getConfig().getInt("delivery." + deliveryID + ".max-submissions");
        this.skipCost = plugin.getConfig().getInt("delivery." + deliveryID + ".skip-cost");
        this.shouldSendAlert = plugin.getConfig().getBoolean("delivery." + deliveryID + ".send-alert", true);

        List<String> perDeliveryInfoLore = ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(plugin.getConfig().getStringList("delivery." + deliveryID + ".info-lore")));
        this.deliveryInfoLore = perDeliveryInfoLore != null && !perDeliveryInfoLore.isEmpty() ? perDeliveryInfoLore : Message.ICON_ITEM_LORE;

        this.cooldownStart = System.currentTimeMillis();
        this.deliveryItem = GenerationHandler.generateDeliveryItem(deliveryID);
        this.iconItem = GenerationHandler.generateItemFromString(plugin.getConfig().getString("delivery." + deliveryID + ".icon-item"));

        new BukkitRunnable() {
            @Override
            public void run() {
                updateDeliveryItem();
            }
        }.runTaskTimer(plugin, 20, cooldownTime * 20L);
    }

    public void updateDeliveryItem() {
        cooldownStart = System.currentTimeMillis();
        deliveryItem = GenerationHandler.generateDeliveryItem(deliveryID);
        deliveryCompletedPlayerList.get(deliveries.indexOf(this)).clear();

        //Expose the generated item/delivery using an API for others to use
        if (shouldSendAlert) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(ColorUtils.translateHexColorCodes("<#", ">", ColorUtils.translateColorCodes(PlaceholderUtils.parsePlaceholders(Message.REFRESH_REMINDER, player, deliveryName, -1))));
            }
        }
    }

    public void addAccumulation(Player p) {
        int temp = PlayerData.get().getInt("data." + p.getUniqueId().toString() + ".completed", 0) + 1;
        PlayerData.get().set("data." + p.getUniqueId().toString() + ".completed", temp);
        PlayerData.save();

        if (sections.contains(String.valueOf(temp))) {
            new RewardHandler(plugin).giveRewardsAccumulated(p, "accumulated-rewards." + temp);
        }
    }

//    public void addAccumulationOld(Player p) {
//        rewardAccumulation++;
//
//        if (sections.contains(String.valueOf(rewardAccumulation))) {
//            new RewardHandler(plugin).giveRewardsAccumulated(p, "accumulated-rewards." + rewardAccumulation);
//        }
//    }

    public String getDeliveryID() {
        return deliveryID;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public long getCooldownStart() {
        return cooldownStart;
    }

    public void setCooldownStart(long cooldownStart) {
        this.cooldownStart = cooldownStart;
    }

    public long getCooldownTime() {
        return cooldownTime;
    }

    public ItemStack getDeliveryItem() {
        return deliveryItem;
    }

    public void setDeliveryItem(ItemStack deliveryItem) {
        this.deliveryItem = deliveryItem;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getSlotAmount() {
        return slotAmount;
    }

    public void setSlotAmount(int slotAmount) {
        this.slotAmount = slotAmount;
    }

    public int getPositionSlot() {
        return positionSlot;
    }

    public int getMaxSubmission() {
        return maxSubmission;
    }

    public int getSkipCost() {
        return skipCost;
    }

    public boolean isHasReachedMaxSubmission() {
        return hasReachedMaxSubmission;
    }

    public void setHasReachedMaxSubmission(boolean hasReachedMaxSubmission) {
        this.hasReachedMaxSubmission = hasReachedMaxSubmission;
    }

    public List<String> getDeliveryInfoLore() {
        return deliveryInfoLore;
    }

    public boolean isShouldSendAlert() {
        return shouldSendAlert;
    }

    public boolean isAccummulate() {
        return accummulate;
    }
}
