package me.yuval270.rpacore.listeners;

import me.yuval270.rpacore.RpaCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    private final RpaCore main = RpaCore.getMain();
    @EventHandler(ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event) {
    }
}
