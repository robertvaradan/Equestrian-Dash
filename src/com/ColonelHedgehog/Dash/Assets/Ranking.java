package com.ColonelHedgehog.Dash.Assets;

import org.bukkit.entity.Player;
import com.ColonelHedgehog.Dash.Lib.ValueComparatorUUID;

import java.util.*;

/**
 * Created by ColonelHedgehog on 11/11/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class Ranking
{
    public static HashMap<UUID, Integer> Scores = new HashMap<>();

    public static TreeMap<UUID, Integer> getPlayersArranged()
    {
        ValueComparatorUUID bvc = new ValueComparatorUUID(Scores);
        TreeMap<UUID, Integer> sorted_map = new TreeMap<>(bvc);
        sorted_map.putAll(Scores);
        return sorted_map;
    }

    public static int getRank(Player p)
    {
        ValueComparatorUUID bvc = new ValueComparatorUUID(Scores);
        TreeMap<UUID, Integer> sorted_map = new TreeMap<>(bvc);
        sorted_map.putAll(Scores);
        int rank = 1;
        for(Map.Entry entry : sorted_map.entrySet())
        {
            if(entry.getKey().equals(p.getUniqueId()))
            {
                //System.out.println("INDEXED RANK AS: " + rank);
                return rank;
            }

            rank++;
        }
        //System.out.println("INDEXED FAILED! RANK IS -1.");

        return -1;
    }
}


