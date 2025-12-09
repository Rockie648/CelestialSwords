// Kurozai → pull everything, including players
public void activateKurozai(Player player) {
    Location loc = player.getLocation();
    player.getWorld().spawnParticle(Particle.PORTAL, loc, 100, 1, 2, 1, 0.1);
    player.getWorld().playSound(loc, Sound.ENTITY_ENDER_DRAGON_FLAP, 1f, 1f);

    for (Entity e : player.getNearbyEntities(10, 10, 10)) {
        // Remove the check for Player, everyone gets pulled
        e.setVelocity(player.getLocation().toVector().subtract(e.getLocation().toVector()).normalize().multiply(2));
    }
}

// Zanpakuto → explosion wave affects everyone
public void activateZanpakuto(Player player) {
    Location loc = player.getLocation();
    player.getWorld().spawnParticle(Particle.FLAME, loc, 50, 2, 2, 2, 0.2);
    player.getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f);

    for (Entity e : player.getNearbyEntities(5, 5, 5)) {
        if (e instanceof LivingEntity) { // Everyone alive, including other players
            ((LivingEntity) e).damage(8, player);
        }
    }
}

// IceGlacial → freeze + slow everyone
public void activateIceGlacial(Player player) {
    Location loc = player.getLocation();
    player.getWorld().spawnParticle(Particle.SNOWBALL, loc, 50, 3, 1, 3, 0.2);
    player.getWorld().playSound(loc, Sound.BLOCK_SNOW_BREAK, 1f, 1f);

    for (Entity e : player.getNearbyEntities(6, 2, 6)) {
        if (e instanceof LivingEntity) {
            ((LivingEntity) e).setFreezeTicks(100);
            ((LivingEntity) e).setVelocity(((LivingEntity) e).getVelocity().multiply(0.1));
        }
    }

    // Freeze ground blocks
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
