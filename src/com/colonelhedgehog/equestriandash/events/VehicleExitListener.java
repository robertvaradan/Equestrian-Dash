/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.colonelhedgehog.equestriandash.events;

import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleExitEvent;

/**
 * @author Robert
 */
public class VehicleExitListener implements Listener
{
    public static EquestrianDash plugin = EquestrianDash.plugin;

    @EventHandler
    public void onDismount(VehicleExitEvent event)
    {
        Player p = (Player) event.getExited();

        if (p.getGameMode() != GameMode.CREATIVE)
        {
            event.setCancelled(true);
        }
    }
}
