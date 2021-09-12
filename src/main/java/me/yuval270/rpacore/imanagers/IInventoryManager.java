package me.yuval270.rpacore.imanagers;

import org.bukkit.event.inventory.InventoryCloseEvent;

public interface IInventoryManager {
    boolean handleInventoryClose(InventoryCloseEvent event);

}
