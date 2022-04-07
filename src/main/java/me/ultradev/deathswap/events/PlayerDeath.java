package me.ultradev.deathswap.events;

import me.ultradev.deathswap.DeathSwapManager;
import me.ultradev.deathswap.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {

        Player player = e.getEntity();
        if(DeathSwapManager.gameActive && DeathSwapManager.players.contains(player)) {
            Bukkit.broadcastMessage(Main.toColor("&c" + player.getName() + " died!"));
            DeathSwapManager.players.remove(player);
            DeathSwapManager.checkGameEnd();
        }

    }

}
