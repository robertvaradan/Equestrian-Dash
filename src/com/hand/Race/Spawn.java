/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hand.Race;

import org.bukkit.entity.Blaze;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

/**
 *
 * @author Robert
 */
public class Spawn implements Listener
{
    public static Main plugin;
    public Spawn(Main ins)
    {
        Spawn.plugin = ins;
    }
    @EventHandler
    public static void onSpawn(CreatureSpawnEvent event)
    {
        if(event.getEntity() instanceof Horse || event.getEntity() instanceof Player || event.getEntity() instanceof Firework || event.getEntity() instanceof TNTPrimed || event.getEntity() instanceof Blaze)
        {
            //event.getEntity().getServer().broadcastMessage("§dAnother horse is ready to race!");
        }
        else
        {
            event.getEntity().remove(); // A less graceful method of disabling all other mob-spawns.
        }
    }
}
