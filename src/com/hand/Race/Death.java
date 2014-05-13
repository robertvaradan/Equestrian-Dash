/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hand.Race;

import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 *
 * @author Robert
 */
public class Death implements Listener
{
    public static Main plugin;
    public Death(Main ins)
    {
        Death.plugin = ins;
    }
    
    @EventHandler
    public static void onDeath(EntityDeathEvent event)
    {
        if(event.getEntity() instanceof Horse)
        {
            if(event.getEntity().getPassenger() instanceof Player)
            {
            Player p = (Player) event.getEntity().getPassenger();
            p.setHealth(0.0);
            }
        }
    }
}
