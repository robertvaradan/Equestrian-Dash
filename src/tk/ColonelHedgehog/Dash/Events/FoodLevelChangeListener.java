/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tk.ColonelHedgehog.Dash.Events;

import org.bukkit.event.EventHandler;
import tk.ColonelHedgehog.Dash.Core.Main;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 *
 * @author Robert
 */
public class FoodLevelChangeListener implements Listener
{
    public static Main plugin = Main.plugin;

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event)
    {
        event.setCancelled(true);
    }
}
