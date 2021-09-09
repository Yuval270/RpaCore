package me.yuval270.rpacore.resetcharacter.gui;

import me.yuval270.rpacore.gui.AbstractStaticGui;
import org.bukkit.configuration.ConfigurationSection;

public class ClanChoosingGui extends AbstractStaticGui {

    public ClanChoosingGui(int rows) {
        super(rows, "Choose Clan");
    }

    @Override
    protected void createItems() {
        ConfigurationSection section = main.getConfig().getConfigurationSection("reset_character.guis.clan_choose_gui");

    }
}
