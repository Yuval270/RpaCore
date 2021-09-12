package me.yuval270.rpacore.listeners;

import me.yuval270.rpacore.RpaCore;
import me.yuval270.rpacore.database.SingleTable;
import me.yuval270.rpacore.resetcharacter.Stage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PlayerJoinListener implements Listener {
    private final RpaCore main = RpaCore.getMain();

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        SingleTable resetCharacterTable = main.getResetCharacterTable();
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        CompletableFuture<Boolean> dataExistsFuture = resetCharacterTable.dataExists(uuid.toString());
        dataExistsFuture.thenAccept(dataExists -> {
            if (dataExists)
                main.getResetCharacterManager().handleJoin(event);
            else
                resetCharacterTable.insertData(uuid.toString(), "completed");
        });


    }
}
