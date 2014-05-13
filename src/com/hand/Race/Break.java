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
import org.bukkit.event.block.BlockBreakEvent;

/**
 *
 * @author Robert
 */
public class Break implements Listener
{
    public static Main plugin;
    public Break(Main ins)
    {
        Break.plugin = ins;
    }    

    @EventHandler
    public static void onBreak(BlockBreakEvent event)
    {
        Player p = event.getPlayer();
        if(p.getGameMode() != GameMode.CREATIVE)
        {
            event.setCancelled(true);
        }
    }

}
