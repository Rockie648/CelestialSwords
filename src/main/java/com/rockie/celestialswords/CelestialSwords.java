package com.rockie.celestialswords;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class CelestialSwords extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Celestial Swords Plugin Enabled!");
        // You can register events here if your abilities are event-based
        getServer().getPluginManager().registerEvents(new SwordAbilities(), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("celestial.admin")) {
            sender.sendMessage("§cYou must be an operator to use this command!");
            return true;
        }

        if (command.getName().equalsIgnoreCase("celestial")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§cOnly players can receive swords!");
                return true;
            }

            // Give all 3 swords
            player.getInventory().addItem(createKurozai());
            player.getInventory().addItem(createZanpakuto());
            player.getInventory().addItem(createIceGlacial());

            // Play a sound and send message
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
            player.sendMessage("§aYou received all 3 Celestial Swords!");
            return true;
        }

        return false;
    }

    // ⚔️ Kurozai
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

    // 🔥 Zanpakuto of Fire
    private ItemStack createZanpakuto() {
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = sword.getItemMeta();
        meta.setDisplayName("§cZanpakuto of Fire");
        List<String> lore = new ArrayList<>();
        lore.add("§7Unleashes fiery explosions!");
        meta.setLore(lore);
        sword.setItemMeta(meta);
        return sword;
    }

    // ❄️ IceGlacial
    private ItemStack createIceGlacial() {
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = sword.getItemMeta();
        meta.setDisplayName("§bIceGlacial");
        List<String> lore = new ArrayList<>();
        lore.add("§7Freezes the land with icy spikes!");
        meta.setLore(lore);
        sword.setItemMeta(meta);
        return sword;
    }
}
