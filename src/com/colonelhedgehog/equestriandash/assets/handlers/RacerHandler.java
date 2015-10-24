package com.colonelhedgehog.equestriandash.assets.handlers;

import com.colonelhedgehog.equestriandash.api.entity.Racer;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

import java.util.*;

public class RacerHandler
{
    public HashMap<UUID, Location> lastLocation;
    private EquestrianDash plugin = EquestrianDash.getInstance();
    public ArrayList<Racer> racers;

    public RacerHandler()
    {
        lastLocation = new HashMap<>();
        racers = new ArrayList<>();
    }


    public void respawnPlayer(final Player p)
    {
        if (p.getVehicle() != null && p.getVehicle() instanceof Horse)
        {
            Horse h = (Horse) p.getVehicle();
            h.setPassenger(null);
            h.setOwner(null);
            h.remove();
        }

        p.getWorld().playSound(p.getLocation(), Sound.WITHER_SPAWN, 3, 1);
        p.setHealth(p.getMaxHealth());
        Location loc = lastLocation.get(p.getUniqueId());
        p.teleport(loc);

        p.getInventory().clear();
        p.setFireTicks(0);
        plugin.getPropertyHandler().generateHorse(p, loc);
    }

    public Racer getRacer(Player p)
    {
        for(Racer racer : racers)
        {
            if(racer.getPlayer().getUniqueId().equals(p.getUniqueId()))
            {
                return racer;
            }
        }

        return null;
    }

    public ArrayList<Player> getPlayers()
    {
        ArrayList<Player> players = new ArrayList<>();
        racers.forEach((racer) -> players.add(racer.getPlayer()));
        return players;
    }
}
