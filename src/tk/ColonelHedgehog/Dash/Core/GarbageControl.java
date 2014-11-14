package tk.ColonelHedgehog.Dash.Core;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

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

        int i2 = 0;
        for(Location loc : RespawningIBs)
        {
            loc.getWorld().spawnEntity(loc, EntityType.ENDER_CRYSTAL);
            i2++;
        }

        Main.plugin.getLogger().info("Restoring " + i2 + " respawning Item Boxes.");

    }
}
