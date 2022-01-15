package me.kodysimpson.questnpc.model;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class KillQuest extends Quest {

    private EntityType target; // The Bukkit entity type to kill
    private int amount; //the amount of targets to kill

    //progress
    private int progress; //the amount of targets killed

    public KillQuest(String name, String description, double reward, EntityType target, int amount) {
        super(name, description, reward);
        this.target = target;
        this.amount = amount;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public EntityType getTarget() {
        return target;
    }

    public void setTarget(EntityType target) {
        this.target = target;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
