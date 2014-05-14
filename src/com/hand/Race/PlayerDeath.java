/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hand.Race;

import static com.hand.Race.Join.Prefix;
import org.bukkit.ChatColor;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 *
 * @author Robert
 */
public class PlayerDeath implements Listener
{
    public static Main plugin;
    public PlayerDeath(Main ins)
    {
        PlayerDeath.plugin = ins;
    }
    
    @EventHandler
    public static void onPlayerDeath(PlayerDeathEvent event)
    {
        Player p = (Player) event.getEntity(); 
        if(p.getVehicle() != null)
        {
            if(p.getVehicle() instanceof Horse)
            {
                Horse h = (Horse) p.getVehicle();
                h.setHealth(0.0);
            }
        }

        event.setDeathMessage(Prefix + ChatColor.DARK_RED + event.getDeathMessage() + ".");
    }
}
