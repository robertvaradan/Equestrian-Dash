package com.colonelhedgehog.equestriandash.assets.handlers;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by ColonelHedgehog on 11/13/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class CooldownHandler
{
    private HashMap<UUID, Long> Cooling;

    public CooldownHandler()
    {
        this.Cooling = new HashMap<>();
    }

    public void placeInCooldown(Player p, Long mil)
    {
        Cooling.put(p.getUniqueId(), System.currentTimeMillis() + mil);
    }

    public boolean isCooling(Player p)
    {
        boolean containsPlayer = Cooling.containsKey(p.getUniqueId());
        if(!containsPlayer || (Cooling.get(p.getUniqueId()) <= System.currentTimeMillis()))
        {
            if(containsPlayer)
            {
                final long time = Cooling.get(p.getUniqueId());
                Cooling.remove(p.getUniqueId());
                return time > System.currentTimeMillis();
            }

            return false;
        }


        return true;
    }
}
