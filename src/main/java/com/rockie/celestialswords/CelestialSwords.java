package com.rockie.celestialswords;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class CelestialSwords extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new SwordAbilities(this), this);
        registerRecipes();
    }

    // ===== Sword Creation =====
    public ItemStack createKurozai() {
        ItemStack sword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = sword.getItemMeta();
        meta.setDisplayName("§5Kurozai");
        List<String> lore = new ArrayList<>();
        lore.add("§7Pulls everything into a dark vortex!");
        meta.setLore(lore);
        sword.setItemMeta(meta);
        return sword;
    }

    public ItemStack createZanpakuto() {
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = sword.getItemMeta();
        meta.setDisplayName("§cZanpakuto of Fire");
        List<String> lore = new ArrayList<>();
        lore.add("§7Unleashes fiery explosions!");
        meta.setLore(lore);
        sword.setItemMeta(meta);
        return sword;
    }

    public ItemStack createIceGlacial() {
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = sword.getItemMeta();
        meta.setDisplayName("§bIceGlacial");
        List<String> lore = new ArrayList<>();
        lore.add("§7Freezes the land with icy spikes!");
        meta.setLore(lore);
        sword.setItemMeta(meta);
        return sword;
    }

    // ===== Sword Abilities =====
    public void activateKurozai(Player player) {
        Location loc = player.getLocation();
        player.getWorld().spawnParticle(Particle.PORTAL, loc, 100, 1, 2, 1, 0.1);
        player.getWorld().playSound(loc, Sound.ENTITY_ENDER_DRAGON_FLAP, 1f, 1f);

        for (Entity e : player.getNearbyEntities(10, 10, 10)) {
            if (e instanceof LivingEntity && !(e instanceof Player)) {
                e.setVelocity(player.getLocation().toVector().subtract(e.getLocation().toVector()).normalize().multiply(2));
            }
        }
    }

    public void activateZanpakuto(Player player) {
        Location loc = player.getLocation();
        player.getWorld().spawnParticle(Particle.FLAME, loc, 50, 2, 2, 2, 0.2);
        player.getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f);

        for (Entity e : player.getNearbyEntities(5, 5, 5)) {
            if (e instanceof LivingEntity && !(e instanceof Player)) {
                ((LivingEntity) e).damage(8, player);
            }
        }
    }

    public void activateIceGlacial(Player player) {
        Location loc = player.getLocation();
        player.getWorld().spawnParticle(Particle.SNOWBALL, loc, 50, 3, 1, 3, 0.2);
        player.getWorld().playSound(loc, Sound.BLOCK_SNOW_BREAK, 1f, 1f);

        for (Entity e : player.getNearbyEntities(6, 2, 6)) {
            if (e instanceof LivingEntity && !(e instanceof Player)) {
                ((LivingEntity) e).setFreezeTicks(100); // Freeze mobs for 5 seconds
                ((LivingEntity) e).setVelocity(((LivingEntity) e).getVelocity().multiply(0.1)); // slow
            }
        }

        // Freeze ground blocks destructively (turn water to ice, lava to obsidian)
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
        }.runTask(this);
    }

    // ===== Give Swords =====
    public void giveAllSwords(Player player) {
        player.getInventory().addItem(createKurozai(), createZanpakuto(), createIceGlacial());
    }

    // ===== Recipes Placeholder =====
    private void registerRecipes() {
        // You can add custom recipes here later
    }
}
