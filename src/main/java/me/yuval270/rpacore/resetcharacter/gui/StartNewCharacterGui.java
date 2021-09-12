package me.yuval270.rpacore.resetcharacter.gui;

import dev.triumphteam.gui.guis.GuiItem;
import me.yuval270.rpacore.RpaCore;
import me.yuval270.rpacore.gui.AbstractStaticGui;
import me.yuval270.rpacore.lang.Lang;
import me.yuval270.rpacore.resetcharacter.ResetCharacterManager;
import me.yuval270.rpacore.resetcharacter.Stage;
import me.yuval270.rpacore.util.ItemBuilder;
import me.yuval270.rpacore.util.async.FutureUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.UUID;

public class StartNewCharacterGui extends AbstractStaticGui {

    public StartNewCharacterGui() {
        super(3, "Reset Character");

    }

    @Override
    protected void createItems() {
        int slot = 13; // middle slot
        ItemBuilder itemBuilder = new ItemBuilder(Material.NETHER_STAR).setName("&6Start New Character");
        GuiItem guiItem = new GuiItem(itemBuilder.build());
        guiItem.setAction(event -> {
            ResetCharacterManager resetCharacterManager = main.getResetCharacterManager();
            Player player = (Player) event.getWhoClicked();
            event.setCancelled(true);
            player.sendMessage(Lang.STARTED_NEW_CHARACTER);
            resetCharacterManager.executeBeforeNickCommands(player.getName());
            resetCharacterManager.addActivePlayer(player);
            player.closeInventory();
            FutureUtil.executeDelayedTask(60, () -> player.sendMessage(Lang.TO_CONTINUE));
            player.closeInventory();
        });
        setItem(slot, guiItem);
    }


}
