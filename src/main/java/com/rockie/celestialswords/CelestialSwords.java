package com.rockie.celestialswords;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

public class CelestialSwords extends JavaPlugin {
    @Override
    public void onEnable() {
        Bukkit.getLogger().info("[CelestialSwords] Plugin enabled!");
        getServer().getPluginManager().registerEvents(new SwordAbilities(), this);
    }
}
