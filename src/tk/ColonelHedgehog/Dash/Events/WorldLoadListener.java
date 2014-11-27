package tk.ColonelHedgehog.Dash.Events;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import tk.ColonelHedgehog.Dash.Core.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ColonelHedgehog on 11/11/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class WorldLoadListener implements Listener
{
    public static void killallHorses(World w) // RIP in peace D:
    {
        List<Horse> toremove = new ArrayList<>();

        for (Entity e : w.getEntities())
        {
            if (e instanceof Horse)
            {
                toremove.add((Horse) e);
            }
        }

        for (Horse h : toremove)
        {
            h.remove();
        }

        Main.plugin.getLogger().info("Removed " + toremove.size() + " horse(s).");
    }

    @EventHandler
    public void onLoad(WorldInitEvent event)
    {
        killallHorses(event.getWorld());
    }
}
