/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.colonelhedgehog.equestriandash.events;

import com.colonelhedgehog.equestriandash.api.powerup.Powerup;
import com.colonelhedgehog.equestriandash.api.powerup.itembox.ItemBox;
import com.colonelhedgehog.equestriandash.api.powerup.itembox.ItemBoxRegistry;
import com.colonelhedgehog.equestriandash.api.track.Marker;
import com.colonelhedgehog.equestriandash.api.track.MarkerHandler;
import com.colonelhedgehog.equestriandash.api.track.Track;
import com.colonelhedgehog.equestriandash.assets.handlers.RacerHandler;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import com.colonelhedgehog.equestriandash.utilities.LocationUtils;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static org.bukkit.GameMode.CREATIVE;

/**
 * @author Robert
 */
public class EntityDamageByEntityListener implements Listener
{
    public static EquestrianDash plugin = EquestrianDash.plugin;

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event)
    {
        Entity e = event.getEntity();
        ItemBoxRegistry boxRegistry = plugin.getItemBoxRegistry();
        //e.getServer().broadcastMessage("Combusted me!"); 
        //e.getServer().broadcastMessage("§6Damage event!");

        if (e instanceof EnderCrystal)
        {
            if (event.getDamager() instanceof Player)
            {
                Player p = (Player) event.getDamager();

                if (p.getGameMode() == CREATIVE)
                {
                    if (plugin.getItemBoxRegistry().getByLocation(e.getLocation()) != null)
                    {
                        ItemBox ib = boxRegistry.getByLocation(e.getLocation());
                        ib.getEnderCrystal().remove();
                        boxRegistry.deregister(true, ib);
                        p.sendMessage(EquestrianDash.Prefix + "§aRemoved Item Box.");

                    }
                    else
                    {
                        e.remove();
                        p.sendMessage(EquestrianDash.Prefix + "§cRemoved Ender Crystal. §eIt was not an Item Box.");
                    }
                }
            }

            ItemBox ib = boxRegistry.getByLocation(e.getLocation());
            if (ib != null)
            {
                ib.respawn();
            }
        }
        else if (e instanceof Player)
        {
            Player victim = (Player) event.getEntity();

            if(victim.hasMetadata("finished") && victim.getMetadata("finished").get(0).asBoolean())
            {
                event.setCancelled(true);
                return;
            }

            RacerHandler racerHandler = plugin.getRacerHandler();
            if (event.getDamager() instanceof Slime)
            {
                event.setCancelled((event.getDamager().hasMetadata("Creator") && event.getDamager().getMetadata("Creator").get(0).asString().equals(event.getEntity().getName())));
            }
            else if (event.getDamager() instanceof Player)
            {
                Player hurter = (Player) event.getDamager();

                if(hurter.hasMetadata("finished") && hurter.getMetadata("finished").get(0).asBoolean())
                {
                    event.setCancelled(true);
                    return;
                }

                if (hurter.getItemInHand() != null)
                {
                    for (Powerup pow : plugin.getPowerupsRegistry().getPowerups())
                    {
                        if (pow.getItem().getType() == hurter.getItemInHand().getType() && pow.getItem().getDurability() == hurter.getItemInHand().getDurability())
                        {
                            pow.doOnLeftClickRacer(racerHandler.getRacer(hurter), racerHandler.getRacer(victim));
                            if (pow.cancelledEvents().contains(Powerup.ActionType.ALL) || pow.cancelledEvents().contains(Powerup.ActionType.LEFT_CLICK_ENTITY))
                            {
                                event.setCancelled(true);
                            }
                            return;
                        }
                    }
                }
            }
        }
        else if (e instanceof ArmorStand)
        {
            if (event.getDamager() instanceof Player)
            {
                ArmorStand victim = (ArmorStand) event.getEntity();
                Player hurter = (Player) event.getDamager();

                if (hurter.getGameMode() == GameMode.CREATIVE)
                {
                    Location loc = victim.getLocation();
                    if(!victim.hasMetadata("MarkerLocation"))
                    {
                        return;
                    }

                    String id = victim.getMetadata("MarkerLocation").get(0).asString();

                    Track track = plugin.getTrackRegistry().getTrackByWorld(hurter.getWorld());
                    MarkerHandler markerHandler = track.getMarkerHandler();
                    Marker marker = markerHandler.getMarkerAtLocation(LocationUtils.deserialize(id));

                    if(marker != null)
                    {
                        int number = marker.getNumber();

                        //marker.setVisualized(false);
                        marker.getLocation().getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 100);
                        marker.delete();

                        hurter.sendMessage(EquestrianDash.Prefix + "§aMarker #§e" + number + " §awas removed.");
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}

