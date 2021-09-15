package me.yuval270.rpacore.resetcharacter.gui;

import dev.triumphteam.gui.guis.GuiItem;
import me.yuval270.rpacore.gui.AbstractStaticGui;
import me.yuval270.rpacore.resetcharacter.ResetCharacterManager;
import me.yuval270.rpacore.resetcharacter.Stage;
import me.yuval270.rpacore.util.ItemBuilder;
import me.yuval270.rpacore.util.async.FutureUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.List;

public class ResetCharacterGui extends AbstractStaticGui {
    private final Stage relatedStage;

    public ResetCharacterGui(int rows, String name, Stage relatedStage, String path) {
        super(rows, name, path);
        this.relatedStage = relatedStage;
        this.gui.setCloseGuiAction(this::onGuiClose);
    }


    @Override
    protected void createItems() {
        for (String item : main.getConfig().getConfigurationSection(path).getKeys(true)) {
            ConfigurationSection section = main.getConfig().getConfigurationSection(path + "." + item);
            if (section == null)
                return;
            List<String> commands = section.getStringList("commands_to_run");
            int slot = section.getInt("slot");
            GuiItem guiItem = createGuiItemUsingConfig(section);
            guiItem.setAction(event -> onGuiClick(event, commands));
            gui.setItem(slot, guiItem);
        }

    }

    private void onGuiClick(InventoryClickEvent event, List<String> commands) {
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        commands.forEach(command ->
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName())));
        main.getResetCharacterManager().handleProgress(player, relatedStage.get());
    }

    private void onGuiClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        ResetCharacterManager resetCharacter = main.getResetCharacterManager();
        Stage stage = resetCharacter.getPlayerStage(player.getUniqueId());
        FutureUtil.executeDelayedTask(1, () -> {
            if (stage != null && stage == relatedStage)
                gui.open(player);
        });
    }


    /**
     * creates basic item, name, type, lore and returns it as a gui item
     *
     * @param section the config section that it would use to retrieve data
     * @return the gui item that it created
     */
    protected GuiItem createGuiItemUsingConfig(ConfigurationSection section) {
        Material material = Material.getMaterial(section.getString("type").toUpperCase());
        List<String> lore = section.getStringList("lore");
        String name = section.getString("name");
        GuiItem guiItem = new GuiItem(new ItemBuilder(material).setName(name).setLore(lore).build());
        return guiItem;
    }
}
