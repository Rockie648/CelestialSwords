package com.rockie.celestialswords;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class SwordAbilities implements Listener {

    private final CelestialSwords plugin;

    public SwordAbilities(CelestialSwords plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSwordUse(PlayerInteractEvent e) {
        if (e.getHand() != EquipmentSlot.HAND) return;

        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || !item.hasItemMeta()) return;

        String name = item.getItemMeta().getDisplayName();

        switch (name) {
            case "§5Kurozai" -> {
                // Pull nearby entities
                for (Entity ent : player.getNearbyEntities(10, 5, 10)) {
                    if (ent instanceof LivingEntity && ent != player) {
                        Location loc = player.getLocation();
                        ent.setVelocity(loc.toVector().subtract(ent.getLocation().toVector()).normalize().multiply(1.5));
                    }
                }
                // Particle + sound
                player.getWorld().spawnParticle(Particle.PORTAL, player.getLocation(), 50, 1, 2, 1, 0.5);
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 2f, 1f);
            }
            case "§cZanpakuto of Fire" -> {
                player.getWorld().createExplosion(player.getLocation(), 2f, false, false);
                player.getWorld().spawnParticle(Particle.FLAME, player.getLocation(), 30, 1, 1, 1, 0.1);
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1.5f, 1f);
            }
            case "§bIceGlacial" -> {
                for (Entity ent : player.getNearbyEntities(5, 2, 5)) {
                    if (ent instanceof LivingEntity living && living != player) {
                        living.setFreezeTicks(100);
                    }
                }
                player.getWorld().spawnParticle(Particle.SNOWBALL, player.getLocation(), 40, 2, 1, 2, 0.2);
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_SNOW_BREAK, 1.5f, 1f);
            }
        }
    }
}
