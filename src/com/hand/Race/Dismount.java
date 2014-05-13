/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hand.Race;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleExitEvent;

/**
 *
 * @author Robert
 */
public class Dismount implements Listener
{
    public static Main plugin;
    public Dismount(Main ins)
    {
        Leave.plugin = ins;
    }
    
    @EventHandler
    public static void onDismount(VehicleExitEvent event)
    {
        Player p = (Player) event.getExited();
        if(p.getGameMode() == GameMode.CREATIVE)
        {
        
        } 
        else 
        {
            event.setCancelled(true);
        }
        }
}
