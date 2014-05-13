/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hand.Race;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;

/**
 *
 * @author Robert
 */
public class Stop implements Listener
{
    public static Main plugin;
    public Stop(Main ins)
    {
        Stop.plugin = ins;
    }
    
    @EventHandler
    public static void onStop(PluginDisableEvent event)
    {
        for(Player p : Bukkit.getOnlinePlayers())
        {
            if(p.getVehicle() != null)
            {
                p.getVehicle().remove();
            }
        }
    }
}
