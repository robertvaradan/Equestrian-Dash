package com.ColonelHedgehog.Dash.API.Powerup.Default;

import com.ColonelHedgehog.Dash.API.Powerup.Powerup;
import com.ColonelHedgehog.Dash.Core.EquestrianDash;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import com.ColonelHedgehog.Dash.API.Entity.Racer;
import com.ColonelHedgehog.Dash.Core.GarbageControl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ColonelHedgehog on 11/13/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class IcePowerup implements Powerup
{

    @Override
    public ItemStack getItem()
    {
        ItemStack icon = new ItemStack(Material.getMaterial(EquestrianDash.plugin.getConfig().getString("Powerups.Ice.Material")));
        ItemMeta iconMeta = icon.getItemMeta();
        iconMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', EquestrianDash.plugin.getConfig().getString("Powerups.Ice.Title")));
        icon.setItemMeta(iconMeta);
        return icon;
    }

    @Override
    public void doOnRightClick(Racer racer, Action action)
    {
        if (EquestrianDash.plugin.getConfig().getBoolean("Powerups.Ice.ThrowAhead.Enabled"))
        {
            racer.getPlayer().sendMessage(EquestrianDash.Prefix + "§eDrop or left click with this item to use it!");
        }
        else
        {
            racer.getPlayer().sendMessage(EquestrianDash.Prefix + "§eDrop this item to use it!");
        }
    }

    @Override
    public void doOnLeftClick(Racer racer, Action action)
    {
        if(EquestrianDash.plugin.getConfig().getBoolean("Powerups.Ice.ThrowAhead.Enabled"))
        {
            Location loc = racer.getPlayer().getEyeLocation();
            final Item dropped = loc.getWorld().dropItem(loc, this.getItem());
            dropped.setVelocity(loc.getDirection().multiply(EquestrianDash.plugin.getConfig().getDouble("Powerups.Ice.ThrowAhead.Multiplier")));

            racer.getPlayer().sendMessage(getMessage());
            dropped.getWorld().playSound(dropped.getLocation(), Sound.FIZZ, 3, 0);

            new BukkitRunnable()
            {

                @Override
                public void run()
                {
                    // Ice thingy
                    if (!dropped.isDead())
                    {
                        createIceShardThings(dropped.getLocation());
                        dropped.getWorld().playSound(dropped.getLocation(), Sound.GLASS, 3, 1);
                        dropped.remove();
                    }

                }


            }.runTaskLater(EquestrianDash.plugin, EquestrianDash.plugin.getConfig().getLong("Powerups.Ice.ThrowAhead.DelayInTicks"));
            racer.getPlayer().getInventory().clear();

        }
    }

    private String getMessage()
    {
        return EquestrianDash.Prefix + "§aYou used a " + this.getItem().getItemMeta().getDisplayName() + "§a!";
    }

    @Override
    public void doOnDrop(Racer racer, final Item dropped)
    {
        racer.getPlayer().sendMessage(getMessage());
        dropped.getWorld().playSound(dropped.getLocation(), Sound.FIZZ, 3, 0);

        new BukkitRunnable()
        {

            @Override
            public void run()
            {
                   // Ice thingy
                if(!dropped.isDead())
                {
                    createIceShardThings(dropped.getLocation());
                    dropped.getWorld().playSound(dropped.getLocation(), Sound.GLASS, 3, 1);
                    dropped.remove();
                }

            }


        }.runTaskLater(EquestrianDash.plugin, EquestrianDash.plugin.getConfig().getLong("Powerups.Ice.DelayInTicks"));
    }

    @SuppressWarnings("deprecation") // pls nu hurt mi
    private void createIceShardThings(final Location loc2)
    {
        int sh = EquestrianDash.plugin.getConfig().getInt("Powerups.Ice.Sphere.Height");
        int sr = EquestrianDash.plugin.getConfig().getInt("Powerups.Ice.Sphere.Radius");

        /*System.out.println("HEIGHT: " + sh);
        System.out.println("RADIUS: " + sr);*/
        final List<Location> ice = getCircle(loc2, sr, sh, true, true, 0);

        for(Location fin : ice)
        {
            fin.getBlock().setType(new Random().nextInt(2) == 1 ? Material.ICE : Material.PACKED_ICE);
            fin.getWorld().playEffect(fin, Effect.STEP_SOUND, Material.SNOW.getId());
            GarbageControl.DespawningIce.add(fin);
            //System.out.println("Creating ice at " + fin.toString());
        }

        new BukkitRunnable()
        {

            @Override
            public void run()
            {
                GarbageControl.DespawningIce.removeAll(ice);
                for(Location loc : ice)
                {
                    loc.getBlock().breakNaturally(new ItemStack(Material.AIR));
                }
            }
        }.runTaskLater(EquestrianDash.plugin, EquestrianDash.plugin.getConfig().getLong("Powerups.Ice.DespawnTime"));

    }

    @Override
    public void doOnRightClickRacer(Racer racer, Racer clicked)
    {

    }

    @Override
    public void doOnLeftClickRacer(Racer racer, Racer clicked)
    {

    }

    @Override
    public void doOnPickup(Racer racer, Racer dropper, Item item)
    {
        dropper.getPlayer().sendMessage(EquestrianDash.Prefix + "§e" + racer.getPlayer().getName() + " §6recovered your " + EquestrianDash.plugin.getConfig().getString("Powerups.Ice.Title") + "§6!");
    }

    @Override
    public double getChance(int rank)
    {
        return (8 - rank) / EquestrianDash.plugin.getConfig().getDouble("Powerups.Ice.Chance");

    }

    @Override
    public List<ActionType> cancelledEvents()
    {
        List<ActionType> list = new ArrayList<>();
        list.add(ActionType.LEFT_CLICK);
        list.add(ActionType.LEFT_CLICK_ENTITY);
        list.add(ActionType.RIGHT_CLICK);
        list.add(ActionType.RIGHT_CLICK_ENTITY);
        return list;
    }

    @SuppressWarnings("deprecation")
    private List<Location> getCircle(Location loc, Integer r, Integer h, Boolean hollow, Boolean sphere, int plus_y)
    {
        List<Location> circleblocks = new ArrayList<>();
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();
        for (int x = cx - r; x <= cx + r; x++)
            for (int z = cz - r; z <= cz + r; z++)
                for (int y = (sphere ? cy - r : cy); y < (sphere ? cy + r : cy + h); y++)
                {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r * r && !(hollow && dist < (r - 1) * (r - 1)))
                    {
                        Location l = new Location(loc.getWorld(), x, y + plus_y, z);
                        if(l.getBlock() == null || l.getBlock().getType() == null || l.getBlock().getType() == Material.AIR)
                        {
                            circleblocks.add(l);
                        }
                    }
                }

        return circleblocks;
    }

}
