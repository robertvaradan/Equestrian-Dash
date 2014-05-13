/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hand.Race;

import static org.bukkit.GameMode.CREATIVE;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 *
 * @author Robert
 */
public class Combust implements Listener
{
    public static Main plugin;
    public Combust(Main ins)
    {
        Combust.plugin = ins;
    }
    
    @EventHandler
    public static void onCombust(EntityDamageByEntityEvent event)
    {
        Entity e = event.getEntity();
        //e.getServer().broadcastMessage("Combusted me!"); 
        //e.getServer().broadcastMessage("§6Damage event!");

        if(e instanceof EnderCrystal)
        {
            if(event.getDamager() instanceof Player)
            {
                Player p = (Player) event.getDamager();
            
                if(p.getGameMode() == CREATIVE)
                {
                e.remove();
                }
            }
            if(event.getDamager() instanceof TNTPrimed || event.getDamager() instanceof Arrow)
            {
                event.getDamager().remove();
                event.setCancelled(true);
            }
            event.setCancelled(true);
        }
    }
    
}
