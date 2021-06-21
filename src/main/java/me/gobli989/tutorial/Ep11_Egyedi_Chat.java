package me.gobli989.tutorial;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Ep11_Egyedi_Chat implements Listener {

    String format = "&7&o({locale}) &e{name} &7» &f{msg}";

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();

        String low = e.getMessage().toLowerCase();

        e.setMessage(
                StringUtils.capitalize(low
                        .replace("süti", "****")
                        .replace("saláta", "******"))
        );

        e.setFormat(ChatColor.translateAlternateColorCodes('&',
                format
                .replace("{locale}", player.getLocale())
                .replace("{name}", player.getDisplayName())
                .replace("{msg}", e.getMessage())
        ));
    }

}
