package me.yuval270.rpacore.listeners;

import me.yuval270.rpacore.RpaCore;
import me.yuval270.rpacore.resetcharacter.ResetCharacterManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {
    private final RpaCore main = RpaCore.getMain();
    private final ResetCharacterManager resetCharacterManager = main.getResetCharacterManager();

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
      /*  if (resetCharacterManager.shouldHandleEvent(player))
            resetCharacterManager.onInventoryClose(event);*/
    }

}
