/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hand.Race;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

/**
 *
 * @author Robert
 */
public class Damage implements Listener
{
    public static Main plugin;
    public Damage(Main ins)
    {
        Damage.plugin = ins;
    }
    
    @EventHandler
    public static void onDamage(EntityDamageEvent event)
    {
        Entity e = event.getEntity();
        //EntityDamageEvent.DamageCause Cause = event.getCause();
        //plugin.getServer().broadcastMessage("§6Horse detected.");
        
        if(e.getType() == EntityType.HORSE && !Join.racing)
        {
        Horse h = (Horse) e;
        //plugin.getServer().broadcastMessage("§6Horse detected.");

                if(h.getPassenger() instanceof Player)
                {
                Player pl = (Player) h.getPassenger();

                //pl.sendMessage("§6Player detected.");
        
                
                    if(h.getInventory().getArmor() != null && h.getInventory().getArmor().getType().equals(Material.DIAMOND_BARDING))
                    {
                        //pl.sendMessage("§6Diamond armor detected.");
                        pl.playSound(pl.getLocation(), Sound.ANVIL_LAND, 2, 1 / 2 + 1);
                        h.setFireTicks(0);
                        pl.setFireTicks(0);
                        event.setCancelled(true);
                    }
                    else
                    {
                    if(h.getInventory().getArmor().getType().equals(Material.IRON_BARDING))
                    {
                        if(event.getCause() == DamageCause.PROJECTILE || event.getCause() == DamageCause.ENTITY_EXPLOSION)
                        {
                        //pl.sendMessage("§6Diamond armor detected.");
                        pl.playSound(pl.getLocation(), Sound.ANVIL_LAND, 2, 1 / 2 + 1);
                        h.setFireTicks(0);
                        pl.setFireTicks(0);
                        event.setCancelled(true);
                        }
                    }
                    }
                }    
        }
        else
        {
        //plugin.getServer().broadcastMessage("§6Wasn't a horse! §4" + e.getType() + " §6or §4" + e);
        event.setCancelled(false);
        }
    }
}
