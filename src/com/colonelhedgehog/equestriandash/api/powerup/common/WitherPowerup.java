package com.colonelhedgehog.equestriandash.api.powerup.common;

import com.colonelhedgehog.equestriandash.api.entity.Racer;
import com.colonelhedgehog.equestriandash.api.powerup.Powerup;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ColonelHedgehog on 11/14/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class WitherPowerup implements Powerup
{

    @Override
    public ItemStack getItem()
    {
        ItemStack icon = new ItemStack(Material.SKULL_ITEM, 1, (byte) 1); // Data values are evil though! D:
        ItemMeta im = icon.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', EquestrianDash.plugin.getConfig().getString("Powerups.Wither.Title")));
        icon.setItemMeta(im);

        return icon;
    }

    private String getMessage()
    {
        return EquestrianDash.Prefix + "§aYou used a " + this.getItem().getItemMeta().getDisplayName() + "§a!";
    }

    @Override
    public void doOnRightClick(Racer racer, Action action)
    {
        racer.getPlayer().sendMessage(getMessage());
        Location loc = racer.getPlayer().getEyeLocation();
        WitherSkull skull = (WitherSkull) loc.getWorld().spawnEntity(loc, EntityType.WITHER_SKULL);
        skull.setShooter(racer.getPlayer());
        skull.setVelocity(loc.getDirection().multiply(EquestrianDash.plugin.getConfig().getDouble("Powerups.Wither.SpeedMultiplier")));
        racer.getPlayer().getInventory().clear();
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

    }

    @Override
    public double getChance(int rank)
    {
        return rank / EquestrianDash.plugin.getConfig().getDouble("Powerups.Wither.Chance");
    }

    @Override
    public List<ActionType> cancelledEvents()
    {
        List<ActionType> list = new ArrayList<>();
        list.add(ActionType.ALL);
        return list;
    }
}
