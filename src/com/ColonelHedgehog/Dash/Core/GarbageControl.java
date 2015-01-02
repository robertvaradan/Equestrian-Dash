package com.ColonelHedgehog.Dash.Core;

import com.ColonelHedgehog.Dash.API.Powerup.ItemBox.ItemBox;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;

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
    public static List<Location> RespawningIBs = new ArrayList<>();

    public static void destroyGarbage()
    {
        int i = 0;
        for(Location loc : DespawningIce)
        {
            loc.getBlock().setType(Material.AIR);
            i++;
        }

        Main.plugin.getLogger().info("Destroying " + i + " undeleted Ice Powerup blocks.");

        /*int i2 = 0;
        for(Location loc : RespawningIBs)
        {
            loc.getWorld().spawnEntity(loc, EntityType.ENDER_CRYSTAL);
            i2++;
        }*/

        //Main.plugin.getLogger().info("Restoring " + i2 + " respawning Item Boxes.");
        /*Main.plugin.getLogger().info("DEBUG WORLDS: " + Bukkit.getWorlds().size());
        for (World w : Bukkit.getWorlds())
        {
            System.out.println("TESTING FOR WORLD " + w.getName()+ "...");

            for (Entity e : w.getEntities())
            {
                System.out.println("TESTING FOR AN ENTITY " + e.getType().name() + "...");
                if (e instanceof EnderCrystal)
                {
                    System.out.println("IS ENDER CRYSTAL! REMOVE...");

                    e.remove();
                }
            }
        }*/

        for(World w : Bukkit.getWorlds())
        {
            for (ItemBox ib : Main.getItemBoxRegistry().getByWorld(w))
            {
                ib.despawn();
            }

            for(Entity e : w.getEntities())
            {
                if(e instanceof EnderCrystal)
                {
                    e.remove();
                }
            }
        }
    }
}
