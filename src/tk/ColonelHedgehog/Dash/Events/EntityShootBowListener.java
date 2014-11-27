/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tk.ColonelHedgehog.Dash.Events;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import tk.ColonelHedgehog.Dash.Core.Main;

/**
 * @author Robert
 */
public class EntityShootBowListener implements Listener
{
    public static Main plugin = Main.plugin;

    public static void createArrows(final Entity e, final Projectile p)
    {
        new BukkitRunnable()
        {

            @Override
            public void run()
            {

                if (e instanceof Player)
                {
                    Player pl = (Player) e;


                    // .-.

                    if (pl.hasMetadata("superCharged") && pl.getMetadata("superCharged").get(0).asBoolean() && !p.isOnGround() && !p.isDead())
                    {

                        Arrow a = pl.launchProjectile(Arrow.class);
                        a.setVelocity(pl.getEyeLocation().getDirection().multiply(3));

                    }
                    else
                    {
                        cancel(); //Cancels the timer
                    }
                }
            }
        }.runTaskTimer(plugin, 0L /* The amount of time until the timer starts */, 5L /*  The delay of each call */);

    }

    @SuppressWarnings("deprecation")
    public static void createWitherHeads(Player shooter, final Projectile p, final boolean charged)
    {
        WitherSkull a = p.getShooter().launchProjectile(WitherSkull.class);
        a.setVelocity(((Player) a.getShooter()).getEyeLocation().getDirection().multiply(3));
        a.setCharged(charged);
        ItemStack is = shooter.getInventory().getItemInHand();
        is.setAmount(is.getAmount() - 1);
        shooter.setItemInHand(is);
    }

    @EventHandler
    public void onShoot(final EntityShootBowEvent event)
    {
        final Entity e = event.getEntity();
        final Projectile p = (Projectile) event.getProjectile();

        new BukkitRunnable()
        {

            @Override
            public void run()
            {
                // .-.

                p.getWorld().playEffect(p.getLocation(), Effect.SMOKE, 5);
                if (event.getProjectile().isOnGround() || event.getProjectile().isDead())
                {
                    cancel(); //Cancels the timer
                    p.getWorld().playSound(p.getLocation(), Sound.FIZZ, 3, 1);
                }
            }


        }.runTaskTimer(plugin, 0L /* The amount of time until the timer starts */, 2L /*  The delay of each call */);

        createArrows(e, p);
    }


}
