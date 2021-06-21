package me.gobli989.tutorial;

import com.mojang.datafixers.util.Pair;
import net.minecraft.server.v1_16_R3.EnumItemSlot;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityEquipment;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.Main;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Pumpkin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.Random;

public class Ep15_Halloween implements Listener, CommandExecutor {

    private static final Random r = new Random();
    private static final MainClass m = MainClass.getPlugin(MainClass.class);

    public static void start() {
        new BukkitRunnable() {
            @Override
            public void run() {

                // Tökfej
//                int index = r.nextInt(Bukkit.getOnlinePlayers().size());
//                Player player = (Player) Bukkit.getOnlinePlayers().toArray()[index];
//                tokFej(player);

            }
        }.runTaskTimer(m, 0, 200);

        new BukkitRunnable() {
            @Override
            public void run() {
                for (String key : m.getConfig().getConfigurationSection("Pumpkins").getKeys(false)) {
                    String[] s = key.split("\\|");
                    Block b = Bukkit.getWorld(s[0]).getBlockAt(Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]));
                    int i = r.nextInt(2);

                    if (i == 0) {
                        b.setType(Material.JACK_O_LANTERN);

//                        if (r.nextInt(5) == 0) {
//                            Bat bat = (Bat) b.getWorld().spawnEntity(b.getLocation(), EntityType.BAT);
//
//                            new BukkitRunnable() {
//                                @Override
//                                public void run() {
//                                    bat.remove();
//                                    bat.getWorld().spawnParticle(Particle.SMOKE_NORMAL, bat.getLocation(), 5, 0.1, 0.1, 0.1, 1);
//                                }
//                            }.runTaskLater(m, 200);
//                        }
                    } else b.setType(Material.CARVED_PUMPKIN);

                    Directional dir = (Directional) b.getBlockData();
                    dir.setFacing(BlockFace.valueOf(m.getConfig().getString("Pumpkins." + key)));
                    b.setBlockData(dir);
                }
            }
        }.runTaskTimer(m, 0, 5);
    }

    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent e) {
        Entity target = e.getEntity();
        Entity damager = e.getDamager();

        if (damager instanceof Player || damager instanceof Projectile) {

            if (target instanceof Bat) {
                LivingEntity witch = (LivingEntity) target.getWorld().spawnEntity(target.getLocation(), EntityType.WITCH);
                witch.setHealth(((Bat) target).getHealth());
                target.getWorld().spawnParticle(Particle.SMOKE_NORMAL, target.getLocation(), 20, 1, 1, 1, 0.1);
                target.remove();
            }
            if (target instanceof Witch) {
                LivingEntity bat = (LivingEntity) target.getWorld().spawnEntity(target.getLocation(), EntityType.BAT);
                bat.setMaxHealth(((Witch) target).getMaxHealth());
                bat.setHealth(((Witch) target).getHealth());
                target.getWorld().spawnParticle(Particle.SMOKE_NORMAL, target.getLocation(), 20, 1, 1, 1, 0.1);
                target.remove();
            }

        }

    }

    @EventHandler(ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent e) {
        Block b = e.getBlock();
        if (b.getType() == Material.CARVED_PUMPKIN || b.getType() == Material.JACK_O_LANTERN) {

            Directional dir = (Directional) b.getBlockData();

            m.getConfig().set("Pumpkins." + b.getWorld().getName() + "|" + b.getX() + "|" + b.getY() + "|" + b.getZ(), dir.getFacing().name());
            m.saveConfig();

        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlock(BlockBreakEvent e) {
        Block b = e.getBlock();
        if (b.getType() == Material.JACK_O_LANTERN || b.getType() == Material.CARVED_PUMPKIN) {
            if (m.getConfig().contains("Pumpkins." + b.getWorld().getName() + "|" + b.getX() + "|" + b.getY() + "|" + b.getZ())) {
                m.getConfig().set("Pumpkins." + b.getWorld().getName() + "|" + b.getX() + "|" + b.getY() + "|" + b.getZ(), null);
                m.saveConfig();

                b.getWorld().dropItemNaturally(b.getLocation().add(0.5, 0, 0.5), toklampas());
                e.setCancelled(true);
                b.setType(Material.AIR);
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage("§6/h fej §7§oFejedre rak egy tököt");
            return true;
        }

        if (args[0].equalsIgnoreCase("fej")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cEgy játékosnak kell lenned.");
                return true;
            }

            Player player = (Player) sender;
            tokFej(player);

        }

        return false;
    }

    public static void tokFej(Player player) {
        ItemStack is = new ItemStack(Material.CARVED_PUMPKIN);

        PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(
                player.getEntityId(),
                Collections.singletonList(
                        new Pair<>(EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(is))
                )
        );

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public static ItemStack toklampas() {
        ItemStack is = new ItemStack(Material.JACK_O_LANTERN);
        is.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.addUnsafeEnchantment(Enchantment.IMPALING, 1);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§6Töklámpás");
        is.setItemMeta(im);
        return is;
    }

}
