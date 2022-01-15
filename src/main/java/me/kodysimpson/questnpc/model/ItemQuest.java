package me.kodysimpson.questnpc.model;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ItemQuest extends Quest {

    private Material itemType; // The type of item to be collected
    private int itemAmount; // The amount of items to be collected

    public ItemQuest(String name, String description, double reward, Material itemType, int itemAmount) {
        super(name, description, reward);
        this.itemType = itemType;
        this.itemAmount = itemAmount;
    }

    public Material getItemType() {
        return itemType;
    }

    public void setItemType(Material itemType) {
        this.itemType = itemType;
    }

    public int getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
    }
}
