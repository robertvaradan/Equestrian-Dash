/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hand.Race;

import static com.hand.Race.Join.Prefix;
import static com.hand.Race.Join.RaceEnded;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author Robert
 */
public class Login implements Listener 
{
    public static Main plugin;
    public Login(Main ins)
    {
        Login.plugin = ins;
    }
    
    public static void onLogin(PlayerQuitEvent event)
    {
    if(!RaceEnded)
    {
    event.getPlayer().getServer().broadcastMessage(Prefix + "" + ChatColor.AQUA + "" + event.getPlayer().getDisplayName() + " §3is no longer competing.");
    }
    }
}
