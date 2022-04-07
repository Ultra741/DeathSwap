package me.ultradev.deathswap;

import me.ultradev.deathswap.api.util.NumberUtil;
import me.ultradev.deathswap.runnables.SwapRunnable;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DeathSwapManager {

    public static boolean gameActive = false;
    public static List<Player> players = new ArrayList<>();

    public static void startGame() {

        Bukkit.broadcastMessage(Main.toColor("&aA game of DeathSwap is starting in 5 seconds!"));
        gameActive = true;
        players.clear();
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(player.hasPermission("deathswap.participate")) players.add(player);
        }

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), task -> {

            if(!gameActive) return;
            Bukkit.broadcastMessage(Main.toColor("&aTeleporting players..."));

            List<Location> locations = new ArrayList<>();
            for(Player player : players) {

                Location playerLocation = new Location(player.getWorld(),
                        NumberUtil.getRandomBetween(-100000, 100000),
                        0,
                        NumberUtil.getRandomBetween(-100000, 100000));

               playerLocation.setY(player.getWorld().getHighestBlockYAt(playerLocation) + 1);

               for(int i = 0; i < 100; i++) {
                   for(Location checkLocation : locations) {
                        if(checkLocation.distance(playerLocation) < 5000) {
                            playerLocation = new Location(player.getWorld(),
                                    NumberUtil.getRandomBetween(-100000, 100000),
                                    0,
                                    NumberUtil.getRandomBetween(-100000, 100000));
                            playerLocation.setY(player.getWorld().getHighestBlockYAt(playerLocation) + 1);
                        }
                   }
               }

               for(int i = 0; i < 100; i++) {
                   if(!playerLocation.getBlock().getType().equals(Material.AIR)) {
                       playerLocation.add(0, 1, 0);
                   }
               }

                locations.add(playerLocation);
                player.teleport(playerLocation);

            }

            Bukkit.broadcastMessage(Main.toColor("&aDeathSwap has started!"));

            for(Player player : Bukkit.getOnlinePlayers()) {
                player.getInventory().clear();
                player.setSaturation(20);
                player.setFoodLevel(20);
                player.setHealth(20);
                player.setGameMode(GameMode.SURVIVAL);
            }

            new SwapRunnable().runTaskTimer(Main.getInstance(), 0, 20);

        }, 100);

    }

    public static void checkGameEnd() {

        if(gameActive && players.size() == 1) {

            Bukkit.broadcastMessage(Main.toColor("&6&l" + players.get(0).getName() + " won the game! Congratulations!"));
            gameActive = false;
            players.clear();

            for(Player player : Bukkit.getOnlinePlayers()) {
                if(player.hasPermission("deathswap.participate")) {
                    player.teleport(player.getWorld().getSpawnLocation());
                }
            }

        }

    }

}
