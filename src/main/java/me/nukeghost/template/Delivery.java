package me.nukeghost.template;

import me.nukeghost.handlers.GenerationHandler;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static me.nukeghost.DeliveryBoard.plugin;

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

    private List<Player> deliveryCompletedPlayersList = new ArrayList<>();

    public Delivery(String deliveryID, String deliveryName, long cooldown) {
        this.deliveryID = deliveryID;
        this.deliveryName = deliveryName;
        this.cooldownTime = cooldown;

        this.positionSlot = plugin.getConfig().getInt("delivery." + deliveryID + ".position-slot");

        this.cooldownStart = System.currentTimeMillis();
        this.deliveryItem = GenerationHandler.generateDeliveryItem(deliveryID);
        this.iconItem = GenerationHandler.generateItemFromString(plugin.getConfig().getString("delivery." + deliveryID + ".icon-item"));
    }

    /**
     * Returns the remaining cooldown time
     * @return Negative value if the cooldown is finished
     */
    public long getRemainingCooldownTime() {
        return System.currentTimeMillis() - (cooldownStart + cooldownTime);
    }

    public void updateDeliveryItem() {
        cooldownStart = System.currentTimeMillis();
        deliveryItem = GenerationHandler.generateDeliveryItem(deliveryID);
    }

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
}
