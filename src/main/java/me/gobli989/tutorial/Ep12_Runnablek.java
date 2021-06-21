package me.gobli989.tutorial;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Ep12_Runnablek implements Listener {

    private MainClass m = MainClass.getPlugin(MainClass.class);

    @EventHandler
    public void onBlockBreak(BlockPlaceEvent e) {

        new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                e.getBlock().setType(Material.RED_WOOL);

                if(i == 60) {
                    // Lejárt 3 másodperc (20*3 = 60 tick)
                    cancel();
                }
            }
        }.runTaskTimer(m, 0, 1);

    }

}
