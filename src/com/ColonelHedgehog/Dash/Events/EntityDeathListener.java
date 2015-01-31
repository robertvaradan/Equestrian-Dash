/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ColonelHedgehog.Dash.Events;

import com.ColonelHedgehog.Dash.API.Powerup.ItemBox.ItemBox;
import com.ColonelHedgehog.Dash.Core.EquestrianDash;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 * @author Robert
 */
public class EntityDeathListener implements Listener
{
    public static EquestrianDash plugin = EquestrianDash.plugin;

    @EventHandler
    public void onDeath(EntityDeathEvent event)
    {
        event.getDrops().clear();
        final Entity e = event.getEntity();
        ItemBox ib = EquestrianDash.getItemBoxRegistry().getByLocation(e.getLocation());
        /*if (event.getEntity() instanceof Horse)
        {
            if (event.getEntity().getPassenger() != null && event.getEntity().getPassenger() instanceof Player)
            {
                Player p = (Player) event.getEntity().getPassenger();
                p.damage(1000);
            }
            Horse h = (Horse) event.getEntity();
            h.getInventory().clear();
        }
        else */if (ib != null)
        {
            ib.respawn();
        }
    }
}
