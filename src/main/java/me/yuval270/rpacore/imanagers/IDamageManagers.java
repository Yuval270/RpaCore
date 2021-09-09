package me.yuval270.rpacore.imanagers;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public interface IDamageManagers {
    boolean handleDeath(PlayerDeathEvent event);
    boolean handleEntityDamaged(EntityDamageByEntityEvent event);
}
