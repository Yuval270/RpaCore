package me.yuval270.rpacore;

import lombok.Getter;
import me.yuval270.rpacore.commands.ResetCharacterCommand;
import me.yuval270.rpacore.database.MySql;
import me.yuval270.rpacore.database.SingleTable;
import me.yuval270.rpacore.resetcharacter.ResetCharacterManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

public final class RpaCore extends JavaPlugin {
    @Getter
    private static RpaCore main;
    @Getter
    private MySql mySql;
    @Getter
    private ResetCharacterManager resetCharacterManager;
    @Getter
    private SingleTable resetCharacterTable;
    @Override
    public void onEnable() {
        setInstance();
        loadConfigData();
        loadManagers();
        registerCommands();
        registerListeners();
    }

    private void loadManagers() {
        loadDatabase();
        resetCharacterManager = new ResetCharacterManager();
    }

    private void loadDatabase() {
        mySql = new MySql();
        resetCharacterTable = new SingleTable("character_reset", "UUID", "STAGE");
    }

    private void registerCommands() {
        getCommand("resetcharacter").setExecutor(new ResetCharacterCommand());
        //getCommand("chunkhopper").setExecutor(new GiveHopperCommand());
    }

    private void loadConfigData() {
        saveDefaultConfig();
    }

    private void registerListeners() {
        String packageName = main.getClass().getPackage().getName();
        for (Class<?> clazz : new Reflections(packageName + ".listeners")
                .getSubTypesOf(Listener.class)) {
            try {
                Listener listener = (Listener) clazz.getDeclaredConstructor().newInstance();
                main.getServer().getPluginManager().registerEvents(listener, main);
            } catch (InvocationTargetException | NoSuchMethodException
                    | InstantiationException | IllegalAccessException e) {
            }
        }
    }


    private void setInstance() {
        main = this;
    }
}
