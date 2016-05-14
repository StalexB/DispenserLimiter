package me.stalexgaming.dispenserlimiter.managers;

import me.stalexgaming.dispenserlimiter.Main;
import me.stalexgaming.dispenserlimiter.utils.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Created by alex on 14-5-2016.
 */
public class ThrottleManager
{

    Main plugin;

    private int throttleTime;
    private int chars;
    private List<Location> throttledDispensers;

    public ThrottleManager(Main main)
    {
        this.plugin = main;
        this.throttledDispensers = new ArrayList<>();
        this.throttleTime = plugin.getConfig().getInt("throttle_time");
        this.chars = plugin.getConfig().getInt("chars");
    }


    public boolean isThrottled(Location loc)
    {
        return throttledDispensers.contains(loc);
    }

    public void setThrottled(Location loc)
    {
        throttledDispensers.add(loc);
        Hologram hologram = new Hologram(Arrays.asList("", ""), loc.clone().add(0.5, 2, 0.5));
        new BukkitRunnable()
        {
            int i = throttleTime*20;
            @Override
            public void run()
            {
                if(loc.getBlock().getType() == Material.DISPENSER) {
                    hologram.setLine(0, color("&a" + (i/20) + "s"));
                    hologram.setLine(1, color(getTimeLeft(i, throttleTime*20)));
                    hologram.update();
                    if (i <= 0) {
                        hologram.destroy();
                        throttledDispensers.remove(loc);
                        this.cancel();
                    }
                    i--;
                } else {
                    throttledDispensers.remove(loc);
                    hologram.destroy();
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    public int getThrottleTime()
    {
        return throttleTime;
    }

    private String getTimeLeft(double ticks, double maxTicks)
    {
        StringBuilder sb = new StringBuilder();
        double done = ((ticks / maxTicks) * chars);
        for(int i = 0; i < chars; i++){
            if(done-1 >= i)
            {
                sb.append(color("&a|"));
            } else {
                sb.append(color("&c|"));
            }
        }
        return sb.toString();
    }

    private String color(String s)
    {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
