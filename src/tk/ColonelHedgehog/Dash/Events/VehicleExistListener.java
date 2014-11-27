/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tk.ColonelHedgehog.Dash.Events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleExitEvent;
import tk.ColonelHedgehog.Dash.Core.Main;

/**
 * @author Robert
 */
public class VehicleExistListener implements Listener
{
    public static Main plugin = Main.plugin;

    @EventHandler
    public void onDismount(VehicleExitEvent event)
    {
        Player p = (Player) event.getExited();
        if (p.getGameMode() == GameMode.CREATIVE)
        {

        }
        else
        {
            event.setCancelled(true);
        }
    }
}
