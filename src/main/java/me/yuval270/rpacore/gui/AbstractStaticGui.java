package me.yuval270.rpacore.gui;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.yuval270.rpacore.RpaCore;
import me.yuval270.rpacore.resetcharacter.Stage;
import me.yuval270.rpacore.util.Chat;
import me.yuval270.rpacore.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public abstract class AbstractStaticGui {
    protected final RpaCore main = RpaCore.getMain();
    protected final Gui gui;
    protected String path;
    public AbstractStaticGui(int rows, String name) {
        gui = new Gui(rows, Chat.translate(name));
        createItems();
    }
    public AbstractStaticGui(int rows, String name, String path) {
        gui = new Gui(rows, Chat.translate(name));
        this.path = path;
        createItems();
    }

    public void openGui(Player player) {
        gui.open(player);
    }

    protected abstract void createItems();

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
