package me.yuval270.rpacore.imanagers;

import org.bukkit.event.player.PlayerQuitEvent;

public interface IQuitJoinManagers {
    boolean handleQuit(PlayerQuitEvent event);
}
