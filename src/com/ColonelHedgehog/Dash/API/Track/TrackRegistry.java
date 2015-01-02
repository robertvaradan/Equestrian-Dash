package com.ColonelHedgehog.Dash.API.Track;

import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ColonelHedgehog on 11/17/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class TrackRegistry
{
    private List<Track> Tracks;

    public TrackRegistry()
    {
        this.Tracks = new ArrayList<>();
    }

    public List<Track> getTracks()
    {
        return this.Tracks;
    }

    public void registerTrack(Track track)
    {
        this.Tracks.add(track);
    }

    public Track getClosest(String in)
    {
        for(Track track : Tracks)
        {
            if(track.getDisplayName().toLowerCase().contains(in.toLowerCase()))
            {
                return track;
            }
        }

        return null;
    }

    public void deregisterTrack(Track track)
    {
        Tracks.remove(track);
    }

    public FileConfiguration getTrackDataFile(Track track)
    {
        return track.getTrackData();
    }

    public Track getTrackByID(String name)
    {
        for(Track track : Tracks)
        {
            if(track.getWorld().getName().equalsIgnoreCase(name))
            {
                return track;
            }
        }
        return null;
    }

    public Track getTrackByIDExact(String name)
    {
        for (Track track : Tracks)
        {
            if (track.getWorld().getName().equals(name))
            {
                return track;
            }
        }
        return null;
    }

    public Track getTrackByWorld(World world)
    {
        for (Track track : Tracks)
        {
            if (track.getWorld().equals(world))
            {
                return track;
            }
        }
        return null;
    }

    public Track getByDisplayName(String name)
    {
        for (Track track : Tracks)
        {
            if (track.getDisplayName().equals(name))
            {
                return track;
            }
        }
        return null;
    }
}
