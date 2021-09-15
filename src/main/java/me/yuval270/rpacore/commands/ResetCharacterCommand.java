package me.yuval270.rpacore.commands;

import me.yuval270.rpacore.RpaCore;
import me.yuval270.rpacore.lang.Lang;
import me.yuval270.rpacore.resetcharacter.ResetCharacterManager;
import me.yuval270.rpacore.resetcharacter.Stage;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ResetCharacterCommand implements CommandExecutor {
    private final RpaCore main = RpaCore.getMain();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) return false;
        ResetCharacterManager resetCharacterManager = main.getResetCharacterManager();
        Player player = (Player) commandSender;
        UUID uuid = player.getUniqueId();
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reset")) {
                resetCharacterManager.getStartNewCharacterGui().openGui(player);
                return false;
            }
            if (args[0].equalsIgnoreCase("proceed")) {
                if (!resetCharacterManager.isChoosingNickname(uuid)) {
                    player.sendMessage(Lang.NOT_IN_PROGRESS);
                    return false;
                }
                resetCharacterManager.handleProgress(player, Stage.CHOOSING_NICKNAME);
            }
        }
        commandSender.sendMessage(ChatColor.YELLOW + "Commands:");
        commandSender.sendMessage(ChatColor.GOLD + "/resetcharacter proceed");
        commandSender.sendMessage(ChatColor.GOLD + "/resetcharacter reset");

        return false;
    }
}
