package com.colonelhedgehog.equestriandash.events;

import com.colonelhedgehog.equestriandash.api.entity.Racer;
import com.colonelhedgehog.equestriandash.api.powerup.Powerup;
import com.colonelhedgehog.equestriandash.assets.handlers.RacerHandler;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
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
    private EquestrianDash plugin = EquestrianDash.getInstance();

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event)
    {
        RacerHandler racerHandler = plugin.getRacerHandler();
        Racer racer = racerHandler.getRacer(event.getPlayer());
        for (Powerup powerup : plugin.getPowerupsRegistry().getPowerups())
        {
            if (powerup.getItem().getType() == event.getItemDrop().getItemStack().getType() && powerup.getItem().getDurability() == event.getItemDrop().getItemStack().getDurability() && !racer.inventoryIsSpinning())
            {
                powerup.doOnDrop(racerHandler.getRacer(event.getPlayer()), event.getItemDrop());
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
        if(event.getPlayer().hasMetadata("finished") && event.getPlayer().getMetadata("finished").get(0).asBoolean())
        {
            event.setCancelled(true);
            return;
        }

        if(event.getItem().hasMetadata("noPickup"))
        {
            event.setCancelled(true);
        }
        RacerHandler racerHandler = plugin.getRacerHandler();

        Racer racer = racerHandler.getRacer(event.getPlayer());
        for (Powerup powerup : plugin.getPowerupsRegistry().getPowerups())
        {
            if (powerup.getItem().getType() == event.getItem().getItemStack().getType() && powerup.getItem().getDurability() == event.getItem().getItemStack().getDurability() && !racer.inventoryIsSpinning())
            {
                try
                {
                    powerup.doOnPickup(racerHandler.getRacer(event.getPlayer()), racerHandler.getRacer(Bukkit.getPlayer(UUID.fromString(event.getItem().getMetadata("whoDropped").toString()))), event.getItem());
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