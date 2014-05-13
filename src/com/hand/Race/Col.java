/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hand.Race;

import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;

/**
 *
 * @author Robert
 */
public class Col implements Listener
{
    public static Main plugin;
    public Col(Main ins)
    {
        Col.plugin = ins;
    }    
    
    @EventHandler
    public static void onCol(VehicleEntityCollisionEvent event)
    {
        Vehicle horse = event.getVehicle();
        Entity entity = event.getEntity();
        Player player = (Player) horse.getPassenger();
        player.sendMessage("Entity collison!");                
        if(entity instanceof EnderCrystal)
        {
        player.sendMessage("Entity collison!");        
        ItemBox.giveReward(player, entity, entity.getLocation().getBlockX(), entity.getLocation().getBlockY(), entity.getLocation().getBlockZ());
        }
    }
}
