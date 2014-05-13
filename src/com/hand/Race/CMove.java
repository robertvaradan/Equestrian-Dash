/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hand.Race;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

/**
 *
 * @author Robert
 */
public class CMove implements Listener
{
    public static Main plugin;
    public CMove(Main ins)
    {
        CMove.plugin = ins;
    }
    
    @EventHandler
    public static void onCMove(VehicleMoveEvent event)
    {

    }
}
