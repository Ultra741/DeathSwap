package me.ultradev.deathswap.commands;

import me.ultradev.deathswap.DeathSwapManager;
import me.ultradev.deathswap.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DeathSwapEndCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        if(!sender.hasPermission("deathswap.commands.ds-end")) {
            sender.sendMessage(Main.toColor("&cYou don't have permission to do this!"));
            return false;
        }

        if(!DeathSwapManager.gameActive) {
            sender.sendMessage(Main.toColor("&cThere is no game of DeathSwap active!"));
            return false;
        }

        DeathSwapManager.gameActive = false;
        DeathSwapManager.players.clear();

        for(Player player : Bukkit.getOnlinePlayers()) {
            if(player.hasPermission("deathswap.participate")) {
                player.teleport(player.getWorld().getSpawnLocation());
                player.sendMessage(Main.toColor("&c" + sender.getName() + " ended the game!"));
            }
        }

        return true;

    }

}
