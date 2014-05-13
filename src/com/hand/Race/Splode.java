/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hand.Race;

import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Robert
 */
public class Splode implements Listener
{
    public static Main plugin;
    public Splode(Main ins)
    {
        Splode.plugin = ins;
    }    
    
    @EventHandler
    public static void onSplode(EntityDeathEvent event)
    {
        final Entity e = event.getEntity();
        //e.getCombuster().blockList().clear();        
        
       // e.getServer().broadcastMessage("Combusted me!"); 
        
        if(e instanceof EnderCrystal)
        {
                final double nx = e.getLocation().getBlockX() + 0.4;
                final double ny = e.getLocation().getBlockY() - 1;
                final double nz = e.getLocation().getBlockX() + 0.4;
                
    new BukkitRunnable() 
    {
        
        @Override
        public void run() 
        {
          //Start game method
            Location loc = new Location(e.getWorld(), nx, ny, nz);
            e.getWorld().spawnEntity(loc, EntityType.ENDER_CRYSTAL);
            cancel(); //Cancels the timer
        }
      
    }.runTaskTimer(plugin, 100L /* The amount of time until the timer starts */, 20L /*  The delay of each call */);
    

        }    
    }
}

