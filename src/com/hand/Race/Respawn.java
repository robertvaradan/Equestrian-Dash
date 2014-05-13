/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hand.Race;

import static com.hand.Race.Join.getHorseColor;
import static com.hand.Race.Join.getHorsePattern;
import org.bukkit.Location;
import org.bukkit.Material;
import static org.bukkit.entity.EntityType.HORSE;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Robert
 */
public class Respawn implements Listener
{
    public static Main plugin;
    public Respawn(Main ins)
    {
        Respawn.plugin = ins;
    }
    
    @EventHandler
    public static void onRespawn(PlayerRespawnEvent event)
    {
        Player p = event.getPlayer();
       Location loc = new Location(p.getWorld(), p.getMetadata("lastLocX").get(0).asDouble(), p.getMetadata("lastLocY").get(0).asDouble(), p.getMetadata("lastLocZ").get(0).asDouble(), p.getMetadata("lastLocPitch").get(0).asFloat(), p.getMetadata("lastLocYaw").get(0).asFloat());
       event.setRespawnLocation(loc);
        Horse horse = (Horse) p.getWorld().spawnCreature(loc, HORSE);
        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
        horse.setOwner(p);
        horse.setPassenger(p);
        getHorseColor(p, horse, p.getMetadata("colorKey").get(0).asInt());
        getHorsePattern(p, horse, p.getMetadata("patternKey").get(0).asInt());
        p.getLocation().setYaw(p.getMetadata("lastLocYaw").get(0).asFloat());
        p.getLocation().setPitch(p.getMetadata("lastLocPitch").get(0).asFloat());
        horse.getLocation().setYaw(p.getMetadata("lastLocYaw").get(0).asFloat());
        horse.getLocation().setPitch(p.getMetadata("lastLocPitch").get(0).asFloat());
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 999999999));        
        
    }
}