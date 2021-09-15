package me.yuval270.rpacore.imanagers;

import org.bukkit.event.inventory.InventoryCloseEvent;

public interface InventoryManager {
    boolean handleInventoryClose(InventoryCloseEvent event);

}
