/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ColonelHedgehog.Dash.Events;

import com.ColonelHedgehog.Dash.API.Entity.Racer;
import com.ColonelHedgehog.Dash.API.Powerup.ItemBox.ItemBox;
import com.ColonelHedgehog.Dash.API.Powerup.Powerup;
import com.ColonelHedgehog.Dash.Core.EquestrianDash;
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
    public void onCombust(EntityDamageByEntityEvent event)
    {
        Entity e = event.getEntity();
        //e.getServer().broadcastMessage("Combusted me!"); 
        //e.getServer().broadcastMessage("§6Damage event!");

        if (e instanceof EnderCrystal)
        {
            if (event.getDamager() instanceof Player)
            {
                Player p = (Player) event.getDamager();

                if (p.getGameMode() == CREATIVE)
                {
                    if(EquestrianDash.getItemBoxRegistry().getByLocation(e.getLocation()) != null)
                    {
                        ItemBox ib = EquestrianDash.getItemBoxRegistry().getByLocation(e.getLocation());
                        ib.getEnderCrystal().remove();
                        EquestrianDash.getItemBoxRegistry().deregister(ib, true);
                        p.sendMessage(EquestrianDash.Prefix + "§aRemoved Item Box.");

                    }
                    else
                    {
                        e.remove();
                        p.sendMessage(EquestrianDash.Prefix + "§cRemoved Ender Crystal. §eIt was not an Item Box.");
                    }
                }
            }
            ItemBox ib = EquestrianDash.getItemBoxRegistry().getByLocation(e.getLocation());
            if (ib != null)
            {
                ib.respawn();
            }
        }
        else if (e instanceof Player)
        {
            if (event.getDamager() instanceof Slime)
            {
                event.setCancelled((event.getDamager().hasMetadata("Creator") && event.getDamager().getMetadata("Creator").get(0).asString().equals(((Player) event.getEntity()).getName())));
            }
            else if (event.getDamager() instanceof Player)
            {
                Player victim = (Player) event.getEntity();
                Player hurter = (Player) event.getDamager();

                if (hurter.getItemInHand() != null)
                {
                    for (Powerup pow : EquestrianDash.getPowerupsRegistry().getPowerups())
                    {
                        if (pow.getItem().getType() == hurter.getItemInHand().getType() && pow.getItem().getDurability() == hurter.getItemInHand().getDurability())
                        {
                            pow.doOnLeftClickRacer(new Racer(hurter), new Racer(victim));
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
    }

}
