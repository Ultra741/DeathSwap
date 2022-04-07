package me.ultradev.deathswap.runnables;

import me.ultradev.deathswap.DeathSwapManager;
import me.ultradev.deathswap.Main;
import me.ultradev.deathswap.api.util.NumberUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SwapRunnable extends BukkitRunnable {

    int timer = 0;
    int swapDuration = NumberUtil.getRandomBetween(60, 180);

    @Override
    public void run() {

        if(!DeathSwapManager.gameActive) {
            this.cancel();
            return;
        }

        timer++;
        if(timer >= swapDuration) {

            timer = 0;
            swapDuration = NumberUtil.getRandomBetween(60, 180);

            new BukkitRunnable() {
                int untilSwap = 5;
                @Override
                public void run() {
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        if(player.hasPermission("deathswap.participate")) {
                            player.sendTitle(Main.toColor("&cSWAPPING IN " + untilSwap + " SECOND" + (untilSwap == 1 ? "" : "S") + "!"), "", 0, 40, 0);
                        }
                    }
                    untilSwap--;
                    if(untilSwap == 0) this.cancel();
                }
            }.runTaskTimer(Main.getInstance(), 0, 20);

            Bukkit.getScheduler().runTaskLater(Main.getInstance(), task -> {

                if(!DeathSwapManager.gameActive) return;
                HashMap<Player, Player> swaps = new HashMap<>();
                List<Player> players = new ArrayList<>(DeathSwapManager.players);

                for(Player player : new ArrayList<>(players)) {

                    Player swapPlayer = players.get(NumberUtil.getRandomBetween(0, players.size() - 1));
                    for(int i = 0; i < 1000; i++) {
                        if(player.getUniqueId().equals(swapPlayer.getUniqueId())) {
                            swapPlayer = players.get(NumberUtil.getRandomBetween(0, players.size() - 1));
                        } else break;
                    }

                    swaps.put(player, swapPlayer);
                    players.remove(swapPlayer);

                }

                HashMap<Player, Location> locations = new HashMap<>();
                for(Player player : DeathSwapManager.players) {
                    locations.put(player, player.getLocation());
                }

                for(Player player : swaps.keySet()) {
                    player.teleport(locations.get(swaps.get(player)));
                    player.sendMessage(Main.toColor("&aYou swapped with " + swaps.get(player).getName() + "!"));
                }

            }, 100);

        }

    }

}
