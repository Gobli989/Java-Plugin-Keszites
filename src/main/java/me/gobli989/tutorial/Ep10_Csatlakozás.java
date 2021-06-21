
// A tutorialokat meg lehet nézni a Youtube csatornámon részletes magyarázással.
// https://youtube.com/Gobligaming989

package me.gobli989.tutorial;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Ep10_Csatlakozás implements Listener {

    private String prefix = "§b§lTUTORIAL > ";

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        // Ellenőrzi, hogy a játékos játszott-e már a szerveren
        if(player.hasPlayedBefore())
            Bukkit.broadcastMessage(prefix + player.getDisplayName() + "§f elősször csatlakozott a szerverhez!");

        // Beállítja a csatlakozási üzenetet
        e.setJoinMessage(player.getDisplayName() + "§7 jött játszani velünk!");

        // Elküld egy üzenetet csak a felcsatlakozott játékos számára
        player.sendMessage(prefix + "§fÜdvözöllek téged, " + player.getDisplayName() + "§f!");

        // Kitörli a játékos inventoryját és utána a középső eszköztár slotba berakja a TÖK ItemStacket.
        player.getInventory().clear();
        player.getInventory().setItem(4, TÖK(player));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        // Ellenőrzi, hogy a a TÖK ItemStackel nyomott-e
        if(e.getItem().isSimilar(TÖK(e.getPlayer()))) {
            e.getPlayer().sendMessage("Rányomtál a tökre!");
            e.setCancelled(true);
        }
    }

    @EventHandler // Letiltja a kidobást
    public void onDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }
    @EventHandler // Letiltja a tárgyak mozgatását az inventoryban
    public void onMove(InventoryMoveItemEvent e) {
        e.setCancelled(true);
    }
    @EventHandler // Letiltja a tárgyakra való klikkelést az inventoryban
    public void onClick(InventoryClickEvent e) {
        e.setCancelled(true);
    }

    // Létrehozza a TÖK ItemStacket
    private ItemStack TÖK(Player player) {
        ItemStack is = new ItemStack(Material.JACK_O_LANTERN);
        ItemMeta im = is.getItemMeta();

        im.setDisplayName("§eÜdvözöllek téged, §6§l" + player.getDisplayName() + "§e!");
        im.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        is.setItemMeta(im);
        return is;
    }

}
