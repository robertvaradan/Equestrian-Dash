/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tk.ColonelHedgehog.Dash.Events;

//import org.bukkit.GameMode;
import tk.ColonelHedgehog.Dash.Core.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Robert
 */
public class HorseJumpListener implements Listener
{
    public static Main plugin = Main.plugin;

    @EventHandler
    public void onJump(HorseJumpEvent event)
    {
        Player p = (Player) event.getEntity().getPassenger();
        if(p.hasPotionEffect(PotionEffectType.SLOW))
        {
        event.setCancelled(true);
        }
        
        if(p.hasMetadata("superCharged") && p.getMetadata("superCharged").get(0).asBoolean())
        {
            event.setPower(event.getPower() * 3);
        }
        //p.sendMessage(Main.Prefix + "Power is: §4" + event.getPower());
        
    }
}
