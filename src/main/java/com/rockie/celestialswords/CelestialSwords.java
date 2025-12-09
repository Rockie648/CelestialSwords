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
        getLogger().info("Celestial Swords plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Celestial Swords plugin has been disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Permission check
        if (!sender.hasPermission("celestial.admin")) {
            sender.sendMessage("§cYou do not have permission to use this command!");
            return true;
        }

        if (command.getName().equalsIgnoreCase("celestial")) {

            // Self-give: /celestial
            if (args.length == 0) {
                if (sender instanceof Player player) {
                    giveAllSwords(player);
                    sender.sendMessage("§aYou received all Ce
