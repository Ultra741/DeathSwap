package me.ultradev.deathswap;

import me.ultradev.deathswap.commands.DeathSwapEndCommand;
import me.ultradev.deathswap.commands.DeathSwapStartCommand;
import me.ultradev.deathswap.events.PlayerDeath;
import me.ultradev.deathswap.events.PlayerLeave;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main main;

    @Override
    public void onEnable() {
        // Plugin startup logic

        main = this;

        log("Registering commands...");
        registerCommand("ds-start", new DeathSwapStartCommand());
        registerCommand("ds-end", new DeathSwapEndCommand());

        log("Registering event listeners...");
        registerEvent(new PlayerLeave());
        registerEvent(new PlayerDeath());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }

    public static Main getInstance() {
        return main;
    }

    public static void log(String message) {
        System.out.println("[DeathSwap] " + message);
    }

    public static String toColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    private void registerCommand(String label, CommandExecutor command) {
        getCommand(label).setExecutor(command);
    }

    private void registerEvent(Listener event) {
        Bukkit.getPluginManager().registerEvents(event, this);
    }

}
