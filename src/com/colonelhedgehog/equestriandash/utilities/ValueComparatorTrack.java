package com.colonelhedgehog.equestriandash.utilities;

import com.colonelhedgehog.equestriandash.api.track.Track;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

// http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java/1283722#1283722
public class ValueComparatorTrack implements Comparator<Track>
{

    Map<Track, Integer> base;

    public ValueComparatorTrack(HashMap<Track, Integer> base)
    {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.
    public int compare(Track a, Track b)
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
