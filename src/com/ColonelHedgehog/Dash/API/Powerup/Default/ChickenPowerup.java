package com.ColonelHedgehog.Dash.API.Powerup.Default;

import com.ColonelHedgehog.Dash.API.Entity.Racer;
import com.ColonelHedgehog.Dash.API.Powerup.Powerup;
import com.ColonelHedgehog.Dash.Core.Main;
import com.ColonelHedgehog.Dash.Lib.TrackTask;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ColonelHedgehog on 12/28/14.
 * You have freedom to modify given sources. Please credit me as original author.
 * Keep in mind that this is not for sale.
 */
public class ChickenPowerup implements Powerup
{

    @Override
    public ItemStack getItem()
    {
        ItemStack icon = new ItemStack(Material.MONSTER_EGG, 1, (short) 93);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("Powerups.Chicken.Title")));
        icon.setItemMeta(meta);

        return icon;
    }

    private String getMessage()
    {
        return Main.Prefix + "§aYou used a " + this.getItem().getItemMeta().getDisplayName() + "§a!";
    }

    @Override
    public void doOnRightClick(Racer racer, Action action)
    {
        Location loc = racer.getPlayer().getEyeLocation();
        final Chicken c = (Chicken) loc.getWorld().spawnEntity(loc, EntityType.CHICKEN);
        final int maxcount = Main.plugin.getConfig().getInt("Powerups.Chicken.Delay") / 20;
        racer.getPlayer().sendMessage(getMessage());
        double minAngle = 6.2831853071795862D;
        Entity minEntity = null;
        Player player = racer.getPlayer();

        new BukkitRunnable()
        {
            int count = Main.plugin.getConfig().getInt("Powerups.Chicken.DelayInSeconds");

            @Override
            public void run()
            {
                if (count <= 0 || c.isDead())
                {
                    if(count <= 0)
                    {
                        c.addPotionEffect(new PotionEffect(PotionEffectType.HARM, 10, 999999999));
                        c.getWorld().createExplosion(c.getLocation(), (float) Main.plugin.getConfig().getDouble("Powerups.Chicken.ExplosionSize"));
                        cancel();
                    }
                    else
                    {
                        cancel();
                    }
                }
                count--;
            }
        }.runTaskTimer(Main.plugin, 0, 20);

        for (@SuppressWarnings("rawtypes") Iterator iterator = player.getNearbyEntities(128D, 128D, 128D).iterator(); iterator.hasNext(); )
        {
            Entity entity = (Entity) iterator.next();
            if (player.hasLineOfSight(entity) && (entity instanceof LivingEntity) && !entity.isDead())
            {
                Vector toTarget = entity.getLocation().toVector().clone().subtract(player.getLocation().toVector()).multiply(Main.plugin.getConfig().getDouble("Powerups.Chickens.Multiplier"));
                double angle = c.getVelocity().angle(toTarget);
                if (angle < minAngle)
                {
                    minAngle = angle;
                    minEntity = entity;
                }
            }
        }

        if (minEntity != null)
        {
            System.out.println("BAD BOY CONTEST; YOU IN FIRST PLACE");
            new TrackTask(c, (LivingEntity) minEntity, Main.plugin);
        }

        racer.getPlayer().getInventory().clear();
    }

    @Override
    public void doOnLeftClick(Racer racer, Action action)
    {

    }

    private void chicken3(final Chicken c, final int mcount)
    {
        new BukkitRunnable()
        {
            int count = mcount;

            @Override
            public void run()
            {

                if (count == 0 && !c.isDead())
                {
                    c.getWorld().createExplosion(c.getLocation(), (float) Main.plugin.getConfig().getDouble("Powerups.Chicken.ExplosionSize"));
                    c.addPotionEffect(new PotionEffect(PotionEffectType.HARM, 10, 1000));
                    cancel(); //Cancels the timer
                }
                else if (count != 0 && !c.isDead())
                {
                    c.getWorld().playSound(c.getLocation(), Sound.CLICK, 3, 1.5F);
                    count--;
                }
            }

        }.runTaskTimer(Main.plugin, 0L /* The amount of time until the timer starts */, 10L /*  The delay of each call */);
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
        return rank / Main.plugin.getConfig().getInt("Powerups.Chicken.Chance");
    }

    @Override
    public List<ActionType> cancelledEvents()
    {
        List<ActionType> actions = new ArrayList<>();
        actions.add(ActionType.RIGHT_CLICK);
        return actions;
    }
}
