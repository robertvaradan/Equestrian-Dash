/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tk.ColonelHedgehog.Dash.Events;

//import org.bukkit.GameMode;

import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.potion.PotionEffectType;
import tk.ColonelHedgehog.Dash.Core.Main;

/**
 * @author Robert
 */
public class HorseJumpListener implements Listener
{
    public static Main plugin = Main.plugin;

    @EventHandler
    public void onJump(HorseJumpEvent event)
    {
        Player p = (Player) event.getEntity().getPassenger();
        if (p.hasPotionEffect(PotionEffectType.SLOW) || racetrackNearby(event.getEntity()))
        {
            event.setCancelled(true);
        }

        if (p.hasMetadata("superCharged") && p.getMetadata("superCharged").get(0).asBoolean())
        {
            event.setPower(event.getPower() * 3);
        }
        //p.sendMessage(Main.Prefix + "Power is: §4" + event.getPower());

    }

    private boolean racetrackNearby(Horse h)
    {

        int range = plugin.getConfig().getInt("RaceLine.NoJumpRange");
        for (int x = -range; x < range; x++)
        {
            for (int y = -range; y < range; y++)
            {
                for (int z = -range; z < range; z++)
                {
                    if (Main.LapCuboid.contains(h.getLocation().add(x, y, z).getBlock()))
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
