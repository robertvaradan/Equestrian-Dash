package com.colonelhedgehog.equestriandash.events;

import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import com.colonelhedgehog.equestriandash.core.GarbageControl;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

/**
 * Created by ColonelHedgehog on 10/23/15.
 */
public class BlockDamageListener implements Listener
{
    private EquestrianDash plugin = EquestrianDash.getInstance();
    @EventHandler(priority = EventPriority.HIGH)
    public void onDamage(BlockDamageEvent event)
    {

        //plugin.getLogger().info("Clicked block not null confirmed!");
        Block b = event.getBlock();
        if (GarbageControl.DespawningIce.contains(b.getLocation()))
        {
            //plugin.getLogger().info("icePowerupBlock confirmed!");
            if (plugin.getConfig().getBoolean("Powerups.Ice.Breakable"))
            {
                //plugin.getLogger().info("Breakable confirmed!");
                event.setInstaBreak(true);
            }
        }
    }
}
