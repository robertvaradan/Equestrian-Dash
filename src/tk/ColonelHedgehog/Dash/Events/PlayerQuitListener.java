/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tk.ColonelHedgehog.Dash.Events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import tk.ColonelHedgehog.Dash.API.Entity.Racer;
import tk.ColonelHedgehog.Dash.API.Event.EDRaceEndEvent;
import tk.ColonelHedgehog.Dash.Core.Main;

import java.util.ArrayList;

/**
 *
 * @author Robert
 */
public class PlayerQuitListener implements Listener
{
    public static Main plugin = Main.plugin;

    @EventHandler
    public void onLeave(PlayerQuitEvent event)
    {
        if(event.getPlayer().getVehicle() != null)
        {
        event.getPlayer().getVehicle().remove();
        }
        event.getPlayer().getInventory().clear();
        PlayerJoinListener.quitexception = true;

        if (!PlayerJoinListener.RaceEnded)
        {
            event.getPlayer().getServer().broadcastMessage(PlayerJoinListener.Prefix + "" + ChatColor.AQUA + "" + event.getPlayer().getName() + " §3is no longer competing.");
            if(PlayerJoinListener.count <= 0 && Bukkit.getOnlinePlayers().length == plugin.getConfig().getInt("Config.Players.Min") - 1)
            {
                for(Player on : Bukkit.getOnlinePlayers())
                {
                    on.kickPlayer("§c§lNo Contest!\n§7§lToo many players have left the game.");
                    if(plugin.getConfig().getBoolean("Config.RaceOver.Restart.Enabled"))
                    {
                        ArrayList<Racer> list = new ArrayList<>();
                        EDRaceEndEvent callable = new EDRaceEndEvent(list);
                        Bukkit.getPluginManager().callEvent(callable);
                        new BukkitRunnable()
                        {
                            @Override
                            public void run()
                            {
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "restart");
                            }
                        }.runTaskLater(plugin, plugin.getConfig().getLong("Config.RaceOver.Restart.Delay"));
                    }
                }
            }
        }
    }
}
