package tk.ColonelHedgehog.Dash.Assets;

import org.bukkit.entity.Player;

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
        ValueComparator bvc = new ValueComparator(Scores);
        TreeMap<UUID, Integer> sorted_map = new TreeMap<>(bvc);
        sorted_map.putAll(Scores);
        return sorted_map;
    }

    public static int getRank(Player p)
    {
        ValueComparator bvc = new ValueComparator(Scores);
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


// http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java/1283722#1283722
class ValueComparator implements Comparator<UUID>
{

    Map<UUID, Integer> base;

    public ValueComparator(Map<UUID, Integer> base)
    {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.
    public int compare(UUID a, UUID b)
    {
        if (base.get(a) >= base.get(b))
        {
            return -1;
        }
        else
        {
            return 1;
        } // returning 0 would merge keys
    }
}
