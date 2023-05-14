package fr.clem28l.HomePlugin;

import fr.clem28l.HomePlugin.Cmd.HomeCmd;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        HomeCmd.loadHomes();
        System.out.println("§aLe Plugin Home est demarre");
        getCommand("sethome").setExecutor(new HomeCmd());
        getCommand("home").setExecutor(new HomeCmd());
        getCommand("delhome").setExecutor(new HomeCmd());
        getCommand("back").setExecutor(new HomeCmd());
    }

    @Override
    public void onDisable() {
        System.out.println("Le Plugin Home est arreté");
    }
}
