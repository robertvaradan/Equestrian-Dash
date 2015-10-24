/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.colonelhedgehog.equestriandash.utilities;

/**
 *
 * @author Robert
 */

import com.colonelhedgehog.equestriandash.core.EquestrianDash;
import org.bukkit.Sound;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

@SuppressWarnings("unused")
public class TrackTask extends BukkitRunnable
{

    private static final double MaxRotationAngle = 0.12D;
    private static final double TargetSpeed = 1.3999999999999999D;
    Entity arrow;
    LivingEntity target;
    boolean c = false;
    double m;

    public TrackTask(Entity arrow, LivingEntity target, Plugin plugin)
    {
        this.arrow = arrow;
        this.target = target;
        runTaskTimer(plugin, 1L, 1L);
        if(arrow instanceof Chicken)
        {
            c = true;
        }
        m = c ? EquestrianDash.plugin.getConfig().getDouble("Powerups.Chicken.Multiplier") : 0;
    }

    @Override
    public void run()
    {
        if(target == null)
        {
            if (!arrow.isDead())
            {
                return;
            }
        }
        double speed = arrow.getVelocity().length();
        if ((!c && arrow.isOnGround()) || arrow.isDead() || target.isDead())
        {
            cancel();
            return;
        }
        Vector toTarget = target.getLocation().clone()
                .add(new Vector(c ? m : 0.0D, 0.5D, c ? m : 0.0D))
                .subtract(arrow.getLocation()).toVector();
        Vector dirVelocity = arrow.getVelocity().clone().normalize();
        Vector dirToTarget = toTarget.clone().normalize();
        double angle = dirVelocity.angle(dirToTarget);
        double newSpeed = 0.90000000000000002D * speed + 0.13999999999999999D;
        if ((target instanceof Player)
                && arrow.getLocation().distance(target.getLocation()) < 8D)
        {
            Player player = (Player) target;
            if (player.isBlocking())
            {
                newSpeed = speed * 0.59999999999999998D;
            }
        }
        Vector newVelocity;
        if (angle < 0.12D)
        {
            newVelocity = dirVelocity.clone().multiply(newSpeed);
        }
        else
        {
            Vector newDir = dirVelocity.clone()
                    .multiply((angle - 0.12D) / angle)
                    .add(dirToTarget.clone().multiply(0.12D / angle));
            newDir.normalize();
            newVelocity = newDir.clone().multiply(newSpeed);
        }
        arrow.setVelocity(newVelocity.add(new Vector(0.0D,
                0.029999999999999999D, 0.0D)));

        if(c)
        {
            arrow.getWorld().playSound(arrow.getLocation(), Sound.CHICKEN_HURT, 0.5F, 1.5F);
            ((Chicken) arrow).damage(0);
        }
    }
}
