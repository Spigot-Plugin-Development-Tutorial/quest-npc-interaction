package me.kodysimpson.questnpc.commands;

import me.kodysimpson.questnpc.managers.NPCManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QuestNPCCommand implements CommandExecutor {

    private final NPCManager npcManager;

    public QuestNPCCommand(NPCManager npcManager) {
        this.npcManager = npcManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //make sure its a player
        if(sender instanceof Player p){

            npcManager.spawnJeff(p);

        }else{
            System.out.println("Be a player, dummy.");
        }

        return true;
    }
}
