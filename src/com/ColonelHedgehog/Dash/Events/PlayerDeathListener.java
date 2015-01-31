/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ColonelHedgehog.Dash.Events;

import com.ColonelHedgehog.Dash.Core.EquestrianDash;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * @author Robert
 */
public class PlayerDeathListener implements Listener
{
    public static EquestrianDash plugin = EquestrianDash.plugin;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        /*event.getDrops().clear();
        event.setDroppedExp(0);
        Player p = event.getEntity();
        if (p.getVehicle() != null)
        {
            if (p.getVehicle() instanceof Horse)
            {
                Horse h = (Horse) p.getVehicle();
                h.setOwner(null);
                h.setPassenger(null);
                h.setHealth(0.0);
            }
        }
        p.getInventory().clear();

        event.setDeathMessage(PlayerJoinListener.Prefix + ChatColor.RED + event.getDeathMessage() + ".");

        if (plugin.getServer().getPluginManager().getPlugin("ProtocolLib") != null)
        {
            forceRespawn(event.getEntity());
        }*/

        //Bukkit.broadcastMessage("This should not have happened, lol.");
    }
}
