/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tk.ColonelHedgehog.Dash.Events;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import tk.ColonelHedgehog.Dash.Core.GarbageControl;
import tk.ColonelHedgehog.Dash.Core.Main;

/**
 * @author Robert
 */
public class EntityDeathListener implements Listener
{
    public static Main plugin = Main.plugin;

    @EventHandler
    public void onDeath(EntityDeathEvent event)
    {
        event.getDrops().clear();
        final Entity e = event.getEntity();
        if (event.getEntity() instanceof Horse)
        {
            if (event.getEntity().getPassenger() != null && event.getEntity().getPassenger() instanceof Player)
            {
                Player p = (Player) event.getEntity().getPassenger();
                p.setHealth(0.0);
            }
            Horse h = (Horse) event.getEntity();
            h.getInventory().clear();
        }
        else if (e instanceof EnderCrystal)
        {
            final double nx = e.getLocation().getBlockX() + 0.4;
            final double ny = e.getLocation().getBlockY() - 1;
            final double nz = e.getLocation().getBlockX() + 0.4;
            e.getWorld().playEffect(e.getLocation(), Effect.STEP_SOUND, 20);

            GarbageControl.RespawningIBs.add(new Location(e.getWorld(), nx, ny, nz));
            new BukkitRunnable()
            {

                @Override
                public void run()
                {
                    //Start game method
                    Location loc = new Location(e.getWorld(), nx, ny, nz);
                    GarbageControl.RespawningIBs.remove(new Location(e.getWorld(), nx, ny, nz));
                    e.getWorld().spawnEntity(loc, EntityType.ENDER_CRYSTAL);
                    cancel(); //Cancels the timer
                }

            }.runTaskTimer(plugin, 100L /* The amount of time until the timer starts */, 20L /*  The delay of each call */);


        }
    }
}
