/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tk.ColonelHedgehog.Dash.Events;

import tk.ColonelHedgehog.Dash.Core.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Robert
 */
public class EntityExplodeListener implements Listener
{
    public static Main plugin = Main.plugin;

    @EventHandler
    public void onExplode(final EntityExplodeEvent event)
    {
        final Entity e = event.getEntity();
        //e.getServer().broadcastMessage("Exploded me!");
        event.blockList().clear();        
        if(event.getEntity() instanceof EnderCrystal)
        {
            //e.getServer().broadcastMessage("Debug: Explosion!");
            event.setYield(0);
            //e.getServer().broadcastMessage("Explosive yield is now: " + event.getYield());
            event.setCancelled(true); 
            int random = (int )(Math.random() * 6 + 1);
            randomDrop(e.getLocation(), random);
               new BukkitRunnable() 
        {
        
        @Override
        public void run() 
        {
          //Start game method
                double nx = e.getLocation().getBlockX() + 0.25;
                double ny = e.getLocation().getBlockY() - 1;
                double nz = e.getLocation().getBlockZ() + 0.25;

            Location loc = new Location(e.getWorld(), nx, ny, nz);
            event.getLocation().getWorld().spawnEntity(loc, EntityType.ENDER_CRYSTAL);
            cancel(); //Cancels the timer
        }
      
    }.runTaskTimer(plugin, 100L /* The amount of time until the timer starts */, 20L /*  The delay of each call */);

             
        }
        else if(event.getEntity() instanceof WitherSkull)
        {
            WitherSkull w = (WitherSkull) event.getEntity();
            if(w.isCharged())
            {
                w.getWorld().strikeLightning(w.getLocation());
            }
        }
    }


    public static void randomDrop(Location loc, int rand)
    {
        int rand2 = (int )(Math.random() * 3 + 0);
        byte color = 0;
        
        if(rand == 1)
        {
            color = 10;
        }
        else if(rand == 2)
        {
            color = 11;            
        }
        else if(rand == 3)
        {
            color = 5;            
        }
        else if(rand == 4)
        {
            color = 4;            
        }
        else if(rand == 5)
        {
            color = 1;            
        }
        else if(rand == 6)
        {
            color = 14;            
        }
        
         
        
        ItemStack drop = new ItemStack(Material.WOOL, rand2, color);
        if(rand2 == 0){rand2 = 1;}
        
        Location destloc = new Location(loc.getWorld(), loc.getBlockX() + rand2, loc.getBlockY() - rand / rand2, loc.getBlockY() / rand2 + rand * 2);
        
        loc.getWorld().dropItem(destloc, drop);    
    }
}
