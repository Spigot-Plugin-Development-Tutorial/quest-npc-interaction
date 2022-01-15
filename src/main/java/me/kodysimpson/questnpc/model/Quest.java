package me.kodysimpson.questnpc.model;

import org.bukkit.entity.Player;

public abstract class Quest {

    //data that every quest will have
    //private QuestType questType;
    private String name; //display name of quest
    private String description; //description of quest
    private double reward; //money

    private long whenStarted; //time quest was started

    //constructor
    public Quest(String name, String description, double reward) {
        this.name = name;
        this.description = description;
        this.reward = reward;
    }

    public long getWhenStarted() {
        return whenStarted;
    }

    public void setWhenStarted(long whenStarted) {
        this.whenStarted = whenStarted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }
}
