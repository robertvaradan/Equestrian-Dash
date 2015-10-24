/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.colonelhedgehog.equestriandash.events;

import com.colonelhedgehog.equestriandash.assets.handlers.PropertyHandler;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.entity.EntityType.HORSE;

//import net.minecraft.server.v1_8_R1.GenericAttributes;
//import org.bukkit.craftbukkit.v1_8_R1.entity.CraftLivingEntity;

/**
 * @author Robert
 */
public class PlayerRespawnListener implements Listener
{
    public static EquestrianDash plugin = EquestrianDash.plugin;

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event)
    {
        Player p = event.getPlayer();
        //System.out.println("RESPAWN EVENT...");
        //event.setRespawnLocation(loc);
        forceRespawn(p);
    }

    private void forceRespawn(final Player p)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                //System.out.println("PREPARING RESPAWN SEQUENCE... ");

                Location loc = plugin.getRacerHandler().lastLocation.get(p.getUniqueId());


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
                PropertyHandler propertyHandler = plugin.getPropertyHandler();
                Horse horse = (Horse) p.getWorld().spawnEntity(loc, HORSE);
                horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));

                p.getLocation().setYaw(p.getMetadata("lastLocYaw").get(0).asFloat());
                p.getLocation().setPitch(p.getMetadata("lastLocPitch").get(0).asFloat());
                horse.getLocation().setYaw(p.getMetadata("lastLocYaw").get(0).asFloat());
                horse.getLocation().setPitch(p.getMetadata("lastLocPitch").get(0).asFloat());
                propertyHandler.getHorseColor(p, horse, p.getMetadata("colorKey").get(0).asInt());
                propertyHandler.getHorsePattern(p, horse, p.getMetadata("patternKey").get(0).asInt());



                //horse.setVariant(Horse.Variant.DONKEY);

                if (plugin.getGameHandler().getCurrentTrack().getTrackData().getBoolean("NMS.Enabled"))
                {
                    plugin.getPropertyHandler().setNMSHorseSpeed(horse, plugin.getGameHandler().getCurrentTrack().getTrackData().getDouble("NMS.MaxHorseSpeed"));
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