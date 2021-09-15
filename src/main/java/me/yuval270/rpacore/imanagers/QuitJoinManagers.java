package me.yuval270.rpacore.imanagers;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public interface QuitJoinManagers {
    boolean handleQuit(PlayerQuitEvent event);
    boolean handleJoin(PlayerJoinEvent event);
}
