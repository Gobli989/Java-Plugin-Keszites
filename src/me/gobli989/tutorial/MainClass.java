package me.gobli989.tutorial;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class MainClass extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
//        getServer().getPluginManager().registerEvents(new Ep10_Csatlakozás(), this);
//        getServer().getPluginManager().registerEvents(new Ep11_Egyedi_Chat(), this);
//        getServer().getPluginManager().registerEvents(new Ep12_Runnablek(), this);
//        getServer().getPluginManager().registerEvents(new Ep13_Csomagok(), this);
//        getServer().getPluginManager().registerEvents(new Ep14_Tüzijátékok(), this);
//        getServer().getPluginManager().registerEvents(new Ep15_Halloween(), this);

        getServer().getPluginManager().registerEvents(new Ep16_Karacsony(), this);
        Ep16_Karacsony.mikulasSapka();

//        getCommand("halloween").setExecutor(new Ep15_Halloween());
//        Ep15_Halloween.start();

    }

}
