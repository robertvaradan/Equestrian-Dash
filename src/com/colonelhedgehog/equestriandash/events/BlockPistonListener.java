package com.colonelhedgehog.equestriandash.events;

import com.colonelhedgehog.equestriandash.core.GarbageControl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonEvent;

/**
 * Created by ColonelHedgehog on 12/26/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class BlockPistonListener implements Listener
{
    @EventHandler
    public void onPiston(BlockPistonEvent event)
    {
        if(GarbageControl.DespawningIce.contains(event.getBlock().getRelative(event.getDirection()).getLocation()))
        {
            event.setCancelled(true);
        }
    }
}
