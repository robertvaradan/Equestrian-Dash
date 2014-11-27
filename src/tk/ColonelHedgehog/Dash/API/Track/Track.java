package tk.ColonelHedgehog.Dash.API.Track;

import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
import tk.ColonelHedgehog.Dash.Core.Main;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
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

    public Track(World world)
    {
        this.world = world;
        this.reloadTrackData();
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

    }
    public void reloadTrackData()
    {
        if (trackDataFile == null)
        {
            trackDataFile = new File(Main.plugin.getDataFolder() + "/Tracks/" + world.getName(), "TrackData.yml");
        }
        trackData  = YamlConfiguration.loadConfiguration(trackDataFile);

        // Look for defaults in the jar
        Reader defConfigStream = null;
        try
        {
            defConfigStream = new InputStreamReader(Main.plugin.getResource("TrackData.yml"), "UTF8");
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
            Main.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + trackData, ex);
        }
    }

    public String getDisplayName()
    {
        return getTrackData().getString("TrackName");
    }
}
