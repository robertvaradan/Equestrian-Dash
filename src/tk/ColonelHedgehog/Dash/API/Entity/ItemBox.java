package tk.ColonelHedgehog.Dash.API.Entity;

import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EntityType;

/**
 * Created by ColonelHedgehog on 11/8/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public abstract class ItemBox implements EnderCrystal
{
    @Deprecated
    public ItemBox(Location loc)
    {
        loc.getWorld().spawnEntity(loc, EntityType.ENDER_CRYSTAL);
    }

    //public boolean isRespawning()    {        return false;    }
}
