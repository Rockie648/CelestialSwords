package com.rockie.celestialswords;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class SwordAbilities implements Listener {

    private final JavaPlugin plugin;

    public SwordAbilities(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onUse(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand() == null) return;

        String name = player.getInventory().getItemInMainHand().getItemMeta() != null ?
                player.getInventory().getItemInMainHand().getItemMeta().getDisplayName() : "";

        switch (name) {
            case "§5Kurozai" -> useKurozai(player);
            case "§cZanpakuto of Fire" -> useZanpakuto(player);
            case "§bIceGlacial" -> useIceGlacial(player);
        }
    }

    private void useKurozai(Player player) {
        Location loc = player.getLocation();
        for (Entity entity : player.getWorld().getNearbyEntities(loc, 10, 10, 10)) {
            if (entity instanceof LivingEntity && entity != player) {
                Vector direction = loc.toVector().subtract(entity.getLocation().toVector()).normalize().multiply(2);
                entity.setVelocity(direction);
            }
        }
        player.getWorld().spawnParticle(Particle.PORTAL, loc, 100, 1,1,1, 0.2);
        player.getWorld().playSound(loc, Sound.ENTITY_ENDERMAN_TELEPORT, 1,1);
    }

    private void useZanpakuto(Player player) {
        Location loc = player.getLocation();
        player.getWorld().spawnParticle(Particle.FLAME, loc, 50, 2,2,2, 0.3);
        player.getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 1,1);
        for (Entity entity : player.getWorld().getNearbyEntities(loc, 5,5,5)) {
            if (entity instanceof LivingEntity && entity != player) {
                entity.setFireTicks(60);
            }
        }
    }

    private void useIceGlacial(Player player) {
        Location loc = player.getLocation();
        player.getWorld().spawnParticle(Particle.SNOWBALL, loc, 50, 2,2,2, 0.1);
        player.getWorld().playSound(loc, Sound.BLOCK_SNOW_BREAK, 1,1);
        for (Entity entity : player.getWorld().getNearbyEntities(loc, 5,5,5)) {
            if (entity instanceof LivingEntity && entity != player) {
                ((LivingEntity) entity).setFreezeTicks(60);
                entity.setVelocity(new Vector(0,0,0));
            }
        }
    }
}
