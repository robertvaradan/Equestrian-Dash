/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hand.Race;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

/**
 *
 * @author Robert
 */
public class Place implements Listener
{
    public static Main plugin;
    public Place(Main ins)
    {
        Place.plugin = ins;
    }    
    
    @EventHandler
    public static void onPlace(BlockPlaceEvent event)
    {    
        Block b = event.getBlock();
        Player p = event.getPlayer();
        /*if(p.getMetadata("editorEnabled").get(0).asBoolean())
        {
            if(b.getType() == Material.SIGN || b.getType() == Material.WALL_SIGN || b.getType() == Material.SIGN_POST)
            {
                    BlockState state = b.getState();
                    Sign sign = (Sign)state;
                    sign.setLine(0, "#");
                    sign.setLine(1, "" + p.getMetadata("editorNumber").get(0).asInt());
                    p.setMetadata("editorNumber", new FixedMetadataValue(plugin, p.getMetadata("editorNumber").get(0).asInt() + 1));
            }
        }*/
        if(p.getGameMode() != GameMode.CREATIVE)
        {
            event.setCancelled(true);
        }
    }
}
