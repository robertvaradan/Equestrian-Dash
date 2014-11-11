/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tk.ColonelHedgehog.Dash.Events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import tk.ColonelHedgehog.Dash.Core.Main;

import static org.bukkit.entity.EntityType.HORSE;
import static tk.ColonelHedgehog.Dash.Events.PlayerJoinListener.getHorseColor;
import static tk.ColonelHedgehog.Dash.Events.PlayerJoinListener.getHorsePattern;

/**
 *
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

                Horse horse = (Horse) p.getWorld().spawnEntity(loc, HORSE);
                horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
                getHorseColor(p, horse, p.getMetadata("colorKey").get(0).asInt());
                getHorsePattern(p, horse, p.getMetadata("patternKey").get(0).asInt());
                p.getLocation().setYaw(p.getMetadata("lastLocYaw").get(0).asFloat());
                p.getLocation().setPitch(p.getMetadata("lastLocPitch").get(0).asFloat());
                horse.getLocation().setYaw(p.getMetadata("lastLocYaw").get(0).asFloat());
                horse.getLocation().setPitch(p.getMetadata("lastLocPitch").get(0).asFloat());
                respawnSequence(p, horse);
            }
        }.runTaskLater(plugin, 5);
    }

    public void respawnSequence(final Player p, final Horse horse)
    {
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999999, 999999999));
        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999999, -15));
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                //System.out.println("FORCE RESPAWN!");
                horse.setOwner(p);
                horse.setPassenger(p);
                p.removePotionEffect(PotionEffectType.SLOW);
                p.removePotionEffect(PotionEffectType.JUMP);
            }
        }.runTaskLater(plugin, 40);
    }
}