/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tk.ColonelHedgehog.Dash.Events;

import net.minecraft.server.v1_7_R4.GenericAttributes;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftLivingEntity;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import tk.ColonelHedgehog.Dash.Core.Main;

/**
 *
 * @author Robert
 */
public class CreatureSpawnListener implements Listener
{
    public static Main plugin = Main.plugin;

    @EventHandler
    public void onSpawn(final CreatureSpawnEvent event)
    {
        if(event.getEntity() instanceof Horse || event.getEntity() instanceof Player || event.getEntity() instanceof Firework || event.getEntity() instanceof TNTPrimed || event.getEntity() instanceof Slime)
        {
            //event.getEntity().getServer().broadcastMessage("§dAnother horse is ready to race!");
            if(event.getEntity() instanceof Slime)
            {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("EquestrianDash"), new Runnable()
                {
                    @Override
                    public void run()
                    {
                        event.getEntity().remove();
                        event.getLocation().getWorld().createExplosion(event.getLocation(), (float) 0.0);
                    }
                }, 100L);
            }
            else if(event.getEntity() instanceof Horse)
            {
                if(plugin.getConfig().getBoolean("Config.NMS.Enabled"))
                {
                    try
                    {
                        Horse horse = (Horse) event.getEntity();
                        ((CraftLivingEntity) horse).getHandle().getAttributeInstance(GenericAttributes.d).setValue(plugin.getConfig().getDouble("Config.NMS.MaxHorseSpeed"));
                    }
                    catch (NoClassDefFoundError error)
                    {
                        plugin.getLogger().severe("NMS handling failed! The version of Spigot/Bukkit you are using is not compatible with this. Set \"Enabled\" to \"false\" in the NMS section of your config to prevent problems.");
                    }
                }
            }
        }
        else
        {
            event.getEntity().remove(); // A less graceful method of disabling all other mob-spawns.
        }
    }
}
