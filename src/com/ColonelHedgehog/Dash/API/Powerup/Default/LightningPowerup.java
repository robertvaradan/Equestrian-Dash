package com.ColonelHedgehog.Dash.API.Powerup.Default;

import com.ColonelHedgehog.Dash.API.Entity.Racer;
import com.ColonelHedgehog.Dash.API.Powerup.Powerup;
import com.ColonelHedgehog.Dash.Core.EquestrianDash;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ColonelHedgehog on 11/14/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class LightningPowerup implements Powerup
{

    @Override
    public ItemStack getItem()
    {
        ItemStack icon = new ItemStack(Material.valueOf(EquestrianDash.plugin.getConfig().getString("Powerups.Lightning.Material")), EquestrianDash.plugin.getConfig().getInt("Powerups.Lightning.Amount"));
        ItemMeta im = icon.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', EquestrianDash.plugin.getConfig().getString("Powerups.Lightning.Title")));
        icon.setItemMeta(im);

        return icon;
    }

    private String getMessage()
    {
        return EquestrianDash.Prefix + "§aYou used a " + this.getItem().getItemMeta().getDisplayName() + "§a!";
    }

    private ArrayList<Location> getLine(Location start, double range, int locations_between)
    {
        Vector dir = start.getDirection().multiply(new Vector(1, 0, 1));
        double step = range / locations_between;
        ArrayList<Location> temp = new ArrayList<>();

        for (int i = 0; i < locations_between; i++)
        {
            temp.add(dir.add(dir.clone().normalize().multiply(step)).toLocation(start.getWorld()));
        }

        return temp;
    }

    @Override
    public void doOnRightClick(final Racer racer, Action action)
    {
        racer.getRacer().getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999999, 999999999));
        if(racer.getPlayer().getItemInHand().getAmount() > 1)
        {
            ItemStack is = racer.getPlayer().getItemInHand();
            is.setAmount(is.getAmount() - 1);
            racer.getPlayer().setItemInHand(is);
        }
        else
        {
            racer.getPlayer().getInventory().clear();
        }

        FileConfiguration config = EquestrianDash.plugin.getConfig();
        String path = "Powerups.Lightning.";
        final double force = config.getDouble(path + "Force");
        final int speed = config.getInt(path + "Speed");
        final int range = config.getInt(path + "Range");
        final int offset = config.getInt(path + "YOffset");
        racer.getPlayer().sendMessage(getMessage());

        Location pos = racer.getPlayer().getEyeLocation();
        pos.setDirection(pos.getDirection().multiply(new Vector(1, 0, 1)));

        BlockIterator iterator = new BlockIterator(pos, offset, range);

        final List<Location> line = new ArrayList<>();

        while(iterator.hasNext())
        {
            line.add(iterator.next().getLocation());
        }

        new BukkitRunnable()
        {
            int index = 0;
            @Override
            public void run()
            {
                if(index == range)
                {
                    cancel();
                    racer.getPlayer().removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                    return;
                }

                Location loc = line.get(index);
                loc.getWorld().strikeLightning(loc);
                loc.getWorld().createExplosion(loc, (float) force);
                index++;
            }
        }.runTaskTimer(EquestrianDash.plugin, 0, speed);
    }

    @Override
    public void doOnLeftClick(Racer racer, Action action)
    {

    }

    @Override
    public void doOnDrop(Racer racer, Item dropped)
    {
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
        FileConfiguration config = EquestrianDash.plugin.getConfig();
        String path = "Powerups.Lightning.";
        final double force = config.getDouble(path + "Force");

        racer.getPlayer().getWorld().strikeLightning(racer.getPlayer().getLocation());
        racer.getPlayer().getWorld().createExplosion(racer.getPlayer().getLocation(), (float) force);
    }

    @Override
    public double getChance(int rank)
    {
        return rank / EquestrianDash.plugin.getConfig().getDouble("Powerups.Lightning.Chance");
    }

    @Override
    public List<ActionType> cancelledEvents()
    {
        List<ActionType> list = new ArrayList<>();
        list.add(ActionType.ALL);
        return list;
    }
}
