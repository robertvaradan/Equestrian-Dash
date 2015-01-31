package com.ColonelHedgehog.Dash.API.Powerup.ItemBox;

import com.ColonelHedgehog.Dash.Core.EquestrianDash;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Created by ColonelHedgehog on 12/26/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class ItemBoxRegistry
{
    private FileConfiguration boxData;
    private File boxDataFile;

    private HashMap<World, List<ItemBox>> wbox;

    public ItemBoxRegistry(ItemBox... boxes)
    {
        wbox = new HashMap<>();
        HashMap<World, List<ItemBox>> wbox = new HashMap<>();
        for(World w : Bukkit.getWorlds())
        {
            List<ItemBox> bx = new ArrayList<>();
            for(ItemBox box : boxes)
            {
                if(box.getLocation().getWorld().equals(w))
                {
                    bx.add(box);
                }
            }

            wbox.put(w, bx);
        }

        this.wbox.putAll(wbox);
    }

    public void register(ItemBox ib, boolean save)
    {
        if(ib == null)
        {
            return;
        }
        List<ItemBox> bx = !wbox.containsKey(ib.getLocation().getWorld()) || wbox.get(ib.getLocation().getWorld()).isEmpty() ? new ArrayList<ItemBox>() : wbox.get(ib.getLocation().getWorld());
        bx.add(ib);

        wbox.put(ib.getLocation().getWorld(), bx);
        HashMap<String, List<String>> serialized = serialize(wbox);

        if(!save)
        {
            return;
        }

        for (Map.Entry<String, List<String>> entry : serialized.entrySet())
        {
            getBoxData().set(entry.getKey(), entry.getValue());
        }
        saveBoxData();
    }

    private HashMap<String, List<String>> serialize(HashMap<World, List<ItemBox>> wbox)
    {
        HashMap<String, List<String>> map = new HashMap<>();

        for(Map.Entry<World, List<ItemBox>> entry : wbox.entrySet())
        {
            List<String> boxes = new ArrayList<>();
            for(ItemBox box : entry.getValue())
            {
                boxes.add(box.getLocation().getBlockX() + "," + box.getLocation().getBlockY() + "," + box.getLocation().getBlockZ());
            }
            map.put(entry.getKey().getName(), boxes);
        }

        return map;
    }

    public void deregister(ItemBox ib, boolean save)
    {
        List<ItemBox> bx = wbox.get(ib.getLocation().getWorld());
        bx.remove(ib);
        ib.despawn();

        wbox.put(ib.getLocation().getWorld(), bx);
        HashMap<String, List<String>> serialized = serialize(wbox);

        if (!save)
        {
            return;
        }

        for (Map.Entry<String, List<String>> entry : serialized.entrySet())
        {
            getBoxData().set(entry.getKey(), entry.getValue());
        }
        saveBoxData();

    }

    public List<ItemBox> getByWorld(World w)
    {
        return wbox.get(w);
    }

    public ItemBox getByLocation(Location loc)
    {
        if(wbox.isEmpty())
        {
            return null;
        }

        for(Map.Entry<World, List<ItemBox>> entry : wbox.entrySet())
        {
            if(loc.getWorld().equals(entry.getKey()))
            {
                for (ItemBox ib : entry.getValue())
                {
                    if (ib.getLocation().getBlock().getLocation().equals(loc.getBlock().getLocation()))
                    {
                        return ib;
                    }
                }
            }
        }

        return null;
    }

    public void initialize()
    {
        reloadBoxData();

        if (boxDataFile.exists())
        {
            boxDataFile.delete();
        }

        try
        {
            boxData.save(boxDataFile);
            boxData.options().copyDefaults(true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        saveBoxData();

    }

    public void reloadBoxData()
    {
        if (boxDataFile == null)
        {
            boxDataFile = new File(EquestrianDash.plugin.getDataFolder(), "ItemBoxes.yml");
        }

        boxData = YamlConfiguration.loadConfiguration(boxDataFile);

        // Look for defaults in the jar
        Reader defConfigStream = null;
        try
        {
            defConfigStream = new InputStreamReader(EquestrianDash.plugin.getResource("ItemBoxes.yml"), "UTF8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        if (defConfigStream != null)
        {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            boxData.setDefaults(defConfig);
        }
    }

    public FileConfiguration getBoxData()
    {
        if (boxData == null)
        {
            reloadBoxData();
        }
        return boxData;
    }

    public void saveBoxData()
    {
        if (boxData == null || boxDataFile == null)
        {
            return;
        }
        try
        {
            getBoxData().save(boxDataFile);
        }
        catch (IOException ex)
        {
            EquestrianDash.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + boxData, ex);
        }
    }
}
