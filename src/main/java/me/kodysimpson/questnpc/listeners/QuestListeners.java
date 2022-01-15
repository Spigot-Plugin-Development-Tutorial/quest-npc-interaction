package me.kodysimpson.questnpc.listeners;

import me.kodysimpson.questnpc.QuestNPC;
import me.kodysimpson.questnpc.managers.QuestManager;
import me.kodysimpson.questnpc.model.KillQuest;
import me.kodysimpson.questnpc.model.Quest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class QuestListeners implements Listener {

    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {

        Player p = event.getEntity().getKiller();

        //see if the player is on a quest
        QuestManager qm = QuestNPC.getPlugin().getQuestManager();
        Quest q = qm.getQuest(p);
        if (q != null) {
            //see if the quest has a kill requirement
            if (q instanceof KillQuest killQuest) {
                //see if the entity killed is the same as the quest's target
                if (killQuest.getTarget().equals(event.getEntity().getType())) {
                    //increase the progress count
                    killQuest.setProgress(killQuest.getProgress() + 1);

                    //see if the progress count is equal to the required amount
                    if (killQuest.getProgress() == killQuest.getAmount()) {
                        //remove the quest
                        qm.completeQuest(p);
                    }else{
                        //tell them the progress they have made so far
                        p.sendMessage("You have killed " + killQuest.getProgress() + " " + killQuest.getTarget().name().toLowerCase() + "s.");
                        //tell them how many they need still
                        p.sendMessage("You need to kill " + (killQuest.getAmount() - killQuest.getProgress()) + " more " + killQuest.getTarget().name().toLowerCase() + "s.");
                    }
                }
            }
        }

    }

}
