package com.colonelhedgehog.equestriandash.api.track;

import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import com.colonelhedgehog.equestriandash.utilities.LocationUtils;
import com.colonelhedgehog.equestriandash.utilities.Reflection;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;

public class Marker
{
    private EquestrianDash plugin = EquestrianDash.getInstance();

    private Location location;
    private int number;
    private Track track;
    private MarkerHandler markerHandler;
    private boolean visualized;
    private ArmorStand hologram;

    public Marker(Location location, int number)
    {
        this.number = number;
        this.location = location;
        this.track = plugin.getTrackRegistry().getTrackByWorld(location.getWorld());
        this.markerHandler = track.getMarkerHandler();
        this.visualized = false;
    }

    public Track getTrack()
    {
        return track;
    }

    public Location getLocation()
    {
        return location;
    }

    public MarkerHandler getMarkerHandler()
    {
        return markerHandler;
    }

    public boolean getVisualized()
    {
        return visualized;
    }

    public void setVisualized(boolean visualized)
    {
        setVisualized(visualized, -1);
    }

    public void setVisualized(boolean visualized, int time)
    {
        this.visualized = visualized;

        boolean nms = plugin.getTrackRegistry().getTrackByWorld(getLocation().getWorld()).getTrackData().getBoolean("NMS.Enabled");
        if(visualized)
        {
            BukkitRunnable runnable = new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    if(getVisualized())
                    {
                        setVisualized(false);
                    }
                }
            };

            final Location location = getLocation().clone();
            Location loc = location.add(0.5, -0.5, 0.5);

            if(!loc.getChunk().isLoaded())
            {
                loc.getChunk().load();
            }

            String serialized = LocationUtils.serialize(getLocation().clone());

            ItemStack itemstack = new ItemStack(Material.BEACON);
            ItemMeta meta = itemstack.getItemMeta();
            meta.setLore(Collections.singletonList(serialized)); // Keeps item from merging with other nearby items.
            itemstack.setItemMeta(meta);

            Item item = loc.getWorld().dropItem(loc, itemstack);
            item.setMetadata("noPickup", new FixedMetadataValue(plugin, true));
            item.setMetadata("MarkerLocation", new FixedMetadataValue(plugin, true));

            this.hologram = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);

            if(nms)
            {
                try
                {
                    Class<?> CraftArmorStand = Reflection.getOBC("entity.CraftArmorStand"); // Gets the CraftBukkit class
                    Class<?> EntityArmorStand = Reflection.getNMSClass("EntityArmorStand"); // Gets the NMS class
                    Object CASInstance = CraftArmorStand.cast(hologram);
                    Method getHandleMethod = CraftArmorStand.getMethod("getHandle");
                    Object handle = getHandleMethod.invoke(CASInstance);
                    Method setGravityMethod = EntityArmorStand.getMethod("setGravity", boolean.class);
                    setGravityMethod.invoke(handle, true);
                }
                catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e)
                {
                    plugin.getLogger().severe("NMS handling failed! Unsuccessfully set gravity for armor stand.");
                    e.printStackTrace();
                }
            }
            hologram.setMetadata("MarkerNumber", new FixedMetadataValue(plugin, getNumber()));
            hologram.setMetadata("MarkerLocation", new FixedMetadataValue(plugin, serialized));

            hologram.setVisible(false);
            hologram.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999999, 999999999));

            hologram.setCustomNameVisible(true);
            hologram.setCustomName("§9Marker §b" + getNumber());

            hologram.setPassenger(item);
            // Disable gravity I guess?

            if(time > -1)
            {
                runnable.runTaskLater(plugin, time);
            }
        }
        else
        {
            //Bukkit.broadcastMessage("Starting to set vis to false for marker #" + getNumber());
            ArmorStand hologram = getHologram();

            if(!getLocation().getChunk().isLoaded())
            {
                getLocation().getChunk().load();
            }

            if(hologram == null)
            {
                //Bukkit.broadcastMessage("§c- Hologram is null, cancel!");
                return;
            }

            //Bukkit.broadcastMessage("§a- Hologram is not null! Continue!");

            if(hologram.isDead())
            {
                //Bukkit.broadcastMessage("§c- Hologram is dead, cancel!");
                return;
            }

            //Bukkit.broadcastMessage("§a- Hologram is not dead! Continue!");

            if(hologram.getPassenger() != null)
            {
                //Bukkit.broadcastMessage("§a- Hologram passenger is not null, DELETE!");
                hologram.getPassenger().remove();
            }

            hologram.remove();


            //Bukkit.broadcastMessage("§b- Hologram is removed! DONE!");
        }
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public void save()
    {
        Track track = plugin.getTrackRegistry().getTrackByWorld(getLocation().getWorld());
        MarkerHandler markerHandler = track.getMarkerHandler();
        FileConfiguration config = markerHandler.getMarkerConfig();
        config.set("MarkerPositions." + LocationUtils.serialize(getLocation()) + ".Number", getNumber());
        markerHandler.saveMarkerConfig();
        markerHandler.reloadMarkerConfig();
    }

    public void delete()
    {
        setVisualized(false);
        Track track = plugin.getTrackRegistry().getTrackByWorld(getLocation().getWorld());
        MarkerHandler markerHandler = track.getMarkerHandler();
        FileConfiguration config = markerHandler.getMarkerConfig();
        config.set("MarkerPositions." + LocationUtils.serialize(getLocation()), null);
        markerHandler.saveMarkerConfig();
        markerHandler.reloadMarkerConfig();
    }

    public ArmorStand getHologram()
    {
        if(hologram == null)
        {
            for(ArmorStand armorStand : track.getWorld().getEntitiesByClass(ArmorStand.class))
            {
                if(armorStand.hasMetadata("MarkerLocation"))
                {
                    if(armorStand.getMetadata("MarkerLocation").get(0).asString().equals(LocationUtils.serialize(location)))
                    {
                        hologram = armorStand;
                        break;
                    }
                }
            }
        }
        
        return hologram;
    }
}
