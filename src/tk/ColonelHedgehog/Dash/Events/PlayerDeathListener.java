/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tk.ColonelHedgehog.Dash.Events;

import tk.ColonelHedgehog.Dash.Core.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import static tk.ColonelHedgehog.Dash.Events.PlayerJoinListener.Prefix;

/**
 *
 * @author Robert
 */
public class PlayerDeathListener implements Listener
{
    public static Main plugin = Main.plugin;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
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
        p.getInventory().clear();

        event.setDeathMessage(Prefix + ChatColor.DARK_RED + event.getDeathMessage() + ".");
    }
}
