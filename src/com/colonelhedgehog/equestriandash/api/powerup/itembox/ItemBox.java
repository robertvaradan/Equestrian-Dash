package com.colonelhedgehog.equestriandash.api.powerup.itembox;

import com.colonelhedgehog.equestriandash.api.track.Track;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntitySpawnEvent;
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
    private EquestrianDash plugin = EquestrianDash.getInstance();

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

    public Track getTrack()
    {
        return plugin.getTrackRegistry().getTrackByWorld(loc.getWorld());
    }
    public void despawn(boolean delayDeletion)
    {
        if (!getLocation().getChunk().isLoaded())
        {
            getLocation().getChunk().load(true);
        }

        if(ec == null)
        {
            return;
        }

        if (!delayDeletion)
        {
            //Bukkit.broadcastMessage("Deleted EC at " + ec.getLocation());
            ec.remove();
        }
        else
        {
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    //plugin.getLogger().info("Delayed deletion, Removed. DONE.");

                    ec.remove();
                }
            }.runTask(plugin);
        }
    }

    public void setLocation(Location loc)
    {
        this.loc = loc;
    }

    public void setEnderCrystal(EnderCrystal ec)
    {
        this.ec = ec;
    }

    public boolean spawn(boolean ignore)
    {
        return spawn();
    }

    public boolean spawn()
    {
        return spawn(0, 0, 0);
    }

    public boolean spawn(double xOffset, double yOffset, double zOffset)
    {
        //System.out.println("SPAWNING: FORCED? " + force);
        final Location loc = this.loc;
        loc.getChunk().load(true);
        if (ec == null || ec.isDead())
        {
            ec = (EnderCrystal) loc.getWorld().spawnEntity(loc.add(xOffset, yOffset, zOffset), EntityType.ENDER_CRYSTAL);
            EntitySpawnEvent event = new EntitySpawnEvent(ec);
            Bukkit.getPluginManager().callEvent(event);
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

                ib.spawn();
                cancel(); //Cancels the timer
            }

        }.runTaskTimer(EquestrianDash.plugin, 100L /* The amount of time until the timer starts */, 20L /*  The delay of each call */);
    }

    public void despawn()
    {
        despawn(false);
    }
}
