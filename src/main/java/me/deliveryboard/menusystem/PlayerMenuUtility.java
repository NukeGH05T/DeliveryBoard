package me.deliveryboard.menusystem;

import org.bukkit.entity.Player;

public class PlayerMenuUtility {
    private Player owner;

    private String deliveryTitle;

    public PlayerMenuUtility(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public String getDeliveryTitle() {
        return deliveryTitle;
    }

    public void setDeliveryTitle(String deliveryTitle) {
        this.deliveryTitle = deliveryTitle;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
