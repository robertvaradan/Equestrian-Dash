package com.colonelhedgehog.equestriandash.assets.tasks;

import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MovementTask extends BukkitRunnable
{
    private EquestrianDash plugin = EquestrianDash.getInstance();
    public HashMap<UUID, Location> players;
    public MovementTask()
    {
        players = new HashMap<>();
    }

    @Override
    public void run()
    {
        for(Map.Entry<UUID, Location> entry : players.entrySet())
        {
            Player p = Bukkit.getPlayer(entry.getKey());
            Entity v = p.getVehicle();

            if(v == null || !(v instanceof Horse))
            {
                continue;
            }

            Location loc = entry.getValue();
            Horse h = (Horse) p.getVehicle();
            h.teleport(loc);
            p.teleport(loc);
        }
    }
}
