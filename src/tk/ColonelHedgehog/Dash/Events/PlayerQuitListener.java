/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tk.ColonelHedgehog.Dash.Events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import tk.ColonelHedgehog.Dash.API.Entity.Racer;
import tk.ColonelHedgehog.Dash.API.Event.EDRaceEndEvent;
import tk.ColonelHedgehog.Dash.Assets.GameState;
import tk.ColonelHedgehog.Dash.Core.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Robert
 */
public class PlayerQuitListener implements Listener
{
    public static Main plugin = Main.plugin;

    @EventHandler
    public void onLeave(PlayerQuitEvent event)
    {
        HashMap<Location, UUID> map = new HashMap<>();
        for (Map.Entry<Location, UUID> entry : PlayerJoinListener.SpawnPoints.entrySet())
        {
            if (entry.getValue() != event.getPlayer().getUniqueId())
            {
                PlayerJoinListener.SpawnPoints.put(entry.getKey(), entry.getValue());
            }
            else
            {
                PlayerJoinListener.SpawnPoints.put(entry.getKey(), null);

            }
        }
        if (event.getPlayer().getVehicle() != null)
        {
            event.getPlayer().getVehicle().remove();
        }
        event.getPlayer().getInventory().clear();
        PlayerJoinListener.quitexception = true;

        if (GameState.getState() == GameState.State.RACE_IN_PROGRESS || GameState.getState() == GameState.State.COUNT_DOWN_TO_START)
        {
            event.getPlayer().getServer().broadcastMessage(PlayerJoinListener.Prefix + "" + ChatColor.AQUA + "" + event.getPlayer().getName() + " §3is no longer competing.");
            if (Bukkit.getOnlinePlayers().length <= plugin.getConfig().getInt("Players.MinPlayers"))
            {
                GameState.setState(GameState.State.RACE_ENDED);
                for (Player on : Bukkit.getOnlinePlayers())
                {
                    on.kickPlayer("§c§lNo Contest!\n§7§lToo many players have left the game.");
                    if (plugin.getConfig().getBoolean("RaceOver.Restart.Enabled"))
                    {
                        new BukkitRunnable()
                        {
                            @Override
                            public void run()
                            {
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "restart");
                            }
                        }.runTaskLater(plugin, plugin.getConfig().getLong("RaceOver.Restart.Delay"));
                    }

                    ArrayList<Racer> list = new ArrayList<>();
                    EDRaceEndEvent callable = new EDRaceEndEvent(list);
                    Bukkit.getPluginManager().callEvent(callable);
                }
            }
        }
    }
}
