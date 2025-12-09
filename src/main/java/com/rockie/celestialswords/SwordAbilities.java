package com.rockie.celestialswords;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class SwordAbilities implements Listener {

    private final CelestialSwords plugin;

    // Cooldowns in milliseconds
    private final HashMap<UUID, Long> kurozaiCooldown = new HashMap<>();
    private final HashMap<UUID, Long> zanpakutoCooldown = new HashMap<>();
    private final HashMap<UUID, Long> iceGlacialCooldown = new HashMap<>();

    public SwordAbilities(CelestialSwords plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item == null || item.getType() == Material.AIR) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasDisplayName()) return;

        String name = meta.getDisplayName();
        Action action = event.getAction();

        // Right click
        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {

            if (name.equals("§5Kurozai")) {
                if (isOnCooldown(player, kurozaiCooldown, 5000)) return;
                useKurozai(player);
                setCooldown(player, kurozaiCooldown);
            }

            else if (name.equals("§cZanpakuto of Fire")) {
                if (isOnCooldown(player, zanpakutoCooldown, 5000)) return;
                useZanpakuto(player);
                setCooldown(player, zanpakutoCooldown);
            }

            else if (name.equals("§bIceGlacial")) {
                if (isOnCooldown(player, iceGlacialCooldown, 5000)) return;
                useIceGlacial(player);
                setCooldown(player, iceGlacialCooldown);
            }
        }
    }

    private boolean isOnCooldown(Player player, HashMap<UUID, Long> map, long cooldown) {
        UUID id = player.getUniqueId();
        if (map.containsKey(id)) {
            long lastUse = map.get(id);
            if (System.currentTimeMillis() - lastUse < cooldown) {
                long remaining = (cooldown - (System.currentTimeMillis() - lastUse)) / 1000;
                player.sendMessage("§cAbility on cooldown! Wait " + remaining + "s.");
                return true;
            }
        }
        return false;
    }

    private void setCooldown(Player player, HashMap<UUID, Long> map) {
        map.put(player.getUniqueId(), System.currentTimeMillis());
    }

    // ⚡ Kurozai: pulls nearby entities into a vortex
    private void useKurozai(Player player) {
        Location loc = player.getLocation();
        double radius = 5;
        player.getWorld().playSound(loc, Sound.ENTITY_ENDER_DRAGON_FLAP, 1f, 1f);
        player.getWorld().spawnParticle(Particle.PORTAL, loc, 100, 2, 2, 2, 0.5);

        for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
            if (entity.equals(player)) continue;
            Vector direction = loc.toVector().subtract(entity.getLocation().toVector()).normalize().multiply(1.5);
            entity.setVelocity(direction);
        }

        player.sendMessage("§5Kurozai activated!");
    }

    // 🔥 Zanpakuto: launches a fireball explosion
    private void useZanpakuto(Player player) {
        Location loc = player.getLocation().add(player.getLocation().getDirection());
        player.getWorld().spawnParticle(Particle.FLAME, loc, 50, 1, 1, 1, 0.2);
        player.getWorld().playSound(loc, Sound.ENTITY_BLAZE_SHOOT, 1f, 1f);

        for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
            if (entity.equals(player)) continue;
            entity.setFireTicks(60);
            entity.setVelocity(entity.getVelocity().add(new Vector(0, 0.5, 0)));
        }

        player.sendMessage("§cZanpakuto unleashed!");
    }

    // ❄ IceGlacial: freezes nearby entities with ice spikes
    private void useIceGlacial(Player player) {
        Location loc = player.getLocation();
        player.getWorld().spawnParticle(Particle.SNOWBALL, loc, 100, 2, 2, 2, 0.5);
        player.getWorld().playSound(loc, Sound.BLOCK_GLASS_BREAK, 1f, 1f);

        for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
            if (entity.equals(player)) continue;
            entity.setVelocity(new Vector(0, 0, 0));
            if (entity instanceof Player target) {
                target.sendMessage("§bYou are frozen by IceGlacial!");
            }
        }

        player.sendMessage("§bIceGlacial activated!");
    }
}
