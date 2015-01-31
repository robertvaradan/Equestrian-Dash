package com.ColonelHedgehog.Dash.Events;

import com.ColonelHedgehog.Dash.API.Entity.Racer;
import com.ColonelHedgehog.Dash.API.Powerup.Powerup;
import com.ColonelHedgehog.Dash.Core.EquestrianDash;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.UUID;

/**
 * Created by ColonelHedgehog on 11/10/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class PlayerDropItemListener implements Listener
{
    private EquestrianDash plugin = EquestrianDash.plugin;

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event)
    {
        Racer racer = new Racer(event.getPlayer());
        for (Powerup powerup : EquestrianDash.getPowerupsRegistry().getPowerups())
        {
            if (powerup.getItem().getType() == event.getItemDrop().getItemStack().getType() && powerup.getItem().getDurability() == event.getItemDrop().getItemStack().getDurability() && !racer.inventoryIsSpinning())
            {
                powerup.doOnDrop(new Racer(event.getPlayer()), event.getItemDrop());
                if (powerup.cancelledEvents().contains(Powerup.ActionType.DROP) || powerup.cancelledEvents().contains(Powerup.ActionType.ALL))
                {
                    event.setCancelled(true);
                }
                else
                {
                    event.getItemDrop().setMetadata("whoDropped", new FixedMetadataValue(plugin, event.getPlayer().getUniqueId().toString()));
                }
                return;
            }
            else if (racer.inventoryIsSpinning())
            {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event)
    {
        Racer racer = new Racer(event.getPlayer());
        for (Powerup powerup : EquestrianDash.getPowerupsRegistry().getPowerups())
        {
            if (powerup.getItem().getType() == event.getItem().getItemStack().getType() && powerup.getItem().getDurability() == event.getItem().getItemStack().getDurability() && !racer.inventoryIsSpinning())
            {
                try
                {
                    powerup.doOnPickup(new Racer(event.getPlayer()), new Racer(Bukkit.getPlayer(UUID.fromString(event.getItem().getMetadata("whoDropped").toString()))), event.getItem());
                    if (powerup.cancelledEvents().contains(Powerup.ActionType.PICKUP) || powerup.cancelledEvents().contains(Powerup.ActionType.ALL))
                    {
                        event.setCancelled(true);
                    }
                }
                catch (IllegalArgumentException e)
                {
                    // Nuffin, muffin!
                }

                return;
            }
        }
    }
}