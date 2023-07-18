package me.nukeghost.menusystem;

import org.bukkit.entity.Player;

public class PlayerMenuUtility {
    private Player owner;

    private String deliveryTitle;
    private String deliveryID;


    public PlayerMenuUtility(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public String getDeliveryTitle() {
        return deliveryTitle;
    }

    public String getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(String deliveryID) {
        this.deliveryID = deliveryID;
    }

    public void setDeliveryTitle(String deliveryTitle) {
        this.deliveryTitle = deliveryTitle;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
