package com.rockie.celestialswords;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class SwordAbilities {

    private final JavaPlugin plugin;

    // Cooldowns in seconds
    private final int KUROZAI_COOLDOWN = 10;
    private final int ZANPAKUTO_COOLDOWN = 8;
    private final int ICEGLACIAL_COOLDOWN = 12;

    // Track cooldowns per player
    private final HashMap<UUID, Long> kurozaiCooldown = new HashMap<>();
    private final HashMap<UUID, Long> zanpakutoCooldown = new HashMap<>();
    private final HashMap<UUID, Long> iceGlacialCooldown = new HashMap<>();

    public SwordAbilities(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    // ----------------- Kurozai -----------------
    public void activateKurozai(Player player) {
        if (isOnCooldown(player, kurozaiCooldown)) return;

        Location loc = player.getLocation();
        player.getWorld().spawnParticle(Particle.PORTAL, loc, 100, 1, 2, 1, 0.1);
        player.getWorld().playSound(loc, Sound.ENTITY_ENDER_DRAGON_FLAP, 1f, 1f);

        for (Entity e : player.getNearbyEntities(10, 10, 10)) {
            e.setVelocity(player.getLocation().toVector().subtract(e.getLocation().toVector()).normalize().multiply(2));
        }

        setCooldown(player, kurozaiCooldown, KUROZAI_COOLDOWN);
    }

    // ----------------- Zanpakuto -----------------
    public void activateZanpakuto(Player player) {
        if (isOnCooldown(player, zanpakutoCooldown)) return;

        Location loc = player.getLocation();
        player.getWorld().spawnParticle(Particle.FLAME, loc, 50, 2, 2, 2, 0.2);
        player.getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f);

        for (Entity e : player.getNearbyEntities(5, 5, 5)) {
            if (e instanceof LivingEntity) {
                ((LivingEntity) e).damage(8, player);
            }
        }

        setCooldown(player, zanpakutoCooldown, ZANPAKUTO_COOLDOWN);
    }

    // ----------------- IceGlacial -----------------
    public void activateIceGlacial(Player player) {
        if (isOnCooldown(player, iceGlacialCooldown)) return;

        Location loc = player.getLocation();
        player.getWorld().spawnParticle(Particle.SNOWBALL, loc, 50, 3, 1, 3, 0.2);
        player.getWorld().playSound(loc, Sound.BLOCK_SNOW_BREAK, 1f, 1f);

        for (Entity e : player.getNearbyEntities(6, 2, 6)) {
            if (e instanceof LivingEntity) {
                ((LivingEntity) e).setFreezeTicks(100);
                ((LivingEntity) e).setVelocity(((LivingEntity) e).getVelocity().multiply(0.1));
            }
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                for (int x = -3; x <= 3; x++) {
                    for (int z = -3; z <= 3; z++) {
                        Location blockLoc = loc.clone().add(x, 0, z);
                        if (blockLoc.getBlock().getType() == Material.WATER) blockLoc.getBlock().setType(Material.ICE);
                        if (blockLoc.getBlock().getType() == Material.LAVA) blockLoc.getBlock().setType(Material.OBSIDIAN);
                    }
                }
            }
        }.runTask(plugin);

        setCooldown(player, iceGlacialCooldown, ICEGLACIAL_COOLDOWN);
    }

    // ----------------- Cooldown Helper Methods -----------------
    private boolean isOnCooldown(Player player, HashMap<UUID, Long> map) {
        UUID uuid = player.getUniqueId();
        if (map.containsKey(uuid)) {
            long timeLeft = map.get(uuid) - System.currentTimeMillis();
            if (timeLeft > 0) {
                player.sendMessage("§cAbility is on cooldown! " + (timeLeft / 1000) + "s remaining.");
                return true;
            }
        }
        return false;
    }

    private void setCooldown(Player player, HashMap<UUID, Long> map, int seconds) {
        map.put(player.getUniqueId(), System.currentTimeMillis() + (seconds * 1000L));
    }
}
