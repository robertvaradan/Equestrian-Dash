/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tk.ColonelHedgehog.Dash.Events;

import org.bukkit.ChatColor;
import tk.ColonelHedgehog.Dash.Core.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author Robert
 */
public class PlayerQuitListener implements Listener
{
    public static Main plugin = Main.plugin;

    @EventHandler
    public void onLeave(PlayerQuitEvent event)
    {
        if(event.getPlayer().getVehicle() != null)
        {
        event.getPlayer().getVehicle().remove();
        }
        event.getPlayer().getInventory().clear();
        PlayerJoinListener.quitexception = true;

        if (!PlayerJoinListener.RaceEnded)
        {
            event.getPlayer().getServer().broadcastMessage(PlayerJoinListener.Prefix + "" + ChatColor.AQUA + "" + event.getPlayer().getName() + " §3is no longer competing.");
        }
    }
}
