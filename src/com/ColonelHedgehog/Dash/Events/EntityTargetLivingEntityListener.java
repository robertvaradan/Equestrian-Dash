package com.ColonelHedgehog.Dash.Events;

import com.ColonelHedgehog.Dash.Core.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

/**
 * Created by ColonelHedgehog on 9/5/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class EntityTargetLivingEntityListener implements Listener
{
    public static Main plugin = Main.plugin;

    @EventHandler
    public void onTarget(EntityTargetLivingEntityEvent event)
    {
        if (event.getTarget() instanceof Player && event.getEntity().hasMetadata("Creator") && event.getEntity().getMetadata("Creator").get(0).asString() == ((Player) event.getTarget()).getName())
        {
            event.setCancelled(true);
        }
    }
}
