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
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import static org.bukkit.entity.EntityType.HORSE;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Robert
 */
public class Damage implements Listener
{
    public static Main plugin;
    public Damage(Main ins)
    {
        Damage.plugin = ins;
    }
    
    @EventHandler
    public static void onDamage(EntityDamageEvent event)
    {
        Entity e = event.getEntity();
        //EntityDamageEvent.DamageCause Cause = event.getCause();
        //plugin.getServer().broadcastMessage("§6Horse detected.");
        
        if(e.getType() == EntityType.PLAYER)
        {
            Player p = (Player) e;
            event.getDamage();
            if((p.getHealth() - event.getDamage()) <= 0)
            {
                event.setCancelled(true);
                if(p.getVehicle() != null)
                {
                    p.getVehicle().remove();
                }
                p.setHealth(p.getMaxHealth());
                Location loc = new Location(p.getWorld(), p.getMetadata("lastLocX").get(0).asDouble(), p.getMetadata("lastLocY").get(0).asDouble(), p.getMetadata("lastLocZ").get(0).asDouble(), p.getMetadata("lastLocPitch").get(0).asFloat(), p.getMetadata("lastLocYaw").get(0).asFloat());
                p.teleport(loc);

                makeHorse(p, loc);
                
            }
        }
        
        if(e.getType() == EntityType.HORSE && Join.racing)
        {
        Horse h = (Horse) e;
        //plugin.getServer().broadcastMessage("§6Horse detected.");

                if(h.getPassenger() instanceof Player)
                {
                Player pl = (Player) h.getPassenger();

                //pl.sendMessage("§6Player detected.");
        
                
                    if(h.getInventory().getArmor() != null)
                    {        
                        if(h.getInventory().getArmor().getType().equals(Material.DIAMOND_BARDING))
                        {
                            //pl.sendMessage("§6Diamond armor detected.");
                            pl.playSound(pl.getLocation(), Sound.ANVIL_LAND, 2, 1 / 2 + 1);
                            h.setFireTicks(0);
                            pl.setFireTicks(0);
                            event.setCancelled(true);
                        }
                    }
                    else
                    {
                    if(h.getInventory().getArmor() != null)
                    {
                        if(h.getInventory().getArmor().getType().equals(Material.IRON_BARDING))
                        {
                            if(event.getCause() == DamageCause.PROJECTILE || event.getCause() == DamageCause.ENTITY_EXPLOSION)
                            {
                                //pl.sendMessage("§6Diamond armor detected.");
                                pl.playSound(pl.getLocation(), Sound.ANVIL_LAND, 2, 1 / 2 + 1);
                                h.setFireTicks(0);
                                pl.setFireTicks(0);
                                event.setCancelled(true);
                            }
                        }
                    }
                    }
                }    
        }
        else if(e.getType() == EntityType.HORSE || e.getType() == EntityType.PLAYER)
        {
            if(!Join.racing)
            {
            //plugin.getServer().broadcastMessage("§6Wasn't a horse! §4" + e.getType() + " §6or §4" + e);
            event.setCancelled(false);
            }
        }
    }


    public static void makeHorse(Player p, Location loc)
    {
        Horse horse = (Horse) p.getWorld().spawnEntity(loc, HORSE);
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
