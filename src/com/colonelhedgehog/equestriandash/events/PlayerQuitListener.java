/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.colonelhedgehog.equestriandash.events;

import com.colonelhedgehog.equestriandash.api.entity.Racer;
import com.colonelhedgehog.equestriandash.api.event.EDRaceEndEvent;
import com.colonelhedgehog.equestriandash.assets.handlers.GameHandler;
import com.colonelhedgehog.equestriandash.assets.handlers.RacerHandler;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

/**
 * @author Robert
 */
public class PlayerQuitListener implements Listener
{
    public static EquestrianDash plugin = EquestrianDash.plugin;

    @EventHandler
    public void onLeave(PlayerQuitEvent event)
    {
        Player p = event.getPlayer();
        
        RacerHandler racerHandler = plugin.getRacerHandler();
        
        racerHandler.racers.remove(racerHandler.getRacer(p));
        for (Map.Entry<Location, UUID> entry : PlayerJoinListener.SpawnPoints.entrySet())
        {
            if (entry.getValue() != p.getUniqueId())
            {
                PlayerJoinListener.SpawnPoints.put(entry.getKey(), entry.getValue());
            }
            else
            {
                PlayerJoinListener.SpawnPoints.put(entry.getKey(), null);

            }
        }
        if (p.getVehicle() != null)
        {
            p.getVehicle().remove();
        }
        p.getInventory().clear();

        if (plugin.getGameHandler().getGameState() == GameHandler.GameState.RACE_IN_PROGRESS || plugin.getGameHandler().getGameState() == GameHandler.GameState.COUNT_DOWN_TO_START)
        {
            Bukkit.broadcastMessage(PlayerJoinListener.Prefix + "" + ChatColor.AQUA + "" + p.getName() + " §3is no longer competing.");

            if (racerHandler.getPlayers().size() <= plugin.getConfig().getInt("Players.MinPlayers"))
            {
                if(plugin.getGameHandler().getFinished().size() > 0)
                {
                    plugin.getGameHandler().endGame(true);
                    return;
                }

                plugin.getGameHandler().setState(GameHandler.GameState.RACE_ENDED);

                for (Player on : racerHandler.getPlayers())
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
