package tk.ColonelHedgehog.Dash.Lib;

import java.util.Comparator;
import java.util.Map;
import java.util.UUID;

// http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java/1283722#1283722
public class ValueComparatorUUID implements Comparator<UUID>
{

    Map<UUID, Integer> base;

    public ValueComparatorUUID(Map<UUID, Integer> base)
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
