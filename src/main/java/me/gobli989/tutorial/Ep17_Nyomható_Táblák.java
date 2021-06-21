package me.gobli989.tutorial;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Ep17_Nyomható_Táblák implements Listener {

    @EventHandler
    public void onSignPlace(SignChangeEvent e) {
        String line0 = e.getLine(0);

        if (line0 != null) {
            if (line0.equalsIgnoreCase("[teleport]")) {
                e.setLine(0, "§5[Teleport]");
                e.getPlayer().sendMessage("§aElkészítettél egy " + e.getLine(0) + " §atáblát.");
            }
            if (line0.equalsIgnoreCase("[gamemode]")) {
                e.setLine(0, "§5[GameMode]");
                e.getPlayer().sendMessage("§aElkészítettél egy " + e.getLine(0) + " §atáblát.");
            }
        }
    }

    @EventHandler
    public void onSignClick(PlayerInteractEvent e) {
        Block b = e.getClickedBlock();
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && b != null) {

            if (!b.getType().name().endsWith("_SIGN")) return;

            Sign sign = (Sign) b.getState();

            String line0 = sign.getLine(0);

            if (line0.equals("§5[Teleport]")) {
                Location loc = new Location(
                        e.getPlayer().getWorld(),
                        Integer.parseInt(sign.getLine(1)) + 0.5,
                        Integer.parseInt(sign.getLine(2)),
                        Integer.parseInt(sign.getLine(3)) + 0.5
                );
                e.getPlayer().teleport(loc);
            } else
            if (line0.equals("§5[GameMode]")) {
                e.getPlayer().setGameMode(
                        GameMode.valueOf(sign.getLine(1))
                );
            }

        }
    }

}
