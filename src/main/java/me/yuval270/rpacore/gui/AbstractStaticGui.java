package me.yuval270.rpacore.gui;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.yuval270.rpacore.RpaCore;
import me.yuval270.rpacore.util.Chat;
import org.bukkit.entity.Player;
import net.kyori.adventure.text.Component;

import java.util.Map;

public abstract class AbstractStaticGui {
    protected final RpaCore main = RpaCore.getMain();
    protected final Gui gui;
    protected String path;

    public AbstractStaticGui(int rows, String name) {
        this.gui = Gui.gui().title(Component.text(Chat.translate(name))).rows(rows).create();
        createItems();
    }

    public AbstractStaticGui(int rows, String name, String path) {
        this.gui = Gui.gui().title(Component.text(Chat.translate(name))).rows(rows).create();
        this.path = path;
        createItems();
    }

    public void openGui(Player player) {
        gui.open(player);
    }

    protected abstract void createItems();

    protected void setItem(int slot, GuiItem guiItem) {
        gui.setItem(slot, guiItem);
    }

/*
    protected void setItems(Map<Integer, GuiItem> items) {
        items.forEach(gui::setItem);
    }
*/

}
