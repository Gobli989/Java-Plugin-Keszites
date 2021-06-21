package me.gobli989.tutorial;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.TileEntitySkull;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.libs.org.apache.commons.codec.binary.Base64;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;
import java.util.UUID;

public class Ep16_Karacsony implements Listener {

    Random r = new Random();

    public static void mikulasSapka() {
        new BukkitRunnable() {
            @Override
            public void run() {

                for (Player player : Bukkit.getOnlinePlayers()) {
                    Location loc = player.getEyeLocation().add(0, 0.3, 0);
                    World w = loc.getWorld();

                    for (int i = 0; i < 12; i++) {
                        double d = Math.toRadians(360 / 12d * i);
                        w.spawnParticle(Particle.REDSTONE, loc.clone().add(0.25 * Math.cos(d), 0.1, 0.25 * Math.sin(d)), 0, 0, 0, 0, new Particle.DustOptions(Color.RED, 1));
                        w.spawnParticle(Particle.REDSTONE, loc.clone().add(0.16 * Math.cos(d), 0.2, 0.16 * Math.sin(d)), 0, 0, 0, 0, new Particle.DustOptions(Color.RED, 1));
                        w.spawnParticle(Particle.REDSTONE, loc.clone().add(0.07 * Math.cos(d), 0.3, 0.07 * Math.sin(d)), 0, 0, 0, 0, new Particle.DustOptions(Color.RED, 1));
                        w.spawnParticle(Particle.REDSTONE, loc.clone().add(0.07 * Math.cos(d), 0.2, 0.07 * Math.sin(d)), 0, 0, 0, 0, new Particle.DustOptions(Color.RED, 1));
                        w.spawnParticle(Particle.REDSTONE, loc.clone().add(0.35 * Math.cos(d), 0, 0.35 * Math.sin(d)), 0, 0, 0, 0, new Particle.DustOptions(Color.WHITE, 1));
                    }
                    for (int i = 0; i < 5; i++) {
                        w.spawnParticle(Particle.REDSTONE, loc.clone().add((Math.random() - 0.5) / 10, 0.65, (Math.random() - 0.5) / 10), 0, 0, 0, 0, 0, new Particle.DustOptions(Color.WHITE, 1));
                    }

                    w.spawnParticle(Particle.REDSTONE, loc, 0, 1, 0, 0, 1, new Particle.DustOptions(Color.WHITE, 1));

                }

            }
        }.runTaskTimer(MainClass.getPlugin(MainClass.class), 0, 1);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {

        if (e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.PLAYER_HEAD) {
            Material mat = Material.values()[r.nextInt(Material.values().length)];

            e.getClickedBlock().setType(Material.AIR);
            e.getClickedBlock().getWorld().dropItemNaturally(e.getClickedBlock().getLocation().add(0.5, 0.5, 0.5), new ItemStack(mat));
            e.getClickedBlock().getWorld().spawnParticle(Particle.FALLING_DUST, e.getClickedBlock().getLocation(), 50, 0.5, 0.5, 0.5, 0, Bukkit.createBlockData(Material.RED_CONCRETE));
            e.getClickedBlock().getWorld().spawnParticle(Particle.FALLING_DUST, e.getClickedBlock().getLocation(), 50, 0.5, 0.5, 0.5, 0, Bukkit.createBlockData(Material.SNOW_BLOCK));
        }

        if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.STICK) {

            Horse horse = (Horse) e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(), EntityType.HORSE);
            horse.setStyle(Horse.Style.NONE);
            horse.setColor(Horse.Color.CHESTNUT);
            horse.setInvulnerable(true);

            horse.setVelocity(e.getPlayer().getLocation().getDirection().multiply(2));

            new BukkitRunnable() {
                @Override
                public void run() {
                    horse.getWorld().spawnParticle(Particle.FALLING_DUST, horse.getLocation(), 300, 2, 2, 2, 0, Bukkit.createBlockData(Material.RED_CONCRETE));
                    horse.getWorld().spawnParticle(Particle.FALLING_DUST, horse.getLocation(), 300, 2, 2, 2, 0, Bukkit.createBlockData(Material.SNOW_BLOCK));
                    horse.getWorld().createExplosion(horse.getLocation(), 2);

                    for (int i = 0; i < 10; i++) {
                        FallingBlock fb = horse.getWorld().spawnFallingBlock(horse.getLocation(), Bukkit.createBlockData(Material.RED_WOOL));
                        fb.setDropItem(false);
                        fb.setVelocity(new Vector(
                                r.nextBoolean() ? r.nextDouble() : -r.nextDouble(),
                                r.nextDouble(),
                                r.nextBoolean() ? r.nextDouble() : -r.nextDouble()
                        ));
                    }

                    horse.remove();
                }
            }.runTaskLater(MainClass.getPlugin(MainClass.class), 20);
        }
    }

    @EventHandler
    public void onFall(EntityChangeBlockEvent e) {
        if (e.getEntityType() == EntityType.FALLING_BLOCK) {
            FallingBlock fb = (FallingBlock) e.getEntity();

            if (fb.getBlockData().getMaterial().name().endsWith("WOOL")) {
                e.setCancelled(true);
                fb.remove();
                e.getBlock().setType(Material.PLAYER_HEAD);

                Block b = e.getBlock();

                GameProfile randomProfile =
                        new GameProfile[]{
                                getProfile("http://textures.minecraft.net/texture/6fd452870d493718eb63647ad80e00f50b774601cb067775f90fc1eaada8fcef"),
                                getProfile("http://textures.minecraft.net/texture/cb3c17b2ddec2b726d717a8b5a7d68b7772fbd9c09a3ff4bd2a412d24ccd491c"),
                                getProfile("http://textures.minecraft.net/texture/f73b8769b461e865cb85d0e05a348cc412ec701b8fca99dd5d464c9e27f9b440")
                        }[r.nextInt(3)];

                TileEntitySkull skull = (TileEntitySkull) ((CraftWorld) b.getWorld()).getHandle().getTileEntity(new BlockPosition(b.getX(), b.getY(), b.getZ()));
                if (skull == null) return;
                skull.setGameProfile(randomProfile);
                b.getState().update(true);
            }
        }
    }

    private GameProfile getProfile(String url) {
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] data = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(data)));
        return profile;
    }

}
