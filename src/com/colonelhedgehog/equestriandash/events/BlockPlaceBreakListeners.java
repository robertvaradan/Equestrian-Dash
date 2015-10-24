/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.colonelhedgehog.equestriandash.events;

import com.colonelhedgehog.equestriandash.api.track.Marker;
import com.colonelhedgehog.equestriandash.api.track.Track;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import com.colonelhedgehog.equestriandash.core.GarbageControl;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * @author Robert
 */
public class BlockPlaceBreakListeners implements Listener
{
    public static EquestrianDash plugin = EquestrianDash.plugin;

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlace(BlockPlaceEvent event)
    {
        Player p = event.getPlayer();
        Block b = event.getBlock();

        if (p.hasMetadata("editorEnabled") && p.getMetadata("editorEnabled").get(0).asBoolean())
        {
            event.setCancelled(true);

            if(b.getType() != Material.BEACON)
            {
                return;
            }

            int value = p.getMetadata("editorNumber").get(0).asInt();

            Track track = plugin.getTrackRegistry().getTrackByWorld(b.getWorld());

            if(track.getMarkerHandler().getMarkerAtLocation(b.getLocation()) != null)
            {
                p.sendMessage(EquestrianDash.Prefix + "§cA marker already exists at this location! Please delete it first before placing another here.");
                return;
            }

            if(value > 99999 || value < -99999)
            {
                p.sendMessage(EquestrianDash.Prefix + "§4No thanks! §c" + value + " is a bit too long of a number.");
                return;
            }

            Marker marker = new Marker(b.getLocation(), value);
            if(track.getMarkerHandler().getVisualized())
            {
                marker.setVisualized(true);
            }
            else
            {
                marker.setVisualized(true, 40);
            }

            p.setMetadata("editorNumber", new FixedMetadataValue(plugin, p.getMetadata("editorNumber").get(0).asInt() + 1));
            marker.save();
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event)
    {
        Block b = event.getBlock();

        if(GarbageControl.DespawningIce.contains(b.getLocation()))
        {
            b.getDrops().clear();
        }
    }
}
