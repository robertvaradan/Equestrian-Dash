/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ColonelHedgehog.Dash.Events;

import com.ColonelHedgehog.Dash.API.Entity.Racer;
import com.ColonelHedgehog.Dash.API.Powerup.ItemBox.ItemBox;
import com.ColonelHedgehog.Dash.API.Powerup.Powerup;
import com.ColonelHedgehog.Dash.Core.Main;
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
    public static Main plugin = Main.plugin;

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
                    if(Main.getItemBoxRegistry().getByLocation(e.getLocation()) != null)
                    {
                        ItemBox ib = Main.getItemBoxRegistry().getByLocation(e.getLocation());
                        ib.getEnderCrystal().remove();
                        Main.getItemBoxRegistry().deregister(ib, true);
                        p.sendMessage(Main.Prefix + "§aRemoved Item Box.");

                    }
                    else
                    {
                        e.remove();
                        p.sendMessage(Main.Prefix + "§cRemoved Ender Crystal. §eIt was not an Item Box.");
                    }
                }
            }
            ItemBox ib = Main.getItemBoxRegistry().getByLocation(e.getLocation());
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
                    for (Powerup pow : Main.getPowerupsRegistry().getPowerups())
                    {
                        if (pow.getItem().equals(hurter.getItemInHand()))
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
