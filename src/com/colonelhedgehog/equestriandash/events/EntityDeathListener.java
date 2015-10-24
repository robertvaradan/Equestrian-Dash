/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.colonelhedgehog.equestriandash.events;

import com.colonelhedgehog.equestriandash.api.powerup.itembox.ItemBox;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
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

        if(e instanceof Horse)
        {
            if(e.getPassenger() != null)
            {
                if(e.getPassenger() instanceof Player)
                {
                    Player p = (Player) e.getPassenger();
                    plugin.getRacerHandler().respawnPlayer(p);
                    return;
                }
            }
        }

        if(e.getPassenger() != null && e.getPassenger() instanceof Item && e.hasMetadata("MarkerLocation"))
        {
            e.getPassenger().remove();
        }

        ItemBox ib = plugin.getItemBoxRegistry().getByLocation(e.getLocation());
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
        else */

        if (ib != null && ib.getEnderCrystal().isDead())
        {
            ib.respawn();
        }
    }
}
