package me.yuval270.rpacore.listeners;

import me.yuval270.rpacore.RpaCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {
    private final RpaCore main = RpaCore.getMain();

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClose(InventoryCloseEvent event) {
        System.out.println("InventoryCloseListener.onInventoryClose");
    }

}
