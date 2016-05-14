package me.stalexgaming.dispenserlimiter.utils;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alex on 14-5-2016.
 */
public class Hologram
{

    private Map<Integer, ArmorStand> armorStands;
    private List<String> lines;
    private Location loc;
    private double distance = 0.24;

    public Hologram(List<String> lines, Location loc)
    {
        this.armorStands = new HashMap<>();
        this.lines = lines;
        this.loc = loc.clone().subtract(0, 2, 0);
        update();
    }

    public void update()
    {
        int i = 0;

        for(String s : lines)
        {
            ArmorStand armorStand;
            if(!(i <= armorStands.size()-1))
            {
                armorStand = (ArmorStand) loc.getWorld().spawnEntity(loc.clone().subtract(0, i*distance, 0), EntityType.ARMOR_STAND);
                armorStand.setGravity(false);
                armorStand.setVisible(false);
                armorStand.setCanPickupItems(false);
                armorStand.setRemoveWhenFarAway(false);
                armorStand.setCustomName(s);
                armorStand.setCustomNameVisible(true);
                armorStands.put(i, armorStand);
            } else
            {
                armorStand = armorStands.get(i);
                armorStand.setCustomName(s);
            }
            i++;
        }
        if(i < armorStands.size())
        {
            for(int b = i; b < armorStands.size(); b++)
            {
                armorStands.get(i).remove();
                armorStands.remove(i);
            }
        }
    }

    public void destroy()
    {
        for(ArmorStand armorStand : armorStands.values())
        {
            armorStand.remove();
        }
    }

    public void setLine(int i , String s)
    {
        this.lines.set(i, s);
    }

    public void teleport(Location location)
    {
        this.loc = location;
        update();
    }

    public void removeLine(int i)
    {
        lines.remove(i);
    }

    public void clearLines()
    {
        this.lines = new ArrayList<>();
    }

    public void addLine(String line){
        lines.add(line);
    }

    public void setLines(List<String> lines)
    {
        this.lines = lines;
    }

    public void setDistance(double distance)
    {
        this.distance = distance;
    }

    public List<String> getLines()
    {
        return lines;
    }

    public Location getLocation()
    {
        return loc;
    }

    public double getDistance()
    {
        return distance;
    }

}
