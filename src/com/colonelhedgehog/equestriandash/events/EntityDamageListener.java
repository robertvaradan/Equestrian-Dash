/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.colonelhedgehog.equestriandash.events;

import com.colonelhedgehog.equestriandash.assets.handlers.GameHandler;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * @author Robert
 */
public class EntityDamageListener implements Listener
{
    private EquestrianDash plugin = EquestrianDash.plugin;

    @EventHandler
    public void onDamage(EntityDamageEvent event)
    {
        Entity e = event.getEntity();

        if(event.getCause() == EntityDamageEvent.DamageCause.FALL)
        {
            event.setCancelled(true);
            return;
        }

        if (e instanceof EnderCrystal)
        {
            event.setCancelled(true);
        }

        if(e instanceof Player)
        {
            Player p = (Player) e;
            if ((p.getHealth() - event.getFinalDamage()) <= 0)
            {
                plugin.getRacerHandler().respawnPlayer(p);
                event.setCancelled(true);
            }
        }

        GameHandler.GameState gameState = plugin.getGameHandler().getGameState();
        if (gameState != GameHandler.GameState.RACE_IN_PROGRESS)
        {
            event.setCancelled(true);
        }
    }
}
