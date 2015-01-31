package com.ColonelHedgehog.Dash.Core;

import com.ColonelHedgehog.Dash.API.Powerup.ItemBox.ItemBox;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ColonelHedgehog on 11/13/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class GarbageControl
{
    public static List<Location> DespawningIce = new ArrayList<>();

    public static void destroyGarbage()
    {
        int i = 0;
        for(Location loc : DespawningIce)
        {
            loc.getBlock().setType(Material.AIR);
            i++;
        }

        EquestrianDash.plugin.getLogger().info("Destroying " + i + " undeleted Ice Powerup blocks.");

        for(World w : Bukkit.getWorlds())
        {
            for (ItemBox ib : EquestrianDash.getItemBoxRegistry().getByWorld(w))
            {
                ib.despawn();
            }

            for(Entity e : w.getEntities())
            {

                if(e instanceof EnderCrystal || e instanceof Horse)
                {
                    e.remove();
                }
            }
        }
    }
}
