/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ColonelHedgehog.Dash.Events;

import com.ColonelHedgehog.Dash.Core.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * @author Robert
 */
public class BlockPlaceListener implements Listener
{
    public static Main plugin = Main.plugin;

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlace(SignChangeEvent event)
    {
        Player p = event.getPlayer();
        if (p.hasMetadata("editorEnabled") && p.getMetadata("editorEnabled").get(0).asBoolean())
        {
            event.setLine(0, "#");
            event.setLine(1, "" + p.getMetadata("editorNumber").get(0).asInt());
            p.setMetadata("editorNumber", new FixedMetadataValue(plugin, p.getMetadata("editorNumber").get(0).asInt() + 1));
            //event.update();
        }
    }
}
