/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hand.Race;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.HorseJumpEvent;

/**
 *
 * @author Robert
 */
public class Jump implements Listener 
{
    public static Main plugin;
    public Jump(Main ins)
    {
        Jump.plugin = ins;
    }    
    @EventHandler
    public static void onJump(HorseJumpEvent event)
    {
        Player p = (Player) event.getEntity().getPassenger();
        if(p.getGameMode() != GameMode.CREATIVE)
        {
        event.setCancelled(true);
        }
    }
}
