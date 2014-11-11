/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tk.ColonelHedgehog.Dash.Events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tk.ColonelHedgehog.Dash.Core.Main;

import static org.bukkit.entity.EntityType.HORSE;

/**
 *
 * @author Robert
 */
public class EntityDamageListener implements Listener
{
    public static Main plugin = Main.plugin;

    @EventHandler
    public void onDamage(EntityDamageEvent event)
    {
        Entity e = event.getEntity();

        if(e instanceof EnderCrystal)
        {
            event.setCancelled(true);
        }
        //EntityDamageEvent.DamageCause Cause = event.getCause();
        //plugin.getServer().broadcastMessage("§6Horse detected.");
        
        if(e.getType() == EntityType.PLAYER)
        {
            if (event.getCause() == DamageCause.FALL)
            {
                event.setCancelled(true);

            }
            else
            {
                Player p = (Player) e;
                event.getDamage();
                if ((p.getHealth() - event.getDamage()) <= 0)
                {
                    event.setCancelled(true);
                    if (p.getVehicle() != null)
                    {
                        p.getVehicle().remove();
                    }
                    p.setHealth(p.getMaxHealth());
                    Location loc = new Location(p.getWorld(), p.getMetadata("lastLocX").get(0).asDouble(), p.getMetadata("lastLocY").get(0).asDouble(), p.getMetadata("lastLocZ").get(0).asDouble(), p.getMetadata("lastLocPitch").get(0).asFloat(), p.getMetadata("lastLocYaw").get(0).asFloat());
                    p.teleport(loc);

                    makeHorse(p, loc);


                }
            }
        }
        
        if(e.getType() == EntityType.HORSE && PlayerJoinListener.racing)
        {
        Horse h = (Horse) e;
        //plugin.getServer().broadcastMessage("§6Horse detected.");

            if(h.getPassenger() != null && h.getPassenger() instanceof Player)
            {
                Player pl = (Player) h.getPassenger();

                //pl.sendMessage(Main.Prefix + "§6Player detected.");

                if(event.getCause() == DamageCause.FALL)
                {
                    event.setCancelled(true);
                    return;
                }
                if (h.getInventory().getArmor() != null)
                {
                    if (h.getInventory().getArmor().getType().equals(Material.DIAMOND_BARDING))
                    {
                        //pl.sendMessage(Main.Prefix + "§6Diamond armor detected.");
                        pl.playSound(pl.getLocation(), Sound.ANVIL_LAND, 2, 1 / 2 + 1);
                        h.setFireTicks(0);
                        pl.setFireTicks(0);
                        event.setCancelled(true);
                    }
                    else if (h.getInventory().getArmor().getType().equals(Material.IRON_BARDING))
                    {
                        if (event.getCause() == DamageCause.PROJECTILE || event.getCause() == DamageCause.ENTITY_EXPLOSION)
                        {
                            //pl.sendMessage(Main.Prefix + "§6Diamond armor detected.");
                            pl.playSound(pl.getLocation(), Sound.ANVIL_LAND, 2, 1 / 2 + 1);
                            h.setFireTicks(0);
                            pl.setFireTicks(0);
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
        else if(e.getType() == EntityType.HORSE || e.getType() == EntityType.PLAYER)
        {
            if(!PlayerJoinListener.racing)
            {
            //plugin.getServer().broadcastMessage("§6Wasn't a horse! §4" + e.getType() + " §6or §4" + e);
            event.setCancelled(true);
            }
        }
    }


    public static void makeHorse(Player p, Location loc)
    {
        Horse horse = (Horse) p.getWorld().spawnEntity(loc, HORSE);
        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
        horse.setOwner(p);
        horse.setPassenger(p);
        PlayerJoinListener.getHorseColor(p, horse, p.getMetadata("colorKey").get(0).asInt());
        PlayerJoinListener.getHorsePattern(p, horse, p.getMetadata("patternKey").get(0).asInt());
        p.getLocation().setYaw(p.getMetadata("lastLocYaw").get(0).asFloat());
        p.getLocation().setPitch(p.getMetadata("lastLocPitch").get(0).asFloat());
        horse.getLocation().setYaw(p.getMetadata("lastLocYaw").get(0).asFloat());
        horse.getLocation().setPitch(p.getMetadata("lastLocPitch").get(0).asFloat());
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 999999999));        
        
    }
}
