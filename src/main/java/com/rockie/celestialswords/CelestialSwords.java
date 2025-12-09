package com.rockie.celestialswords;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class CelestialSwords extends JavaPlugin {

    private SwordAbilities abilities;

    @Override
    public void onEnable() {
        abilities = new SwordAbilities(this);
        getServer().getPluginManager().registerEvents(abilities, this);
        createRecipes();
        getLogger().info("§aCelestialSwords enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("§cCelestialSwords disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
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

    public void giveAllSwords(Player player) {
        player.getInventory().addItem(createKurozai());
        player.getInventory().addItem(createZanpakuto());
        player.getInventory().addItem(createIceGlacial());
        player.sendMessage("§aYou received all Celestial Swords!");
    }

    // ⚔️ Swords
    public ItemStack createKurozai() {
        ItemStack sword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = sword.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§5Kurozai");
            List<String> lore = new ArrayList<>();
            lore.add("§7Pulls everything into a dark vortex!");
            meta.setLore(lore);
            sword.setItemMeta(meta);
        }
        return sword;
    }

    public ItemStack createZanpakuto() {
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = sword.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§cZanpakuto of Fire");
            List<String> lore = new ArrayList<>();
            lore.add("§7Unleashes fiery explosions!");
            meta.setLore(lore);
            sword.setItemMeta(meta);
        }
        return sword;
    }

    public ItemStack createIceGlacial() {
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = sword.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§bIceGlacial");
            List<String> lore = new ArrayList<>();
            lore.add("§7Freezes the land with icy spikes!");
            meta.setLore(lore);
            sword.setItemMeta(meta);
        }
        return sword;
    }

    private void createRecipes() {
        NamespacedKey key = new NamespacedKey(this, "kurozai");
        ShapedRecipe recipe = new ShapedRecipe(key, createKurozai());
        recipe.shape(" D ", " N ", " S ");
        recipe.setIngredient('D', Material.DRAGON_EGG);
        recipe.setIngredient('N', Material.NETHERITE_BLOCK);
        recipe.setIngredient('S', Material.STICK);
        Bukkit.addRecipe(recipe);

        // Optional: Add recipes for other swords too
    }
}
