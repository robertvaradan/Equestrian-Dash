package com.colonelhedgehog.equestriandash.api.track;

import com.colonelhedgehog.equestriandash.api.powerup.itembox.ItemBox;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by ColonelHedgehog on 11/17/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class Track
{
    private World world;
    private FileConfiguration trackData = null;
    private File trackDataFile = null;
    private String displayName;
    private MarkerHandler markerHandler;
    private List<ItemBox> itemBoxes;

    public Track(World world, MarkerHandler markerHandler)
    {
        this(world);
        this.markerHandler = markerHandler;
        this.itemBoxes = new ArrayList<>();
    }

    public Track(World world)
    {
        this.world = world;
        this.markerHandler = new MarkerHandler(this);
        this.reloadTrackData();
        this.reloadItemBoxData();
        this.itemBoxes = new ArrayList<>();
    }

    public void setMarkerHandler(MarkerHandler markerHandler)
    {
        markerHandler.setTrack(this);
        this.markerHandler = markerHandler;
    }

    public MarkerHandler getMarkerHandler()
    {
        return markerHandler;
    }

    public World getWorld()
    {
        return this.world;
    }

    public void initialize(String name)
    {
        reloadTrackData();

        if (trackDataFile.exists())
        {
            trackDataFile.delete();
        }

        try
        {
            trackData.save(trackDataFile);
            trackData.options().copyDefaults(true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        trackData.set("TrackName", name.replace("_", " "));
        saveTrackData();

        initializeItemBoxData();
        markerHandler.initialize();
    }

    public void reloadTrackData()
    {
        reloadItemBoxData();
        if (trackDataFile == null)
        {
            trackDataFile = new File(EquestrianDash.plugin.getDataFolder() + "/Tracks/" + world.getName(), "TrackData.yml");
        }

        trackData  = YamlConfiguration.loadConfiguration(trackDataFile);

        // Look for defaults in the jar
        Reader defConfigStream = null;
        try
        {
            defConfigStream = new InputStreamReader(EquestrianDash.plugin.getResource("TrackData.yml"), "UTF8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        if (defConfigStream != null)
        {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            trackData.setDefaults(defConfig);
        }
    }

    public FileConfiguration getTrackData()
    {
        if (trackData == null)
        {
            reloadTrackData();
        }
        return trackData;
    }

    public void saveTrackData()
    {
        if (trackData == null || trackDataFile == null)
        {
            return;
        }
        try
        {
            getTrackData().save(trackDataFile);
        }
        catch (IOException ex)
        {
            EquestrianDash.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + trackData, ex);
        }
    }

    public String getDisplayName()
    {
        return getTrackData().getString("TrackName");
    }

    private File itemBoxDataFile;
    private FileConfiguration itemBoxData;

    public void initializeItemBoxData()
    {
        reloadItemBoxData();

        if (itemBoxDataFile.exists())
        {
            itemBoxDataFile.delete();
        }

        try
        {
            itemBoxData.save(itemBoxDataFile);
            itemBoxData.options().copyDefaults(true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        saveItemBoxData();

    }

    public void reloadItemBoxData()
    {
        if (itemBoxDataFile == null)
        {
            itemBoxDataFile = new File(EquestrianDash.plugin.getDataFolder() + "/Tracks/" + world.getName(), "ItemBoxData.yml");
        }

        itemBoxData = YamlConfiguration.loadConfiguration(itemBoxDataFile);

        // Look for defaults in the jar
        Reader defConfigStream = null;
        try
        {
            defConfigStream = new InputStreamReader(EquestrianDash.plugin.getResource("ItemBoxData.yml"), "UTF8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        if (defConfigStream != null)
        {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            itemBoxData.setDefaults(defConfig);
        }
    }

    public FileConfiguration getItemBoxData()
    {
        if (itemBoxData == null)
        {
            reloadItemBoxData();
        }
        return itemBoxData;
    }

    public void saveItemBoxData()
    {
        if (itemBoxData == null || itemBoxDataFile == null)
        {
            return;
        }
        try
        {
            getItemBoxData().save(itemBoxDataFile);
        }
        catch (IOException ex)
        {
            EquestrianDash.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + itemBoxData, ex);
        }
    }

    public List<ItemBox> getItemBoxes()
    {
        return itemBoxes;
    }
}
