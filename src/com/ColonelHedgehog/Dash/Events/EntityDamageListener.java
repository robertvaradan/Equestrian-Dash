/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ColonelHedgehog.Dash.Events;

import com.ColonelHedgehog.Dash.Assets.Respawner;
import com.ColonelHedgehog.Dash.Core.EquestrianDash;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.entity.EntityType.HORSE;

/**
 * @author Robert
 */
public class EntityDamageListener implements Listener
{
    public static EquestrianDash plugin = EquestrianDash.plugin;

    public static Horse makeHorse(Player p, Location loc)
    {
        Horse horse = (Horse) p.getWorld().spawnEntity(loc, HORSE);
        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
        PlayerJoinListener.getHorseColor(p, horse, p.getMetadata("colorKey").get(0).asInt());
        PlayerJoinListener.getHorsePattern(p, horse, p.getMetadata("patternKey").get(0).asInt());
        p.getLocation().setYaw(p.getMetadata("lastLocYaw").get(0).asFloat());
        p.getLocation().setPitch(p.getMetadata("lastLocPitch").get(0).asFloat());
        horse.getLocation().setYaw(p.getMetadata("lastLocYaw").get(0).asFloat());
        horse.getLocation().setPitch(p.getMetadata("lastLocPitch").get(0).asFloat());
        return horse;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event)
    {
        Entity e = event.getEntity();

        if (e instanceof EnderCrystal)
        {
            event.setCancelled(true);
        }

        if (e.getType() == EntityType.PLAYER)
        {
            if (event.getCause() == DamageCause.FALL)
            {
                event.setCancelled(true);
            }
            else
            {
                Player p = (Player) e;
                p.sendMessage("YOU HURTN', MANE!");
                if ((p.getHealth() - event.getFinalDamage()) <= 0)
                {
                    Respawner.respawn(p);
                    event.setCancelled(true);
                }
            }
        }

        if (e.getType() == EntityType.HORSE && PlayerJoinListener.racing)
        {
            Horse h = (Horse) e;
            //plugin.getServer().broadcastMessage("§6Horse detected.");

            if (h.getPassenger() != null && h.getPassenger() instanceof Player)
            {
                if (event.getCause() == DamageCause.FALL)
                {
                    event.setCancelled(true);
                }
            }
        }
        else if (e.getType() == EntityType.HORSE || e.getType() == EntityType.PLAYER)
        {
            if (!PlayerJoinListener.racing)
            {
                event.setCancelled(true);
            }
        }
    }
}
