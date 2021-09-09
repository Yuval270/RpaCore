package me.yuval270.rpacore.resetcharacter;

import lombok.Getter;
import me.yuval270.rpacore.imanagers.IDamageManagers;
import me.yuval270.rpacore.imanagers.IManager;
import me.yuval270.rpacore.RpaCore;
import me.yuval270.rpacore.imanagers.IQuitJoinManagers;
import me.yuval270.rpacore.lang.Lang;
import me.yuval270.rpacore.resetcharacter.gui.StartNewCharacterGui;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

public class ResetCharacterManager implements IManager, IDamageManagers, IQuitJoinManagers {
    private final Map<UUID, Stage> activePlayers = new HashMap();
    private final List<String> commands_before_nickname = new ArrayList<>();
    @Getter
    private final StartNewCharacterGui startNewCharacterGui = new StartNewCharacterGui();
    private final RpaCore main = RpaCore.getMain();

    public ResetCharacterManager() {
        ConfigurationSection configSection = main.getConfig().getConfigurationSection("reset_character");
        commands_before_nickname.addAll(configSection.getStringList("commands_before_nick_name"));
    }

    public void addActivePlayer(Player player) {
        activePlayers.put(player.getUniqueId(), Stage.CHOOSING_NICKNAME);
        startNewCharacterGui.openGui(player);
    }

    public void executeBeforeNickCommands(String playerName) {
        commands_before_nickname.forEach(command ->
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", playerName)));
    }
    public boolean isChoosingNickname(UUID uuid){
        return activePlayers.containsKey(uuid) && activePlayers.get(uuid) == Stage.CHOOSING_NICKNAME;
    }

    public void handleProgress(Player player, Stage currentStage){
        if (currentStage == Stage.CHOOSING_NICKNAME){
            player.sendMessage(Lang.NICK_NAME_CHOSEN);
            // todo open new gui

        }
    }
    @Override
    public boolean shouldHandleEvent(Player player) {
        return activePlayers.containsKey(player.getUniqueId());
    }

    @Override
    public boolean handleQuit(PlayerQuitEvent event) {
        activePlayers.remove(event.getPlayer().getUniqueId());
        return false;
    }

    @Override
    public boolean handleEntityDamaged(EntityDamageByEntityEvent event) {
        Player player = (Player) event.getEntity();
        if (activePlayers.containsKey(player.getUniqueId()))
            event.setCancelled(true);
        return false;
    }
    @Override
    public boolean handleDeath(PlayerDeathEvent event) {
        return false;
    }

}
