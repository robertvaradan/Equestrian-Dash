package com.colonelhedgehog.equestriandash.assets;

import com.colonelhedgehog.equestriandash.assets.handlers.RacerHandler;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by ColonelHedgehog on 11/11/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class Ranking
{
    private static EquestrianDash plugin = EquestrianDash.getInstance();

    public static LinkedHashMap<UUID, Integer> scores = new LinkedHashMap<>();

    public static LinkedHashMap<UUID, Integer> getPlayersArranged()
    {
        return sort(scores);
    }

    public static int getRank(Player p)
    {
        RacerHandler racerHandler = plugin.getRacerHandler();

        int index = racerHandler.getPlayers().size();

        for(Map.Entry<UUID, Integer> entry : Ranking.getPlayersArranged().entrySet())
        {
            if(entry.getKey().equals(p.getUniqueId()))
            {
                break;
            }

            index--;
        }

        return index;
    }

    public static LinkedHashMap<UUID, Integer> sort(HashMap<UUID, Integer> map)
    {
        LinkedHashMap<UUID, Integer> sortedMap = new LinkedHashMap<>();
        map.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(entry -> sortedMap.put(entry.getKey(), entry.getValue()));
        return sortedMap;
    }
}


