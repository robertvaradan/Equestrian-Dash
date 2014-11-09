/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tk.ColonelHedgehog.Dash.Events;

import tk.ColonelHedgehog.Dash.Core.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

/**
 *
 * @author Robert
 */
public class CreatureSpawnListener implements Listener
{
    public static Main plugin = Main.plugin;

    @EventHandler
    public void onSpawn(final CreatureSpawnEvent event)
    {
        if(event.getEntity() instanceof Horse || event.getEntity() instanceof Player || event.getEntity() instanceof Firework || event.getEntity() instanceof TNTPrimed || event.getEntity() instanceof Slime)
        {
            //event.getEntity().getServer().broadcastMessage("§dAnother horse is ready to race!");
            if(event.getEntity() instanceof Slime)
            {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("EquestrianDash"), new Runnable()
                {
                    @Override
                    public void run()
                    {
                        event.getEntity().remove();
                        event.getLocation().getWorld().createExplosion(event.getLocation(), (float) 0.0);
                    }
                }, 100L);
            }
        }
        else
        {
            event.getEntity().remove(); // A less graceful method of disabling all other mob-spawns.
        }
    }
}
