package me.yuval270.rpacore.gui;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.yuval270.rpacore.RpaCore;
import me.yuval270.rpacore.util.Chat;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public abstract class AbstractStaticGui {
    protected final RpaCore main = RpaCore.getMain();
    protected final Gui gui;

    public AbstractStaticGui(int rows, String name) {
        gui = new Gui(rows, Chat.translate(name));
    }

    protected abstract void createItems();

    public void openGui(Player player) {
        gui.open(player);
    }

    protected void setItems(Map<Integer, GuiItem> items) {
        for (Map.Entry<Integer, GuiItem> entry : items.entrySet()) {
            int slot = entry.getKey();
            GuiItem guiItem = entry.getValue();
            gui.setItem(slot, guiItem);
        }
    }

    protected void setItem(int slot, GuiItem guiItem) {
        gui.setItem(slot, guiItem);
    }


}
