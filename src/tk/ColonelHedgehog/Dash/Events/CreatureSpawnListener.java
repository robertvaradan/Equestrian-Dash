/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tk.ColonelHedgehog.Dash.Events;

import net.minecraft.server.v1_7_R4.GenericAttributes;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftLivingEntity;
import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import tk.ColonelHedgehog.Dash.Assets.GameState;
import tk.ColonelHedgehog.Dash.Core.Main;

/**
 * @author Robert
 */
public class CreatureSpawnListener implements Listener
{
    public static Main plugin = Main.plugin;

    @EventHandler
    public void onSpawn(final CreatureSpawnEvent event)
    {
        //event.getEntity().getServer().broadcastMessage("§dAnother horse is ready to race!");
        if (event.getEntity() instanceof Horse)
        {
            if (GameState.getCurrentTrack().getTrackData().getBoolean("NMS.Enabled"))
            {
                try
                {
                    Horse horse = (Horse) event.getEntity();
                    ((CraftLivingEntity) horse).getHandle().getAttributeInstance(GenericAttributes.d).setValue(GameState.getCurrentTrack().getTrackData().getDouble("NMS.MaxHorseSpeed"));
                }
                catch (NoClassDefFoundError error)
                {
                    plugin.getLogger().severe("NMS handling failed! The version of Spigot/Bukkit you are using is not compatible with this. Set \"Enabled\" to \"false\" in the NMS section of the track config to prevent problems.");
                }
            }
        }

    }
}
