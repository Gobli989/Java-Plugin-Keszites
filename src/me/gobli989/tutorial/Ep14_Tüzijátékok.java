package me.gobli989.tutorial;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.List;

public class Ep14_Tüzijátékok implements Listener {

    private final List<Firework> fireworkList = new ArrayList<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        Firework fw = (Firework) e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(), EntityType.FIREWORK);
        FireworkMeta meta = fw.getFireworkMeta();

        meta.addEffects(
                FireworkEffect.builder().withColor(Color.PURPLE).with(FireworkEffect.Type.BALL_LARGE).build(),
                FireworkEffect.builder().with(FireworkEffect.Type.CREEPER).withColor(Color.FUCHSIA).build()
        );

        fw.setFireworkMeta(meta);
        fireworkList.add(fw);
        fw.detonate();

    }

    @EventHandler
    public void onFireworkDamage(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Firework) {
            if(fireworkList.contains((Firework) e.getDamager())) {
                fireworkList.remove((Firework) e.getDamager());
                e.setCancelled(true);
            }
        }
    }

}
