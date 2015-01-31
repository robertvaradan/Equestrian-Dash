package com.ColonelHedgehog.Dash.Assets;

import com.ColonelHedgehog.Dash.Core.EquestrianDash;
import com.ColonelHedgehog.Dash.Events.EntityDamageListener;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by ColonelHedgehog on 1/30/15.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class Respawner
{
    public static void respawn(final Player p)
    {
        if(p.getVehicle() != null && p.getVehicle() instanceof Horse)
        {
            Horse h = (Horse) p.getVehicle();
            h.setPassenger(null); // Why you alwiz ridin' on me for every thing you need?
            h.setOwner(null); // Y'ALL DON'T OWN ME!
            h.remove(); // And the moral of this story is you'll die without the help you need. cri cri
        }
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 999999999));
        p.getWorld().playSound(p.getLocation(), Sound.WITHER_SPAWN, 3, 1);

        if (p.getVehicle() != null)
        {
            p.getVehicle().remove();
        }
        p.setHealth(p.getMaxHealth());
        Location loc = new Location(p.getWorld(), p.getMetadata("lastLocX").get(0).asDouble(), p.getMetadata("lastLocY").get(0).asDouble(), p.getMetadata("lastLocZ").get(0).asDouble(), p.getMetadata("lastLocPitch").get(0).asFloat(), p.getMetadata("lastLocYaw").get(0).asFloat());
        p.teleport(loc);

        final Horse h = EntityDamageListener.makeHorse(p, loc);
        h.setOwner(p);
        h.setPassenger(p);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 999999999));
        new BukkitRunnable()
        {

            @Override
            public void run()
            {
                h.setPassenger(p);
                h.setOwner(p);
            }
        }.runTaskLater(EquestrianDash.plugin, 60);
    }

}
