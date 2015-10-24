package com.colonelhedgehog.equestriandash.assets.handlers;

import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import com.colonelhedgehog.equestriandash.utilities.Reflection;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PropertyHandler
{
    private EquestrianDash plugin = EquestrianDash.getInstance();

    public PropertyHandler()
    {

    }

    public void setNMSHorseSpeed(Horse horse, double speed)
    {
        try
        {
            Class<?> genericAttributesClass = Reflection.getNMSClass("GenericAttributes");
            Field genericAttributeD = genericAttributesClass.getDeclaredField("MOVEMENT_SPEED");
            genericAttributeD.setAccessible(true);
            Class<?> iAttribute = Reflection.getNMSClass("IAttribute");
            Class<?> craftLivingEntityClass = Reflection.getOBC("entity.CraftLivingEntity");
            Object craftLivingEntity = craftLivingEntityClass.cast(horse);
            //System.out.println("ALL CLE METHODS: " + Arrays.toString(craftLivingEntity.getClass().getMethods()));
            Method getHandle = craftLivingEntity.getClass().getMethod("getHandle");
            Object entityLiving = getHandle.invoke(craftLivingEntity);
            Method getAttributeInstance = entityLiving.getClass().getMethod("getAttributeInstance", iAttribute);
            Object attributeInstance = getAttributeInstance.invoke(entityLiving, genericAttributeD.get(null)); // ERROR POINT
            Method setValue = attributeInstance.getClass().getMethod("setValue", double.class);
            setValue.invoke(attributeInstance.getClass().cast(attributeInstance), speed);


             /*Equivalent of:

            ((CraftLivingEntity) horse).getHandle().getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(speed);
            */
        }
        catch (NoSuchFieldException | IllegalAccessException | InvocationTargetException | ClassNotFoundException | NoSuchMethodException e)
        {
            plugin.getLogger().severe("NMS handling failed! Looks like this plugin is out of date!");
            e.printStackTrace();
        }
    }

    public void disableMovement(final Player p, Horse h)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                FileConfiguration data = plugin.getGameHandler().getCurrentTrack().getTrackData();

                if (data.getBoolean("NMS.Enabled"))
                {
                    setNMSHorseSpeed(h, 0);
                }
                else
                {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999999, 999999999));
                    h.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999999, 999999999));
                }
            }
        }.runTaskLater(plugin, 1);
    }

    public void enableMovement(Player p)
    {
        Horse h = (Horse) p.getVehicle();
        FileConfiguration data = plugin.getGameHandler().getCurrentTrack().getTrackData();

        if (data.getBoolean("NMS.Enabled"))
        {
            setNMSHorseSpeed(h, data.getDouble("NMS.MaxHorseSpeed"));
        }
        else
        {
            p.removePotionEffect(PotionEffectType.SLOW);
            h.removePotionEffect(PotionEffectType.SLOW);
        }
    }

    public Horse generateHorse(final Player p, Location loc)
    {
        final Horse horse = (Horse) loc.getWorld().spawnEntity(loc, EntityType.HORSE);
        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));

        getHorseColor(p, horse, p.getMetadata("colorKey").get(0).asInt());
        getHorsePattern(p, horse, p.getMetadata("patternKey").get(0).asInt());

        FileConfiguration data = plugin.getGameHandler().getCurrentTrack().getTrackData();
        if (data.getBoolean("NMS.Enabled"))
        {
            setNMSHorseSpeed(horse, data.getDouble("NMS.MaxHorseSpeed"));
        }

        horse.teleport(loc);

        horse.setOwner(p);
        horse.setAdult();

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                horse.setPassenger(p);
            }
        }.runTaskLater(plugin, 1);

        return horse;
    }

    public void getHorseColor(Player p, Horse horse, int res)
    {

        if (res == 1 && p.hasPermission("equestriandash.horsecolors.white"))
        {
            horse.setColor(Horse.Color.WHITE);
        }
        else if (res == 2 && p.hasPermission("equestriandash.horsecolors.black"))
        {
            horse.setColor(Horse.Color.BLACK);
        }
        else if (res == 3 && p.hasPermission("equestriandash.horsecolors.brown"))
        {
            horse.setColor(Horse.Color.BROWN);
        }
        else if (res == 4 && p.hasPermission("equestriandash.horsecolors.chestnut"))
        {
            horse.setColor(Horse.Color.CHESTNUT);
        }
        else if (res == 5 && p.hasPermission("equestriandash.horsecolors.creamy"))
        {
            horse.setColor(Horse.Color.CREAMY);
        }
        else if (res == 6 && p.hasPermission("equestriandash.horsecolors.dark_brown"))
        {
            horse.setColor(Horse.Color.DARK_BROWN);
        }
        else if (res == 7 && p.hasPermission("equestriandash.horsecolors.gray"))
        {
            horse.setColor(Horse.Color.GRAY);
        }
        else
        {
            horse.setColor(Horse.Color.WHITE);
        }
    }

    public void getHorsePattern(Player p, Horse horse, int res)
    {
        if (res == 1 && p.hasPermission("equestriandash.horsestyles.black_dots"))
        {
            horse.setStyle(Horse.Style.BLACK_DOTS);
        }
        else if (res == 2 && p.hasPermission("equestriandash.horsestyles.none"))
        {
            horse.setStyle(Horse.Style.NONE);
        }
        else if (res == 3 && p.hasPermission("equestriandash.horsestyles.white"))
        {
            horse.setStyle(Horse.Style.WHITE);
        }
        else if (res == 4 && p.hasPermission("equestriandash.horsestyles.whitefield"))
        {
            horse.setStyle(Horse.Style.WHITEFIELD);
        }
        else if (res == 5 && p.hasPermission("equestriandash.horsestyles.white_Dots"))
        {
            horse.setStyle(Horse.Style.WHITE_DOTS);
        }
        else if (res == 6 && p.hasPermission("equestriandash.horsestyles.skeleton"))
        {
            horse.setVariant(Horse.Variant.SKELETON_HORSE);
        }
        else if (res == 7 && p.hasPermission("equestriandash.horsestyles.zombie"))
        {
            horse.setVariant(Horse.Variant.UNDEAD_HORSE);
        }
        else
        {
            horse.setStyle(Horse.Style.NONE);
        }
    }
}
