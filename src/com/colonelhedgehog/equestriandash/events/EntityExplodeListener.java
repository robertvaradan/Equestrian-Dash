/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.colonelhedgehog.equestriandash.events;

import com.colonelhedgehog.equestriandash.api.powerup.itembox.ItemBox;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author Robert
 */
public class EntityExplodeListener implements Listener
{
    public static EquestrianDash plugin = EquestrianDash.plugin;

    public static void randomDrop(Location loc, int rand)
    {
        int rand2 = (int) (Math.random() * 3 + 0);
        byte color = 0;

        if (rand == 1)
        {
            color = 10;
        }
        else if (rand == 2)
        {
            color = 11;
        }
        else if (rand == 3)
        {
            color = 5;
        }
        else if (rand == 4)
        {
            color = 4;
        }
        else if (rand == 5)
        {
            color = 1;
        }
        else if (rand == 6)
        {
            color = 14;
        }


        ItemStack drop = new ItemStack(Material.WOOL, rand2, color);
        if (rand2 == 0)
        {
            rand2 = 1;
        }

        Location destloc = new Location(loc.getWorld(), loc.getBlockX() + rand2, loc.getBlockY() - rand / rand2, loc.getBlockY() / rand2 + rand * 2);

        loc.getWorld().dropItem(destloc, drop);
    }

    @EventHandler
    public void onExplode(final EntityExplodeEvent event)
    {
        final Entity e = event.getEntity();
        //e.getServer().broadcastMessage("Exploded me!");
        event.blockList().clear();
        try
        {
            ItemBox ib = plugin.getItemBoxRegistry().getByLocation(e.getLocation());
            if (ib != null)
            {
                ib.respawn();
                event.setCancelled(true);
            }
            else if (event.getEntity() instanceof WitherSkull)
            {
                WitherSkull w = (WitherSkull) event.getEntity();
                if (w.isCharged())
                {
                    w.getWorld().strikeLightning(w.getLocation());
                }
            }
        }
        catch(Exception ex)
        {
            // PLEASE DON'T KILL ME, JAVA GODS, I CAN'T FIGURE OUT WHY IT'S CRASHING. :(
        }
    }
}
