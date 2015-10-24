package com.colonelhedgehog.equestriandash.api.powerup.common;

import com.colonelhedgehog.equestriandash.api.entity.Racer;
import com.colonelhedgehog.equestriandash.api.powerup.Powerup;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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

    private EquestrianDash plugin = EquestrianDash.getInstance();

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
        ItemStack stack = racer.getPlayer().getItemInHand();
        ItemStack thro = racer.getPlayer().getItemInHand().clone();
        thro.setAmount(1);

        if(stack.getAmount() == 1)
        {
            racer.getPlayer().getInventory().clear();
            return;
        }

        stack.setAmount(stack.getAmount() - 1);

        racer.getPlayer().getInventory().setItem(0, stack);

        Item item = racer.getPlayer().getWorld().dropItem(racer.getPlayer().getLocation(), thro);
        item.setPickupDelay(0);

        racer.getPlayer().sendMessage(getMessage());
    }

    @Override
    public void doOnLeftClick(Racer racer, Action action)
    {
        ItemStack stack = racer.getPlayer().getItemInHand();
        final ItemStack thro = racer.getPlayer().getItemInHand().clone();
        thro.setAmount(1);

        racer.getPlayer().getInventory().clear();
        if(stack.getAmount() - 1 > 0)
        {
            stack.setAmount(stack.getAmount() - 1);
        }

        racer.getPlayer().getInventory().setItem(0, stack);

        Item item = racer.getPlayer().getWorld().dropItem(racer.getPlayer().getLocation(), thro);
        item.setVelocity(racer.getPlayer().getEyeLocation().getDirection().multiply(plugin.getConfig().getDouble("Powerups.Lightning.ThrowAheadMultiplier")));
        item.setPickupDelay(0);
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
        if(racer.getPlayer().equals(dropper.getPlayer()))
        {
            return;
        }

        FileConfiguration config = EquestrianDash.plugin.getConfig();
        String path = "Powerups.Lightning.";
        final double force = config.getDouble(path + "Force");

        racer.getPlayer().getWorld().strikeLightningEffect(racer.getPlayer().getLocation());
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
