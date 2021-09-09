package me.yuval270.rpacore.listeners;

import me.yuval270.rpacore.RpaCore;
import me.yuval270.rpacore.resetcharacter.ResetCharacterManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageEntityListener implements Listener {
    private final RpaCore main = RpaCore.getMain();

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        ResetCharacterManager resetCharacterManager = main.getResetCharacterManager();
        if (event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if (resetCharacterManager.shouldHandleEvent(player) && resetCharacterManager.handleEntityDamaged(event))
                    return;
        }

    }
}
