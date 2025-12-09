package com.rockie.celestialswords;

import org.bukkit.Bukkit;
import org.bukkit.Material;
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
        getLogger().info("Celestial Swords plugin enabled!");
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
            }

            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    giveAllSwords(target);
                    sender.sendMessage("§aGiven all swords to " + target.getName());
                } else {
                    sender.sendMessage("§cPlayer not found!");
                }
                return true;
            }

            sender.sendMessage("§cUsage: /celestial [player]");
            return true;
        }

        return false;
    }

    private void giveAllSwords(Player player) {
        player.getInventory().addItem(createKurozai());
        player.getInventory().addItem(createZanpakuto());
        player.getInventory().addItem(createIceGlacial());
        player.sendMessage("§aYou received all Celestial Swords!");
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
