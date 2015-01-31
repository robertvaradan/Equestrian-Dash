/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ColonelHedgehog.Dash.Events;

import com.ColonelHedgehog.Dash.Assets.GameState;
import com.ColonelHedgehog.Dash.Core.EquestrianDash;
import net.minecraft.server.v1_8_R1.GenericAttributes;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftLivingEntity;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

/**
 * @author Robert
 */
public class CreatureSpawnListener implements Listener
{
    public static EquestrianDash plugin = EquestrianDash.plugin;

    @EventHandler
    public void onSpawn(final EntitySpawnEvent event)
    {
        //event.getEntity().getServer().broadcastMessage("§dAnother horse is ready to race!");
        if (event.getEntity() instanceof Horse)
        {
            if (GameState.getCurrentTrack() != null && GameState.getCurrentTrack().getTrackData().getBoolean("NMS.Enabled"))
            {
                try
                {
                    Horse horse = (Horse) event.getEntity();
                    ((CraftLivingEntity) horse)
                            .getHandle()
                            .getAttributeInstance(GenericAttributes.d)
                            .setValue(GameState.getCurrentTrack().getTrackData().getDouble("NMS.MaxHorseSpeed"));
                }
                catch (NoClassDefFoundError error)
                {
                    plugin.getLogger().severe("NMS handling failed! The version of Spigot/Bukkit you are using is not compatible with this. Set \"Enabled\" to \"false\" in the NMS section of the track config to prevent problems.");
                }
            }
        }
        else if(event.getEntity() instanceof EnderCrystal)
        {
            for(Entity e : event.getEntity().getNearbyEntities(0.2, 0.2, 0.2))
            {
                if(e instanceof EnderCrystal)
                {
                    e.remove();
                    event.setCancelled(true);
                    plugin.getLogger().info("Removing duplicated Item Box.");
                    return;
                }
            }
        }
    }
}
