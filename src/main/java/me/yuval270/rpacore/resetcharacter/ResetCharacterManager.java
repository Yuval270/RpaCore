package me.yuval270.rpacore.resetcharacter;

import lombok.Getter;
import me.yuval270.rpacore.imanagers.IDamageManagers;
import me.yuval270.rpacore.imanagers.IInventoryManager;
import me.yuval270.rpacore.imanagers.IManager;
import me.yuval270.rpacore.RpaCore;
import me.yuval270.rpacore.imanagers.IQuitJoinManagers;
import me.yuval270.rpacore.lang.Lang;
import me.yuval270.rpacore.resetcharacter.gui.ResetCharacterGui;
import me.yuval270.rpacore.resetcharacter.gui.StartNewCharacterGui;
import me.yuval270.rpacore.util.async.FutureUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class ResetCharacterManager implements IManager, IDamageManagers, IQuitJoinManagers {

    @Getter
    private final StartNewCharacterGui startNewCharacterGui;
    @Getter
    private final ResetCharacterGui clanChooseGui;
    @Getter
    private final ResetCharacterGui chakraNatureGui;
    @Getter
    private final ResetCharacterGui specializationGui;
    private final Map<UUID, Stage> activePlayers = new HashMap();
    private final List<String> commands_before_nickname = new ArrayList<>();
    private final RpaCore main = RpaCore.getMain();

    public ResetCharacterManager() {

        ConfigurationSection configSection = main.getConfig().getConfigurationSection("reset_character");
        commands_before_nickname.addAll(configSection.getStringList("commands_before_nick_name"));
        startNewCharacterGui = new StartNewCharacterGui();
        clanChooseGui = new ResetCharacterGui(configSection.getInt("guis.clan_choose_gui.rows"),
                "Choose Clan", Stage.CLAN_CHOOSING, "reset_character.guis.clan_choose_gui.items");
        chakraNatureGui = new ResetCharacterGui(configSection.getInt("guis.chakra_nature.rows"),
                "Choose Chakra Nature", Stage.CHAKRA_NATURE, "reset_character.guis.chakra_nature.items");
        ;
        specializationGui = new ResetCharacterGui(configSection.getInt("guis.specialization.rows"),
                "Choose Specialization", Stage.SPECIALIZATION, "reset_character.guis.specialization.items");
    }

    public void addActivePlayer(Player player) {
        activePlayers.put(player.getUniqueId(), Stage.CHOOSING_NICKNAME);
        startNewCharacterGui.openGui(player);
    }

    public void executeBeforeNickCommands(String playerName) {
        commands_before_nickname.forEach(command ->
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", playerName)));
    }

    public boolean isChoosingNickname(UUID uuid) {
        return activePlayers.containsKey(uuid) && activePlayers.get(uuid) == Stage.CHOOSING_NICKNAME;
    }

    public void handleProgress(Player player, Stage currentStage) {
        activePlayers.put(player.getUniqueId(), currentStage);
        UUID uuid = player.getUniqueId();
        if (currentStage == Stage.CHOOSING_NICKNAME) {
            player.sendMessage(Lang.NICK_NAME_CHOSEN);
            activePlayers.put(uuid, Stage.CLAN_CHOOSING);
            clanChooseGui.openGui(player);
        }

        if (currentStage == Stage.CLAN_CHOOSING_PROCESS) {
            player.sendMessage(Lang.CLAN_CHOSEN);
            chakraNatureGui.openGui(player);
            activePlayers.put(uuid, Stage.CHAKRA_NATURE);
        }
        if (currentStage == Stage.CHAKRA_NATURE_PROCESS) {
            player.sendMessage(Lang.CHAKRA_NATURE_CHOSEN);
            specializationGui.openGui(player);
            activePlayers.put(uuid, Stage.SPECIALIZATION);

        }
        if (currentStage == Stage.SPECIALIZATION_PROCESS) {
            player.sendMessage(Lang.SPECIALIZATION_CHOSEN);
            activePlayers.remove(uuid);
            player.closeInventory();
            main.getResetCharacterTable().updateField(uuid.toString(), "completed");
        }


    }

    public Stage getPlayerStage(UUID uuid) {
        return activePlayers.get(uuid);
    }

    private void continueReset(Player player, Stage stage) {
        activePlayers.put(player.getUniqueId(), stage);
        openInventoryOnAccidentalClose(player);
    }

    @Override
    public boolean shouldHandleEvent(Player player) {
        return activePlayers.containsKey(player.getUniqueId());
    }

    @Override
    public boolean handleEntityDamaged(EntityDamageByEntityEvent event) {
        Player player = (Player) event.getEntity();
        if (activePlayers.containsKey(player.getUniqueId()))
            event.setCancelled(true);
        return false;
    }

    @Override
    public boolean handleQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        Stage stage = activePlayers.get(uuid);
        if (stage != Stage.CHOOSING_NICKNAME)
            main.getResetCharacterTable().updateField(uuid.toString(), activePlayers.get(uuid).getOnQuit().toString());
        activePlayers.remove(uuid);
        return false;
    }

    @Override
    public boolean handleJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        CompletableFuture<String> playerStageFuture = main.getResetCharacterTable().getField(uuid.toString());
        playerStageFuture.thenAccept(stage -> {
            if (!stage.equalsIgnoreCase("completed")) {
                Stage currentStage = Stage.getStage(stage);
                continueReset(player, currentStage);
            }
        });
        return false;
    }

    @Override
    public boolean handleDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        player.setHealth(player.getMaxHealth());
        openInventoryOnAccidentalClose(player);
        return false;
    }

    private void openInventoryOnAccidentalClose(Player player) {
        Stage stage = getPlayerStage(player.getUniqueId());
        if (stage == Stage.CLAN_CHOOSING)
            clanChooseGui.openGui(player);
        if (stage == Stage.CHAKRA_NATURE)
            chakraNatureGui.openGui(player);
        if (stage == Stage.SPECIALIZATION)
            specializationGui.openGui(player);
    }
}
