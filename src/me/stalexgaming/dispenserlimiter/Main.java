package me.stalexgaming.dispenserlimiter;

import me.stalexgaming.dispenserlimiter.listeners.DispenseEvent;
import me.stalexgaming.dispenserlimiter.managers.ThrottleManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by alex on 14-5-2016.
 */
public class Main extends JavaPlugin
{

    private static Main instance;
    private static ThrottleManager throttleManager;

    public void onEnable()
    {
        instance = this;
        throttleManager = new ThrottleManager(this);

        saveDefaultConfig();

        registerListeners();
    }

    public void onDisable()
    {
        instance = null;
    }

    public static Main getInstance()
    {
        return instance;
    }

    public static ThrottleManager getThrottleManager()
    {
        return throttleManager;
    }

    private void registerListeners()
    {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new DispenseEvent(throttleManager), this);
    }

}
