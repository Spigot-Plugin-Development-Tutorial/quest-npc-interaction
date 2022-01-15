package me.kodysimpson.questnpc;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import me.kodysimpson.questnpc.commands.QuestNPCCommand;
import me.kodysimpson.questnpc.listeners.QuestListeners;
import me.kodysimpson.questnpc.managers.NPCManager;
import me.kodysimpson.questnpc.managers.QuestManager;
import me.kodysimpson.questnpc.menu.QuestMenu;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class QuestNPC extends JavaPlugin {

    private static QuestNPC plugin;
    private NPCManager npcManager;
    private QuestManager questManager;

    @Override
    public void onEnable() {

        plugin = this;
        npcManager = new NPCManager();
        questManager = new QuestManager();

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getCommand("questnpc").setExecutor(new QuestNPCCommand(npcManager));

        getServer().getPluginManager().registerEvents(new QuestListeners(), this);

        //setup the menus
        MenuManager.setup(getServer(), this);

        //make a packet listener for entity interaction
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        manager.addPacketListener(new PacketAdapter(this, PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                    //figure out if the entity interacted with is Jeff the quest NPC
                    int entityID = packet.getIntegers().read(0);
                    if (npcManager.getJeffID() == entityID){
                        //the packet will happen 4 times, twice for each hand and again twice for INTERACT AND INTERACT_AT
                        //lets choose one to listen for specifically
                        EnumWrappers.Hand hand = packet.getEnumEntityUseActions().read(0).getHand();
                        EnumWrappers.EntityUseAction action = packet.getEnumEntityUseActions().read(0).getAction();
                        if (hand == EnumWrappers.Hand.MAIN_HAND && action == EnumWrappers.EntityUseAction.INTERACT) {
                            //open the menu in a synchronous bukkit task
                            getServer().getScheduler().runTask(plugin, () -> {
                                //open the quest menu to display the available quests
                                try {
                                    MenuManager.openMenu(QuestMenu.class, event.getPlayer());
                                } catch (MenuManagerException | MenuManagerNotSetupException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                    }
                }
        });

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static QuestNPC getPlugin() {
        return plugin;
    }

    public NPCManager getNpcManager() {
        return npcManager;
    }

    public QuestManager getQuestManager() {
        return questManager;
    }
}
