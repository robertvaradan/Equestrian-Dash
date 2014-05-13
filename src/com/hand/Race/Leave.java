/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hand.Race;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author Robert
 */
public class Leave implements Listener
{
    public static Main plugin;
    public Leave(Main ins)
    {
        Leave.plugin = ins;
    }
    
    @EventHandler
    public static void onLeave(PlayerQuitEvent event)
    {
        if(event.getPlayer().getVehicle() != null)
        {
        event.getPlayer().getVehicle().remove();
        }
        event.getPlayer().getInventory().clear();
        Join.quitexception = true;
    }
}
