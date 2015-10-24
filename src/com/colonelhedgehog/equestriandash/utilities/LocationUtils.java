package com.colonelhedgehog.equestriandash.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * Created by ColonelHedgehog on 6/1/15.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class LocationUtils
{
    public static String serialize(Location loc)
    {
        return loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();
    }

    public static Location deserialize(String loc)
    {
        String[] split = loc.split(",");
        return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3])).getBlock().getLocation();
    }

    public static String serializeAdvanced(Location loc)
    {
        return loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getYaw() + "," + loc.getPitch();
    }

    public static Location deserializeAdvanced(String loc)
    {
        String[] split = loc.split(",");
        return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), Float.parseFloat(split[4]), Float.parseFloat(split[5]));
    }
}
