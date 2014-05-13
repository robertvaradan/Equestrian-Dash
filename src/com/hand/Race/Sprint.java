/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hand.Race;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSprintEvent;

/**
 *
 * @author Robert
 */
public class Sprint implements Listener
{
    public static Main plugin;
    public Sprint(Main ins)
    {
        Sprint.plugin = ins;
    }
    
    @EventHandler
    public static void onSprint(PlayerToggleSprintEvent event)
    {
        event.setCancelled(true);
        event.getPlayer().sendMessage("§4§oDon't try to cheat... >_>");
        //event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 999999999));
    }
    
}
