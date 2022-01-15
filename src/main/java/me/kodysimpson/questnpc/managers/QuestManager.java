package me.kodysimpson.questnpc.managers;

import me.kodysimpson.questnpc.QuestNPC;
import me.kodysimpson.questnpc.model.KillQuest;
import me.kodysimpson.questnpc.model.Quest;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//responsible for managing(and storing) all quests
public class QuestManager {

    private final HashMap<Player, Quest> quests;

    public QuestManager() {
        this.quests = new HashMap<>();
    }

    //Load the quests from config.yml
    public List<Quest> getAvailableQuests() {
        List<Quest> availableQuests = new ArrayList<>();

        QuestNPC.getPlugin().getConfig().getConfigurationSection("quests.kill").getKeys(false).forEach(questName -> {
            //create a Quest object from each of these

            String name = QuestNPC.getPlugin().getConfig().getString("quests.kill." + questName + ".name");
            String description = QuestNPC.getPlugin().getConfig().getString("quests.kill." + questName + ".description");
            double reward = QuestNPC.getPlugin().getConfig().getDouble("quests.kill." + questName + ".reward");
            String entityType = QuestNPC.getPlugin().getConfig().getString("quests.kill." + questName + ".target.type");
            int count = QuestNPC.getPlugin().getConfig().getInt("quests.kill." + questName + ".target.count");

            EntityType entityType1 = EntityType.valueOf(entityType);

            Quest quest = new KillQuest(name, description, reward, entityType1, count);
            availableQuests.add(quest);
            System.out.println(quest);
        });

        return availableQuests;
    }

    //Get the Quest that a Player is on, null if they aren't on one
    public Quest getQuest(Player player) {
        return this.quests.get(player);
    }

    public void giveQuest(Player p, Quest q){
        q.setWhenStarted(System.currentTimeMillis());
        this.quests.put(p, q);
    }

    public void completeQuest(Player p){

        Quest quest = this.quests.get(p);

        //tell them they completed the quest
        long timeTook = System.currentTimeMillis() - quest.getWhenStarted();
        //convert to a string with minutes and seconds
        String timeTookString = String.format("%02d:%02d", timeTook / 60000, (timeTook % 60000) / 1000);
        p.sendMessage(ColorTranslator.translateColorCodes("&aYou completed the quest &7\"" + quest.getName() + "\"&a in " + timeTookString));

        //give them the reward
        p.sendMessage("You received " + this.quests.get(p).getReward() + " coins!");

        //remove the quest from the HashMap
        this.quests.remove(p);
    }

}
