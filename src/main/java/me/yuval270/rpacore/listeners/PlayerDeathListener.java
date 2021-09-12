package me.yuval270.rpacore.listeners;

import me.yuval270.rpacore.RpaCore;
import me.yuval270.rpacore.resetcharacter.ResetCharacterManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    private final RpaCore main = RpaCore.getMain();
    private final ResetCharacterManager resetCharacterManager = main.getResetCharacterManager();
    @EventHandler(ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (resetCharacterManager.shouldHandleEvent(player))
            resetCharacterManager.handleDeath(event);
    }
}
