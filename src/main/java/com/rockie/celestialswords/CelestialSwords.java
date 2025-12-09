package com.rockie.celestialswords;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class CelestialSwords extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Register events
        getServer().getPluginManager().registerEvents(new SwordAbilities(this), this);

        // Add custom recipes
        addRecipes();
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
            } else if (args.length == 2 && args[0].equalsIgnoreCase("get")) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    giveAllSwords(target);
                    sender.sendMessage("§aGiven all swords to " + target.getName());
                } else {
                    sender.sendMessage("§cPlayer not found!");
                }
                return true;
            } else {
                sender.sendMessage("§cUsage: /celestial [get player]");
                return true;
            }
        }
        return false;
    }

    public void giveAllSwords(Player player) {
        player.getInventory().addItem(createKurozai());
        player.getInventory().addItem(createZanpakuto());
        player.getInventory().addItem(createIceGlacial());
        player.sendMessage("§aYou have received all Celestial Swords!");
    }

    // ⚔️ Kurozai
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

    // 🔥 Zanpakuto
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

    // ❄️ IceGlacial
    public ItemStack createIceGlacial() {
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = sword.getItemMeta();
        meta.setDisplayName("§bIceGlacial");
        List<String> lore = new ArrayList<>();
        lore.add("§7Freezes the land and slows mobs!");
        meta.setLore(lore);
        sword.setItemMeta(meta);
        return sword;
    }

    private void addRecipes() {
        // Kurozai Recipe
        ShapedRecipe kurozaiRecipe = new ShapedRecipe(new NamespacedKey(this, "kurozai"), createKurozai());
        kurozaiRecipe.shape("DED", "ES ", " S ");
        kurozaiRecipe.setIngredient('D', Material.DRAGON_EGG);
        kurozaiRecipe.setIngredient('E', Material.NETHERITE_BLOCK);
        kurozaiRecipe.setIngredient('S', Material.STICK);

        getServer().addRecipe(kurozaiRecipe);

        // Zanpakuto Recipe
        ShapedRecipe zanpakutoRecipe = new ShapedRecipe(new NamespacedKey(this, "zanpakuto"), createZanpakuto());
        zanpakutoRecipe.shape("F F", " F ", " S ");
        zanpakutoRecipe.setIngredient('F', Material.BLAZE_POWDER);
        zanpakutoRecipe.setIngredient('S', Material.STICK);
        getServer().addRecipe(zanpakutoRecipe);

        // IceGlacial Recipe
        ShapedRecipe iceRecipe = new ShapedRecipe(new NamespacedKey(this, "iceglacial"), createIceGlacial());
        iceRecipe.shape("S S", " W ", " S ");
        iceRecipe.setIngredient('S', Material.SNOW_BLOCK);
        iceRecipe.setIngredient('W', Material.BLUE_ICE);
        getServer().addRecipe(iceRecipe);
    }
}
