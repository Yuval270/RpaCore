package me.yuval270.rpacore.resetcharacter;

import org.bukkit.Material;

public enum Stage {
    CHOOSING_NICKNAME(0), CLAN_CHOOSING(1), CHAKRA_NATURE(2), SPECIALIZATION(3),
    CLAN_CHOOSING_PROCESS(4), CHAKRA_NATURE_PROCESS(5), SPECIALIZATION_PROCESS(6);
    private int key;

    Stage(int key) {
        this.key = key;
    }

    public Stage get() {
        if (key == 1)
            return CLAN_CHOOSING_PROCESS;
        if (key == 2)
            return CHAKRA_NATURE_PROCESS;
        if (key == 3)
            return SPECIALIZATION_PROCESS;
        if (key == 4)
            return CLAN_CHOOSING;
        if (key == 5)
            return CHAKRA_NATURE;
        return SPECIALIZATION;

    }

    public Stage getOnQuit() {
        if (key == 1 || key == 4)
            return CLAN_CHOOSING;
        if (key == 2 || key == 5)
            return CHAKRA_NATURE;
        return SPECIALIZATION;

    }

    public static Stage getStage(String name) {
        if (name.equalsIgnoreCase("CLAN_CHOOSING"))
            return CLAN_CHOOSING;
        else if (name.equalsIgnoreCase("CHAKRA_NATURE"))
            return CHAKRA_NATURE;
        return SPECIALIZATION;
    }

}
