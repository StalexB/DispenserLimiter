package me.stalexgaming.dispenserlimiter.listeners;

import me.stalexgaming.dispenserlimiter.managers.ThrottleManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;

/**
 * Created by alex on 14-5-2016.
 */
public class DispenseEvent implements Listener
{

    ThrottleManager manager;

    public DispenseEvent(ThrottleManager throttleManager){
        this.manager = throttleManager;
    }

    @EventHandler
    public void onDispense(BlockDispenseEvent e)
    {
        if(e.getItem().getType() == Material.TNT)
        {
            if(!manager.isThrottled(e.getBlock().getLocation()))
            {
                manager.setThrottled(e.getBlock().getLocation());
                return;
            }

            e.setCancelled(true);
        }
    }

}
