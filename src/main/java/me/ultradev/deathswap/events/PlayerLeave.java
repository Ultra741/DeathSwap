package me.ultradev.deathswap.events;

import me.ultradev.deathswap.DeathSwapManager;
import me.ultradev.deathswap.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {

        Player player = e.getPlayer();
        if(DeathSwapManager.gameActive && DeathSwapManager.players.contains(player)) {
            Bukkit.broadcastMessage(Main.toColor("&c" + player.getName() + " left the game!"));
            DeathSwapManager.players.remove(player);
            DeathSwapManager.checkGameEnd();
        }

    }

}
