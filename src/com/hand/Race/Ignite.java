/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hand.Race;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;

/**
 *
 * @author Robert
 */
public class Ignite implements Listener
{
    public static Main plugin;
    public Ignite(Main ins)
    {
        Ignite.plugin = ins;
    }
    
    @EventHandler
    public static void onIgnite(BlockIgniteEvent event)
    {    
        event.setCancelled(true);
    }
}