package com.rockie.celestialswords;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.NamespacedKey;

import java.util.ArrayList;
import java.util.List;

public class CelestialSwords extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("Celestial Swords Enabled!");
        getServer().getPluginManager().registerEvents(this, this);
        addCustomRecipes();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // OP-only
        if (!sender.isOp()) {
            sender.sendMessage("§cOnly operators can use this command!");
            return true;
        }

        if (command.getName().equalsIgnoreCase("celestial")) {
            if (args.length == 1 && args[0].equalsIgnoreCase("getall")) {
                if (sender instanceof Player player) {
                    giveAllSwords(player);
                    sender.sendMessage("§aAll Celestial Swords added to your inventory!");
                } else {
                    sender.sendMessage("§cOnly players can run this command!");
                }
                return true;
            } else if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    giveAllSwords(target);
                    sender.sendMessage("§aAll Celestial Swords given to " + target.getName());
                } else {
                    sender.sendMessage("§cPlayer not found!");
                }
                return true;
            } else {
                sender.sendMessage("§cUsage: /celestial getall OR /celestial give <player>");
                return true;
            }
        }
        return false;
    }

    private void giveAllSwords(Player player) {
        player.getInventory().addItem(createKurozai(), createZanpakuto(), createIceGlacial());
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
        List<String
