/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.colonelhedgehog.equestriandash.events;

import com.colonelhedgehog.equestriandash.api.powerup.itembox.ItemBox;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

//import net.minecraft.server.v1_8_R1.GenericAttributes;
//import org.bukkit.craftbukkit.v1_8_R1.entity.CraftLivingEntity;

/**
 * @author Robert
 */
public class EntitySpawnListener implements Listener
{
    public static EquestrianDash plugin = EquestrianDash.plugin;

    @EventHandler
    public void onSpawn(EntitySpawnEvent event)
    {
        //plugin.getLogger().info("SPAWN EVENT!");
        if(event.getEntity() instanceof EnderCrystal)
        {
            ItemBox itemBox = plugin.getItemBoxRegistry().getByLocation(event.getLocation().getBlock().getLocation());

            if(itemBox != null && !itemBox.getEnderCrystal().equals(event.getEntity()))
            {
                event.setCancelled(true);
            }
        }
    }
}
