package com.colonelhedgehog.equestriandash.api.track;

import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import com.colonelhedgehog.equestriandash.utilities.LocationUtils;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class MarkerHandler
{
    private EquestrianDash plugin = EquestrianDash.getInstance();

    private HashMap<Location, Marker> markers;
    private FileConfiguration markerConfig;
    private File markerConfigFile;
    private Track track;
    private boolean visualizing;
    protected HashMap<Marker, ArmorStand> holograms;

    public MarkerHandler(Track track)
    {
        markers = new HashMap<>();
        holograms = new HashMap<>();
        visualizing = false;
        this.track = track;
    }

    public void initialize()
    {
        reloadMarkerConfig();

        if (markerConfigFile.exists())
        {
            markerConfigFile.delete();
        }

        try
        {
            markerConfig.save(markerConfigFile);
            markerConfig.options().copyDefaults(true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        saveMarkerConfig();
    }

    public FileConfiguration getMarkerConfig()
    {
        if (markerConfig == null) // We can't return something that doesn't exist!
        {
            reloadMarkerConfig(); // This reloads the data (we'll get to it in a minute).
        }

        return markerConfig;
    }

    public void saveMarkerConfig()
    {
        // We first need to make sure what we're saving actually exists.
        if (markerConfig == null || markerConfigFile == null)
        {
            return;
        }

        // Even though there are very few circumstances where an IOException will be thrown, even if the file isn't null, we need to add a try-catch.
        try
        {
            // We're accessing the config method we just made.
            getMarkerConfig().save(markerConfigFile);
        }
        catch (IOException ex)
        {
            plugin.getLogger().log(Level.SEVERE, "Could not save marker config to " + markerConfigFile, ex);
        }
    }

    public void reloadMarkerConfig()
    {
        String configName = "MarkerData";

        if (markerConfigFile == null)
        {
            markerConfigFile = new File(plugin.getDataFolder() + "/Tracks/" + getTrack().getWorld().getName(), configName + ".yml");
        }

        markerConfig = YamlConfiguration.loadConfiguration(markerConfigFile);

        Reader defConfigStream = null;

        try
        {
            defConfigStream = new InputStreamReader(plugin.getResource(configName + ".yml"), "UTF8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        if (defConfigStream != null)
        {
            // Setting the defaults we've loaded.
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            markerConfig.setDefaults(defConfig);
        }

        markers.clear();
        if (markerConfig.contains("MarkerPositions"))
        {
            for (String serialized : markerConfig.getConfigurationSection("MarkerPositions").getKeys(false))
            {
                int number = getMarkerConfig().getInt("MarkerPositions." + serialized + ".Number");
                if(serialized.split(",").length - 1 != 3)
                {
                    plugin.getLogger().info("Unable to read Location \"" + serialized + "\" from MarkerConfig for track.");
                    continue;
                }

                Location loc = LocationUtils.deserialize(serialized);
                Marker marker = new Marker(loc, number);
                markers.put(loc, marker);
            }
        }
    }

    public Marker getMarkerAtLocation(Location loc)
    {
        return markers.get(loc);
    }

    public void addMarker(Marker... markers)
    {
        for (Marker marker : markers)
        {
            addMarker(marker);
        }
    }

    public void setTrack(Track track)
    {
        this.track = track;
    }

    public Track getTrack()
    {
        return track;
    }

    public HashMap<Location, Marker> getMarkers()
    {
        return markers;
    }

    public boolean getVisualized()
    {
        return visualizing;
    }

    public void setVisualized(boolean enabled)
    {
        setVisualized(enabled, -1);
    }

    public void setVisualized(boolean enabled, int time)
    {
        //plugin.getLogger().info("Set enabled for " + time);

        if (enabled)
        {
            if (visualizing)
            {
                //plugin.getLogger().info("Already visualizing, so skip - END");
                return;
            }

            //plugin.getLogger().info("Not visualizing - CONTINUE");

            for (Marker marker : markers.values())
            {
                //plugin.getLogger().info("Looping for marker, visualizing #" + marker.getNumber());

                marker.setVisualized(true);
            }

            if (time > -1)
            {
                //plugin.getLogger().info("Time is bigger than -1 so - Start Runnable");

                runnable = new BukkitRunnable() // Better use our own runnable so we don't schedule 300 different tasks lol
                {
                    @Override
                    public void run()
                    {
                        //plugin.getLogger().info("Not visualizing - CONTINUE");
                        setVisualized(false);
                    }
                }.runTaskLater(plugin, time);
            }

            //plugin.getLogger().info("Done scheduling - FINISHED");
        }
        else
        {
            if (runnable != null)
            {
                runnable.cancel();
            }

            for (Marker marker : markers.values())
            {
                marker.setVisualized(false);
            }
        }

        visualizing = enabled;
    }

    private BukkitTask runnable;

    public List<Marker> getMarkersByNumber(int id)
    {
        return getMarkers().values().stream().filter(marker -> marker.getNumber() == id).collect(Collectors.toList()); // You just got Java 8'd! OH YEAH!
    }


    public List<Marker> getMarkersInRange(int start, int end)
    {

        return getMarkers().values().stream().filter(marker -> marker.getNumber() >= start && marker.getNumber() <= end).collect(Collectors.toList());
    }
}
