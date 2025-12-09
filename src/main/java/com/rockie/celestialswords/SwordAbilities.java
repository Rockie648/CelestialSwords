package com.rockie.celestialswords;

import org.bukkit.Material;
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
    public void onRightClick(PlayerInteractEvent event) {
        // Only handle main hand to avoid duplicate triggers
        if (event.getHand() != EquipmentSlot.HAND) return;

        ItemStack item = event.getItem();
        if (item == null) return;

        // Check sword names
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            String name = item.getItemMeta().getDisplayName();

            switch (name) {
                case "§5Kurozai" -> plugin.activateKurozai(event.getPlayer());
                case "§cZanpakuto of Fire" -> plugin.activateZanpakuto(event.getPlayer());
                case "§bIceGlacial" -> plugin.activateIceGlacial(event.getPlayer());
            }
        }
    }
}
