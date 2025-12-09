package com.rockie.celestialswords;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class CelestialSwords extends JavaPlugin {

    @Override
    public void onEnable() {
        // register crafting recipes
        registerRecipes();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("celestial.admin")) {
            sender.sendMessage("§cYou must be an operator to use this command!");
            return true;
        }

        if (command.getName().equalsIgnoreCase("celestial")) {
            if (args.length == 0 && sender instanceof Player player) {
                giveAllSwords(player);
                return true;
            } else if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    giveAllSwords(target);
                    sender.sendMessage("§aGiven all swords to " + target.getName());
                } else {
                    sender.sendMessage("§cPlayer not found!");
                }
                return true;
            } else {
                sender.sendMessage("§cUsage: /celestial [player]");
                return true;
            }
        }
        return false;
    }

    private void giveAllSwords(Player player) {
        player.getInventory().addItem(createKurozai(), createZanpakuto(), createIceGlacial());
        player.sendMessage("§aYou received all Celestial Swords!");
    }

    // ⚔️ Kurozai - Dark Vortex
    private ItemStack createKurozai() {
        ItemStack sword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = sword.getItemMeta();
        meta.setDisplayName("§5Kurozai");
        List<String> lore = new ArrayList<>();
        lore.add("§7Pulls everything into a dark vortex!");
        meta.setLore(lore);
        sword.setItemMeta(meta);
        return sword;
    }

    // 🔥 Zanpakuto - Explosive Wave
    private ItemStack createZanpakuto() {
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = sword.getItemMeta();
        meta.setDisplayName("§cZanpakuto of Fire");
        List<String> lore = new ArrayList<>();
        lore.add("§7Unleashes destructive explosions!");
        meta.setLore(lore);
        sword.setItemMeta(meta);
        return sword;
    }

    // ❄️ IceGlacial - Freeze Ground
    private ItemStack createIceGlacial() {
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = sword.getItemMeta();
        meta.setDisplayName("§bIceGlacial");
        List<String> lore = new ArrayList<>();
        lore.add("§7Freezes the land and slows mobs!");
        meta.setLore(lore);
        sword.setItemMeta(meta);
        return sword;
    }

    // -----------------------
    // Sword abilities (trigger via player interact)
    // You need a listener class for right-click events
    // -----------------------
    public void activateKurozai(Player player) {
        player.getWorld().spawnParticle(Particle.PORTAL, player.getLocation(), 100, 1,1,1, 0.5);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 2, 1);

        // Pull nearby entities
        player.getWorld().getNearbyEntities(player.getLocation(), 5,5,5).forEach(e -> {
            e.setVelocity(player.getLocation().toVector().subtract(e.getLocation().toVector()).normalize().multiply(1.5));
        });
    }

    public void activateZanpakuto(Player player) {
        player.getWorld().spawnParticle(Particle.FLAME, player.getLocation(), 200, 2,2,2, 0.5);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2, 1);

        // destructive explosion
        player.getWorld().createExplosion(player.getLocation(), 4F, true, true);
    }

    public void activateIceGlacial(Player player) {
        player.getWorld().spawnParticle(Particle.SNOWBALL, player.getLocation(), 200, 2,2,2, 0.2);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_SNOW_BREAK, 2, 1);

        // Freeze nearby blocks
        player.getWorld().getNearbyEntities(player.getLocation(),5,2,5).forEach(e -> {
            if (e instanceof Player p) {
                p.setFreezeTicks(60); // slow/freeze effect
            }
        });
        player.getWorld().getNearbyBlocks(player.getLocation(),5,1,5).forEach(b -> {
            if (b.getType() == Material.WATER) {
                b.setType(Material.ICE);
                // revert after 10 sec
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.setType(Material.WATER);
                    }
                }.runTaskLater(this, 200L);
            }
        });
    }

    // -----------------------
    // Custom recipes
    // -----------------------
    private void registerRecipes() {
        // Example: Kurozai recipe
        var kurozai = createKurozai();
        var recipe = new org.bukkit.inventory.ShapedRecipe(kurozai);
        recipe.shape("DED"," E "," S ");
        recipe.setIngredient('D', Material.DRAGON_EGG);
        recipe.setIngredient('E', Material.NETHERITE_BLOCK);
        recipe.setIngredient('S', Material.STICK);
        Bukkit.addRecipe(recipe);
    }
}
