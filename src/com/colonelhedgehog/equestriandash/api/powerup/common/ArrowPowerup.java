package com.colonelhedgehog.equestriandash.api.powerup.common;

import com.colonelhedgehog.equestriandash.api.entity.Racer;
import com.colonelhedgehog.equestriandash.api.powerup.Powerup;
import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import com.colonelhedgehog.equestriandash.utilities.TrackTask;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ColonelHedgehog on 11/11/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class ArrowPowerup implements Powerup
{

    @Override
    public ItemStack getItem()
    {
        ItemStack icon = new ItemStack(Material.getMaterial(EquestrianDash.plugin.getConfig().getString("Powerups.Arrow.Material")));
        ItemMeta im = icon.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', EquestrianDash.plugin.getConfig().getString("Powerups.Arrow.Title")));
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
        Arrow a = racer.getPlayer().getWorld().spawnArrow(racer.getPlayer().getEyeLocation(), racer.getPlayer().getEyeLocation().getDirection(), 3, 3);
        a.setShooter(racer.getPlayer());
        double minAngle = 6.2831853071795862D;
        Entity minEntity = null;
        Player player = racer.getPlayer();
        for (@SuppressWarnings("rawtypes") Iterator iterator = player.getNearbyEntities(64D, 64D, 64D).iterator(); iterator.hasNext(); )
        {
            Entity entity = (Entity) iterator.next();
            if (player.hasLineOfSight(entity) && (entity instanceof LivingEntity) && !entity.isDead())
            {
                Vector toTarget = entity.getLocation().toVector().clone().subtract(player.getLocation().toVector());
                double angle = a.getVelocity().angle(toTarget);
                if (angle < minAngle)
                {
                    minAngle = angle;
                    minEntity = entity;
                }
            }
        }

        if (minEntity != null)
        {
            new TrackTask(a, (LivingEntity) minEntity, EquestrianDash.plugin);
        }

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
        return (rank / EquestrianDash.plugin.getConfig().getDouble("Powerups.Arrow.Chance")); // Chance that when we hit an item-box, this will be an option.
    }

    @Override
    public List<ActionType> cancelledEvents()
    {
        List<ActionType> actions = new ArrayList<>();
        actions.add(ActionType.ALL);
        // ALL types of actions will be cancelled.
        return actions;
    }
}
