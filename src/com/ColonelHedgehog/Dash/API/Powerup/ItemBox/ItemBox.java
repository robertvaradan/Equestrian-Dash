package com.ColonelHedgehog.Dash.API.Powerup.ItemBox;

import com.ColonelHedgehog.Dash.Core.EquestrianDash;
import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by ColonelHedgehog on 12/26/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class ItemBox
{
    private Location loc;
    private EnderCrystal ec;
    public ItemBox(Location loc, EnderCrystal ec)
    {
        this.loc = loc;
        this.ec = ec;
    }

    public EnderCrystal getEnderCrystal()
    {
        return ec;
    }
    public Location getLocation()
    {
        return loc;
    }

    public boolean despawn()
    {
        if(ec != null)
        {
            ec.remove();
            return true;
        }

        return false;
    }

    public void setLocation(Location loc)
    {
        this.loc = loc;
    }

    public void setEnderCrystal(EnderCrystal ec)
    {
        this.ec = ec;
    }



    public boolean spawn(boolean force)
    {
        //System.out.println("SPAWNING: FORCED? " + force);
        final Location loc = this.loc;
        if(ec != null)
        {
            //System.out.println("EC ISN'T NULL!");
            if(force)
            {
                //System.out.println("FORCED, SO FORCE SPAWNING: " + loc.toString());
                ec.remove();
                ec = (EnderCrystal) loc.getWorld().spawnEntity(loc.add(0.5, 0, 0.5), EntityType.ENDER_CRYSTAL);
                loc.getChunk().load(true);
                return true;
            }
        }
        else
        {
            //System.out.println("SPAWNING AS PER USUAL! " + loc.toString());
            ec = (EnderCrystal) loc.getWorld().spawnEntity(loc.add(0.5, 0, 0.5), EntityType.ENDER_CRYSTAL);
            if(!loc.getChunk().isLoaded())
            {
                loc.getChunk().load();
            }
            return true;
        }

        return false;
    }

    public void respawn()
    {
        final ItemBox ib = this;
        ib.despawn();
        //GarbageControl.RespawningIBs.add(il);


        new BukkitRunnable()
        {

            @Override
            public void run()
            {
                //Start game method

                ib.setLocation(ib.getLocation().subtract(0.5, 0, 0.5));
                ib.spawn(true);
                cancel(); //Cancels the timer
            }

        }.runTaskTimer(EquestrianDash.plugin, 100L /* The amount of time until the timer starts */, 20L /*  The delay of each call */);
    }
}
