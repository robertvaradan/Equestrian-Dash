/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ColonelHedgehog.Dash.Events;

import com.ColonelHedgehog.Dash.Assets.GameState;
import com.ColonelHedgehog.Dash.Core.Main;
import net.minecraft.server.v1_8_R1.GenericAttributes;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.entity.EntityType.HORSE;

/**
 * @author Robert
 */
public class PlayerRespawnListener implements Listener
{
    public static Main plugin = Main.plugin;

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event)
    {
        Player p = event.getPlayer();
        Location loc = new Location(p.getWorld(), p.getMetadata("lastLocX").get(0).asDouble(), p.getMetadata("lastLocY").get(0).asDouble(), p.getMetadata("lastLocZ").get(0).asDouble(), p.getMetadata("lastLocPitch").get(0).asFloat(), p.getMetadata("lastLocYaw").get(0).asFloat());
        event.setRespawnLocation(loc);
        forceRespawn(p);
    }

    private void forceRespawn(final Player p)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {

                Location loc = new Location(p.getWorld(), p.getMetadata("lastLocX").get(0).asDouble(), p.getMetadata("lastLocY").get(0).asDouble(), p.getMetadata("lastLocZ").get(0).asDouble(), p.getMetadata("lastLocPitch").get(0).asFloat(), p.getMetadata("lastLocYaw").get(0).asFloat());


                respawnSequence(p, loc);
            }
        }.runTask(plugin);
    }

    public void respawnSequence(final Player p, final Location loc)
    {

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                //System.out.println("FORCE RESPAWN!");
                Horse horse = (Horse) p.getWorld().spawnEntity(loc, HORSE);
                horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));

                p.getLocation().setYaw(p.getMetadata("lastLocYaw").get(0).asFloat());
                p.getLocation().setPitch(p.getMetadata("lastLocPitch").get(0).asFloat());
                horse.getLocation().setYaw(p.getMetadata("lastLocYaw").get(0).asFloat());
                horse.getLocation().setPitch(p.getMetadata("lastLocPitch").get(0).asFloat());
                PlayerJoinListener.getHorseColor(p, horse, p.getMetadata("colorKey").get(0).asInt());
                PlayerJoinListener.getHorsePattern(p, horse, p.getMetadata("patternKey").get(0).asInt());



                //horse.setVariant(Horse.Variant.DONKEY);

                if (GameState.getCurrentTrack().getTrackData().getBoolean("NMS.Enabled"))
                {
                    try
                    {
                        ((CraftLivingEntity) horse)
                                .getHandle()
                                .getAttributeInstance(GenericAttributes.d)
                                .setValue(GameState.getCurrentTrack().getTrackData().getDouble("NMS.MaxHorseSpeed"));
                    }
                    catch (NoClassDefFoundError error)
                    {
                        plugin.getLogger().severe("NMS handling failed! The version of Spigot/Bukkit you are using is not compatible with this. Set \"Enabled\" to \"false\" in the NMS section of your track config to prevent problems.");
                    }
                }

                horse.teleport(loc);
                p.teleport(horse.getLocation());

                horse.setOwner(p);
                horse.setPassenger(p);
                horse.setAdult();
            }
        }.runTask(plugin);
    }
}