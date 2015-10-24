package com.colonelhedgehog.equestriandash.core;

import com.colonelhedgehog.equestriandash.api.powerup.itembox.ItemBox;
import com.colonelhedgehog.equestriandash.api.track.Marker;
import com.colonelhedgehog.equestriandash.api.track.Track;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by ColonelHedgehog on 11/13/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class GarbageControl
{
    public static List<Location> DespawningIce = new ArrayList<>();
    private static EquestrianDash plugin = EquestrianDash.plugin;

    public void destroyGarbage()
    {
        int i = 0;
        for (Location loc : DespawningIce)
        {
            loc.getBlock().setType(Material.AIR);
            i++;
        }

        plugin.getLogger().info("Destroying " + i + " undeleted Ice Powerup blocks.");

        List<Track> tracks = plugin.getTrackRegistry().getTracks();
        for (Track track : tracks)
        {
            track.getItemBoxes().forEach(ItemBox::despawn);

            Collection<Marker> markers = track.getMarkerHandler().getMarkers().values();

            for (Marker marker : markers)
            {
                marker.setVisualized(false);
            }

            track.getWorld().getEntities().stream().filter(e -> e instanceof EnderCrystal || e instanceof Horse || e.hasMetadata("MarkerLocation") || e.getName().startsWith("§9Marker")).forEach(Entity::remove);
        }
    }
}
